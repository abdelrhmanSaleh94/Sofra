package com.example.abdelrahmansaleh.sofra.data.model.User.itemsOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {
    @Expose
    @SerializedName("pivot")
    private Pivot pivot;
    @Expose
    @SerializedName("photo_url")
    private String photoUrl;
    @Expose
    @SerializedName("restaurant_id")
    private String restaurantId;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("preparing_time")
    private String preparingTime;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("id")
    private int id;

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
