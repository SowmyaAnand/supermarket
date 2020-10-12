package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myorders extends AppCompatActivity {
RecyclerView orders;
    MyOrdersAdapter customAdapter_offers;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    public static final String MY_PREFS_NAME = "CustomerApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        MyOrders();
        orders = (RecyclerView) findViewById(R.id.itemrecycler_orders);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        orders.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        customAdapter_offers = new MyOrdersAdapter(Myorders.this, personNames);
        orders.setAdapter(customAdapter_offers);
    }
    private void MyOrders()
    {
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String useridd = shared.getString("logged_in_userId","");
        int user_idd = Integer.parseInt(useridd);


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
        Call<CustomerAppResponseMyAccount> call = mainInterface.userOrders(user_idd);
        call.enqueue(new Callback<CustomerAppResponseMyAccount>() {
            @Override
            public void onResponse(Call<CustomerAppResponseMyAccount> call, retrofit2.Response<CustomerAppResponseMyAccount> response) {
                CustomerAppResponseMyAccount listCategoryResponseobject = response.body();
                String success = response.body().getResponsedata().getSuccess();
                //  dialog.dismiss();
                Log.e("frag4","success="+response.body());


            }

            @Override
            public void onFailure(Call<CustomerAppResponseMyAccount> call, Throwable t) {
                //  dialog.dismiss();
                // Toast.makeText(Itemlisting.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}

