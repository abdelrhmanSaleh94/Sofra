package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.RestaurantListAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.RestaurantList;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.RestaurantListData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.OnEndless;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {


    @BindView(R.id.RestaurantsFragmentRvRestaurant)
    RecyclerView restaurantsFragmentRvRestaurant;
    Unbinder unbinder;
    private ApiServices apiServices;
    private int maxPages;
    private List<RestaurantListData> restaurantList;
    private RestaurantListAdapter adapter;

    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_restaurants, container, false );
        unbinder = ButterKnife.bind( this, view );
        HelperMethod.hideKeyboard( getActivity() );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        restaurantList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        restaurantsFragmentRvRestaurant.setLayoutManager( manager );
        OnEndless onEndless = new OnEndless( manager, 5 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getRestaurants( current_page );
                }
            }
        };
        restaurantsFragmentRvRestaurant.addOnScrollListener( onEndless );
        adapter = new RestaurantListAdapter( getContext(), restaurantList );
        restaurantsFragmentRvRestaurant.setAdapter( adapter );
        getRestaurants( 1 );
        return view;
    }

    private void getRestaurants(final int page) {
        apiServices.RESTAURANT_LIST_CALL( page ).enqueue( new Callback<RestaurantList>() {
            @Override
            public void onResponse(Call<RestaurantList> call, Response<RestaurantList> response) {
                if (response.body().getStatus() == 1) {
                    maxPages = response.body().getData().getLastPage();
                    restaurantList.addAll( response.body().getData().getData() );
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantList> call, Throwable t) {
                Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
