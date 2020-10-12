package com.dailyestoreapp.supermarket;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponsedataMyAccount {




        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("data")
        @Expose
        private Datum data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public Datum getData() {
            return data;
        }

        public void setData(Datum data) {
            this.data = data;
        }

    }

