package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.ItemsFoodAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFood;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFoodData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.OnEndless;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {


    @BindView(R.id.FoodFragmentRecyclerView)
    RecyclerView foodFragmentRecyclerView;
    Unbinder unbinder;
    private ApiServices apiServices;
    private List<ItemsFoodData> foodDataList;
    private int maxPages;
    private ItemsFoodAdapter foodAdapter;

    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_food, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        String user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        foodDataList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        foodFragmentRecyclerView.setLayoutManager( manager );
        OnEndless onEndless = new OnEndless( manager, 5 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getFoods( current_page );
                }
            }
        };
        foodFragmentRecyclerView.addOnScrollListener( onEndless );
        if (user.equals( "user" )) {
            foodAdapter = new ItemsFoodAdapter( getContext(), foodDataList, true );
        } else {
            foodAdapter = new ItemsFoodAdapter( getContext(), foodDataList, false );
        }
        foodFragmentRecyclerView.setAdapter( foodAdapter );
        getFoods( 1 );
        return view;
    }

    private void getFoods(final int current_page) {
        apiServices.ITEMS_FOOD_CALL( String.valueOf( RestaurantDetailsFragment.id ), current_page )
                .enqueue( new Callback<ItemsFood>() {
                    @Override
                    public void onResponse(Call<ItemsFood> call, Response<ItemsFood> response) {
                        if (response.body().getStatus() == 1) {
                            maxPages = response.body().getData().getLastPage();
                            if (maxPages == current_page) {
                                Toast.makeText( getContext(), "EndPage", Toast.LENGTH_SHORT ).show();
                            }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
