package com.example.abdelrahmansaleh.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.Category;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.RestaurantListData;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.RestaurantsData;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.activity.AppHomeActivity;
import com.example.abdelrahmansaleh.sofra.ui.activity.restaurant.RestaurantHomeActivity;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.RestaurantID;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    Context context;
    List<RestaurantListData> restaurantsList = new ArrayList<>();
    Activity activity;

    public RestaurantListAdapter(Context context, List<RestaurantListData> restaurantsList, Activity activity) {
        this.context = context;
        this.restaurantsList = restaurantsList;
        this.activity = activity;
    }

    public RestaurantListAdapter(Context context, List<RestaurantListData> restaurantsList) {
        this.context = context;
        this.restaurantsList = restaurantsList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.restaurant_card, parent, false );
        return new RestaurantViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantListData restaurantsData = restaurantsList.get( position );
        Glide.with( context ).load( restaurantsData.getPhotoUrl() ).into( holder.restaurantCardImage );
        holder.restaurantCardTvName.setText( restaurantsData.getName() );
        holder.restaurantCardRatingBar.setRating( restaurantsData.getRate() );
        holder.restaurantCardTvDeliveryFee.setText( context.getResources().getString( R.string.deliveryFee ) + restaurantsData.getDeliveryCost() );
        holder.restaurantCardTvMin.setText( context.getResources().getString( R.string.minimum ) + restaurantsData.getMinimumCharger() );
        if (!restaurantsData.getAvailability().equals( "open" )) {
            holder.restaurantCardTvSates.setTextColor( context.getResources().getColor( R.color.red_error ) );
            holder.restaurantCardTvSates.setText( " مغلق" );
            holder.restaurantCardTvSates.setCompoundDrawablesWithIntrinsicBounds( null, null,
                    context.getResources().getDrawable( R.drawable.ic_close ), null );
        }
        StringBuilder categoryString = new StringBuilder();
        List<Category> categories = restaurantsData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            categoryString.append( categories.get( i ).getName() + "." );
        }
        holder.restaurantCardTvCategory.setText( categoryString.toString() );
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.restaurantCardImage)
        ImageView restaurantCardImage;
        @BindView(R.id.restaurantCardTvName)
        TextView restaurantCardTvName;
        @BindView(R.id.restaurantCardTvCategory)
        TextView restaurantCardTvCategory;
        @BindView(R.id.restaurantCardRatingBar)
        RatingBar restaurantCardRatingBar;
        @BindView(R.id.restaurantCardTvSates)
        TextView restaurantCardTvSates;
        @BindView(R.id.restaurantCardTvMin)
        TextView restaurantCardTvMin;
        @BindView(R.id.restaurantCardTvDeliveryFee)
        TextView restaurantCardTvDeliveryFee;

        public RestaurantViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = SharedPreferencesManger.LoadStringData( ((AppHomeActivity) context), VAL );
                    RestaurantDetailsFragment detailsFragment = new RestaurantDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt( RestaurantID, restaurantsList.get( getAdapterPosition() ).getId() );
                    detailsFragment.setArguments( bundle );
                    HelperMethod.replaceFragment( detailsFragment, ((AppHomeActivity) context).getSupportFragmentManager(), R.id.counterFrame, null, null
                    );
                }
            } );
        }
    }
}
