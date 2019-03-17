package com.example.abdelrahmansaleh.sofra.data.model.User.itemsOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrderData {
    @Expose
    @SerializedName("order")
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
