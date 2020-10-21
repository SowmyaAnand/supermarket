package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dailyestoreapp.supermarket.splash.MY_PREFS_NAME;

public class test extends AppCompatActivity {
RecyclerView dealspage;
DealsPageAdpater dd;
    ArrayList<String> deal_image = new ArrayList<>();
    int  intialdealFlyers=1;
    ArrayList<Integer> deals_type_id_for_click_test = new ArrayList<>();
    ArrayList<String> original_deal_splash = new ArrayList<>();
    ArrayList<String> original_flyer2_splash = new ArrayList<>();
    ArrayList<String> Sub_categories_deals = new ArrayList<>();
    ArrayList<Integer> Sub_categories_id_deals = new ArrayList<>();
    ArrayList<String> Images_sub_deals = new ArrayList<>();
    int intialsub=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar21);
        setSupportActionBar(toolbar);
        dealspage=findViewById(R.id.dealspage);
        SharedPreferences shared_deal_typeId_test = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String deal_type_id_test = shared_deal_typeId_test.getString("deals_typeid", "");
        String[] deal_type_id_numbers_test = deal_type_id_test.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String number : deal_type_id_numbers_test) {
            if(!(deal_type_id_numbers_test.equals("")||deal_type_id_numbers_test.equals(null)))
            {
                deals_type_id_for_click_test.add(Integer.valueOf(number));

            }

        }
        SharedPreferences shared_firstflyer = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String shared_dealflyer = shared_firstflyer.getString("viewalldeals_img", "");
        String[] shared_dealflyer_img = shared_dealflyer.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : shared_dealflyer_img) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                deal_image.add(ct);
            }

        }
        intialdealFlyers = shared_firstflyer.getInt("deal_flyer_initalval",0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        dealspage.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

    dd = new DealsPageAdpater(test.this,deal_image,intialdealFlyers,deals_type_id_for_click_test);
    dealspage.setAdapter(dd);


    }



}
