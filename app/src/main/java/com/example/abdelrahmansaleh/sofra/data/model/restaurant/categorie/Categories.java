
package com.example.abdelrahmansaleh.sofra.data.model.restaurant.categorie;

import java.util.List;

import com.example.abdelrahmansaleh.sofra.data.model.cities.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<CategorieData> data = null;

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

    public List<CategorieData> getData() {
        return data;
    }

    public void setData(List<CategorieData> data) {
        this.data = data;
    }

}
