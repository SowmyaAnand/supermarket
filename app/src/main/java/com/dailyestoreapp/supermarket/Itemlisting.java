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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.ToLongBiFunction;

public class Itemlisting extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));

   // ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    RecyclerView recyclerView,itemlistingcategory;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ItemAdapter customAdapter;
    int initialid;
    ArrayList<String> SubcategoriesEditCategies = new ArrayList<>();
    ArrayList<String> SubcategoriesEditCategies_image = new ArrayList<>();
    ArrayList<Integer> Subcategoriescatno_edit = new ArrayList<>();
    Listingsubcategoryadapter customadapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlisting);
Toolbar t = (Toolbar)findViewById(R.id.toolbar_itemlisting);
        setSupportActionBar(t);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        String sb_names = extras.getString("subCatName");
        String sb_id = extras.getString("subCatId");
        String sb_img = extras.getString("subCatImage");
        String  sb_inital =extras.getString("subInitial");
        String[] cats = sb_names.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : cats) {
            if (!(ct.equals("") || ct.equals(null))) {
                SubcategoriesEditCategies.add(ct);
            }
        }
        String[] numbers = sb_id.split(",");
        for (String number : numbers) {
            if(!(numbers.equals("")||numbers.equals(null)))
            {
                Subcategoriescatno_edit.add(Integer.valueOf(number));
            }

        }
        String[] sb_imagess = sb_img.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : sb_imagess) {
            if (!(ct.equals("") || ct.equals(null))) {
                SubcategoriesEditCategies_image.add(ct);
            }
        }
        initialid= Integer.parseInt(sb_inital);
        Log.e("ItemListing","the values are "+sb_names+sb_id+sb_img+sb_inital);
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
        customadapter2 = new Listingsubcategoryadapter(Itemlisting.this,SubcategoriesEditCategies,SubcategoriesEditCategies_image,Subcategoriescatno_edit,initialid);
        itemlistingcategory.setAdapter(customadapter2);
        //second recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.itemrecycler);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        Log.e("names","the names =="+personNames);
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
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.e("MAin","Item selected ="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.cart:
                Intent cart = new Intent(Itemlisting.this,CartPage.class);
                startActivity(cart);
                return true;
            case R.id.account:
                // do whatever
                Intent cartp = new Intent(Itemlisting.this,Login.class);
                startActivity(cartp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Intent t = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(t);
//        return super.onOptionsItemSelected(item);
//    }
}
