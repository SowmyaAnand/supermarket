package com.dailyestoreapp.supermarket;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CustomerAppResponseMyAccount {



        @SerializedName("responsedata")
        @Expose
        private ResponsedataMyAccount responsedata;

        public ResponsedataMyAccount getResponsedata() {
            return responsedata;
        }

        public void setResponsedata(ResponsedataMyAccount responsedata) {
            this.responsedata = responsedata;
        }


}