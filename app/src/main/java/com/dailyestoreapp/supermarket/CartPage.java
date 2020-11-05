package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class CartPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Toolbar tcart;
static EditText coupon_nm;
    String pincode_booking;
EditText name_book,mobile_book,place_book,address_book;
static Button coupon_apply;
static int cod_length=0;
    static int no_cod_length=0;
    String total_address_values_booking;
    RecyclerView recyclerView_cart,recyclerView_cart2;
    CartAdapter customAdapter_cart;

    CartNotEligibleAdapter customAdapter_cart2;
    static TextView sub_tot;
    ACProgressFlower dialog;
    static int no_cod_total=0;
    static String no_cod_total_set="0";
    static String cod_total_set="0";
    static int cod_total=0;
    static Activity thisActivity =null;
    private ArrayList<Integer> validation_items_name_old_cart_id = new ArrayList<>();
    static TextView tot;
    TextView delivery_charge;
    static TextView couponaplliedval;
    String selected_pincode;
    String selected_radio_button_val="PAY COD ELIGIBLE FIRST";
    //RadioGroup r1;
    static Integer minimum_coupon_amnt=1000;
    static String deliverychg="20";
    static String deliverychg1="20.00";
    static String deliverychg_zero="0";
    static String deliverychg1_zero="0.00";
    static Integer minimum_delivery_charge=1000;
    static Integer minimum_amount_prchase=100;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3"));
    ArrayList pincodes = new ArrayList<>(Arrays.asList("400072", "585225"));
    String []pincodess = {"SELECT PINCODE","679335","679502","679503","679504","679505","679506"};
    public static final String MY_PREFS_NAME = "CustomerApp";
    final ArrayList<String> items_name_old_cart = new ArrayList<>();
    final ArrayList<Integer> items_name_old_cart_id = new ArrayList<>();
    static final ArrayList<Integer> removed_positions = new ArrayList<>();
    final ArrayList<String> items_specific_count_cart = new ArrayList<>();
    final ArrayList<String> items_name_image_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> items_name_quantity_cart_new = new ArrayList<>();
    final ArrayList<String> items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> items_name_price_cart = new ArrayList<>();
    final ArrayList<String> items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> items_name_count_cart = new ArrayList<>();
    final ArrayList<String> items_name_count_cart_payment = new ArrayList<>();

    final ArrayList<Integer> items_name_count_cart_integer = new ArrayList<>();
    final ArrayList<Integer> items_index_eligible_cod = new ArrayList<>();
    final ArrayList<Integer> items_index_not_eligible_cod = new ArrayList<>();
    //code eligible
    static final ArrayList<Integer> cod_eligible_items_name_old_cart_id = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_quantity_cart_new = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_eligible_items_name_count_cart = new ArrayList<>();
    final ArrayList<Integer> cod_items_name_count_cart_integer = new ArrayList<>();

    // cod not eligible
    static final ArrayList<Integer> cod_not_eligible_items_name_old_cart_id = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_old_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligibleitems_name_quantity_cart_new = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_quantity_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_image_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_cod_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_price_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_offer_percentage_cart = new ArrayList<>();
    final ArrayList<String> cod_not_eligible_items_name_count_cart = new ArrayList<>();
    final ArrayList<Integer> cod_not_items_name_count_cart_integer = new ArrayList<>();
    Spinner spin;
    static int flag_original_values_cod_befor_remove=1;
    static int flag_original_values_no_cod_befor_remove=1;
    String from_flag;
    private String tag = "cartpage";
    TextView cod_heading1,cod_heading2;
String fullname;
    TextView tot_cart_text;
    TextView cod_reduced_coupon,no_cod_reduced_coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        thisActivity=CartPage.this;
        flag_original_values_cod_befor_remove=1;
        flag_original_values_no_cod_befor_remove=1;
