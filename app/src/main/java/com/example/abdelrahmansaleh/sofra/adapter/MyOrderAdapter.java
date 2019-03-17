package com.example.abdelrahmansaleh.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.Item;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.OrderDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {
    Context context;
    List<OrderDatum> dataList = new ArrayList<>();
    int state;

    public MyOrderAdapter(Context context, List<OrderDatum> dataList, int state) {
        this.context = context;
        this.dataList = dataList;
        this.state = state;

    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.order_card, parent, false );
        return new MyOrderViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        OrderDatum datum = dataList.get( position );
        List<Item> items = dataList.get( position ).getItems();
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            nameBuilder.append( items.get( i ).getName() + " , " );
        }
        holder.orderCardTVName.setText( nameBuilder.toString() );
        holder.orderCardTVOrderNumber.setText( context.getResources().getString( R.string.orderNumber ) + " : " + datum.getId().toString() );
        Glide.with( context ).load( datum.getRestaurant().getPhotoUrl() ).into( holder.orderCardImageView );
        holder.orderCardTVPrice.setText( datum.getCost() );
        holder.orderCardTVDelivery.setText( datum.getDeliveryCost() );
        holder.orderCardTVTotalPrice.setText( datum.getTotal() );
        if (state!=1){
            holder.LinerCardItemButton.setVisibility( View.GONE );
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderCardImageView)
        ImageView orderCardImageView;
        @BindView(R.id.orderCardTVName)
        TextView orderCardTVName;
        @BindView(R.id.orderCardTVPrice)
        TextView orderCardTVPrice;
        @BindView(R.id.orderCardTVDelivery)
        TextView orderCardTVDelivery;
        @BindView(R.id.orderCardTVTotalPrice)
        TextView orderCardTVTotalPrice;
        @BindView(R.id.orderCardTVOrderNumber)
        TextView orderCardTVOrderNumber;
        @BindView(R.id.LinerCardItemButton)
        LinearLayout LinerCardItemButton;
        private View view;

        public MyOrderViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
