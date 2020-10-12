package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Toolbar tcart;

    RecyclerView recyclerView_cart,recyclerView_cart2;
    CartAdapter customAdapter_cart;
    CartNotEligibleAdapter customAdapter_cart2;
    static TextView sub_tot;
    static TextView tot;
    TextView delivery_charge;
    String selected_pincode;
    String selected_radio_button_val="PAY COD ELIGIBLE FIRST";
    RadioGroup r1;
    static String deliverychg;
    static String deliverychg1;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3"));
    ArrayList pincodes = new ArrayList<>(Arrays.asList("400072", "585225"));
    String []pincodess = {"SELECT PINCODE","400072","585225"};
    public static final String MY_PREFS_NAME = "CustomerApp";
    final ArrayList<String> items_name_old_cart = new ArrayList<>();
    final ArrayList<Integer> items_name_old_cart_id = new ArrayList<>();
    final ArrayList<String> items_specific_count_cart = new ArrayList<>();
    final ArrayList<String> items_name_image_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart_new = new ArrayList<>();
    final ArrayList<String> items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> items_name_price_cart = new ArrayList<>();
    final ArrayList<String> items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> items_name_count_cart = new ArrayList<>();
    final ArrayList<Integer> items_name_count_cart_integer = new ArrayList<>();
    final ArrayList<Integer> items_index_eligible_cod = new ArrayList<>();
    final ArrayList<Integer> items_index_not_eligible_cod = new ArrayList<>();
    //code eligible
    final ArrayList<Integer> cod_eligible_items_name_old_cart_id = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_count_cart = new ArrayList<>();

    // cod not eligible
    final ArrayList<Integer> cod_not_eligible_items_name_old_cart_id = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_count_cart = new ArrayList<>();
    Spinner spin;
    String from_flag;
    private String tag = "cartpage";
    TextView cod_heading1,cod_heading2;
String fullname;
    TextView tot_cart_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        from_flag= extras.getString("from_item_flag");
        final SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullname = fullname_shared.getString("fullusername","");
        Log.e("cart","fullname======="+fullname);
        tcart = (Toolbar)findViewById(R.id.toolbar_cart);
        setSupportActionBar(tcart);
        spin = findViewById(R.id.spinner_pincode);
        r1=findViewById(R.id.payfirst);
        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(checkedId);
                selected_radio_button_val= String.valueOf(rb1.getText());
            }
        });
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pincodess);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin.setAdapter(aa);
         deliverychg ="110";
        deliverychg1 ="110.00";
        tot_cart_text=findViewById(R.id.cart_text);
        sub_tot=findViewById(R.id.heading_total_val);
        tot=findViewById(R.id.tot_val2);
        delivery_charge=findViewById(R.id.cart_delivery_val);

        delivery_charge.setText(deliverychg1);

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String total_count_cart = fullname+"total_count_cart";
        String tot_cart_count = shared.getString(total_count_cart, "");
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
        Log.e("cart","fullname name ="+fullname);
        String cart_item_names = fullname+"cart_item_names";
        Log.e("cart","fullname name="+fullname);
        String itemSingle_name_old = shared_tot.getString(cart_item_names, "");

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

        SharedPreferences shared_tot_id = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Log.e("cart","fullname id="+fullname);
        String cart_item_names_id = fullname+"cart_item_names_id";
        Log.e("cart","fullname id="+fullname);
        String itemSingle_name_old_id = shared_tot_id.getString(cart_item_names_id, "");

        if(!(itemSingle_name_old_id==null)||(itemSingle_name_old_id.length()==0))
        {
            String[] cats = itemSingle_name_old_id .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    Integer v = Integer.valueOf(ct);
                    items_name_old_cart_id.add(v);
                }

            }
        }

Log.e("cart","the names="+items_name_old_cart_id);
        for(int i=0;i<items_name_old_cart.size();i++)
        {
            String nm = items_name_old_cart.get(i);
            String sharepreferencename_count = fullname+nm+"_count";
            SharedPreferences shared_new = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String itemSingle_name_old_count = shared_new.getString(sharepreferencename_count, "");
            items_specific_count_cart.add(itemSingle_name_old_count);
            Log.e("cart","items_specific_count_cart=="+items_specific_count_cart+sharepreferencename_count);
        }

        SharedPreferences shared13 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Log.e("cart","fullname image="+fullname);
        String cart_item_image = fullname+"cart_item_image";
        Log.e("cart","fullname image="+fullname);
        String itemSingle_name_image = shared13.getString(cart_item_image, "");
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
        Log.e("cart","fullname qty="+fullname);
        String cart_item_qnty =fullname+"cart_item_qnty";
        Log.e("cart","fullname="+fullname);
        String itemSingle_qnty_old = shared1.getString(cart_item_qnty, "");
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
        Log.e("cart","fullname="+fullname);
        String cart_item_cod = fullname+"cart_item_cod";
        Log.e("cart","fullname="+fullname);
        Log.e("cart","fullname="+cart_item_cod);
        String itemSingle_name_cod = shared7.getString(cart_item_cod, "");
        if(!(itemSingle_name_cod==null)||(itemSingle_name_cod.length()==0))
        {
            String[] cats = itemSingle_name_cod.split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    items_name_cod_cart.add(ct);
                }

            }
        }
