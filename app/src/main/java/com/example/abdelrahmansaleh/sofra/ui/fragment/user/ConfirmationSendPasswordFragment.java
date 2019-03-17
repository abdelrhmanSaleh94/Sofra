package com.example.abdelrahmansaleh.sofra.ui.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.User.resetPassword.ResetPassword;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abdelrahmansaleh.sofra.helper.Constant.VAL;

public class ConfirmationSendPasswordFragment extends Fragment {

    @BindView(R.id.forgetPassEtextCode)
    EditText forgetPassEtextCode;
    @BindView(R.id.forgetPassEtPassword)
    EditText forgetPassEtPassword;
    @BindView(R.id.forgetPassEtConfigPassword)
    EditText forgetPassEtConfigPassword;
    Unbinder unbinder;
    private String user;
    ApiServices apiServices;

    public ConfirmationSendPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_confirmayion_send_password, container, false );
        unbinder = ButterKnife.bind( this, view );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.forgetPassBtnChange)
    public void onViewClicked() {
        String configPassword = forgetPassEtConfigPassword.getText().toString();
        String password = forgetPassEtPassword.getText().toString();
        String code = forgetPassEtextCode.getText().toString();
        if (!configPassword.isEmpty() && !password.isEmpty() && !code.isEmpty()) {
            HelperMethod.showProgressDialog( getActivity(), "Loading" );
            if (user.equals( "user" )) {
                changeUserPassword( code, configPassword, password );
            } else {
                changeRestaurantPassword( code, configPassword, password );
            }
        } else {
            Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
        }
    }

    private void changeRestaurantPassword(String code, String configPassword, String password) {
        apiServices.NEW_PASSWORD_RESTAURANT_CALL( code, password, configPassword ).enqueue( new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    HelperMethod.dismissProgressDialog();
                    HelperMethod.replaceFragment( new LoginUser(), getActivity().getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );
                } else {
                    HelperMethod.dismissProgressDialog();
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        } );
    }

    private void changeUserPassword(String code, String configPassword, String password) {
        apiServices.NEW_PASSWORD_CALL( code, password, configPassword ).enqueue( new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    HelperMethod.dismissProgressDialog();
                    HelperMethod.replaceFragment( new LoginUser(), getActivity().getSupportFragmentManager(), R.id.counterFrame, null, null );
                } else {
                    HelperMethod.dismissProgressDialog();
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        } );
    }
}
