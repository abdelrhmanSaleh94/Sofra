
package com.example.abdelrahmansaleh.sofra.data.model.restaurant.newItemFood;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewItemFood {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NewItemsFoodData data;

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

    public NewItemsFoodData getData() {
        return data;
    }

    public void setData(NewItemsFoodData data) {
        this.data = data;
    }

}
