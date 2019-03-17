package com.example.abdelrahmansaleh.sofra.ui.activity.restaurant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.ItemFoodAdapterSwipe;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFoodData;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.newItemFood.NewItemFood;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.HelperMethod;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;
import com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant.MyProductsFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.abdelrahmansaleh.sofra.helper.Constant.API_TOKEN_USER;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {


    @BindView(R.id.restAddProductFgEditNameProduct)
    EditText restAddProductFgEditNameProduct;
    @BindView(R.id.restAddProductFgEditDisc)
    EditText restAddProductFgEditDisc;
    @BindView(R.id.restAddProductFgEditPrice)
    EditText restAddProductFgEditPrice;
    @BindView(R.id.restAddProductFgEditPreparingTime)
    EditText restAddProductFgEditPreparingTime;
    @BindView(R.id.restAddProductIvProduct)
    ImageView restAddProductIvProduct;
    Unbinder unbinder;
    @BindView(R.id.restAddProductBtnSaveProduct)
    Button restAddProductBtnSaveProduct;
    private ArrayList<AlbumFile> ImagesFiles;
    private ApiServices apiServices;
    private String apiToken;
    private int swipe;


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate( R.layout.fragment_add_product, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), API_TOKEN_USER );

        ItemsFoodData itemsFoodDataEdit = ItemFoodAdapterSwipe.itemsFoodDataEdit;
        if (itemsFoodDataEdit != null) {

            restAddProductFgEditDisc.setText( itemsFoodDataEdit.getDescription() );
            restAddProductFgEditNameProduct.setText( itemsFoodDataEdit.getName() );
            restAddProductFgEditPreparingTime.setText( itemsFoodDataEdit.getPreparingTime() );
            restAddProductFgEditPrice.setText( itemsFoodDataEdit.getPrice() );
            Glide.with( getContext() ).load( itemsFoodDataEdit.getPhotoUrl() ).into( restAddProductIvProduct );
            swipe = 1;
            restAddProductBtnSaveProduct.setText( getActivity().getResources().getString( R.string.edit ) );
        }
        ImagesFiles = new ArrayList<>();
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.restAddProductIvProduct, R.id.restAddProductBtnSaveProduct})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restAddProductIvProduct:

                Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ImagesFiles.clear();
                        ImagesFiles.addAll( result );
                        Glide.with( getActivity() ).load( ImagesFiles.get( 0 ).getPath() ).into( restAddProductIvProduct );
                    }
                };
                HelperMethod.openAlbum( 3, getActivity(), ImagesFiles, action );

                break;
            case R.id.restAddProductBtnSaveProduct:
                if (swipe == 1) {
                    editProduct();
                } else {
                    saveProducts();
                }
                break;
        }
    }

    private void editProduct() {
        RequestBody discBody = HelperMethod.convertToRequestBody( restAddProductFgEditDisc.getText().toString() );
        RequestBody nameProductBody = HelperMethod.convertToRequestBody( restAddProductFgEditNameProduct.getText().toString() );
        RequestBody preparingBody = HelperMethod.convertToRequestBody( restAddProductFgEditPreparingTime.getText().toString() );
        RequestBody priceBody = HelperMethod.convertToRequestBody( restAddProductFgEditPrice.getText().toString() );
        RequestBody itemIdBody = HelperMethod.convertToRequestBody( ItemFoodAdapterSwipe.itemsFoodDataEdit.getId().toString() );
        if (ImagesFiles.size() != 0) {
            MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart( ImagesFiles.get( 0 ).getPath(), "photo" );
            apiServices.UPDATE_ITEM_FOOD_CALL( apiToken, discBody, priceBody, preparingBody, nameProductBody
                    , bodyImage, itemIdBody ).enqueue( new Callback<NewItemFood>() {
                @Override
                public void onResponse(Call<NewItemFood> call, Response<NewItemFood> response) {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.replaceFragment( new MyProductsFragment(), getActivity().getSupportFragmentManager(),
                                R.id.restaurantHomeActivityFrame, null, null );
                        StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Done ).show();
                    } else {
                        StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Error ).show();
                    }
                }

                @Override
                public void onFailure(Call<NewItemFood> call, Throwable t) {

                }
            } );
        } else {
            apiServices.UPDATE_ITEM_FOOD_CALL( apiToken, discBody, priceBody, preparingBody, nameProductBody
                    , itemIdBody ).enqueue( new Callback<NewItemFood>() {
                @Override
                public void onResponse(Call<NewItemFood> call, Response<NewItemFood> response) {
                    if (response.body().getStatus() == 1) {
                        HelperMethod.replaceFragment( new MyProductsFragment(), getActivity().getSupportFragmentManager(),
                                R.id.restaurantHomeActivityFrame, null, null );
                        StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Done ).show();
                    } else {
                        StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Error ).show();
                    }
                }

                @Override
                public void onFailure(Call<NewItemFood> call, Throwable t) {

                }
            } );

        }
    }

    private void saveProducts() {
        RequestBody discBody = HelperMethod.convertToRequestBody( restAddProductFgEditDisc.getText().toString() );
        RequestBody nameProductBody = HelperMethod.convertToRequestBody( restAddProductFgEditNameProduct.getText().toString() );
        RequestBody preparingBody = HelperMethod.convertToRequestBody( restAddProductFgEditPreparingTime.getText().toString() );
        RequestBody priceBody = HelperMethod.convertToRequestBody( restAddProductFgEditPrice.getText().toString() );
        MultipartBody.Part bodyImage = HelperMethod.convertFileToMultipart( ImagesFiles.get( 0 ).getPath(), "photo" );
        RequestBody apiTokenBody = HelperMethod.convertToRequestBody( apiToken );
        apiServices.NEW_ITEM_FOOD_CALL( discBody, priceBody, preparingBody, nameProductBody, bodyImage, apiTokenBody )
                .enqueue( new Callback<NewItemFood>() {
                    @Override
                    public void onResponse(Call<NewItemFood> call, Response<NewItemFood> response) {
                        if (response.body().getStatus() == 1) {
                            HelperMethod.replaceFragment( new MyProductsFragment(), getActivity().getSupportFragmentManager(),
                                    R.id.restaurantHomeActivityFrame, null, null );
                            StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Done ).show();
                        } else {
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                            StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Error ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewItemFood> call, Throwable t) {
                        Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                        Log.i( TAG, "onFailure: " + t.getMessage() );
                    }
                } );
    }
}
