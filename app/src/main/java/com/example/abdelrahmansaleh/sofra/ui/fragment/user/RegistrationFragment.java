package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.cities.Cities;
import com.example.abdelrahmansaleh.sofra.data.model.cities.Datum;
import com.example.abdelrahmansaleh.sofra.data.model.regions.Regions;
import com.example.abdelrahmansaleh.sofra.data.model.regions.RegoinsData;
import com.example.abdelrahmansaleh.sofra.data.model.User.register.Register;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {


    @BindView(R.id.loginUserEditName)
    EditText loginUserEditName;
    @BindView(R.id.loginUserEditEmail)
    EditText loginUserEditEmail;
    @BindView(R.id.loginUserEditPhone)
    EditText loginUserEditPhone;
    @BindView(R.id.loginUserSpinnerGovernorate)
    Spinner loginUserSpinnerGovernorate;
    @BindView(R.id.loginUserSpinnerCity)
    Spinner loginUserSpinnerCity;
    @BindView(R.id.loginUserEditPassword)
    EditText loginUserEditPassword;
    @BindView(R.id.loginUserEditConfermationPassword)
    EditText loginUserEditConfermationPassword;
    @BindView(R.id.loginUserBtnRegister)
    Button loginUserBtnRegister;
    ApiServices apiServices;
    Unbinder unbinder;
    @BindView(R.id.loginUserEditAddress)
    EditText loginUserEditAddress;
    private Integer regionIdSelected;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_rejester, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        getCitiesSpinner();
        return view;
    }

    private void getCitiesSpinner() {
        apiServices.getCities().enqueue( new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    List<Datum> data = response.body().getData().getData();
                    List<String> cityName = new ArrayList<>();
                    final List<Integer> cityId = new ArrayList<>();
                    cityName.add( getActivity().getResources().getString( R.string.city ) );
                    cityId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        cityName.add( data.get( i ).getName() );
                        cityId.add( data.get( i ).getId() );
                    }
                    ArrayAdapter<String> spinnerCityAdapter = new ArrayAdapter<String>
                            ( getActivity(), android.R.layout.simple_spinner_item, cityName );
                    loginUserSpinnerGovernorate.setAdapter( spinnerCityAdapter );
                    loginUserSpinnerGovernorate.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                getStreet( cityId.get( position ) );
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );
                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();

                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
            }
        } );

    }

    private void getStreet(Integer cityId) {
        apiServices.getRegions( String.valueOf( cityId ) ).enqueue( new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                if (response.body().getStatus() == 1) {
                    List<RegoinsData> data = response.body().getData().getData();
                    List<String> regoinName = new ArrayList<>();
                    final List<Integer> regoinId = new ArrayList<>();
                    regoinName.add( getActivity().getResources().getString( R.string.street ) );
                    regoinId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        regoinName.add( data.get( i ).getName() );
                        regoinId.add( data.get( i ).getId() );
                    }
                    ArrayAdapter<String> spinnerRegoinsAdapter = new ArrayAdapter<String>
                            ( getActivity(), android.R.layout.simple_spinner_item, regoinName );
                    loginUserSpinnerCity.setAdapter( spinnerRegoinsAdapter );
                    loginUserSpinnerCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                regionIdSelected = regoinId.get( position );

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {

            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.loginUserBtnRegister)
    public void onViewClicked() {
        saveData();
    }

    private void saveData() {
        String address = loginUserEditAddress.getText().toString();
        String conferPassword = loginUserEditConfermationPassword.getText().toString();
        String email = loginUserEditEmail.getText().toString();
        String name = loginUserEditName.getText().toString();
        String password = loginUserEditPassword.getText().toString();
        String phone = loginUserEditPhone.getText().toString();
        if (!address.isEmpty() && !conferPassword.isEmpty() && !email.isEmpty() && !name.isEmpty()
                && !password.isEmpty() && !phone.isEmpty() && regionIdSelected != 0) {
            apiServices.postRegister( name, email, password, conferPassword, phone, address, regionIdSelected ).enqueue(
                    new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                            } else {
                                Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
            );

        } else {
            Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
        }
    }
}
