package com.example.abdelrahmansaleh.sofra.data.model.restaurant.changeState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStateData {
    @Expose
    @SerializedName("photo_url")
    private String photoUrl;
    @Expose
    @SerializedName("rate")
    private String rate;
    @Expose
    @SerializedName("activated")
    private String activated;
    @Expose
    @SerializedName("availability")
    private String availability;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("whatsapp")
    private String whatsapp;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("minimum_charger")
    private String minimumCharger;
    @Expose
    @SerializedName("delivery_cost")
    private String deliveryCost;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("region_id")
    private String regionId;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("id")
    private int id;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMinimumCharger() {
        return minimumCharger;
    }

    public void setMinimumCharger(String minimumCharger) {
        this.minimumCharger = minimumCharger;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
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
