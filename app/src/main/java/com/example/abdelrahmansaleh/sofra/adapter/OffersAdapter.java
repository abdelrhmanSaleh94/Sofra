package com.example.abdelrahmansaleh.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferListData;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferListsDtum;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferViewHolder> {
    Context context;
    List<OfferListData> offerList = new ArrayList<>();

    public OffersAdapter(Context context, List<OfferListData> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.offer_card, parent, false );
        return new OfferViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        OfferListData offerListData = offerList.get( position );
        Glide.with( context )
                .load( "http://ipda3.com/sofra/" + offerListData.getPhoto() )
                .into( holder.offerCardImageOffer );
        String startingDay = HelperMethod.formatDate( offerListData.getStartingAt() );
        String endingDay = HelperMethod.formatDate( offerListData.getEndingAt() );
        holder.offerCardTvDate.setText( startingDay + " : " + endingDay );
        holder.offerCardTvNameRest.setText( offerListData.getRestaurant().getName() );
        holder.offerCardTvPrice.setText( offerListData.getPrice() + " EGP " );
        holder.offerCardTvTitleOffer.setText( offerListData.getDescription() );
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offerCardImageOffer)
        ImageView offerCardImageOffer;
        @BindView(R.id.offerCardTvTitleOffer)
        TextView offerCardTvTitleOffer;
        @BindView(R.id.offerCardTvNameRest)
        TextView offerCardTvNameRest;
        @BindView(R.id.offerCardTvDate)
        TextView offerCardTvDate;
        @BindView(R.id.offerCardTvPrice)
        TextView offerCardTvPrice;
        private View view;

        public OfferViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
