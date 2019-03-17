package com.example.abdelrahmansaleh.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abdelrahmansaleh.sofra.R;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantReviews.RestaurantReviewData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CommentViewHolder> {
    Context context;
    List<RestaurantReviewData> reviewDataList = new ArrayList<>();

    public ReviewAdapter(Context context, List<RestaurantReviewData> reviewDataList) {
        this.context = context;
        this.reviewDataList = reviewDataList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.comment_card, parent, false );
        return new CommentViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.commentCardName.setText( reviewDataList.get( position ).getClient().getName() );
        holder.commentCardRatingBar.setRating( reviewDataList.get( position ).getRate());
        holder.commentCardDate.setText( reviewDataList.get( position ).getCreatedAt() );
        holder.commentCardTvComment.setText( reviewDataList.get( position ).getComment() );
    }

    @Override
    public int getItemCount() {
        return reviewDataList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.commentCardName)
        TextView commentCardName;
        @BindView(R.id.commentCardRatingBar)
        RatingBar commentCardRatingBar;
        @BindView(R.id.commentCardDate)
        TextView commentCardDate;
        @BindView(R.id.commentCardTvComment)
        TextView commentCardTvComment;
        private View view;

        public CommentViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
