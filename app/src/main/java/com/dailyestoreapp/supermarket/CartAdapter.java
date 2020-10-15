package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    String from_flag_adapter;
    public static final String MY_PREFS_NAME = "CustomerApp";
    ArrayList<String> lts=new ArrayList<String>();
     private ArrayList<String> cod_eligible_items_name_old_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_quantity_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_images_cart = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_cod_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_price_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_count_cartadapter = new ArrayList<>();
    private ArrayList<Integer> items_name_old_cart_id = new ArrayList<>();

    private ArrayList<String> items_name_old_cartadapter = new ArrayList<>();
    private ArrayList<String> items_name_quantity_cartadapter = new ArrayList<>();
    private ArrayList<String> items_images_cart = new ArrayList<>();
    private ArrayList<String> items_name_cod_cartadapter = new ArrayList<>();
    private  ArrayList<String> items_name_price_cartadapter = new ArrayList<>();
    private  ArrayList<String> items_name_offer_percentage_cartadapter = new ArrayList<>();
    private  ArrayList<String> items_name_count_cartadapter = new ArrayList<>();
   private ArrayList<Integer> items_index_eligible_cod = new ArrayList<>();
    String fullname;
    int removal_flag;
    ArrayList pnames = new ArrayList<>(Arrays.asList("ITEM NAME", "ITEM NAME", "ITEM NAME" ));
    int quantity=1;
    public CartAdapter(Context context,ArrayList<Integer> cod_eligible_items_name_old_cartadapter_id,ArrayList<String> cod_eligible_items_name_old_cartadapter,ArrayList<String> cod_eligible_items_name_quantity_cartadapter,ArrayList<String> cod_eligible_items_name_cod_cartadapter,ArrayList<String> cod_eligible_items_name_price_cartadapter,ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter, ArrayList<String> cod_eligible_items_name_count_cartadapter, ArrayList<String> cod_eligible_items_images_cart,ArrayList<String> items_name_old_cartadapter,ArrayList<String> items_name_quantity_cartadapter,ArrayList<String> items_name_cod_cartadapter,ArrayList<String> items_name_price_cartadapter,ArrayList<String> items_name_offer_percentage_cartadapter, ArrayList<String> items_name_count_cartadapter, ArrayList<String> items_images_cart,ArrayList<Integer> items_index_eligible_cod,String from_flag_adapter) {
        this.context = context;
        this.cod_eligible_items_name_old_cartadapter=cod_eligible_items_name_old_cartadapter;
        this.cod_eligible_items_name_quantity_cartadapter=cod_eligible_items_name_quantity_cartadapter;
        this.cod_eligible_items_name_cod_cartadapter=cod_eligible_items_name_cod_cartadapter;
        this.cod_eligible_items_name_price_cartadapter=cod_eligible_items_name_price_cartadapter;
        this.cod_eligible_items_name_offer_percentage_cartadapter=cod_eligible_items_name_offer_percentage_cartadapter;
        this.cod_eligible_items_name_count_cartadapter=cod_eligible_items_name_count_cartadapter;
        this.cod_eligible_items_images_cart=cod_eligible_items_images_cart;
        this.items_name_old_cart_id=cod_eligible_items_name_old_cartadapter_id;
        this.items_name_old_cartadapter=items_name_old_cartadapter;
        this.items_name_quantity_cartadapter=items_name_quantity_cartadapter;
        this.items_name_cod_cartadapter=items_name_cod_cartadapter;
        this.items_name_price_cartadapter=items_name_price_cartadapter;
        this.items_name_offer_percentage_cartadapter=items_name_offer_percentage_cartadapter;
        this.items_name_count_cartadapter=items_name_count_cartadapter;
        this.items_images_cart=items_images_cart;
        this.from_flag_adapter=from_flag_adapter;
        this.items_index_eligible_cod=items_index_eligible_cod;
        this.lts.addAll(personNames);
         removal_flag = items_name_old_cartadapter.size();
    }
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CartAdapter.MyViewHolder vh = new CartAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {


        final SharedPreferences fullname_shared = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullname = fullname_shared.getString("fullusername","");
        // set the data in items
        String name = (String) cod_eligible_items_name_old_cartadapter.get(position);
        String qy = cod_eligible_items_name_quantity_cartadapter.get(position);
        Log.e("cart","the value cod_eligible_items_name_count_cartadapter is "+cod_eligible_items_name_count_cartadapter.get(position) );
        String cnt =cod_eligible_items_name_count_cartadapter.get(position);
        String pr_initial = cod_eligible_items_name_price_cartadapter.get(position);
String cnt1 ="COUNT: "+cnt;

        String[] separated = pr_initial .split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Log.e("cart","the value is "+val );
        Integer int_pr_initial = Integer.valueOf(val);
        Integer int_cnt = Integer.valueOf(cnt);
        Integer tot_count_price = int_cnt*int_pr_initial;
        String string_tot_count_price= "Rs: "+String.valueOf(tot_count_price);
        holder.name_cart.setText(name);
        holder.price_cart.setText(string_tot_count_price);
        holder.qty_cart.setText(cnt1);


        String Sb_img =cod_eligible_items_images_cart.get(position);
        Log.e("image","the image is "+Sb_img);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_cart_adapter);




        holder.rm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Integer main_increment_val = Main2Activity.Get_counter();
        Log.e("cartnoteligible","main increment value ="+main_increment_val);
        Integer increment_val2;
        String new_val2;
        if(from_flag_adapter.equals("1"))
        {
            increment_val2 = Itemlisting.Get_counter();
             new_val2 = String.valueOf(--increment_val2);
            Itemlisting.update_counter(new_val2);
        }
        else
        {
            increment_val2 = Main2Activity.Get_counter();
            new_val2 = String.valueOf(--increment_val2);

            Main2Activity.update_counter(new_val2);
        }
