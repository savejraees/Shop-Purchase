package com.saifi.shoppurchase.retrofitmodel.manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PurchaseStatusModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("data")
    @Expose
    private ArrayList<PurchaseDatum> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<PurchaseDatum> getData() {
        return data;
    }

    public void setData(ArrayList<PurchaseDatum> data) {
        this.data = data;
    }
}
