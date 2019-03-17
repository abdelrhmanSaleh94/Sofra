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
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFoodData;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.ui.activity.AppHomeActivity;
import com.example.abdelrahmansaleh.sofra.ui.fragment.user.ItemFoodFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsFoodAdapter extends RecyclerView.Adapter<ItemsFoodAdapter.FoodViewHolder> {
    Context context;
    List<ItemsFoodData> foodDataList = new ArrayList<>();
    boolean user;
    public static ItemsFoodData itemsFoodDataSelected;

    public ItemsFoodAdapter(Context context, List<ItemsFoodData> foodDataList, boolean user) {
        this.context = context;
        this.foodDataList = foodDataList;
        this.user = user;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_food_card, parent, false );
        return new FoodViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Glide.with( context ).load( foodDataList.get( position ).getPhotoUrl() ).into( holder.itemFoodCardImage );
        holder.itemFoodCardName.setText( foodDataList.get( position ).getName() );
        holder.itemFoodCardDes.setText( foodDataList.get( position ).getDescription() );
        holder.itemFoodCardPrice.setText( foodDataList.get( position ).getPrice() + " EGP" );
        action( holder, position );

    }

    private void action(FoodViewHolder holder, final int position) {
        if (user == true) {
            holder.view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemsFoodDataSelected = foodDataList.get( position );
                    HelperMethod.replaceFragment( new ItemFoodFragment(), ((AppHomeActivity) context).getSupportFragmentManager(),
                            R.id.counterFrame, null, null );
                }
            } );
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return foodDataList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemFoodCardImage)
        ImageView itemFoodCardImage;
        @BindView(R.id.itemFoodCardName)
        TextView itemFoodCardName;
        @BindView(R.id.itemFoodCardDes)
        TextView itemFoodCardDes;
        @BindView(R.id.itemFoodCardPrice)
        TextView itemFoodCardPrice;
        private View view;

        public FoodViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }

    }
}
