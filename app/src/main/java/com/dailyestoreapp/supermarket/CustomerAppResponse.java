package com.dailyestoreapp.supermarket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAppResponse {
    @SerializedName("responsedata")
    @Expose
    private Responsedata responsedata;

    public Responsedata getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(Responsedata responsedata) {
        this.responsedata = responsedata;
    }
}