//        no_cod_total=0;
//        cod_total=0;
        coupon_nm = findViewById(R.id.coupon_name);

        coupon_apply=findViewById(R.id.apply_btn_coupon);
        coupon_nm.setEnabled(true);coupon_nm.setFocusable(true);
        couponaplliedval=findViewById(R.id.tot_val2_reducedprice);
        name_book=findViewById(R.id.edit_text_name_booking);
        mobile_book=findViewById(R.id.mobile_booking);
        place_book=findViewById(R.id.edit_text5_place_booking);
        address_book=findViewById(R.id.edit_text10_address_booking);
//        no_cod_total_set="0";
//        cod_total_set="0";
        coupon_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

String coupon_name = coupon_nm.getText().toString();
                String sb_validation = String.valueOf(sub_tot.getText());
            //    int sb_int_validate = Integer.parseInt(sb_validation);
                Double min_coupon = Double.valueOf(minimum_coupon_amnt);
                Double sb_int_validate= Double.valueOf(sb_validation);
                if(sb_int_validate<min_coupon)
                {

                    settoast(thisActivity,"ORDER ABOVE "+minimum_coupon_amnt+" TO AVAIL THE OFFER");
                }
                else
                {
                    couponDetails(coupon_name);
                }
            }
        });
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        from_flag= extras.getString("from_item_flag");
        final SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullname = fullname_shared.getString("fullusername","");
        Log.e("cart","fullname======="+fullname);
        tcart = (Toolbar)findViewById(R.id.toolbar_cart);
        setSupportActionBar(tcart);
        spin = findViewById(R.id.spinner_pincode);
//        r1=findViewById(R.id.payfirst);
//        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton rb1 = (RadioButton) findViewById(checkedId);
//                selected_radio_button_val= String.valueOf(rb1.getText());
//            }
//        });
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pincodess);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin.setAdapter(aa);
         deliverychg ="20";
        deliverychg1 ="20.00";
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
        Integer check_sub_eligible_freedelivery = total_price;
        Integer d;
        if(check_sub_eligible_freedelivery>=minimum_delivery_charge)
        {
            d= Integer.valueOf(deliverychg_zero);
        }
        else
        {
             d = Integer.valueOf(deliverychg);
        }

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

        final StringBuilder it_pay_count  = new StringBuilder();
        Iterator<String> itr_string = items_name_count_cart.iterator();
        while (itr_string.hasNext()) {

            it_pay_count.append(itr_string.next());
            if (itr_string.hasNext()) {
                it_pay_count.append(",");
            }
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("payment_only_cart_item_count", it_pay_count.toString());
        Log.e("homefragment", "the catgeories shared preference are  login  =" + it_pay_count.toString());
        editor.apply();




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
        cod_length = cod_eligible_items_name_old_cart.size();
        no_cod_length=cod_not_eligible_items_name_old_cart.size();
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
           Toast.makeText(CartPage.this,"No items added in cart", LENGTH_SHORT).show();
       }
