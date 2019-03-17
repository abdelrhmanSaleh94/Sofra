package com.example.abdelrahmansaleh.sofra.data.model.User.itemsOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("quantity")
    private String quantity;
    @Expose
    @SerializedName("price")
    private String price;
    @Expose
    @SerializedName("item_id")
    private String itemId;
    @Expose
    @SerializedName("order_id")
    private String orderId;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
