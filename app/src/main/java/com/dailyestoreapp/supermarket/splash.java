package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;
import java.util.Iterator;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class splash extends AppCompatActivity {
private static int SPLASH_TIME_OUT = 1000;
    ArrayList<String> item_image_splash = new ArrayList<>();
    ArrayList<String> original_item_image_lts_splash = new ArrayList<>();
    ArrayList<String> original_flyer2_splash = new ArrayList<>();
    ArrayList<String> original_deal_splash = new ArrayList<>();
    ArrayList<String> original_firstpopup_splash = new ArrayList<>();
    private String tag= "splash";

    public static final String MY_PREFS_NAME = "CustomerApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       FirstViewFlyers();
    }
    private void FirstViewFlyers()
    {
        final StringBuilder frst_flyer_images  = new StringBuilder();
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

            Call<CustomerAppResponse> call = mainInterface.allFlyers();
            call.enqueue(new Callback<CustomerAppResponse>() {
                @Override
                public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                    CustomerAppResponse listCategoryResponseobject = response.body();
                    int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                    Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());
                    int data_length = response.body().getResponsedata().getData().size();


                    try {


                        if(success==1) {
                            for (int i = 0; i < data_length; i++) {
                                String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                                String imageurl_total=url1+imageurl;
                                item_image_splash.add(imageurl_total);
                                Log.e("fragment1","the imageurl is===="+imageurl);
                            }
                            original_item_image_lts_splash.addAll(item_image_splash);
                            Iterator<String> itr_string_image = original_item_image_lts_splash.iterator();
                            while (itr_string_image.hasNext())
                            {

                                frst_flyer_images.append(itr_string_image.next());
                                if(itr_string_image.hasNext()){
                                    frst_flyer_images.append(",");
                                }
                            }
                            SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor_frst.putString("first_flyer_new", frst_flyer_images.toString());
                            editor_frst.apply();
                            Log.e("fragment1","the imageurl is===="+original_item_image_lts_splash);


                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

                @Override
                public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

                }
            });


        categoriesList();


    }
    private void categoriesList()
    {
        final StringBuilder strbul  = new StringBuilder();
        final StringBuilder ct  = new StringBuilder();
        final StringBuilder ct_images  = new StringBuilder();

        final ArrayList<String> categories = new ArrayList<>();
        final ArrayList<String> categories_image = new ArrayList<>();
        final ArrayList<Integer> nums = new ArrayList<>();
        String url = "http://dailyestoreapp.com/dailyestore/api/";
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
        Call<CustomerAppResponse> call = mainInterface.CategoryList();
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse catObj = response.body();
                int cat_length = catObj.getResponsedata().getData().size();

//                String res= new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getResponsedata());
//                JsonObject obj = new JsonParser().parse(res).getAsJsonObject();
                try {
//                    JSONObject jo2 = new JSONObject(obj.toString());
//                    JSONArray categoriesarray = jo2.getJSONArray("data");
//                    Log.e(tag,"categoriesarray"+categoriesarray);
//                    Set<Integer> set3 = new HashSet<Integer>();

                    for(int i=0; i<cat_length; i++)
                    {
//                        JSONObject j1= categoriesarray.getJSONObject(i);
//                        String item = j1.getString("itemName");
//                        String item_image = j1.getString("itemImage");
//                        int item_no = Integer.parseInt(j1.getString("typeId"));
                        Datum catObj1 = catObj.getResponsedata().getData().get(i);
                        String item = catObj1.getCategoryName();
                        String item_image = catObj1.getCategoryImage();
                        int item_no = Integer.parseInt(catObj1.getTypeId());
                        nums.add(item_no);
                        categories.add(item);
                        categories_image.add(item_image);


                    }
                    Log.e(tag,"value added "+categories_image);
                    Log.e(tag,"value added "+categories);
                    Log.e(tag,"value added "+nums);

                    Iterator<Integer> iter = nums.iterator();
                    while(iter.hasNext())
                    {
                        strbul.append(iter.next());
                        if(iter.hasNext()){
                            strbul.append(",");
                        }
                    }
                    strbul.toString();

                    // to store categories
                    Log.e("res","res="+strbul);
                    Iterator<String> itr_string = categories.iterator();
                    while (itr_string.hasNext())
                    {

                        ct.append(itr_string.next());
                        if(itr_string.hasNext()){
                            ct.append(",");
                        }
                    }


                    Iterator<String> itr_string_image = categories_image.iterator();
                    while (itr_string_image.hasNext())
                    {

                        ct_images.append(itr_string_image.next());
                        if(itr_string_image.hasNext()){
                            ct_images.append(",");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(tag,"catch exception"+e.getMessage());
                }

                Log.e(tag,"categories = "+categories);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("categories_new", ct.toString());
                Log.e("homefragment","the catgeories shared preference are  login  ="+ct.toString());
                editor.apply();
                if(categories_image.size()>0){
                    Log.e(tag,"images array "+ct_images.toString());
                    SharedPreferences.Editor editor3 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor3.putString("categories_image_new", ct_images.toString());
                    editor3.apply();
                }


                Log.e(tag,"array of numbers "+strbul.toString());
                SharedPreferences.Editor editor2 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("categories_no_new", strbul.toString());
                editor.apply();

//                SharedPreferences.Editor editor3= getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                editor.putString("categories_no", String.valueOf(nums.get(0)));
//                editor.apply();



            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

            }
        });