//        if(!((cod_eligible_items_name_old_cart.size()==0))&&(!(cod_not_eligible_items_name_old_cart.size()==0)))
//       {
//           r1.setVisibility(View.VISIBLE);
//       }
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
String sub_total_minimum_charge = String.valueOf(sub_tot.getText());
Double sub_total_minimum_charge_int = Double.valueOf(sub_total_minimum_charge);
Double mn_amount_purchase = Double.valueOf(minimum_amount_prchase);
if(sub_total_minimum_charge_int>mn_amount_purchase)
{


               // Book_now();
                String name= String.valueOf(name_book.getText());
                String mobile = String.valueOf(mobile_book.getText());
                String place = String.valueOf(place_book.getText());
                String address =String.valueOf(address_book.getText());
if(name.equals("")||mobile.equals("")||place.equals("")||address.equals(""))
{
    Toast.makeText(CartPage.this,"Please fil all the details", LENGTH_SHORT).show();

}
else
{
    int pin_flag=0;
    if(selected_pincode.equals("SELECT PINCODE"))
    {
        Toast.makeText(CartPage.this,"Please select pincode", LENGTH_SHORT).show();

    }
    else
    {
        total_address_values_booking = name+"          "+place+"            "+address+"             "+mobile;
        pincode_booking =selected_pincode;
                String  sub_txt_val  = sub_tot.getText().toString();
                String tot_val = tot.getText().toString();
                String delivery = delivery_charge.getText().toString();
        Intent n = new Intent(CartPage.this,Payment.class);
        Bundle b = new Bundle();
         if(!(cod_length==0)&&(!(no_cod_length==0)))
         {
             Log.e("cart","entered1");
          b.putString("cod_eligible_pay","0");
         } else if (no_cod_length>0) {
             Log.e("cart","entered2");
                  b.putString("cod_eligible_pay","0");
         }
         else
         {
             Log.e("cart","entered3");
                  b.putString("cod_eligible_pay","1");
         }
Log.e("cart","itemid===="+items_name_count_cart+items_name_old_cart_id);
        final ArrayList<Integer> cod_eligible_items_name_old_cart_id_newpayment = new ArrayList<>();
        String coupon_name_for_intent = coupon_nm.getText().toString();
        String tot_for_intent = tot.getText().toString();
        String coupon_applid_amount_for_intent =couponaplliedval.getText().toString();
        Log.e("cart ","new values ==>"+tot_for_intent);
        b.putString("sub_txt_val",sub_txt_val);
        b.putString("tot_val",tot_val);
        b.putString("delivery",delivery);
        b.putString("name_booking",name);
        b.putString("book_address",total_address_values_booking);
        b.putString("pincode_book",pincode_booking);
        b.putString("coupon_name_for_intent",coupon_name_for_intent);
        b.putString("tot_for_intent",tot_for_intent);
        b.putString("coupon_applid_amount_for_intent",coupon_applid_amount_for_intent);
//        b.putSerializable("cod_eligible_items_name_count_cart",cod_eligible_items_name_count_cart);
//        b.putSerializable("cod_eligible_items_name_quantity_cart",cod_eligible_items_name_quantity_cart);
//        b.putSerializable("cod_eligible_items_name_old_cart_id",cod_eligible_items_name_old_cart_id);
//        b.putSerializable("cod_eligible_items_name_price_cart",cod_eligible_items_name_price_cart);
        items_name_count_cart_payment.clear();
        SharedPreferences shared_tot_id = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String itemSingle_name_old_id_pay = shared_tot_id.getString("payment_only_cart_item_count", "");


            String[] cats_pay = itemSingle_name_old_id_pay .split(",");//if spaces are uneven, use \\s+ instead of " "
            for (String ct : cats_pay) {
                if(!(ct.equals("")||ct.equals(null)))
                {
                    String c  = ct;
                    items_name_count_cart_payment.add(c);
                }

            }




        b.putSerializable("cod_eligible_items_name_count_cart",items_name_count_cart_payment);
        b.putSerializable("cod_eligible_items_name_quantity_cart",items_name_quantity_cart);
        b.putSerializable("cod_eligible_items_name_old_cart_id",items_name_old_cart_id);
        b.putSerializable("cod_eligible_items_name_price_cart",items_name_price_cart);

        n.putExtras(b);
        startActivity(n);

   }


}

}
else
{
Toast.makeText(CartPage.this,"MINIMUM ORDER AMOUNT SHOULD BE MORE THAN "+minimum_amount_prchase,LENGTH_SHORT).show();
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
    public static void update_total_values(Integer value,String type_cod_or_not_cod){
        String tot_price_with_colon = String.valueOf(sub_tot.getText());
        Log.e("cart","delete="+tot_price_with_colon);
        Double intval = Double.valueOf(tot_price_with_colon);

        Double newintval = intval-value;
        String t= String.valueOf(newintval);
        Double sb_tt = newintval;
        if(sb_tt>0)
        {
            sub_tot.setText(t);
        }
        else {
            sub_tot.setText("0");
        }
        Double new_del= Double.valueOf(minimum_delivery_charge);
        Double dd;
        if(newintval>new_del)
        {
            dd= Double.valueOf(deliverychg1_zero);
        }
        else
        {
            dd = Double.valueOf(deliverychg1);
        }

        Double totval = newintval+dd;
        String stringtotval = String.valueOf(totval);
        tot.setText(stringtotval);
        if(type_cod_or_not_cod.equals("1"))
        {
            if(cod_length>0)
            {
                cod_length=cod_length-1;
            }

        }
        else
        {
            if(no_cod_length>0)
            {
                no_cod_length=no_cod_length-1;
            }

        }
        Log.e("cart","sub tottt===="+String.valueOf(sub_tot.getText()));
        String coupon_name = coupon_nm.getText().toString();
        String sb_validation = String.valueOf(sub_tot.getText());
        Double sb_int_validate = Double.valueOf(sb_validation);
        Double min_amnt = Double.valueOf(minimum_coupon_amnt);
        if(sb_int_validate<min_amnt)
        {

           settoast(thisActivity,"ORDER ABOVE "+minimum_coupon_amnt+" TO AVAIL THE OFFER");
        }
        else
        {
            couponDetails(coupon_name);
        }

    }
    public static void validate_items(ArrayList<Integer> originall_items_name_old_cart_id){
        int cod_count=0;
        int not_cod_count=0;
        for(int l=0;l<cod_eligible_items_name_old_cart_id.size();l++)
      {

         Integer id= cod_eligible_items_name_old_cart_id.get(l);
         for(int d=0;d<originall_items_name_old_cart_id.size();d++)
          if(id.equals(originall_items_name_old_cart_id.get(d)))
          {
             cod_count=1;
          }

      }
        for(int l=0;l<cod_not_eligible_items_name_old_cart_id.size();l++)
        {

            Integer id= cod_not_eligible_items_name_old_cart_id.get(l);
            for(int d=0;d<originall_items_name_old_cart_id.size();d++)
                if(id.equals(originall_items_name_old_cart_id.get(d)))
                {
                    not_cod_count=1;
                }

        }
        flag_original_values_cod_befor_remove=cod_count;
        flag_original_values_no_cod_befor_remove=not_cod_count;
    }
    private static void couponDetails(String cpname)
    {



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

        Call<CustomerAppResponse> call = mainInterface.viewCouponDetail(cpname);
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {

                CustomerAppResponse listCategoryResponseobject = response.body();
               String success = response.body().getResponsedata().getSuccess();
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());

                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata());

            if(success.equals("1"))
            {
                String tot_price_with_colon = String.valueOf(sub_tot.getText());
                Log.e("cart","delete="+tot_price_with_colon);
                double vl = Double.parseDouble(response.body().getResponsedata().getData().get(0).getPercent());
                Log.e("cart","delete="+vl);
                Double intval = Double.valueOf(tot_price_with_colon);
                Log.e("cart","delete="+intval);
                double percent = Double.valueOf(vl/100);
                Log.e("cart","delete percent ="+percent);

                Double mul_val = percent*intval;
                Log.e("cart","delete mul_val="+percent);
                Log.e("cart","delete="+mul_val);
                Double value = (vl/100)*intval;
                Log.e("cart","delete="+value);
                String reduced_price = "-"+String.valueOf(value);
                couponaplliedval.setText(reduced_price);
                Double newintval = intval-value;
                Log.e("cart","delete="+newintval);
                String t= String.valueOf(newintval);
                Log.e("cart","delete="+t);
                sub_tot.setText(t);
                Double d_coupn;
                Double d_min = Double.valueOf(minimum_delivery_charge);
                if(newintval>d_min)
                {
                    d_coupn = Double.valueOf(deliverychg1_zero);
                }
                else
                {
                    d_coupn = Double.valueOf(deliverychg1);
                }

                Double totval = newintval+d_coupn;
                String stringtotval = String.valueOf(totval);
                tot.setText(stringtotval);

                coupon_apply.setVisibility(View.GONE);
               coupon_nm.setEnabled(false);coupon_nm.setFocusable(false);

            }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {
                Log.e("frag","error="+t.getMessage());
            }
        });





    }


public static void settoast(Context c,String a)
{
    Toast.makeText(c,a,LENGTH_SHORT).show();
}



}