//        Integer increment_val2 = Itemlisting.Get_counter();
//        String new_val2 = String.valueOf(--increment_val2);

        Main2Activity.update_counter(new_val2);

        Integer specific_cnt= Integer.valueOf(cod_eligible_items_name_count_cartadapter.get(position));
        String pr_initial = cod_eligible_items_name_price_cartadapter.get(position);
        String[] separated2 = pr_initial .split(" ");
        Log.e("cart","the value is  "+separated2[1] );
        String val2 = separated2[1];
        Log.e("cart","the value is "+val2 );
        Integer int_pr_initial = Integer.valueOf(val2);
        Integer tot_count_price = specific_cnt*int_pr_initial;
        Log.e("cart","the value is specific_cnt"+specific_cnt );
        Log.e("cart","the value is int_pr_initial "+int_pr_initial);
        CartPage.update_total_values(tot_count_price);




        SharedPreferences.Editor editor_tot = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        String total_count_cart=fullname+"total_count_cart";
        editor_tot.putString(total_count_cart, new_val2);
        editor_tot.apply();
        String delete_price=cod_eligible_items_name_price_cartadapter.get(position);

        Log.e("cart","the value is "+delete_price);
        String[] separated = delete_price.split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Integer intdelete_price= Integer.valueOf(val);
        final StringBuilder it  = new StringBuilder();
        final StringBuilder it_qnty  = new StringBuilder();
        final StringBuilder it_ig  = new StringBuilder();
        final StringBuilder it_id  = new StringBuilder();
        final StringBuilder it_price  = new StringBuilder();
        final StringBuilder it_cod  = new StringBuilder();
        final StringBuilder it_offer_percnt  = new StringBuilder();
        holder.rm.setText("ITEM REMOVED");
        String delete_item_name = String.valueOf(holder.name_cart.getText());
        int int_new_position = items_name_old_cartadapter.indexOf(delete_item_name);
