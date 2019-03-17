package com.example.abdelrahmansaleh.sofra.data.rest;

import com.example.abdelrahmansaleh.sofra.data.model.User.addReview.AddReview;
import com.example.abdelrahmansaleh.sofra.data.model.User.offerList.OfferList;
import com.example.abdelrahmansaleh.sofra.data.model.User.resetPassword.ResetPassword;
import com.example.abdelrahmansaleh.sofra.data.model.cities.Cities;
import com.example.abdelrahmansaleh.sofra.data.model.regions.Regions;
import com.example.abdelrahmansaleh.sofra.data.model.User.register.Register;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.categorie.Categories;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.changeState.ChangeState;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.itemsFood.ItemsFood;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.newItemFood.NewItemFood;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.order.Order;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantDetails.RestaurantDetails;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantList.RestaurantList;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantRegister.RestaurantRegister;
import com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantReviews.RestaurantReviews;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("cities")
    Call<Cities> getCities();

    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") String city_id);

    //Client
    @POST("client/register")
    @FormUrlEncoded
    Call<Register> postRegister(@Field("name") String name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("password_confirmation") String password_confirmation,
                                @Field("phone") String phone,
                                @Field("address") String address,
                                @Field("region_id") int region_id);

    @POST("client/login")
    @FormUrlEncoded
    Call<Register> PostLogin(@Field("email") String email,
                             @Field("password") String password);

    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> RESET_PASSWORD_CALL(@Field("email") String email);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<ResetPassword> NEW_PASSWORD_CALL(@Field("code") String code,
                                          @Field("password") String password,
                                          @Field("password_confirmation") String password_confirmation);

    //Restaurant
    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<ResetPassword> NEW_PASSWORD_RESTAURANT_CALL(@Field("code") String code,
                                                     @Field("password") String password,
                                                     @Field("password_confirmation") String password_confirmation);


    @GET("categories")
    Call<Categories> restaurantCategories();

    @Multipart
    @POST("restaurant/register")
    Call<RestaurantRegister> restaurantRegister(@Part("name") RequestBody name,
                                                @Part("email") RequestBody email,
                                                @Part("password") RequestBody password,
                                                @Part("password_confirmation") RequestBody password_confirmation,
                                                @Part("phone") RequestBody phone,
                                                @Part("whatsapp") RequestBody whatsapp,
                                                @Part("region_id") RequestBody region_id,
                                                @Part("categories[]") List<RequestBody> categories,
                                                @Part("delivery_cost") RequestBody delivery_cost,
                                                @Part("minimum_charger") RequestBody minimum_charger,
                                                @Part MultipartBody.Part File,
                                                @Part("availability") RequestBody availability);

    @Multipart
    @POST("restaurant/new-item")
    Call<NewItemFood> NEW_ITEM_FOOD_CALL(@Part("description") RequestBody description,
                                         @Part("price") RequestBody price,
                                         @Part("preparing_time") RequestBody preparing_time,
                                         @Part("name") RequestBody name,
                                         @Part MultipartBody.Part File,
                                         @Part("api_token") RequestBody api_token);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantRegister> restaurantLogin(@Field("email") String email,
                                             @Field("password") String password);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> RESET_PASSWORD_CALL_RESTR(@Field("email") String email);

    @GET("restaurants")
    Call<RestaurantList> RESTAURANT_LIST_CALL(@Query("page") int page);

    @GET("restaurant")
    Call<RestaurantDetails> RESTAURANT_DETAILS_CALL(@Query("restaurant_id") String restaurant_id);

    @GET("items")
    Call<ItemsFood> ITEMS_FOOD_CALL(@Query("restaurant_id") String restaurant_id,
                                    @Query("page") int page);

    @GET("restaurant/my-items")
    Call<ItemsFood> ITEMS_FOOD_CALL_REST(@Query("api_token") String api_token,
                                         @Query("page") int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> RESTAURANT_REVIEWS_CALL(@Query("restaurant_id") String restaurant_id,
                                                    @Query("page") int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> RESTAURANT_REVIEWS_CALL(@Query("restaurant_id") String restaurant_id,
                                                    @Query("page") int page,
                                                    @Query("api_token") String api_token);

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReview> ADD_REVIEW_CALL(@Field("rate") int rate,
                                    @Field("comment") String comment,
                                    @Field("restaurant_id") int restaurant_id,
                                    @Field("api_token") String api_token);

    @GET("restaurant/my-orders")
    Call<Order> ORDER_CALL(@Query("api_token") String api_token,
                           @Query("state") String state,
                           @Query("page") int page);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<ResetPassword> CALL_DELETE_ITEM_FOOD(@Query("api_token") String api_token,
                                              @Field("item_id") int item_id);

    @POST("restaurant/update-item")
    @Multipart
    Call<NewItemFood> UPDATE_ITEM_FOOD_CALL(@Query("api_token") String api_token,
                                            @Part("description") RequestBody description,
                                            @Part("price") RequestBody price,
                                            @Part("preparing_time") RequestBody preparing_time,
                                            @Part("name") RequestBody name,
                                            @Part MultipartBody.Part File,
                                            @Part("item_id") RequestBody item_id);

    @POST("restaurant/update-item")
    @Multipart
    Call<NewItemFood> UPDATE_ITEM_FOOD_CALL(@Query("api_token") String api_token,
                                            @Part("description") RequestBody description,
                                            @Part("price") RequestBody price,
                                            @Part("preparing_time") RequestBody preparing_time,
                                            @Part("name") RequestBody name,
                                            @Part("item_id") RequestBody item_id);

    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<ChangeState> CHANGE_STATE_CALL(@Field("state") String state,
                                        @Field("api_token") String api_token);

    @GET("offers")
    Call<OfferList> OFFER_LIST_CALL(@Query("page") int page);

}
