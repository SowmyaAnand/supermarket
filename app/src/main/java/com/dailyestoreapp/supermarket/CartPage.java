package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class CartPage extends AppCompatActivity {
Toolbar tcart;
    RecyclerView recyclerView_cart;
    CartAdapter customAdapter_cart;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3"));
    public static final String MY_PREFS_NAME = "CustomerApp";
    final ArrayList<String> items_name_old_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> items_name_price_cart = new ArrayList<>();
    final ArrayList<String> items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> items_name_count_cart = new ArrayList<>();
    private String tag = "cartpage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        tcart = (Toolbar)findViewById(R.id.toolbar_cart);
        setSupportActionBar(tcart);
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_old = shared.getString("cart_item_names", "");
        if(!(itemSingle_name_old==null)||(itemSingle_name_old.length()==0))
        {
            String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_old_cart.add(ct);
                }

            }
        }
        SharedPreferences shared1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_qnty_old = shared1.getString("cart_item_qnty", "");
        if(!(itemSingle_qnty_old==null)||(itemSingle_qnty_old.length()==0))
        {
            String[] cats = itemSingle_qnty_old .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_quantity_cart.add(ct);
                }

            }
        }
        SharedPreferences shared2 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_price = shared2.getString("cart_item_price", "");
        if(!(itemSingle_name_price==null)||(itemSingle_name_price.length()==0))
        {
            String[] cats = itemSingle_name_price .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_price_cart.add(ct);
                }

            }
        }
        SharedPreferences shared3 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_offerpercent = shared3.getString("cart_item_offer_percent", "");
        if(!(itemSingle_name_offerpercent==null)||(itemSingle_name_offerpercent.length()==0))
        {
            String[] cats = itemSingle_name_offerpercent .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_offer_percentage_cart.add(ct);
                }

            }
        }
        for( int j=0;j<items_name_old_cart.size();j++)
        {
            String ncount = items_name_old_cart.get(j);
            String shrd_name_count =ncount+"_count";
            String itemSingle_name_old_count = shared.getString(shrd_name_count, "");
            items_name_count_cart.add(itemSingle_name_old_count);
        }
        Log.e(tag,"the values in cart are "+items_name_old_cart);
        Log.e(tag,"the values in cart are "+items_name_quantity_cart);
        Log.e(tag,"the values in cart are "+items_name_price_cart);
        Log.e(tag,"the values in cart are "+items_name_offer_percentage_cart);
        Log.e(tag,"the values in cart are "+items_name_count_cart);
        recyclerView_cart = (RecyclerView) findViewById(R.id.itemrecycler_cart);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_cart.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        Log.e("names","the names =="+personNames);
        customAdapter_cart = new CartAdapter(CartPage.this, personNames);
        recyclerView_cart.setAdapter(customAdapter_cart);
        Button checkout= (Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(CartPage.this,Payment.class);
                startActivity(n);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }
}
