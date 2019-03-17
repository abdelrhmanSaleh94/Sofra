package com.example.abdelrahmansaleh.sofra.data.model.User.itemsOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrder {

    @Expose
    @SerializedName("data")
    private NewOrderData data;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("status")
    private int status;

    public NewOrderData getData() {
        return data;
    }

    public void setData(NewOrderData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
