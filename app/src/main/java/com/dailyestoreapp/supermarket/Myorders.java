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
    final ArrayList<String> items_name_myorders = new ArrayList<>();
    final ArrayList<String> items_name_quantity_myorders = new ArrayList<>();
    final ArrayList<Integer> items_name_status_myorders = new ArrayList<>();
    final ArrayList<String> items_image_myorders = new ArrayList<>();
    final ArrayList<Integer> items_count_myorders = new ArrayList<>();
    final ArrayList<String> items_name_price_myorders = new ArrayList<>();
    final ArrayList<String> items_name_offer_percentage_myorders = new ArrayList<>();
    final ArrayList<String> items_name_count_myorders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
       // MyOrders();
        orders = (RecyclerView) findViewById(R.id.itemrecycler_orders);
        // set a LinearLayoutManager with default vertical orientation
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String useridd = shared.getString("logged_in_userId","");
        String  usr_flag =shared.getString("logged_in_flag","");
        if(usr_flag.equals("1"))
        {
            MyOrders();
        }
        else
        {
            Toast.makeText(Myorders.this,"Please Login ",Toast.LENGTH_SHORT).show();
        }
        //  call the constructor of CustomAdapter to send the reference and data to Adapter


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
        Call<CustomerAppResponse> call = mainInterface.userOrders(user_idd);
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse listCategoryResponseobject = response.body();
                String success = response.body().getResponsedata().getSuccess();
                //  dialog.dismiss();
                Log.e("frag4 orders","success="+success);
//                {"responsedata":{"success":"1","data":[{"orderId":"13","cartId":"0","itemId":"32","count":"1","quantity":"QUANTITY: 1","price":"","type":"0","userId":"16","status":"0","createdAt":"2020-10-13 17:56:04","address":"abc","postCode":"585225","itemName":"SAMSUNG"}]}}

                if(success.equals("1"))
                {
                    int data_length = response.body().getResponsedata().getData().size();

                    for(int i=0; i<data_length; i++)
                    {
//                        JSONObject j1= categoriesarray.getJSONObject(i);
//                        String item_name = j1.getString("itemName");
                        String item_name = response.body().getResponsedata().getData().get(i).getItemName();
                        int it_id = Integer.parseInt(response.body().getResponsedata().getData().get(i).getItemId());

                        String item_quant = response.body().getResponsedata().getData().get(i).getQuantity();
                        String item_price = response.body().getResponsedata().getData().get(i).getPrice();
                        Integer item_status = Integer.valueOf(response.body().getResponsedata().getData().get(i).getStatus());
                        String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                       Integer cnt = Integer.valueOf(response.body().getResponsedata().getData().get(i).getCount());
                        String imageurl_total=url1+imageurl;
                        Log.e("itemlisting","imageurl"+url1+imageurl);

                        items_name_quantity_myorders.add(item_quant);
                        items_name_price_myorders.add(item_price);
                       items_name_myorders.add(item_name);
                        items_name_status_myorders.add(item_status);
                        items_count_myorders.add(cnt);
                        items_image_myorders.add(imageurl_total);


                    }

                    Log.e("myorders","orders are ="+items_name_quantity_myorders);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    orders.setLayoutManager(linearLayoutManager);
                    customAdapter_offers = new MyOrdersAdapter(Myorders.this, items_name_myorders,items_name_quantity_myorders,items_name_status_myorders,items_count_myorders,items_name_price_myorders,items_image_myorders);
                    orders.setAdapter(customAdapter_offers);
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {
                //  dialog.dismiss();
                // Toast.makeText(Itemlisting.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}

