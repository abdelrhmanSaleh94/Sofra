package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.ItemFoodAdapterSwipe;
import com.example.abdelrahmansaleh.sofra.adapter.ItemsFoodAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.User.resetPassword.ResetPassword;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFood;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFoodData;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.Item;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.OnEndless;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.activity.restaurant.AddProductFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.LoginUser;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.API_TOKEN_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProductsFragment extends Fragment {


    @BindView(R.id.myProductFgRecyclerV)
    RecyclerView myProductFgRecyclerV;
    Unbinder unbinder;
    private ApiServices apiServices;
    private Integer maxPages;
    private ArrayList<ItemsFoodData> foodDataList;
    private ItemFoodAdapterSwipe foodAdapter;
    private String apiToken;

    public MyProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_products, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), API_TOKEN_USER );
        if (apiToken == null) {
            HelperMethod.replaceFragment( new LoginUser(), getActivity().getSupportFragmentManager(),
                    R.id.restaurantHomeActivityFrame, null, null );
            getActivity().finish();
        }
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        foodDataList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        myProductFgRecyclerV.setLayoutManager( manager );
        OnEndless onEndless = new OnEndless( manager, 5 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getFoods( current_page );
                }
            }
        };
        myProductFgRecyclerV.addOnScrollListener( onEndless );
        foodAdapter = new ItemFoodAdapterSwipe( getContext(), foodDataList, apiToken, getActivity() );
        myProductFgRecyclerV.setAdapter( foodAdapter );
        getFoods( 1 );
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.myProductFgBtnAddProduct)
    public void onViewClicked() {
        ItemFoodAdapterSwipe.itemsFoodDataEdit = null;
        HelperMethod.replaceFragment( new AddProductFragment(), getActivity().getSupportFragmentManager(),
                R.id.restaurantHomeActivityFrame, null, null );
    }

    private void getFoods(final int current_page) {
        apiServices.ITEMS_FOOD_CALL_REST( apiToken, current_page )
                .enqueue( new Callback<ItemsFood>() {
                    @Override
                    public void onResponse(Call<ItemsFood> call, Response<ItemsFood> response) {
                        if (response.body().getStatus() == 1) {
                            maxPages = response.body().getData().getLastPage();
                            foodDataList.addAll( response.body().getData().getData() );
                            foodAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemsFood> call, Throwable t) {

                    }
                } );
    }

}
