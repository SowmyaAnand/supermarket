package com.dailyestoreapp.supermarket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAppResponseLogin {
    @SerializedName("responsedata")
    @Expose
    private LoginResponseData responsedata;

    public LoginResponseData getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(LoginResponseData responsedata) {
        this.responsedata = responsedata;
    }
}
