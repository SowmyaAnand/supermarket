package com.dailyestoreapp.supermarket;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    @FormUrlEncoded
    @POST("listSubCategory")
    Call<CustomerAppResponse> SubCategory(@Field("typeId") int id);



    @GET("allOrders")
    Call<CustomerAppResponse> orderslist();


}
