package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.categorie.CategorieData;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.categorie.Categories;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantRegister.RestaurantRegister;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.MultiSelectionSpinner;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationRest2Fragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.RegistrationRestEtMinimum)
    EditText RegistrationRestEtMinimum;
    @BindView(R.id.RegistrationRestEtDeliveryFee)
    EditText RegistrationRestEtDeliveryFee;
    @BindView(R.id.RegistrationRestEtWhats)
    EditText RegistrationRestEtWhats;
    @BindView(R.id.RegistrationRestEtPhone)
    EditText RegistrationRestEtPhone;
    @BindView(R.id.RegistrationRestIvRestaurant)
    ImageView RegistrationRestIvRestaurant;
    @BindView(R.id.SpinnerCategory)
    MultiSelectionSpinner SpinnerCategory;
    private ApiServices apiServices;
    private Integer categorySelected;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private List<Integer> selectedIndicies = new ArrayList<>();
    private List<CategorieData> data = new ArrayList<>();

    public RegistrationRest2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_registration_rest2, container, false );
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
        apiServices.restaurantCategories().enqueue( new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.body().getStatus() == 1) {
                    data = response.body().getData();
                    final List<String> catName = new ArrayList<>();
                    final List<Integer> catId = new ArrayList<>();
                    catName.add( getActivity().getResources().getString( R.string.category ) );
                    catId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        catName.add( data.get( i ).getName() );
                        catId.add( data.get( i ).getId() );
                    }
                    SpinnerCategory.setItems( catName );
                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();

            }
        } );

    }

    @OnClick({R.id.RegistrationRestIvRestaurant, R.id.RegistrationRestBtnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RegistrationRestIvRestaurant:
                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ImagesFiles.clear();
                        ImagesFiles.addAll( result );
                        Glide.with( getActivity() ).load( ImagesFiles.get( 0 ).getPath() ).into( RegistrationRestIvRestaurant );
                    }
                };
                HelperMethod.openAlbum( 3, getActivity(), ImagesFiles, action );
                break;
            case R.id.RegistrationRestBtnRegister:
                sendDataRegistration();
                break;
        }
    }

    private void sendDataRegistration() {
        MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart( ImagesFiles.get( 0 ).getPath(), "photo" );
        RequestBody bodyDelivery = HelperMethod.convertToRequestBody( RegistrationRestEtDeliveryFee.getText().toString() );
        RequestBody bodyMinimum = HelperMethod.convertToRequestBody( RegistrationRestEtMinimum.getText().toString() );
        RequestBody bodyPhone = HelperMethod.convertToRequestBody( RegistrationRestEtPhone.getText().toString() );
        RequestBody bodyWhats = HelperMethod.convertToRequestBody( RegistrationRestEtWhats.getText().toString() );
        RequestBody bodyConfigPassword = HelperMethod.convertToRequestBody( RegistrationRest1Fragment.confermationPassword );
        RequestBody bodyEmail = HelperMethod.convertToRequestBody( RegistrationRest1Fragment.email );
        RequestBody bodyName = HelperMethod.convertToRequestBody( RegistrationRest1Fragment.name );
        RequestBody bodyPassword = HelperMethod.convertToRequestBody( RegistrationRest1Fragment.password );
        RequestBody bodyRegion = HelperMethod.convertToRequestBody( RegistrationRest1Fragment.regionIdSelected.toString() );
        RequestBody bodyAvailability = HelperMethod.convertToRequestBody( "open" );
        List<String> selectedStrings = SpinnerCategory.getSelectedStrings();
        List<String> listedSelected = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (selectedStrings.contains( data.get( i ).getName() )) {
                listedSelected.add( data.get( i ).getId().toString() );
            }
        }
        List<RequestBody> bodyCategory = new ArrayList<>();
        for (int i = 0; i < listedSelected.size(); i++) {
            bodyCategory.add( HelperMethod.convertToRequestBody( listedSelected.get( i ) ) );
        }
        apiServices.restaurantRegister( bodyName, bodyEmail, bodyPassword, bodyConfigPassword, bodyPhone, bodyWhats, bodyRegion
                , bodyCategory, bodyDelivery, bodyMinimum, bodyImage, bodyAvailability ).enqueue( new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                if (response.body().getStatus() == 1) {
                    HelperMethod.showProgressDialog( getActivity(), "" );
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
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
}