//        int new_removal_flag = items_name_old_cartadapter.size();
//        int diff = removal_flag-new_removal_flag;
//        int int_new_position_val = items_index_eligible_cod.get(position);
//        int int_new_position = int_new_position_val-diff;
        Log.e("cartadapter","text removed"+items_name_old_cartadapter+items_index_eligible_cod+int_new_position);

        String name_item_nm = String.valueOf(holder.name_cart.getText());
        String sharepreferencename_count = fullname+name_item_nm+"_count";
        SharedPreferences.Editor editor5 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor5.putString(sharepreferencename_count,"");
        editor5.apply();


        Log.e("cartadapter","cod before remove"+items_name_old_cartadapter);
        items_name_old_cartadapter.remove(int_new_position);
        Log.e("cartadapter","cod after remove"+items_name_old_cartadapter);
            Iterator<String> itr_string = items_name_old_cartadapter.iterator();
            while (itr_string.hasNext()) {

                it.append(itr_string.next());
                if (itr_string.hasNext()) {
                    it.append(",");
                }
            }
            SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            String cart_item_names_shared = fullname+"cart_item_names";
            String cart_Items_toolbar_count= fullname+"cart_Items_toolbar_count";
            editor.putString(cart_item_names_shared, it.toString());
            editor.putString(cart_Items_toolbar_count, String.valueOf(items_name_old_cartadapter.size()));
            Log.e("homefragment", "the catgeories shared preference are  login  =" + it.toString());
            editor.apply();



        items_images_cart.remove(int_new_position);
            Iterator<String> itr_string_ig = items_images_cart.iterator();
            while (itr_string_ig.hasNext()) {

                it_ig.append(itr_string_ig.next());
                if (itr_string_ig.hasNext()) {
                    it_ig.append(",");
                }
            }
            SharedPreferences.Editor editor11 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            String cart_item_image_shared = fullname+"cart_item_image";
            String cart_Items_toolbar_count_shared = fullname+"cart_Items_toolbar_count";
            editor11.putString(cart_item_image_shared, it_ig.toString());
            editor11.putString(cart_Items_toolbar_count_shared, String.valueOf(items_images_cart.size()));
            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_ig.toString());
            editor11.apply();

        items_name_old_cart_id.remove(int_new_position);
        Iterator<Integer> itr_integer_id = items_name_old_cart_id.iterator();
        while (itr_integer_id.hasNext()) {

            it_id.append(itr_integer_id.next());
            if (itr_integer_id.hasNext()) {
                it_id.append(",");
            }
        }
        SharedPreferences.Editor editor_idd = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        String cart_item_id = fullname+"cart_item_names_id";
        editor_idd.putString(cart_item_id, it_id.toString());
        Log.e("homefragment", "the catgeories shared preference are  login  =" + it_id.toString());
        editor_idd.apply();



      items_name_quantity_cartadapter.remove(int_new_position);

            Iterator<String> itr_string_qty = items_name_quantity_cartadapter.iterator();
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
            String cart_item_qnty = fullname+"cart_item_qnty";
            editor2.putString(cart_item_qnty, it_qnty.toString());
            Log.e("homefragment", "the catgeories shared preference are  login quantity =" + it_qnty.toString());
            editor2.apply();



        items_name_cod_cartadapter.remove(int_new_position);


            Iterator<String> itr_string_cod = items_name_cod_cartadapter.iterator();
            while (itr_string_cod.hasNext()) {

                it_cod.append(itr_string_cod.next());
                if (itr_string_cod.hasNext()) {
                    it_cod.append(",");
                }
            }
            SharedPreferences.Editor editor8 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            String cart_item_cod = fullname+"cart_item_cod";
            editor8.putString(cart_item_cod, it_cod.toString());
            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_cod.toString());
            editor8.apply();




       items_name_price_cartadapter.remove(int_new_position);
            Iterator<String> itr_string_price = items_name_price_cartadapter.iterator();
            while (itr_string_price.hasNext()) {

                it_price.append(itr_string_price.next());
                if (itr_string_price.hasNext()) {
                    it_price.append(",");
                }
            }
            SharedPreferences.Editor editor3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            String cart_item_price = fullname+"cart_item_price";
            editor3.putString(cart_item_price, it_price.toString());
            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_price.toString());
            editor3.apply();


            items_name_offer_percentage_cartadapter.remove(int_new_position);
            Iterator<String> itr_string_offerpercentage = items_name_offer_percentage_cartadapter.iterator();
            while (itr_string_offerpercentage.hasNext()) {

                it_offer_percnt.append(itr_string_offerpercentage.next());
                if (itr_string_offerpercentage.hasNext()) {
                    it_offer_percnt.append(",");
                }
            }
            SharedPreferences.Editor editor4 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            String cart_item_offer_percent = fullname+"cart_item_offer_percent";
            editor4.putString(cart_item_offer_percent, it_offer_percnt.toString());
            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_offer_percnt.toString());
            editor4.apply();














    }
});


    }


    @Override
    public int getItemCount() {
        return cod_eligible_items_name_old_cartadapter.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_cart,quantityy,price_cart,qty_cart;// init the item view's
Button rm;
        ImageView img_cart_adapter;
        public MyViewHolder(View itemView) {
            super(itemView);
            name_cart = (TextView) itemView.findViewById(R.id.Title_cart);
            price_cart = (TextView) itemView.findViewById(R.id.prce_cart);
            qty_cart = (TextView) itemView.findViewById(R.id.Qty_cart);
            rm=itemView.findViewById(R.id.remove_btn);
            img_cart_adapter=itemView.findViewById(R.id.img_cart);
            // get the reference of item view's

        }
    }
    public void filter(String charText) {
        Log.e("texting if","persons="+charText);
        charText = charText.toLowerCase(Locale.getDefault());
        Log.e("texting if2","persons="+charText);
        personNames.clear();
        Iterator itr=personNames.iterator();
        if (charText.length() == 0) {
            Log.e("texting if3","persons="+charText);
            personNames.addAll(lts);
        } else {
            for(int i =0;i<lts.size();i++) {
                Log.e("texting else","persons="+lts.get(i));
                String s = (String) lts.get(i);
                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    personNames.add(s);
                }
            }
        }
        Log.e("text","persons="+personNames);
        notifyDataSetChanged();
    }

}

