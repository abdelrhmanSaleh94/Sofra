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

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment {


    @BindView(R.id.forgetPassEtextEmail)
    EditText forgetPassEtextEmail;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String user;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_forget_password, container, false );

        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.forgetPassBtnSend)
    public void onViewClicked() {
        String email = forgetPassEtextEmail.getText().toString();
        if (!email.isEmpty()) {
            HelperMethod.showProgressDialog( getActivity(), "Loading" );
            if (user.equals( "user" )) {
                sendDataUser( email );
            } else {
                sendDataRest( email );
            }
        } else {
            Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
        }
    }

    private void sendDataRest(String email) {
        apiServices.RESET_PASSWORD_CALL_RESTR( email ).enqueue( new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus() == 1) {
                    HelperMethod.replaceFragment( new ConfirmationSendPasswordFragment(),getActivity().getSupportFragmentManager(),R.id.restaurantHomeActivityFrame,null,null );
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
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

    private void sendDataUser(String email) {
        apiServices.RESET_PASSWORD_CALL( email ).enqueue( new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus() == 1) {
                    HelperMethod.replaceFragment( new ConfirmationSendPasswordFragment(),getActivity().getSupportFragmentManager(),R.id.counterFrame,null,null );
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    HelperMethod.dismissProgressDialog();
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
