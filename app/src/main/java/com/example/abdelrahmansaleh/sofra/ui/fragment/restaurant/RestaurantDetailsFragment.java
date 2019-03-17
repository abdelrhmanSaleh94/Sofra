package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.Category;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.RestaurantDetailsData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.RestaurantID;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.SHARED_ID_REST;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment {


    @BindView(R.id.restaurantCardDetailsImage)
    ImageView restaurantCardDetailsImage;
    @BindView(R.id.restaurantCardDetailsTvName)
    TextView restaurantCardDetailsTvName;
    @BindView(R.id.restaurantCardDetailsTvCategory)
    TextView restaurantCardDetailsTvCategory;
    @BindView(R.id.restaurantCardDetailsRatingBar)
    RatingBar restaurantCardDetailsRatingBar;
    @BindView(R.id.restaurantCardDetailsTvSates)
    TextView restaurantCardDetailsTvSates;
    @BindView(R.id.restaurantCardDetailsTvMin)
    TextView restaurantCardDetailsTvMin;
    @BindView(R.id.restaurantCardDetailsTvDeliveryFee)
    TextView restaurantCardDetailsTvDeliveryFee;
    @BindView(R.id.restaurantCardDetailsTabLayout)
    TabLayout restaurantCardDetailsTabLayout;
    @BindView(R.id.restaurantCardDetailsVPager)
    ViewPager restaurantCardDetailsVPager;
    private Unbinder unbinder;
    Fragment[] fragments;
    public static int id;
    private String user;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_restaurant_details, container, false );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        fragments = new Fragment[]{new FoodFragment(), new CommentFragment(), new InformationRestaurantFragment()};
        if (user.equals( "user" )) {
            id = getArguments().getInt( RestaurantID );
        } else {
            id = SharedPreferencesManger.LoadIntegerData( getActivity(), SHARED_ID_REST );
        }
        ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );

        PagerAdapterMainActivity pagerAdapter = new PagerAdapterMainActivity( getActivity().getSupportFragmentManager() );

        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                FoodFragment foodFragment = (FoodFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                CommentFragment commentFragment = (CommentFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                InformationRestaurantFragment restaurantFragment = (InformationRestaurantFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        if (id != 0) {
            apiServices.RESTAURANT_DETAILS_CALL( String.valueOf( id ) ).enqueue( new Callback<RestaurantDetails>() {
                @Override
                public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                    if (response.body().getStatus() == 1) {
                        RestaurantDetailsData data = response.body().getData();
                        StringBuilder categoryString = new StringBuilder();
                        List<Category> categories = data.getCategories();
                        for (int i = 0; i < categories.size(); i++) {
                            categoryString.append( categories.get( i ).getName() + " , " );
                        }
                        Glide.with( getContext() ).load( data.getPhotoUrl() ).into( restaurantCardDetailsImage );
                        restaurantCardDetailsTvName.setText( data.getName() );
                        restaurantCardDetailsRatingBar.setRating( data.getRate() );
                        restaurantCardDetailsTvMin.setText( getActivity().getResources().getString( R.string.deliveryFee ) + data.getMinimumCharger() );
                        restaurantCardDetailsTvDeliveryFee.setText( getActivity().getResources().getString( R.string.deliveryFee ) + data.getDeliveryCost() );
                        restaurantCardDetailsTvCategory.setText( categoryString.toString() );
                        if (!data.getAvailability().equals( "open" )) {
                            restaurantCardDetailsTvSates.setTextColor( getContext().getResources().getColor( R.color.red_error ) );
                            restaurantCardDetailsTvSates.setText( " مغلق" );
                            restaurantCardDetailsTvSates.setCompoundDrawablesWithIntrinsicBounds( null, null,
                                    getContext().getResources().getDrawable( R.drawable.ic_close ), null );
                        }
                    } else {
                        Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<RestaurantDetails> call, Throwable t) {
                    Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }
        unbinder = ButterKnife.bind( this, view );
        restaurantCardDetailsVPager.setAdapter( pagerAdapter );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class PagerAdapterMainActivity extends FragmentPagerAdapter {
        public PagerAdapterMainActivity(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = getActivity().getResources().getString( R.string.itemsFood );
                    break;
                case 1:
                    title = getActivity().getResources().getString( R.string.commentFood );
                    break;
                case 2:
                    title = getActivity().getResources().getString( R.string.informationFood );
                    break;
            }
            return title;
        }
    }
}
