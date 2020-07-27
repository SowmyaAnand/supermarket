package com.dailyestoreapp.supermarket;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.ToLongBiFunction;

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
Toolbar t = (Toolbar)findViewById(R.id.toolbar_itemlisting);
        setSupportActionBar(t);
t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
t.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent prev= new Intent(Itemlisting.this,MainActivity.class);
        startActivity(prev);
    }
});

//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
            customAdapter.filter(text);
                return false;
            }
        });
        return false;
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Intent t = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(t);
//        return super.onOptionsItemSelected(item);
//    }
}