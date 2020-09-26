package com.dailyestoreapp.supermarket;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ResponseInterface {

    @GET("listcategory")
    Call<CustomerAppResponse> CategoryList();
    @GET("viewAllPopup")
    Call<CustomerAppResponse> firstpop();
    @GET("viewAllUpFlyers")
    Call<CustomerAppResponse> allFlyers();
    @GET("viewAllCoupons")
    Call<CustomerAppResponse> viewallcoupons();
    @GET("viewAllDownFlyers")
    Call<CustomerAppResponse> allseconfFlyers();
    @GET("viewAllDeals")
    Call<CustomerAppResponse> viewalldeal();

    @GET("viewComments")
    Call<CustomerAppResponse> messagelist();




    @GET("allOrders")
    Call<CustomerAppResponse> orderslist();


}
