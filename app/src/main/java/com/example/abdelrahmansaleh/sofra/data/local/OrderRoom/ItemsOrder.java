package com.example.abdelrahmansaleh.sofra.data.local.OrderRoom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ItemsOrder {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String createdAt;
    private String price;
    private String preparingTime;
    private String photoUrl;
    private String restaurantId;
    private String itemId;
    private String note;

    public ItemsOrder(String name, String createdAt, String price, String preparingTime
            , String photoUrl, String restaurantId, String itemId, String note) {
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.preparingTime = preparingTime;
        this.photoUrl = photoUrl;
        this.restaurantId = restaurantId;

        this.itemId = itemId;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
