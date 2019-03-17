package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.User.register.Register;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantRegister.RestaurantRegister;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RegistrationRest1Fragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RestaurantDetailsFragment;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.RestaurantsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.API_TOKEN_USER;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.SHARED_ID_REST;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginUser extends Fragment {


    @BindView(R.id.loginUserEditEmail)
    EditText loginUserEditEmail;
    @BindView(R.id.loginUserEditPassword)
    EditText loginUserEditPassword;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String user;

    public LoginUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_login_user, container, false );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.loginUserBtnEnter, R.id.loginUserTextForgetPass, R.id.loginUserBtnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginUserBtnEnter:
                loginSave();
                break;
            case R.id.loginUserTextForgetPass:
                if (user.equals( "user" )) {
                    HelperMethod.replaceFragment( new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.counterFrame, null, null );
                } else {
                    HelperMethod.replaceFragment( new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );

                }
                break;
            case R.id.loginUserBtnRegister:
                if (user.equals( "user" )) {
                    HelperMethod.replaceFragment( new RegistrationFragment(), getActivity().getSupportFragmentManager(), R.id.counterFrame, null, null );
                } else {
                    HelperMethod.replaceFragment( new RegistrationRest1Fragment(), getActivity().getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );
                }
                break;
        }
    }

    private void loginSave() {
        String email = loginUserEditEmail.getText().toString();
        String password = loginUserEditPassword.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
            HelperMethod.showProgressDialog( getActivity(), getActivity().getResources().getString( R.string.loading ) );
            if (user.equals( "user" )) {
                apiServices.PostLogin( email, password ).enqueue( new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                            HelperMethod.dismissProgressDialog();
                            String apiToken = response.body().getData().getApiToken();
                            SharedPreferencesManger.SaveData( getActivity(), API_TOKEN_USER, apiToken );
                            HelperMethod.replaceFragment( new RestaurantsFragment(), getActivity().getSupportFragmentManager(), R.id.counterFrame, null, null );
                        } else {
                            HelperMethod.dismissProgressDialog();
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {

                        HelperMethod.dismissProgressDialog();
                        Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                } );
            } else {
                HelperMethod.showProgressDialog( getActivity(), getActivity().getResources().getString( R.string.loading ) );
                apiServices.restaurantLogin( email, password ).enqueue( new Callback<RestaurantRegister>() {
                    @Override
                    public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                            String apiToken = response.body().getData().getApiToken();
                            SharedPreferencesManger.SaveData( getActivity(), API_TOKEN_USER, apiToken );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_NAME, data.getName() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_EMAIL, data.getEmail() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_DELIVERY, data.getDeliveryCost() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_MINIMUM, data.getMinimumCharger() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_PHONE, data.getPhone() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_WHATS_APP, data.getWhatsapp() );
//                            SharedPreferencesManger.SaveData( getActivity(), SHARED_PHOTO, data.getPhotoUrl() );
                            SharedPreferencesManger.SaveData( getActivity(), SHARED_ID_REST, response.body().getData().getUser().getId() );
                            HelperMethod.replaceFragment( new RestaurantDetailsFragment(), getActivity().getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );
                            HelperMethod.dismissProgressDialog();
                            HelperMethod.hideKeyboard( getActivity() );
                        } else {
                            HelperMethod.dismissProgressDialog();
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantRegister> call, Throwable t) {
                        HelperMethod.dismissProgressDialog();
                        Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        } else {
            HelperMethod.dismissProgressDialog();
            Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
        }
    }
}
