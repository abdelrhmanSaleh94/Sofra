package com.example.abdelrahmansaleh.sofra.data.model.restaurant.changeState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeState {

    @Expose
    @SerializedName("data")
    private ChangeStateData data;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("status")
    private int status;

    public ChangeStateData getData() {
        return data;
    }

    public void setData(ChangeStateData data) {
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
