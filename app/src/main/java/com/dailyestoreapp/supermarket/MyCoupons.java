package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCoupons extends AppCompatActivity {
RecyclerView coupons;
    CouponAdapter customAdapter_offers;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    ArrayList<String> Coupon_Names = new ArrayList<>();
    ArrayList<String> Coupon_Desc = new ArrayList<>();
    ArrayList<Integer> Coupon_percentage = new ArrayList<>();
    ArrayList<Integer> Coupon_status = new ArrayList<>();
    ArrayList<Integer> Coupon_id = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);
        coupons = (RecyclerView) findViewById(R.id.itemrecycler_coupons);
        // set a LinearLayoutManager with default vertical orientation
        couponList();

    }
    public void couponList()
    {
//        dialog = new ACProgressFlower.Builder(getContext())
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .borderPadding(1)
//
//                .fadeColor(Color.DKGRAY).build();
//        dialog.show();


        String url = "http://dailyestoreapp.com/dailyestore/api/";
        final String url1 = "http://dailyestoreapp.com/dailyestore/";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ResponseInterface mainInterface = retrofit.create(ResponseInterface.class);
        Call<CustomerAppResponse> call = mainInterface.viewallcoupons();
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {

                String success = response.body().getResponsedata().getSuccess();

                Log.e("frag4","success="+success);
                try {
                    if(success.equals("1"))
                    {
                        String cpNames,cpDesc;

                        Integer percnt,st,cpId;
                        cpNames = response.body().getResponsedata().getData().get(1).getCouponName();
                        cpDesc = response.body().getResponsedata().getData().get(1).getDescription();
                        percnt = Integer.parseInt(response.body().getResponsedata().getData().get(1).getPercent());

                        int len = response.body().getResponsedata().getData().size();
                        Log.e("couponsedit","length"+len);
                        Coupon_Names.clear();
                        Coupon_Desc.clear();
                        Coupon_percentage.clear();
                        Coupon_status.clear();
                        Coupon_id.clear();
                        for(int i=0;i<len;i++)
                        {
                            cpNames = response.body().getResponsedata().getData().get(i).getCouponName();
                            cpDesc = response.body().getResponsedata().getData().get(i).getDescription();
                            percnt = Integer.parseInt(response.body().getResponsedata().getData().get(i).getPercent());
                            st = Integer.valueOf(response.body().getResponsedata().getData().get(i).getStatus());
                            cpId= Integer.valueOf(response.body().getResponsedata().getData().get(i).getCouponId());
                            Log.e("couponsedit","++"+cpNames+cpDesc+percnt+st);
                            if(st==1)
                            {
                                Coupon_Names.add(cpNames);
                                Coupon_Desc.add(cpDesc);
                                Coupon_percentage.add(percnt);
                                Coupon_status.add(st);
                                Coupon_id.add(cpId);
                            }

                        }
                    }
                    Log.e("couponsedit","++ values"+Coupon_Names+Coupon_Desc+Coupon_percentage+Coupon_id);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    coupons.setLayoutManager(linearLayoutManager);
                    //  call the constructor of CustomAdapter to send the reference and data to Adapter

                    customAdapter_offers = new CouponAdapter(MyCoupons.this, Coupon_Names,Coupon_Desc,Coupon_percentage,Coupon_status,Coupon_id);
                    coupons.setAdapter(customAdapter_offers);

                }
                catch (Exception e)
                {
                    Log.e("couponsedit","the coupons are exception"+e);
                }
            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {
//                dialog.dismiss();
            }
        });
    }
}
