package com.dailyestoreapp.supermarket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    String fullusername_global;
    ArrayList<String> lts=new ArrayList<String>();
    ArrayList pnames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    ArrayList<String> item_image_adapter = new ArrayList<>();
    ArrayList<String> Item_categories_adapter = new ArrayList<>();
    ArrayList<String> Item_cod_adapter = new ArrayList<>();
    ArrayList<String> Item_Refunf_adapter = new ArrayList<>();
    ArrayList<String> Item_categories_offer_desc_adapter = new ArrayList<>();
//    ArrayList<Integer> Item_Quantity_adapter = new ArrayList<>();
ArrayList<String> Item_Quantity_adapter = new ArrayList<>();
    ArrayList<Integer> Item_Price_adapter = new ArrayList<>();
    ArrayList<Integer> Sub_categories_id_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_offer_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_status_adapter = new ArrayList<>();
    ArrayList<String> Item_description = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    String ct_name;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    public static final String MY_PREFS_NAME = "CustomerApp";
    int quantity=1;
    final String url1 = "http://dailyestoreapp.com/dailyestore/";
    public ItemAdapter(Context context,ArrayList Item_categories_adapter,ArrayList Item_Quantity_adapter,ArrayList Item_Price_adapter,ArrayList item_id_adapter,ArrayList item_image_adapter,ArrayList Item_categories_offer_desc_adapter,ArrayList item_id_offer_adapter, ArrayList Item_cod_adapter,
    ArrayList Item_Refunf_adapter,  ArrayList Item_description, String cat_nane) {
        this.context = context;
        this.Item_categories_adapter=Item_categories_adapter;
        this.Item_Quantity_adapter=Item_Quantity_adapter;
        this.Item_Price_adapter=Item_Price_adapter;
        this.item_id_adapter=item_id_adapter;
        this.item_image_adapter=item_image_adapter;
        this.Item_categories_offer_desc_adapter=Item_categories_offer_desc_adapter;
        this.item_id_offer_adapter=item_id_offer_adapter;
        this.lts.addAll(Item_categories_adapter);
        this.Item_cod_adapter=Item_cod_adapter;
        this.Item_Refunf_adapter =Item_Refunf_adapter;
        this.Item_description = Item_description;
        this.ct_name=cat_nane;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ArrayList<String> items_name_old = new ArrayList<>();
        final ArrayList<Integer> items_name_old_idd = new ArrayList<>();
        final ArrayList<String> items_name_image= new ArrayList<>();
        final ArrayList<String> items_name_quantity = new ArrayList<>();
        final ArrayList<String> items_name_price = new ArrayList<>();
        final ArrayList<String> items_name_cod = new ArrayList<>();
        final ArrayList<String> items_name_offer_percentage = new ArrayList<>();
        final StringBuilder it  = new StringBuilder();
        final StringBuilder it_id  = new StringBuilder();
        final StringBuilder it_qnty  = new StringBuilder();
        final StringBuilder it_ig  = new StringBuilder();
        final StringBuilder it_price  = new StringBuilder();
        final StringBuilder it_cod  = new StringBuilder();
        final StringBuilder it_offer_percnt  = new StringBuilder();
        final SharedPreferences fullname_shared = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullusername_global = fullname_shared.getString("fullusername","");
        //second addition
        // set the data in items
        String name =  Item_categories_adapter.get(position);
       holder.name.setText(name);
       String item_qty = "QUANTITY: "+Item_Quantity_adapter.get(position);
       holder.item_inital_qty.setText(item_qty);
       String amt = String.valueOf(Item_Price_adapter.get(position));
       String at = "RS: "+amt;
       holder.amont.setText(at);
       final int o = item_id_offer_adapter.get(position);
        if(ct_name.equals("Cakes")||ct_name.equals("CAKES")||ct_name.equals("Cake")||ct_name.equals("CAKE"))
        {
            holder.preoder_btn.setVisibility(View.VISIBLE);
        }
        else 
        {
            holder.preoder_btn.setVisibility(View.GONE);
        }
            holder.preoder_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         Intent n = new Intent(context,Main3Activity.class);
               Bundle b = new Bundle();
               b.putInt("item_id",item_id_adapter.get(position));
               b.putInt("prce",Item_Price_adapter.get(position));
               b.putString("quant",Item_Quantity_adapter.get(position));
               n.putExtras(b);
         context.startActivity(n);

           }
       });
       if(Item_categories_offer_desc_adapter.get(position).matches("")||Item_categories_offer_desc_adapter.get(position).equals("none"))
       {
           holder.ofrdec.setText("");
       }
       else
       {
           holder.ofrdec.setText(Item_categories_offer_desc_adapter.get(position));
       }


           String offerp = String.valueOf(item_id_offer_adapter.get(position));
           String offerpercnt = offerp+"% OFF";
           if(offerpercnt.equals("0% OFF"))
           {
               holder.offer_percent.setText("");
           }
           else
           {
               holder.offer_percent.setText(offerpercnt);
           }

        String Sb_img =item_image_adapter.get(position);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_item);

       holder.addbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences login_flag = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
               String log_in_flag = login_flag.getString("logged_in_flag","");

               if(log_in_flag.equals("1"))
               {
                   Integer item_old_val;
                   final StringBuilder it_count  = new StringBuilder();
                   final ArrayList<String> items_name_count = new ArrayList<>();

                   holder.addbtn.setVisibility(View.GONE);


                   // check if cart has older values
                   SharedPreferences shared_new = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname = shared_new.getString("fullusername","");
                   String name_item_nm = String.valueOf(holder.name.getText());
                   String sharepreferencename_count = fullname+name_item_nm+"_count";

                   String itemSingle_name_old_count = shared_new.getString(sharepreferencename_count, "");
                   if(itemSingle_name_old_count.equals("")||itemSingle_name_old_count.length()==0)
                   {
                       item_old_val=0;
                   }
                   else
                   {
                       item_old_val= Integer.valueOf(itemSingle_name_old_count);
                   }
                   //Integer qty_vl_from_text = Item_Quantity_adapter.get(position);
                   //Log.e("itemadapter","the integer val for add qty is "+qty_vl_from_text);
                  // item_old_val=item_old_val+qty_vl_from_text;
                  ++item_old_val;


                   //add new volaues
                   String new_count_val = String.valueOf(item_old_val);

                   items_name_count.add(new_count_val);

                   SharedPreferences.Editor editor5 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                   editor5.putString(sharepreferencename_count, new_count_val);
                   Log.e("homefragment","the catgeories shared preference are  login count for   ="+sharepreferencename_count+it_count.toString());
                   editor5.apply();

                   SharedPreferences shared = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname2 = shared_new.getString("fullusername","");
                   String cart_item_names = fullname2+"cart_item_names";
                   String itemSingle_name_old = shared.getString(cart_item_names, "");
                   if(!(itemSingle_name_old.equals(""))||(itemSingle_name_old.length()==0))
                   {
                       String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_old.add(ct);
                       }

                       }
                   }
