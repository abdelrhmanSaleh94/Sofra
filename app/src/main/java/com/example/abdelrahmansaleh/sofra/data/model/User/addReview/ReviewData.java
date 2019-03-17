
package com.example.abdelrahmansaleh.sofra.data.model.User.addReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewData {

    @SerializedName("review")
    @Expose
    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
