package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.MyOrderAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.Order;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.OrderDatum;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
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
public class CurrentOrderFragment extends Fragment {


    @BindView(R.id.currentOrderFrRecyclerView)
    RecyclerView currentOrderFrRecyclerView;
    Unbinder unbinder;
    private List<OrderDatum> orderListData;
    private ApiServices apiServices;
    private MyOrderAdapter orderAdapter;
    private int maxPage;

    public CurrentOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_current_order, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        orderListData = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        currentOrderFrRecyclerView.setLayoutManager( manager );
        OnEndless onEndless = new OnEndless( manager, 3 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    getOrderData( current_page );
                }
            }
        };
        currentOrderFrRecyclerView.addOnScrollListener( onEndless );
        orderAdapter = new MyOrderAdapter( getContext(), orderListData ,1 );
        currentOrderFrRecyclerView.setAdapter( orderAdapter );
        getOrderData( 1 );
        return view;
    }

    private void getOrderData(int page) {
        apiServices.ORDER_CALL( "quW3tUS7GVL5lv1BfAT0Orm4CXBtmRVREu3tCP6B5WebYsVaIQYdeoyg7yay", "current", page ).enqueue( new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    orderListData.addAll( response.body().getData().getData() );
                    orderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
