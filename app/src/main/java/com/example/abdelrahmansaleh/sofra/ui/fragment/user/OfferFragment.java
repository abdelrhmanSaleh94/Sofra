package com.example.abdelrahmansaleh.sofra.ui.fragment.user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.OffersAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferList;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferListData;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferListsDtum;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.OnEndless;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends Fragment {


    @BindView(R.id.offerFragmentRecyclerV)
    RecyclerView offerFragmentRecyclerV;
    Unbinder unbinder;
    private OffersAdapter offersAdapter;
    private List<OfferListData> offerList;
    private ApiServices apiServices;
    private Integer maxPage;

    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_offer, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        offerList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        offerFragmentRecyclerV.setLayoutManager( manager );
        OnEndless onEndless = new OnEndless( manager, 3 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    getOffers( current_page );
                }
            }
        };
        offerFragmentRecyclerV.addOnScrollListener( onEndless );
        offersAdapter = new OffersAdapter( getContext(), offerList );
        offerFragmentRecyclerV.setAdapter( offersAdapter );
        getOffers( 1 );
        return view;
    }

    private void getOffers(int page) {
        apiServices.OFFER_LIST_CALL( page ).enqueue( new Callback<OfferList>() {
            @Override
            public void onResponse(Call<OfferList> call, Response<OfferList> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    offerList.addAll( response.body().getData().getData() );
                    offersAdapter.notifyDataSetChanged();

                } else {
                    StyleableToast.makeText( getContext(), response.body().getMsg(), R.style.Error ).show();
                }
            }

            @Override
            public void onFailure(Call<OfferList> call, Throwable t) {
                Log.i( TAG, "onFailure: " + t.getMessage() );
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
