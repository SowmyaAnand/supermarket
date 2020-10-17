package com.dailyestoreapp.supermarket;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ResponseInterface {

    @GET("listcategory")
    Call<CustomerAppResponse> CategoryList();
    @FormUrlEncoded
    @POST("orderStatus")
    Call<CustomerAppResponseLogin> changeOrderStatus(@Field("orderId") int id,
                                                 @Field("status") int status);
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
    @FormUrlEncoded
    @POST("searchAllItems")
    Call<CustomerAppResponse> SearhApi(@Field("keyword") String keyword);
    @FormUrlEncoded
    @POST("userDetails")
    Call<CustomerAppResponseMyAccount> userDetails(@Field("userId") int userId);
    @FormUrlEncoded
    @POST("viewCouponByIdNew")
    Call<CustomerAppResponse> viewCouponDetail(@Field("couponName") String cpName);
    @FormUrlEncoded
    @POST("userOrders")
    Call<CustomerAppResponse> userOrders(@Field("userId") int userId);
    @GET("allOrders")
    Call<CustomerAppResponse> orderslist();
    @FormUrlEncoded
    @POST("subItemListWithOfferPercentage")
    Call<CustomerAppResponse> Items(@Field("subId") int id);
    @FormUrlEncoded
    @POST("subItemListNew")
    Call<CustomerAppResponse> Items(@Field("subId") int id,
                                    @Field("start") int start,
                                    @Field("limit") int limit);
    @FormUrlEncoded
    @POST("loginData")
    Call<CustomerAppResponseLogin> Loginapi(@Field("username") String usernameres,
                                            @Field("password") String passwordres,
                                            @Field("type") String typeres
            );
    @FormUrlEncoded
    @POST("contactUs")
    Call<CustomerAppResponseLogin> contactus_send(@Field("userId") int userId,
                                            @Field("email") String email,
                                            @Field("mobile") int mobile,
                                                  @Field("address")String address,
                                                  @Field("message") String msg
    );

//    @FormUrlEncoded
//    @POST("loginData")
//    Call<CustomerAppResponseLogin> contactus_send(@Field("data[]") ArrayList<String> data
//
//    );

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<CustomerAppResponseLogin> Forgot_pswdapi(@Field("email") String email

    );
    @FormUrlEncoded
    @POST("userRegister")
    Call<CustomerAppResponseLogin> signUpapi(@Field("firstName") String fname,
                                            @Field("lastName") String lname,
                                            @Field("email") String email,
                                            @Field("phone") int phone_num,
                                            @Field("address") String address,
                                            @Field("pinCode") int pincode,
                                            @Field("dob") String dob,
                                            @Field("username") String usernme,
                                            @Field("password") String pswd,
                                            @Field("type") String type
    );





    @FormUrlEncoded
    @POST("updateProfile")
    Call<CustomerAppResponseLogin> UpdateMyaccount(@Field("userId") int id,
                                        @Field("firstName") String fname,
                                        @Field("lastName") String lname,
                                        @Field("email") String email,
                                        @Field("phone") String phone,
                                        @Field("address") String address,
                                        @Field("pinCode") String pincode,
                                        @Field("dob") String dob);
    @FormUrlEncoded
    @POST("checkout")
    Call<Void> checkoutapi(@Field("itemId[]") ArrayList<Integer> ItemId ,
                                          @Field("count[]") ArrayList<Integer> count,
                                          @Field("quantity[]") ArrayList<String> quantity,
                                          @Field("type[]") ArrayList<Integer> type,
                                               @Field("price[]") ArrayList<String> price,
                                          @Field("userId[]") ArrayList<Integer> id,
                                          @Field("address") String address,
                                          @Field("postCode") String postcode,
                                            @Field("paymentType") Integer payment
                                          );
}
