package com.example.abdelrahmansaleh.sofra.ui.fragment.restaurant;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.adapter.ReviewAdapter;
import com.example.abdelrahmansaleh.sofra.data.model.User.addReview.AddReview;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantReviews.RestaurantReviewData;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantReviews.RestaurantReviews;
import com.example.abdelrahmansaleh.sofra.data.rest.ApiServices;
import com.example.abdelrahmansaleh.sofra.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.sofra.helper.OnEndless;
import com.example.abdelrahmansaleh.sofra.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.List;

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
public class CommentFragment extends Fragment {


    @BindView(R.id.commentFragmentRv)
    RecyclerView commentFragmentRv;
    Unbinder unbinder;
    @BindView(R.id.CommentFragmentLinerLayout)
    LinearLayout CommentFragmentLinerLayout;
    private ApiServices apiServices;
    private int maxPages;
    private List<RestaurantReviewData> reviewDataList;
    private ReviewAdapter adapter;
    private String api_token;
    private String user;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_comment, container, false );
        unbinder = ButterKnife.bind( this, view );
        api_token = SharedPreferencesManger.LoadStringData( getActivity(), API_TOKEN_USER );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        user = SharedPreferencesManger.LoadStringData( getActivity(), VAL );
        reviewDataList = new ArrayList<>();
        if (!user.equals( "user" )) {
            CommentFragmentLinerLayout.setVisibility( View.GONE );
        }
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        commentFragmentRv.setLayoutManager( manager );
        adapter = new ReviewAdapter( getContext(), reviewDataList );
        OnEndless onEndless = new OnEndless( manager, 3 ) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getComments( current_page );
                }
            }
        };
        commentFragmentRv.addOnScrollListener( onEndless );
        commentFragmentRv.setAdapter( adapter );
        getComments( 1 );
        return view;
    }

    private void getComments(int page) {
        apiServices.RESTAURANT_REVIEWS_CALL( String.valueOf( RestaurantDetailsFragment.id ), page )
                .enqueue( new Callback<RestaurantReviews>() {
                    @Override
                    public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                        if (response.body().getStatus() == 1) {
                            maxPages = response.body().getData().getLastPage();
                            reviewDataList.addAll( response.body().getData().getData() );
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantReviews> call, Throwable t) {

                    }
                } );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.commentFragmentBtnAddReviews)
    public void onViewClicked() {
        final Dialog rateDialog = new Dialog( getContext() );
        View view = LayoutInflater.from( getContext() ).inflate( R.layout.diloge_comment, null );
        rateDialog.setContentView( view );
        rateDialog.show();
        final RatingBar ratingBar = view.findViewById( R.id.dilogeCommentRatingBar );
        final EditText commentEditText = view.findViewById( R.id.dilogeCommentETComment );
        Button btnAdd = view.findViewById( R.id.dilogeCommentBtnAdd );
        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) ratingBar.getRating();
                String comment = commentEditText.getText().toString();
                if (rating != 0 && comment != null) {
                    apiServices.ADD_REVIEW_CALL( rating, comment, RestaurantDetailsFragment.id, api_token )
                            .enqueue( new Callback<AddReview>() {
                                @Override
                                public void onResponse(Call<AddReview> call, Response<AddReview> response) {
                                    if (response.body().getStatus() == 1) {
                                        Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                                        rateDialog.dismiss();
                                    } else {
                                        Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                                        rateDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddReview> call, Throwable t) {
                                }
                            } );
                } else {
                    Toast.makeText( getContext(), getActivity().getResources().getString( R.string.confirmData ), Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }


}
