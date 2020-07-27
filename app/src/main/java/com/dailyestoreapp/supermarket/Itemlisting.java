package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class Itemlisting extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    RecyclerView recyclerView,itemlistingcategory;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ItemAdapter customAdapter;
    Listingsubcategoryadapter customadapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlisting);


        itemlistingcategory = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        itemlistingcategory.setLayoutManager(linearLayoutManager2);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customadapter2 = new Listingsubcategoryadapter(Itemlisting.this, personNames);
        itemlistingcategory.setAdapter(customadapter2);
        //second recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.itemrecycler);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customAdapter = new ItemAdapter(Itemlisting.this, personNames);
        recyclerView.setAdapter(customAdapter);


    }
}
