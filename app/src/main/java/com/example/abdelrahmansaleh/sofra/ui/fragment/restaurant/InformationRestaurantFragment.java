package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.changeState.ChangeState;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.RestaurantDetailsData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.API_TOKEN_USER;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationRestaurantFragment extends Fragment {


    @BindView(R.id.restaurantInformationFragTvState)
    TextView restaurantInformationFragTvState;
    @BindView(R.id.restaurantInformationFragTvCity)
    TextView restaurantInformationFragTvCity;
    @BindView(R.id.restaurantInformationFragTvStreet)
    TextView restaurantInformationFragTvStreet;
    @BindView(R.id.restaurantInformationFragTvMinimum)
    TextView restaurantInformationFragTvMinimum;
    @BindView(R.id.restaurantInformationFragTvDelivery)
    TextView restaurantInformationFragTvDelivery;
    Unbinder unbinder;
    @BindView(R.id.restaurantInformationFragTvStateSwitch)
    Switch restaurantInformationFragTvStateSwitch;
    @BindView(R.id.restaurantInformationFragBtnSave)
    Button restaurantInformationFragBtnSave;
    private String user;
    private ApiServices apiServices;
    private String apiToken;
    private String stateRest;

    public InformationRestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_infotmayion_restaurant, container, false );
        unbinder = ButterKnife.bind( this, view );
        restaurantInformationFragTvStateSwitch.setVisibility( View.GONE );
        restaurantInformationFragTvState.setVisibility( View.GONE );
        restaurantInformationFragBtnSave.setVisibility( View.GONE );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        apiServices.RESTAURANT_DETAILS_CALL( String.valueOf( RestaurantDetailsFragment.id ) ).enqueue( new Callback<RestaurantDetails>() {
            @Override
            public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                if (response.body().getStatus() == 1) {
                    RestaurantDetailsData data = response.body().getData();
                    restaurantInformationFragTvState.setText( data.getAvailability() );
                    restaurantInformationFragTvCity.setText( data.getRegion().getCity().getName() );
                    restaurantInformationFragTvStreet.setText( data.getRegion().getName() );
                    restaurantInformationFragTvDelivery.setText( data.getDeliveryCost() + " EGP" );
                    restaurantInformationFragTvMinimum.setText( data.getMinimumCharger() + " EGP" );
                    if (user.equals( "user" )) {
                        restaurantInformationFragTvState.setVisibility( View.VISIBLE );
                    } else {
                        restaurantInformationFragTvStateSwitch.setVisibility( View.VISIBLE );
                        restaurantInformationFragBtnSave.setVisibility( View.VISIBLE );
                        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), API_TOKEN_USER );
                        if (!response.body().getData().getAvailability().equals( "open" )) {
                            restaurantInformationFragTvStateSwitch.setText( response.body().getData().getAvailability() );
                            restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.red_error ) );

                        }
                        restaurantInformationFragTvStateSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked == true) {
                                    restaurantInformationFragTvStateSwitch.setText( "open" );
                                    restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.colorGreenAccent3 ) );

                                    stateRest = "open";
                                } else {
                                    restaurantInformationFragTvStateSwitch.setText( "close" );
                                    restaurantInformationFragTvStateSwitch.setTextColor( getActivity().getResources().getColor( R.color.red_error ) );
                                    stateRest = "closed";

                                }
                            }
                        } );
                        if (data.getAvailability().equals( "open" )) {
                            restaurantInformationFragTvStateSwitch.setChecked( true );
                            restaurantInformationFragTvStateSwitch.setText( data.getAvailability() );
                        } else {
                            restaurantInformationFragTvStateSwitch.setChecked( false );
                            restaurantInformationFragTvStateSwitch.setText( data.getAvailability() );
                        }
                    }

                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetails> call, Throwable t) {

            }
        } );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.restaurantInformationFragBtnSave)
    public void onViewClicked() {
        apiServices.CHANGE_STATE_CALL( stateRest, apiToken ).enqueue( new Callback<ChangeState>() {
            @Override
            public void onResponse(Call<ChangeState> call, Response<ChangeState> response) {
                if (response.body().getStatus() == 1) {
                    StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Done ).show();
                } else {
                    StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Error ).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeState> call, Throwable t) {

            }
        } );
    }
}
