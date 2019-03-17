
package com.example.abdelrahmansaleh.sofra.data.model.restaurant.restaurantRegister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantRegister {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantRegisterData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestaurantRegisterData getData() {
        return data;
    }

    public void setData(RestaurantRegisterData data) {
        this.data = data;
    }

}
