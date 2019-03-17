package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.cities.Cities;
import com.example.abdelrahmansaleh.sofra.data.model.cities.Datum;
import com.example.abdelrahmansaleh.sofra.data.model.regions.Regions;
import com.example.abdelrahmansaleh.sofra.data.model.regions.RegoinsData;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRest1Fragment extends Fragment {


    @BindView(R.id.restRegistEditName)
    EditText restRegistEditName;
    @BindView(R.id.restRegistEditEmail)
    EditText restRegistEditEmail;
    @BindView(R.id.loginUserSpinnerGovernorate)
    Spinner loginUserSpinnerGovernorate;
    @BindView(R.id.loginUserSpinnerCity)
    Spinner restRegistSpinnerCity;
    @BindView(R.id.restRegistEditPassword)
    EditText restRegistEditPassword;
    @BindView(R.id.restRegistEditConfermationPassword)
    EditText restRegistEditConfermationPassword;
    Unbinder unbinder;
    private ApiServices apiServices;
    public static Integer regionIdSelected;
    public static String confermationPassword;
    public static String email;
    public static String password;
    public static String name;

    public RegistrationRest1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_registration_rest1, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        getCitiesSpinner();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getCitiesSpinner() {
        apiServices.getCities().enqueue( new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    List<Datum> data = response.body().getData().getData();
                    final List<String> cityName = new ArrayList<>();
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
                    restRegistSpinnerCity.setAdapter( spinnerRegoinsAdapter );
                    restRegistSpinnerCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
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

    @OnClick(R.id.restRegistBtnRegister)
    public void onViewClicked() {
        confermationPassword = restRegistEditConfermationPassword.getText().toString();
        email = restRegistEditEmail.getText().toString();
        password = restRegistEditPassword.getText().toString();
        name = restRegistEditName.getText().toString();
        if (!confermationPassword.isEmpty() && !password.isEmpty()
                && !email.isEmpty() && !name.isEmpty() && regionIdSelected != 0) {
            if (password.equals( confermationPassword )) {
                HelperMethod.replaceFragment( new RegistrationRest2Fragment(), getActivity().getSupportFragmentManager(), R.id.restaurantHomeActivityFrame, null, null );
            } else {
                Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
            }
        } else {
            Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
        }
    }
}