SecondViewFlyers();
    }
    private void SecondViewFlyers()
    {
        final StringBuilder sec_flyers_images  = new StringBuilder();



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
        Call<CustomerAppResponse> call = mainInterface.allseconfFlyers();
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse listCategoryResponseobject = response.body();
                int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());
                int data_length = response.body().getResponsedata().getData().size();


                try {


                    if(success==1) {

                        for (int i = 0; i < data_length; i++) {
                            String type = response.body().getResponsedata().getData().get(i).getType();
                            Log.e("Firstpopup.this", "selected flyerid =" + type);


                            String selectedflyerid = response.body().getResponsedata().getData().get(i).getFlyId();
                            Log.e("Firstpopup.this", "selected flyerid =" + selectedflyerid);
                            String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                            String imageurl_total = url1 + imageurl;
                            original_flyer2_splash.add(imageurl_total);


                        }
                        Iterator<String> itr_string_image = original_flyer2_splash.iterator();
                        while (itr_string_image.hasNext())
                        {

                            sec_flyers_images.append(itr_string_image.next());
                            if(itr_string_image.hasNext()){
                                sec_flyers_images.append(",");
                            }
                        }
                        SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor_frst.putString("sec_flyers_images_new", sec_flyers_images.toString());
                        editor_frst.apply();

                    }
                    else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

            }
        });



    ViewAllDeals();

    }
    private void ViewAllDeals()
    {
        final StringBuilder viewalldeals_img = new StringBuilder();
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
        Call<CustomerAppResponse> call = mainInterface.viewalldeal();
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse listCategoryResponseobject = response.body();
                int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());
                int data_length = response.body().getResponsedata().getData().size();
                try {

                    if(success==1) {

                        for (int i = 0; i < data_length; i++)

                        {

                            String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                            String did = response.body().getResponsedata().getData().get(i).getDealId();
                            String imageurl_total=url1+imageurl;
                            Log.e("firstpop","the succes value is "+imageurl_total);
                           original_deal_splash.add(imageurl_total);

                        }

                        Iterator<String> itr_string_image = original_flyer2_splash.iterator();
                        while (itr_string_image.hasNext())
                        {

                            viewalldeals_img.append(itr_string_image.next());
                            if(itr_string_image.hasNext()){
                                viewalldeals_img.append(",");
                            }
                        }
                        SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor_frst.putString("viewalldeals_img", viewalldeals_img.toString());
                        editor_frst.apply();
                    }
                    else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

            }
        });
        FirstPopup();


    }
    private void FirstPopup()
    {

        final StringBuilder firstpopup_img = new StringBuilder();

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
        Call<CustomerAppResponse> call = mainInterface.firstpop();
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse listCategoryResponseobject = response.body();
                int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());
                int data_length = response.body().getResponsedata().getData().size();
                
                try {


                    if(success==1) {

                        for (int i = 0; i < data_length; i++)

                        {

                            String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                            String imageurl_total=url1+imageurl;
                            original_firstpopup_splash.add(imageurl_total);
                            
                        }

                        Iterator<String> itr_string_image = original_firstpopup_splash.iterator();
                        while (itr_string_image.hasNext())
                        {

                            firstpopup_img.append(itr_string_image.next());
                            if(itr_string_image.hasNext()){
                                firstpopup_img.append(",");
                            }
                        }
                        SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor_frst.putString("viewalldeals_img", firstpopup_img.toString());
                        editor_frst.apply();
                    }
                    else {

                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {
                
            }
        });

                new Handler().postDelayed(new Runnable()
        {
            @Override
                    public void run()
            {
                Intent homeintent = new Intent(splash.this,welcome.class);
                startActivity(homeintent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }

}