Log.e("itemadapter","first befre add  add btn "+cart_item_names+items_name_old);
                   SharedPreferences shared_id = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname3 = shared_new.getString("fullusername","");
                   String shared_cart_item_names_id = fullname3+"cart_item_names_id";
                   String itemSingle_name_old_id = shared_id.getString(shared_cart_item_names_id, "");
                   if(!(itemSingle_name_old_id.equals(""))||(itemSingle_name_old_id.length()==0))
                   {
                       String[] cats = itemSingle_name_old_id .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               Integer c= Integer.valueOf(ct);

                               items_name_old_idd.add(c);
                           }

                       }
                   }


                   SharedPreferences shared10 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname4 = shared_new.getString("fullusername","");
                   String shared_cart_item_image = fullname4+"cart_item_image";
                   String itemSingle_name_old_images = shared10.getString(shared_cart_item_image, "");
                   if(!(itemSingle_name_old_images.equals(""))||(itemSingle_name_old_images.length()==0))
                   {
                       String[] cats = itemSingle_name_old_images .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_image.add(ct);
                           }

                       }
                   }

                   SharedPreferences shared1 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname5 = shared_new.getString("fullusername","");
                   String cart_item_qty = fullname5+"cart_item_qnty";
                   String itemSingle_qnty_old = shared1.getString(cart_item_qty, "");
                   if(!(itemSingle_qnty_old==null)||(itemSingle_qnty_old.length()==0))
                   {
                       String[] cats = itemSingle_qnty_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_quantity.add(ct);
                           }

                       }
                   }
                   SharedPreferences shared2 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname6 = shared_new.getString("fullusername","");
                   String cart_item_price=fullname6+"cart_item_price";
                   String itemSingle_name_price = shared2.getString(cart_item_price, "");
                   if(!(itemSingle_name_price==null)||(itemSingle_name_price.length()==0))
                   {
                       String[] cats = itemSingle_name_price .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_price.add(ct);
                           }

                       }
                   }
                   SharedPreferences shared7 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname7 = shared_new.getString("fullusername","");
                   String cart_item_cod = fullname7+"cart_item_cod";
                   String itemSingle_name_cod = shared7.getString(cart_item_cod, "");
                   if(!(itemSingle_name_cod==null)||(itemSingle_name_cod.length()==0))
                   {
                       String[] cats = itemSingle_name_cod .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_cod.add(ct);
                           }

                       }
                   }
                   SharedPreferences shared3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String fullname8 = shared_new.getString("fullusername","");
                   String cart_item_offer_percent = fullname8+"cart_item_offer_percent";
                   String itemSingle_name_offerpercent = shared3.getString(cart_item_offer_percent, "");
                   if(!(itemSingle_name_offerpercent==null)||(itemSingle_name_offerpercent.length()==0))
                   {
                       String[] cats = itemSingle_name_offerpercent .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_offer_percentage.add(ct);
                           }

                       }
                   }

                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_old);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_old_idd);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_offer_percentage);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_cod);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_image);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_count);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_price);
                   Log.e("itemadapter","the inittal cvalues of cart item in first add"+items_name_quantity);

                   //add new values
                   String nm= String.valueOf(holder.name.getText());
                   if(!(items_name_old.contains(nm))) {
                       items_name_old.add(nm);
                       Integer adapter_id  =  item_id_adapter.get(position);
                       items_name_old_idd.add(adapter_id);
                       Integer increment_val = Itemlisting.Get_counter();
                       String new_val = String.valueOf(++increment_val);
                       Itemlisting.update_counter(new_val);
                       Main2Activity.update_counter(new_val);
                       SharedPreferences.Editor editor_tot = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String fullname9 = shared_new.getString("fullusername","");
                       String total_count_cart = fullname9+"total_count_cart";
                       editor_tot.putString(total_count_cart, new_val);
                       editor_tot.apply();
                       Iterator<String> itr_string = items_name_old.iterator();
                       while (itr_string.hasNext()) {

                           it.append(itr_string.next());
                           if (itr_string.hasNext()) {
                               it.append(",");
                           }
                       }
                       SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String fullname10 = shared_new.getString("fullusername","");
                       String cart_item_names_put = fullname10+"cart_item_names";
                       String cart_Items_toolbar_count = fullname10+"cart_Items_toolbar_count";
                       editor.putString(cart_item_names_put, it.toString());
                       editor.putString(cart_Items_toolbar_count, String.valueOf(items_name_old.size()));
                       Log.e("itemadapter","first after add add btn "+cart_item_names+items_name_old);

                       Log.e("homefragment", "the catgeories shared preference are  login  =" + it.toString());
                       editor.apply();

                       Iterator<Integer> itr_string_id = items_name_old_idd.iterator();
                       while (itr_string_id.hasNext()) {

                           it_id.append(itr_string_id.next());
                           if (itr_string_id.hasNext()) {
                               it_id.append(",");
                           }
                       }
                       SharedPreferences.Editor editor_id = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                     String cart_item_names_id_shared = fullusername_global+"cart_item_names_id";
                       editor_id.putString(cart_item_names_id_shared, it_id.toString());
                       editor_id.apply();

                       String images = item_image_adapter.get(position);
                       items_name_image.add(images);
                       Iterator<String> itr_string_ig = items_name_image.iterator();
                       while (itr_string_ig.hasNext()) {

                           it_ig.append(itr_string_ig.next());
                           if (itr_string_ig.hasNext()) {
                               it_ig.append(",");
                           }
                       }
                       SharedPreferences.Editor editor11 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_image_shared =fullusername_global+"cart_item_image";
                      String cart_Items_toolbar_count_shared=fullusername_global+"cart_Items_toolbar_count";
                       editor11.putString(cart_item_image_shared, it_ig.toString());
                       editor11.putString(cart_Items_toolbar_count_shared, String.valueOf(items_name_image.size()));
                       Log.e("homefragment", "the catgeories shared preference are  login  =" + it_ig.toString());
                       editor11.apply();


                       String qt = String.valueOf(holder.item_inital_qty.getText());
                       Log.e("item", "q==" + qt);
                       Log.e("item", "q==" + items_name_quantity);

                       items_name_quantity.add(qt);

                       Log.e("item", "q==" + items_name_quantity);
                       Iterator<String> itr_string_qty = items_name_quantity.iterator();
                       while (itr_string_qty.hasNext()) {
                           Log.e("item", "q==" + itr_string_qty);

                           it_qnty.append(itr_string_qty.next());
                           Log.e("item", "q==" + it_qnty);
                           if (itr_string_qty.hasNext()) {
                               it_qnty.append(",");
                               Log.e("item", "q==" + it_qnty);
                           }
                           Log.e("item", "q==" + it_qnty);
                       }
                       SharedPreferences.Editor editor2 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_qnty_shared =fullusername_global+"cart_item_qnty";
                       editor2.putString(cart_item_qnty_shared, it_qnty.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login quantity =" + it_qnty.toString());
                       editor2.apply();


                       String cod_cod = Item_cod_adapter.get(position);

                       items_name_cod.add(cod_cod);

                       Log.e("homefragment", "the catgeories shared preference are  login  cod =" + items_name_cod+cod_cod);

                       Iterator<String> itr_string_cod = items_name_cod.iterator();
                       while (itr_string_cod.hasNext()) {

                           it_cod.append(itr_string_cod.next());
                           if (itr_string_cod.hasNext()) {
                               it_cod.append(",");
                           }
                       }
                       SharedPreferences.Editor editor8 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_cod_shared =fullusername_global+"cart_item_cod";
                       editor8.putString(cart_item_cod_shared, it_cod.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login  cod =" + it_cod.toString());
                       editor8.apply();


                       String pr = String.valueOf(holder.amont.getText());

                       items_name_price.add(pr);


                       Iterator<String> itr_string_price = items_name_price.iterator();
                       while (itr_string_price.hasNext()) {

                           it_price.append(itr_string_price.next());
                           if (itr_string_price.hasNext()) {
                               it_price.append(",");
                           }
                       }
                       SharedPreferences.Editor editor3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String cart_item_price_shared =fullusername_global+"cart_item_price";
                       editor3.putString(cart_item_price_shared, it_price.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login  =" + it_price.toString());
                       editor3.apply();


                       String op = String.valueOf(holder.offer_percent.getText());


                       if (op.equals("")) {
                           op = "0";
                       }
                       items_name_offer_percentage.add(op);


                       Iterator<String> itr_string_offerpercentage = items_name_offer_percentage.iterator();
                       while (itr_string_offerpercentage.hasNext()) {

                           it_offer_percnt.append(itr_string_offerpercentage.next());
                           if (itr_string_offerpercentage.hasNext()) {
                               it_offer_percnt.append(",");
                           }
                       }
                       SharedPreferences.Editor editor4 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String cart_item_offer_percent_shared = fullusername_global+"cart_item_offer_percent";
                       editor4.putString(cart_item_offer_percent_shared, it_offer_percnt.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login  =" + it_offer_percnt.toString());
                       editor4.apply();
                   }

               }
               else
               {
                   Toast.makeText(context,"Please Login Before Adding To The Cart",Toast.LENGTH_SHORT).show();
                    Intent n = new Intent(context,Login.class);
                    context.startActivity(n);
               }
           }
       });
       holder.addition.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final StringBuilder new_it  = new StringBuilder();
               final StringBuilder new_it_id  = new StringBuilder();
               final StringBuilder new_it_qnty  = new StringBuilder();
               final StringBuilder new_it_ig  = new StringBuilder();
               final StringBuilder new_it_price  = new StringBuilder();
               final StringBuilder new_it_cod  = new StringBuilder();
               final StringBuilder new_it_offer_percnt  = new StringBuilder();
               Integer item_old_val;
               final StringBuilder it_count  = new StringBuilder();
               final ArrayList<String> items_name_count = new ArrayList<>();

               String q = String.valueOf(holder.quantityy.getText());
               quantity= Integer.parseInt(q);
               quantity=quantity+1;
               String stringquantity = String.valueOf(quantity);
              holder.quantityy.setText(stringquantity);
              // get the old values
               String name_item_nm = String.valueOf(holder.name.getText());

               String sharepreferencename_count = fullusername_global+name_item_nm+"_count";
               SharedPreferences shared = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
               String itemSingle_name_old_count = shared.getString(sharepreferencename_count, "");
               if(itemSingle_name_old_count.equals("")||itemSingle_name_old_count.length()==0)
               {
                   item_old_val=0;
               }
               else
               {
                   item_old_val= Integer.valueOf(itemSingle_name_old_count);
               }
               //Integer qty_vl_from_text = Item_Quantity_adapter.get(position);
             //  Log.e("itemadapter","the integer val for add qty is "+qty_vl_from_text);
               //item_old_val=item_old_val+qty_vl_from_text;
               ++item_old_val;
               //add new volaues
               String new_count_val = String.valueOf(item_old_val);

                   items_name_count.add(new_count_val);

               SharedPreferences.Editor editor5 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
               editor5.putString(sharepreferencename_count, new_count_val);
               Log.e("homefragment","the catgeories shared preference are  specific count for   ="+sharepreferencename_count+new_count_val);
               editor5.apply();




                   // check if cart has older values

                   items_name_old.clear();
                   SharedPreferences sharednew = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_names_shared2 = fullusername_global+"cart_item_names";
                   String itemSingle_name_old = sharednew.getString(cart_item_names_shared2, "");
                   if(!(itemSingle_name_old.equals(""))||(itemSingle_name_old.length()==0))
                   {
                       String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_old.add(ct);
                           }

                       }
                   }
               Log.e("itemadapter","sec befre add  add btn "+cart_item_names_shared2 +items_name_old);

                   items_name_old_idd.clear();
               SharedPreferences sharednew_idd = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
               String cart_item_names_id_shared2 = fullusername_global+"cart_item_names_id";
               String itemSingle_name_old_idd = sharednew_idd.getString(cart_item_names_id_shared2, "");
               Log.e("homefragment", "the catgeories shared preference are  login before adding  id  =" +itemSingle_name_old_idd);
               if(!(itemSingle_name_old_idd.equals(""))||(itemSingle_name_old_idd.length()==0))
               {
                   String[] cats = itemSingle_name_old_idd .split(",");//if spaces are uneven, use \\s+ instead of " "
                   for (String ct : cats) {
                       if(!(ct.equals("")||ct.equals(null)))
                       {
                           Integer iddd = Integer.valueOf(ct);
                           items_name_old_idd.add(iddd);
                       }

                   }
               }

               items_name_image.clear();
                   SharedPreferences shared10 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_image_shared2 = fullusername_global+"cart_item_image";
                   String itemSingle_name_old_images = shared10.getString(cart_item_image_shared2, "");
                   if(!(itemSingle_name_old_images.equals(""))||(itemSingle_name_old_images.length()==0))
                   {
                       String[] cats = itemSingle_name_old_images .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_image.add(ct);
                           }

                       }
                   }
               items_name_quantity.clear();
                   SharedPreferences shared1 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_qnty_Shared2 = fullusername_global+"cart_item_qnty";
                   String itemSingle_qnty_old = shared1.getString(cart_item_qnty_Shared2, "");
                   if(!(itemSingle_qnty_old==null)||(itemSingle_qnty_old.length()==0))
                   {
                       String[] cats = itemSingle_qnty_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_quantity.add(ct);
                           }

                       }
                   }

               items_name_price.clear();
                   SharedPreferences shared2 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_price_shared2 =fullusername_global+"cart_item_price";
                   String itemSingle_name_price = shared2.getString(cart_item_price_shared2, "");
                   if(!(itemSingle_name_price==null)||(itemSingle_name_price.length()==0))
                   {
                       String[] cats = itemSingle_name_price .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_price.add(ct);
                           }

                       }
                   }
               items_name_cod.clear();
                   SharedPreferences shared7 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_cod_shared2 = fullusername_global+"cart_item_cod";
                   String itemSingle_name_cod = shared7.getString(cart_item_cod_shared2, "");
                   if(!(itemSingle_name_old==null)||(itemSingle_name_old.length()==0))
                   {
                       String[] cats = itemSingle_name_cod .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_cod.add(ct);
                           }

                       }
                   }

               items_name_offer_percentage.clear();
                   SharedPreferences shared3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                   String cart_item_offer_percent_shared = fullusername_global+"cart_item_offer_percent";
                   String itemSingle_name_offerpercent = shared3.getString(cart_item_offer_percent_shared, "");
                   if(!(itemSingle_name_offerpercent==null)||(itemSingle_name_offerpercent.length()==0))
                   {
                       String[] cats = itemSingle_name_offerpercent .split(",");//if spaces are uneven, use \\s+ instead of " "
                       for (String ct : cats) {
                           if(!(ct.equals("")||ct.equals(null)))
                           {
                               items_name_offer_percentage.add(ct);
                           }

                       }
                   }
                   //add new values

                   String nm= String.valueOf(holder.name.getText());
                   if(!(items_name_old.contains(nm))) {
                       items_name_old.add(nm);
                       Log.e("homefragment", "the catgeories shared preference are  login before adding  id  =" +items_name_old_idd+items_name_old);

                       Integer idd = item_id_adapter.get(position);
                       items_name_old_idd.add(idd);
                       Log.e("Itemadapter ","added id "+idd+items_name_old_idd);
                       Integer increment_val2 = Itemlisting.Get_counter();
                       String new_val2 = String.valueOf(++increment_val2);
                       Itemlisting.update_counter(new_val2);
                        Main2Activity.update_counter(new_val2);
                       Iterator<String> itr_string = items_name_old.iterator();
                       while (itr_string.hasNext()) {

                           new_it.append(itr_string.next());
                           if (itr_string.hasNext()) {
                               new_it.append(",");
                           }
                       }
                       SharedPreferences.Editor editor_nm = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String cart_item_names_shared3  = fullusername_global+"cart_item_names";
                       editor_nm.putString(cart_item_names_shared3, new_it.toString());

                       Log.e("homefragment", "the catgeories shared preference are  login  =" + new_it.toString());
                       editor_nm.apply();

                       Iterator<Integer> itr_string_id = items_name_old_idd.iterator();
                       while (itr_string_id.hasNext()) {

                           new_it_id.append(itr_string_id.next());
                           if (itr_string_id.hasNext()) {
                               new_it_id.append(",");
                           }
                       }

                       SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String cart_item_names_id_shared3  = fullusername_global+"cart_item_names_id";
                       editor.putString(cart_item_names_id_shared3, new_it_id.toString());

                       Log.e("homefragment", "the catgeories shared preference are  login after adding  id  =" + new_it_id.toString()+cart_item_names_id_shared3+items_name_old_idd);
                       editor.apply();


                       String images = item_image_adapter.get(position);
                       items_name_image.add(images);
                       Iterator<String> itr_string_ig = items_name_image.iterator();
                       while (itr_string_ig.hasNext()) {

                           new_it_ig.append(itr_string_ig.next());
                           if (itr_string_ig.hasNext()) {
                               new_it_ig.append(",");
                           }
                       }
                       SharedPreferences.Editor editor11 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_image_shared3 = fullusername_global+"cart_item_image";
                      String cart_Items_toolbar_count_shared3 = fullusername_global+"cart_Items_toolbar_count";
                       editor11.putString(cart_item_image_shared3, new_it_ig.toString());
                       editor11.putString(cart_Items_toolbar_count_shared3, String.valueOf(items_name_image.size()));
                       Log.e("homefragment", "the catgeories shared preference are  login  =" + new_it_ig.toString());
                       editor11.apply();


                       String qt = String.valueOf(holder.item_inital_qty.getText());
                       Log.e("item", "q==" + qt);
                       Log.e("item", "q==" + items_name_quantity);

                       items_name_quantity.add(qt);

                       Log.e("item", "q==" + items_name_quantity);
                       Iterator<String> itr_string_qty = items_name_quantity.iterator();
                       while (itr_string_qty.hasNext()) {
                           Log.e("item", "q==" + itr_string_qty);

                           new_it_qnty.append(itr_string_qty.next());
                           Log.e("item", "q==" + it_qnty);
                           if (itr_string_qty.hasNext()) {
                               new_it_qnty.append(",");
                               Log.e("item", "q==" + it_qnty);
                           }
                           Log.e("item", "q==" + it_qnty);
                       }
                       SharedPreferences.Editor editor2 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_qnty_shared3= fullusername_global+"cart_item_qnty";
                       editor2.putString(cart_item_qnty_shared3, new_it_qnty.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login quantity =" + new_it_qnty.toString());
                       editor2.apply();


                       String cod_cod = Item_cod_adapter.get(position);

                       items_name_cod.add(cod_cod);

                       Log.e("homefragment", "the catgeories shared preference are  login   cod buton=" + items_name_cod+cod_cod+Item_cod_adapter);

                       Iterator<String> itr_string_cod = items_name_cod.iterator();
                       while (itr_string_cod.hasNext()) {

                           new_it_cod.append(itr_string_cod.next());
                           if (itr_string_cod.hasNext()) {
                               new_it_cod.append(",");
                           }
                       }
                       SharedPreferences.Editor editor8 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_cod = fullusername_global+"cart_item_cod";
                       editor8.putString(cart_item_cod, new_it_cod.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login   cod buton=" + new_it_cod.toString());
                       editor8.apply();


                       String pr = String.valueOf(holder.amont.getText());

                       items_name_price.add(pr);


                       Iterator<String> itr_string_price = items_name_price.iterator();
                       while (itr_string_price.hasNext()) {

                           new_it_price.append(itr_string_price.next());
                           if (itr_string_price.hasNext()) {
                               new_it_price.append(",");
                           }
                       }
                       SharedPreferences.Editor editor3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                       String cart_item_price = fullusername_global+"cart_item_price";
                       editor3.putString(cart_item_price, new_it_price.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login  =" + new_it_price.toString());
                       editor3.apply();


                       String op = String.valueOf(holder.offer_percent.getText());


                       if (op.equals("")) {
                           op = "0";
                       }
                       items_name_offer_percentage.add(op);


                       Iterator<String> itr_string_offerpercentage = items_name_offer_percentage.iterator();
                       while (itr_string_offerpercentage.hasNext()) {

                           new_it_offer_percnt.append(itr_string_offerpercentage.next());
                           if (itr_string_offerpercentage.hasNext()) {
                               new_it_offer_percnt.append(",");
                           }
                       }
                       SharedPreferences.Editor editor4 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      String cart_item_offer_percent_shared5 = fullusername_global+"cart_item_offer_percent";
                       editor4.putString(cart_item_offer_percent_shared5, new_it_offer_percnt.toString());
                       Log.e("homefragment", "the catgeories shared preference are  login offerpercentage  =" + new_it_offer_percnt.toString());
                       editor4.apply();
                   }





           }
       });
        holder.substraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer item_old_val_minus;
                final StringBuilder it_count_minus  = new StringBuilder();
                final ArrayList<String> items_name_count_minus = new ArrayList<>();

                String q2 = String.valueOf(holder.quantityy.getText());
                quantity= Integer.parseInt(q2);

                if(quantity>1)
                {
                    quantity=quantity-1;
                }


                String stringquantity2 = String.valueOf(quantity);
                holder.quantityy.setText(stringquantity2);
                // get the old values
                String name_item_nm = String.valueOf(holder.name.getText());
                String sharepreferencename_count = fullusername_global+name_item_nm+"_count";
                SharedPreferences shared = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String itemSingle_name_old = shared.getString(sharepreferencename_count, "");

                if(itemSingle_name_old.equals("")||itemSingle_name_old.length()==0)
                {
                    item_old_val_minus=0;
                }
                else
                {
                    item_old_val_minus= Integer.valueOf(itemSingle_name_old);
                }
                if(item_old_val_minus>1)
                {

                    --item_old_val_minus;
                }

                //add new volaues
                String new_count_val = String.valueOf(item_old_val_minus);

                items_name_count_minus.add(new_count_val);

                SharedPreferences.Editor editor5 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor5.putString(sharepreferencename_count, new_count_val);
                Log.e("homefragment","the catgeories shared preference are  login count for   ="+sharepreferencename_count+it_count_minus.toString());
                editor5.apply();





            }
        });
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent next= new Intent(context,description.class);
                Bundle b = new Bundle();
                b.putString("itemNameBundle",Item_categories_adapter.get(position));
                b.putString("itemImageBundle",item_image_adapter.get(position));
                b.putInt("itemPrice",Item_Price_adapter.get(position));
                b.putString("refund",Item_Refunf_adapter.get(position));
                b.putString("desc",Item_description.get(position));
                next.putExtras(b);
                context.startActivity(next);

            }
        });
    }


    @Override
    public int getItemCount() {
        return Item_categories_adapter.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantityy,item_inital_qty,amont,offer_percent,ofrdec,deliver_date;// init the item view's
        Button addition,substraction,addbtn,preoder_btn;
        ImageView img_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title);
            addition=(Button) itemView.findViewById(R.id.plus);
            substraction = (Button) itemView.findViewById(R.id.minus);
            quantityy = (TextView) itemView.findViewById(R.id.quantity);
            addbtn = (Button) itemView.findViewById(R.id.add);
            item_inital_qty=itemView.findViewById(R.id.publishNme);
            amont=itemView.findViewById(R.id.prce);
            offer_percent=itemView.findViewById(R.id.unread_check);
            img_item=itemView.findViewById(R.id.img_item);
            ofrdec=itemView.findViewById(R.id.offerdescrp);
            preoder_btn=itemView.findViewById(R.id.preoder_btn);
            // get the reference of item view's

        }
        public void updateaddbtn()
        {
            addbtn.setVisibility(View.VISIBLE);
        }
    }
    public void filter(String charText) {
        Log.e("texting if","persons="+charText);
        charText = charText.toLowerCase(Locale.getDefault());
        Log.e("texting if2","persons="+charText);
        Item_categories_adapter.clear();
        Iterator itr=Item_categories_adapter.iterator();
        if (charText.length() == 0) {
            Log.e("texting if3","persons="+charText);
            Item_categories_adapter.addAll(lts);
        } else {
            for(int i =0;i<lts.size();i++) {
                Log.e("texting else","persons="+lts.get(i));
                String s = (String) lts.get(i);
                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    Item_categories_adapter.add(s);
                }
            }
        }
        Log.e("text","persons="+Item_categories_adapter
        );
        notifyDataSetChanged();
    }


}
