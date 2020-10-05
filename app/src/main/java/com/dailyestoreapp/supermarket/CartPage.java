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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class CartPage extends AppCompatActivity {
Toolbar tcart;

    RecyclerView recyclerView_cart,recyclerView_cart2;
    CartAdapter customAdapter_cart;
    CartNotEligibleAdapter customAdapter_cart2;
    TextView sub_tot,tot,delivery_charge;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3"));
    ArrayList pincodes = new ArrayList<>(Arrays.asList("400072", "585225"));
    public static final String MY_PREFS_NAME = "CustomerApp";
    final ArrayList<String> items_name_old_cart = new ArrayList<>();
    final ArrayList<String> items_specific_count_cart = new ArrayList<>();
    final ArrayList<String> items_name_image_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> items_name_price_cart = new ArrayList<>();
    final ArrayList<String> items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> items_name_count_cart = new ArrayList<>();

    //code eligible
    final ArrayList<String> cod_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_count_cart = new ArrayList<>();

    // cod not eligible

    final ArrayList<String> cod_not_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_count_cart = new ArrayList<>();

    private String tag = "cartpage";
    TextView cod_heading1,cod_heading2,pincode;

    TextView tot_cart_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        tcart = (Toolbar)findViewById(R.id.toolbar_cart);
        setSupportActionBar(tcart);
        String deliverychg ="110";
        String deliverychg1 ="110.00";
        tot_cart_text=findViewById(R.id.cart_text);
        sub_tot=findViewById(R.id.heading_total_val);
        tot=findViewById(R.id.tot_val2);
        delivery_charge=findViewById(R.id.cart_delivery_val);
pincode = findViewById(R.id.edit_text2);
        delivery_charge.setText(deliverychg1);

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String tot_cart_count = shared.getString("total_count_cart", "");
        if(tot_cart_count.equals(""))
        {
            String cnt = "YOUR CART - (0)";
            tot_cart_text.setText(cnt);
        }
        else
        {
            String cnt = "YOUR CART-"+"("+tot_cart_count+")";
            tot_cart_text.setText(cnt);
        }
        cod_heading1 = findViewById(R.id.cod_head1);
        cod_heading2 = findViewById(R.id.cod_head2);
        SharedPreferences shared_tot = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_old = shared_tot.getString("cart_item_names", "");

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

        for(int i=0;i<items_name_old_cart.size();i++)
        {
            String nm = items_name_old_cart.get(i);
            String sharepreferencename_count = nm+"_count";
            SharedPreferences shared_new = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String itemSingle_name_old_count = shared_new.getString(sharepreferencename_count, "");
            items_specific_count_cart.add(itemSingle_name_old_count);
        }

        SharedPreferences shared13 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_image = shared13.getString("cart_item_image", "");
        if(!(itemSingle_name_image==null)||(itemSingle_name_image.length()==0))
        {
            String[] cats = itemSingle_name_image .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_image_cart.add(ct);
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

        SharedPreferences shared7 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_cod = shared7.getString("cart_item_cod", "");
        if(!(itemSingle_name_cod==null)||(itemSingle_name_cod.length()==0))
        {
            String[] cats = itemSingle_name_cod .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_cod_cart.add(ct);
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
        Integer total_price=0;
        for(int l =0;l<items_name_price_cart.size();l++)
        {
            String tot_price_with_colon = items_name_price_cart.get(l);
            Log.e("cart","the value is "+tot_price_with_colon);
            String[] separated = tot_price_with_colon.split(" ");
            Log.e("cart","the value is "+separated[1] );
          String val = separated[1];
            Log.e("cart","the value is "+val );
          Integer val_price = Integer.valueOf(val);
          String total_count_specific_item = items_specific_count_cart.get(l);
          Integer specific_cnt = Integer.valueOf(total_count_specific_item);
          Integer total_val_prod = specific_cnt*val_price;
         total_price=total_price+total_val_prod ;
         String string_total_val = String.valueOf(total_price);
          Log.e("cart","the value is "+string_total_val);
        }
       String t= String.valueOf(total_price)+".00";
        sub_tot.setText(t);
        Integer d = Integer.valueOf(deliverychg);
        Integer totval = total_price+d;
        String stringtotval = String.valueOf(totval)+".00";
        tot.setText(stringtotval);

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
        Log.e(tag,"the values in cart are "+items_name_image_cart);
        for(int k=0;k<items_name_old_cart.size();k++)
        {
            String cod_val =items_name_cod_cart.get(k);
            if(cod_val.equals("0"))
            {
                 cod_not_eligible_items_name_old_cart.add(items_name_old_cart.get(k));
                 cod_not_eligible_items_name_quantity_cart.add(items_name_quantity_cart.get(k));
                 cod_not_eligible_items_name_cod_cart.add(items_name_cod_cart.get(k));
                cod_not_eligible_items_name_price_cart.add(items_name_price_cart.get(k));
              cod_not_eligible_items_name_offer_percentage_cart.add(items_name_offer_percentage_cart.get(k));
               cod_not_eligible_items_name_count_cart.add(items_name_count_cart.get(k));
                cod_not_eligible_items_image_cart.add(items_name_image_cart.get(k));
            }
            else
            {
                cod_eligible_items_name_old_cart.add(items_name_old_cart.get(k));
                 cod_eligible_items_name_quantity_cart.add(items_name_quantity_cart.get(k));
               cod_eligible_items_name_cod_cart.add(items_name_cod_cart.get(k));
                 cod_eligible_items_name_price_cart.add(items_name_price_cart.get(k));
                 cod_eligible_items_name_offer_percentage_cart.add(items_name_offer_percentage_cart.get(k));
                cod_eligible_items_name_count_cart.add(items_name_count_cart.get(k));
                cod_eligible_items_image_cart.add(items_name_image_cart.get(k));
            }
        }
        Log.e(tag,"the values eligible in cart are "+cod_eligible_items_name_old_cart);
        Log.e(tag,"the values not eligible in cart are "+cod_not_eligible_items_name_old_cart);
        recyclerView_cart = (RecyclerView) findViewById(R.id.itemrecycler_cart);
        recyclerView_cart2 = (RecyclerView) findViewById(R.id.itemrecycler_cart_cod_not_eligible);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView_cart.setLayoutManager(linearLayoutManager);
        recyclerView_cart2.setLayoutManager(linearLayoutManager2);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        Log.e("names","the names =="+personNames);
        if(cod_eligible_items_name_old_cart.size()>0)
        {
            cod_heading1.setVisibility(View.VISIBLE);
            customAdapter_cart = new CartAdapter(CartPage.this,cod_eligible_items_name_old_cart,cod_eligible_items_name_quantity_cart,cod_eligible_items_name_cod_cart,cod_eligible_items_name_price_cart,cod_eligible_items_name_offer_percentage_cart, cod_eligible_items_name_count_cart,cod_eligible_items_image_cart);
            recyclerView_cart.setAdapter(customAdapter_cart);
        }
        if(cod_not_eligible_items_name_old_cart.size()>0)
        {
            cod_heading2.setVisibility(View.VISIBLE);
            customAdapter_cart2 = new CartNotEligibleAdapter(CartPage.this,cod_not_eligible_items_name_old_cart,cod_not_eligible_items_name_quantity_cart,cod_not_eligible_items_name_cod_cart,cod_not_eligible_items_name_price_cart,cod_not_eligible_items_name_offer_percentage_cart, cod_not_eligible_items_name_count_cart,cod_not_eligible_items_image_cart);
            recyclerView_cart2.setAdapter(customAdapter_cart2);
        }
       if((cod_eligible_items_name_old_cart.size()==0)&&(cod_not_eligible_items_name_old_cart.size()==0))
       {
           Toast.makeText(CartPage.this,"No items added in cart",Toast.LENGTH_SHORT).show();
       }
        Button checkout= (Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String pin = String.valueOf(pincode.getText());
               int pin_flag=0;
               for(int h =0;h<pincodes.size();h++)
               {
                   String p = String.valueOf(pincodes.get(h));
                   if(pin.contains(p))
                       pin_flag=1;
               }
               if(pin_flag==0)
               {
                   Toast.makeText(CartPage.this,"Sorry we don't deliver in this pincode area",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Intent n = new Intent(CartPage.this,Payment.class);
                   startActivity(n);
               }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.e("MAin","Item selected ="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.account:
                // do whatever
                Intent cartp = new Intent(CartPage.this,Login.class);
                startActivity(cartp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
