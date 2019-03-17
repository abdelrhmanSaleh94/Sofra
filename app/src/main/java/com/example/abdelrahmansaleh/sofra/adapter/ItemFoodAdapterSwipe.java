package com.example.abdelrahmansaleh.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.User.resetPassword.ResetPassword;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFoodData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.activity.restaurant.AddProductFragment;
import com.example.abdelrahmansaleh.sofra.ui.activity.restaurant.RestaurantHomeActivity;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.MyProductsFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFoodAdapterSwipe extends RecyclerView.Adapter<ItemFoodAdapterSwipe.FoodItemSwipeViewHolder> {
    Context context;
    List<ItemsFoodData> foodDataList = new ArrayList<>();
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
    String apiToken;
    Activity activity;
    public static ItemsFoodData itemsFoodDataEdit;
    public static int SwipeSend;

    public ItemFoodAdapterSwipe(Context context, List<ItemsFoodData> foodDataList, String apiToken, Activity activity) {
        this.context = context;
        this.foodDataList = foodDataList;
        this.apiToken = apiToken;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FoodItemSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.item_food_card_swipe, parent, false );
        return new FoodItemSwipeViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemSwipeViewHolder holder, int position) {
        ItemsFoodData itemsFoodData = foodDataList.get( position );
        viewBinderHelper.bind( holder.itemFoodCardSwipeParent, itemsFoodData.getId().toString() );
        holder.itemFoodCardSwipeName.setText( foodDataList.get( position ).getName() );
        holder.itemFoodCardSwipeDes.setText( foodDataList.get( position ).getDescription() );
        holder.itemFoodCardSwipePrice.setText( foodDataList.get( position ).getPrice() + " EGP" );
        Glide.with( context ).load( foodDataList.get( position ).getPhotoUrl() ).into( holder.itemFoodCardSwipeImage );

        action( holder, position );
    }

    private void action(FoodItemSwipeViewHolder holder, final int position) {
        holder.deleteButtonSwipe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Integer id = foodDataList.get( position ).getId();
                apiServices.CALL_DELETE_ITEM_FOOD( apiToken, id ).enqueue( new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                        if (response.body().getStatus() == 1) {
                            foodDataList.remove( position );
                            notifyDataSetChanged();
                            StyleableToast.makeText( context, response.body().getMsg(), R.style.delete ).show();

                        } else {
                            StyleableToast.makeText( context, response.body().getMsg(), R.style.Error ).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResetPassword> call, Throwable t) {

                    }
                } );

            }
        } );
        holder.editButtonSwipe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsFoodDataEdit = foodDataList.get( position );
                SwipeSend = 1;
                HelperMethod.replaceFragment( new AddProductFragment(), ((RestaurantHomeActivity) context).getSupportFragmentManager()
                        , R.id.restaurantHomeActivityFrame, null, null );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return foodDataList.size();
    }

    public class FoodItemSwipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.delete_buttonSwipe)
        ImageButton deleteButtonSwipe;
        @BindView(R.id.edit_buttonSwipe)
        ImageButton editButtonSwipe;
        @BindView(R.id.itemFoodCardSwipeImage)
        ImageView itemFoodCardSwipeImage;
        @BindView(R.id.itemFoodCardSwipeName)
        TextView itemFoodCardSwipeName;
        @BindView(R.id.itemFoodCardSwipeDes)
        TextView itemFoodCardSwipeDes;
        @BindView(R.id.itemFoodCardSwipePrice)
        TextView itemFoodCardSwipePrice;
        @BindView(R.id.itemFoodCardSwipeParent)
        SwipeRevealLayout itemFoodCardSwipeParent;
        private View view;

        public FoodItemSwipeViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