Log.e("cart","cod values ==="+items_name_cod_cart+itemSingle_name_cod+cart_item_cod);


        SharedPreferences shared2 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String cart_item_price = fullname+"cart_item_price";
        String itemSingle_name_price = shared2.getString(cart_item_price, "");
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
            Log.e("cart","the value items_specific_count_cart "+items_specific_count_cart+items_name_price_cart );
          Integer val_price = Integer.valueOf(val);
          String total_count_specific_item = items_specific_count_cart.get(l);
          Integer specific_cnt = Integer.valueOf(total_count_specific_item);
          Integer total_val_prod = specific_cnt*val_price;
            Log.e("cart","the value summ is "+specific_cnt+val_price );
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
        String cart_item_offer_percent = fullname+"cart_item_offer_percent";
        String itemSingle_name_offerpercent = shared3.getString(cart_item_offer_percent, "");
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
        Log.e("cart","offerpercentage ="+cart_item_offer_percent);
        Log.e("cart","offerpercentage ="+fullname);
        Log.e("cart","offerpercentage ="+items_name_offer_percentage_cart);
        for( int j=0;j<items_name_old_cart.size();j++)
        {
            String ncount = items_name_old_cart.get(j);
            String shrd_name_count =fullname+ncount+"_count";
            String itemSingle_name_old_count = shared.getString(shrd_name_count, "");
            items_name_count_cart.add(itemSingle_name_old_count);

        }
        Log.e(tag,"the values name in cart are "+items_name_old_cart);
        Log.e(tag,"the valuesquantity in cart are "+items_name_quantity_cart);
        Log.e(tag,"the valuesprice in cart are "+items_name_price_cart);
        Log.e(tag,"the valuesoffer_percentage in cart are "+items_name_offer_percentage_cart);
        Log.e(tag,"the values name_countin cart are "+items_name_count_cart);
        Log.e(tag,"the valuesname_image in cart are "+items_name_image_cart);
        Log.e(tag,"the valuesitems_name_cod_cart in cart are "+items_name_cod_cart);
        Log.e(tag,"the valuesitems_name_cod_cart in cart are "+items_name_old_cart_id);
        for(int k=0;k<items_name_old_cart.size();k++)
        {
            String cod_val =items_name_cod_cart.get(k);
            if(cod_val.equals("0"))
            {
                items_index_not_eligible_cod.add(k);
                cod_not_eligible_items_name_old_cart_id.add(items_name_old_cart_id.get(k));
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
                items_index_eligible_cod.add(k);
                cod_eligible_items_name_old_cart_id.add(items_name_old_cart_id.get(k));
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
            customAdapter_cart = new CartAdapter(CartPage.this,items_name_old_cart_id,cod_eligible_items_name_old_cart,cod_eligible_items_name_quantity_cart,cod_eligible_items_name_cod_cart,cod_eligible_items_name_price_cart,cod_eligible_items_name_offer_percentage_cart, cod_eligible_items_name_count_cart,cod_eligible_items_image_cart,items_name_old_cart,items_name_quantity_cart,items_name_cod_cart,items_name_price_cart,items_name_offer_percentage_cart,items_name_count_cart,items_name_image_cart,items_index_eligible_cod,from_flag);
            recyclerView_cart.setAdapter(customAdapter_cart);
        }
        if(cod_not_eligible_items_name_old_cart.size()>0)
        {
            cod_heading2.setVisibility(View.VISIBLE);
            customAdapter_cart2 = new CartNotEligibleAdapter(CartPage.this,items_name_old_cart_id,cod_not_eligible_items_name_old_cart,cod_not_eligible_items_name_quantity_cart,cod_not_eligible_items_name_cod_cart,cod_not_eligible_items_name_price_cart,cod_not_eligible_items_name_offer_percentage_cart, cod_not_eligible_items_name_count_cart,cod_not_eligible_items_image_cart,items_name_old_cart,items_name_quantity_cart,items_name_cod_cart,items_name_price_cart,items_name_offer_percentage_cart,items_name_count_cart,items_name_image_cart,items_index_not_eligible_cod,from_flag);
            recyclerView_cart2.setAdapter(customAdapter_cart2);
        }
       if((cod_eligible_items_name_old_cart.size()==0)&&(cod_not_eligible_items_name_old_cart.size()==0))
       {
           Toast.makeText(CartPage.this,"No items added in cart",Toast.LENGTH_SHORT).show();
       }
        if(!((cod_eligible_items_name_old_cart.size()==0))&&(!(cod_not_eligible_items_name_old_cart.size()==0)))
       {
           r1.setVisibility(View.VISIBLE);
       }
        else if(!((cod_eligible_items_name_old_cart.size()==0)))
        {
            selected_radio_button_val="PAY COD ELIGIBLE FIRST";
        }
        else
        {
            selected_radio_button_val="PAY COD NOT ELIGIBLE FIRST";
        }
        Button checkout= (Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Book_now();


               int pin_flag=0;
              if(selected_pincode.equals("SELECT PINCODE"))
              {
                  Toast.makeText(CartPage.this,"Please select pincode",Toast.LENGTH_SHORT).show();

              }
              else
              {
                  if(selected_radio_button_val.equals("PAY COD ELIGIBLE FIRST"))
                  {
                      Log.e("cart","the selected pincode is"+spin.getSelectedItemPosition());
                      Intent n = new Intent(CartPage.this,Payment.class);
                      Bundle b = new Bundle();
                      b.putString("cod_eligible_pay","1");
                      n.putExtras(b);
                      startActivity(n);
                  }
                  else
                  {
                      Log.e("cart","the selected pincode is"+spin.getSelectedItemPosition());
                      Intent n = new Intent(CartPage.this,Payment.class);
                      Bundle b = new Bundle();
                      b.putString("cod_eligible_pay","0");
                      n.putExtras(b);
                      startActivity(n);
                  }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_pincode=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public static void update_total(String value){
//        try{
//            txt.setText(value);
//        }
//        catch (Exception ex){
//            Log.d("Exception","Exception of type"+ex.getMessage());
//        }
    }
    public static void update_total_values(Integer value){
        String tot_price_with_colon = String.valueOf(sub_tot.getText());
        Log.e("cart","delete="+tot_price_with_colon);
        Double intval = Double.valueOf(tot_price_with_colon);

        Double newintval = intval-value;
        String t= String.valueOf(newintval);
        sub_tot.setText(t);
        Double d = Double.valueOf(deliverychg1);
        Double totval = newintval+d;
        String stringtotval = String.valueOf(totval);
        tot.setText(stringtotval);
    }
public void Book_now() {
    String login_type="0";
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
    items_name_count_cart_integer.clear();
    for(int k=0;k<items_name_count_cart.size();k++)
    {
        Integer g = Integer.valueOf(items_name_count_cart.get(k));
        items_name_count_cart_integer.add(g);
    }
    items_name_quantity_cart_new.clear();
    for(int l=0;l<items_name_quantity_cart.size();l++)
    {
        String q =items_name_quantity_cart.get(l);
        String[] separated = q.split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        items_name_quantity_cart_new.add(val);
    }
    Log.e("cart","checkout param=    itemid ====>"+items_name_old_cart_id);
    Log.e("cart","checkout param=    count====>"+items_name_count_cart_integer);
    Log.e("cart","checkout param=    quantity ====>"+items_name_quantity_cart_new);
    Log.e("cart","checkout param=    type ====>"+0);
    Log.e("cart","checkout param=    userid ====>"+4);
    Log.e("cart","checkout param=    address ====> abc");
    Log.e("cart","checkout param=    itemid ====>585225");
    SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    String useridd = shared.getString("logged_in_userId","");
    int user_int_id = Integer.parseInt(useridd);
    final ArrayList<Integer> user_cart_id = new ArrayList<>();
    final ArrayList<Integer> type_cart_id = new ArrayList<>();
    for(int g =0;g<items_name_old_cart_id.size();g++)
    {
        user_cart_id.add(user_int_id);
        type_cart_id.add(0);
    }

    Call<CustomerAppResponseLogin> call = mainInterface.checkoutapi(items_name_old_cart_id,items_name_count_cart_integer,items_name_quantity_cart,type_cart_id,user_cart_id,"abc","585225");
    call.enqueue(new Callback<CustomerAppResponseLogin>() {
        @Override
        public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
            CustomerAppResponseLogin obj =response.body();
            Log.e("login","success="+response.body().getResponsedata());
            int success = Integer.parseInt(obj.getResponsedata().getSuccess());
            Log.e("login","success="+success);

            if(success==1)
            {


            }
            else
            {

                Toast.makeText(CartPage.this,"Invalid Username and Password",Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
            Toast.makeText(CartPage.this,t.getMessage(),Toast.LENGTH_SHORT).show();

        }
    });

}
}
