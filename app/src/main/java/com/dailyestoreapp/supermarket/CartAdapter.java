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
    public static final String MY_PREFS_NAME = "CustomerApp";
    ArrayList<String> lts=new ArrayList<String>();
     private ArrayList<String> cod_eligible_items_name_old_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_quantity_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_images_cart = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_cod_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_price_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_count_cartadapter = new ArrayList<>();
    ArrayList pnames = new ArrayList<>(Arrays.asList("ITEM NAME", "ITEM NAME", "ITEM NAME" ));
    int quantity=1;
    public CartAdapter(Context context,ArrayList<String> cod_eligible_items_name_old_cartadapter,ArrayList<String> cod_eligible_items_name_quantity_cartadapter,ArrayList<String> cod_eligible_items_name_cod_cartadapter,ArrayList<String> cod_eligible_items_name_price_cartadapter,ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter, ArrayList<String> cod_eligible_items_name_count_cartadapter, ArrayList<String> cod_eligible_items_images_cart) {
        this.context = context;
        this.cod_eligible_items_name_old_cartadapter=cod_eligible_items_name_old_cartadapter;
        this.cod_eligible_items_name_quantity_cartadapter=cod_eligible_items_name_quantity_cartadapter;
        this.cod_eligible_items_name_cod_cartadapter=cod_eligible_items_name_cod_cartadapter;
        this.cod_eligible_items_name_price_cartadapter=cod_eligible_items_name_price_cartadapter;
        this.cod_eligible_items_name_offer_percentage_cartadapter=cod_eligible_items_name_offer_percentage_cartadapter;
        this.cod_eligible_items_name_count_cartadapter=cod_eligible_items_name_count_cartadapter;
        this.cod_eligible_items_images_cart=cod_eligible_items_images_cart;
        this.lts.addAll(personNames);

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

        // set the data in items
        String name = (String) cod_eligible_items_name_old_cartadapter.get(position);
        String qy = cod_eligible_items_name_quantity_cartadapter.get(position);

        String cnt =cod_eligible_items_name_count_cartadapter.get(position);
        String pr_initial = cod_eligible_items_name_price_cartadapter.get(position);
String cnt1 ="QUANTITY: "+cnt;

        String[] separated = pr_initial .split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Log.e("cart","the value is "+val );
        Integer int_pr_initial = Integer.valueOf(val);
        Integer int_cnt = Integer.valueOf(cnt);
        Integer tot_count_price = int_cnt*int_pr_initial;
        String string_tot_count_price= "Rs: "+String.valueOf(tot_count_price);
        holder.name_cart.setText(name);
        holder.price_cart.setText(pr_initial);
        holder.qty_cart.setText(cnt1);


        String Sb_img =cod_eligible_items_images_cart.get(position);
        Log.e("image","the image is "+Sb_img);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_cart_adapter);
holder.rm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String delete_price=cod_eligible_items_name_price_cartadapter.get(position);

        Log.e("cart","the value is "+delete_price);
        String[] separated = delete_price.split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Integer intdelete_price= Integer.valueOf(val);
        final StringBuilder it  = new StringBuilder();
        final StringBuilder it_qnty  = new StringBuilder();
        final StringBuilder it_ig  = new StringBuilder();
        final StringBuilder it_price  = new StringBuilder();
        final StringBuilder it_cod  = new StringBuilder();
        final StringBuilder it_offer_percnt  = new StringBuilder();
        holder.rm.setText("ITEM REMOVED");


//        String name_item_nm = String.valueOf(holder.name_cart.getText());
//        String sharepreferencename_count = name_item_nm+"_count";
//        SharedPreferences.Editor editor5 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor5.putString(sharepreferencename_count,"");
//        editor5.apply();
//
//
//        Log.e("cartadapter","cod before remove"+cod_eligible_items_name_old_cartadapter);
//        cod_eligible_items_name_old_cartadapter.remove(position);
//        Log.e("cartadapter","cod after remove"+cod_eligible_items_name_old_cartadapter);
//            Iterator<String> itr_string = cod_eligible_items_name_old_cartadapter.iterator();
//            while (itr_string.hasNext()) {
//
//                it.append(itr_string.next());
//                if (itr_string.hasNext()) {
//                    it.append(",");
//                }
//            }
//            SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor.putString("cart_item_names", it.toString());
//            editor.putString("cart_Items_toolbar_count", String.valueOf(cod_eligible_items_name_old_cartadapter.size()));
//            Log.e("homefragment", "the catgeories shared preference are  login  =" + it.toString());
//            editor.apply();
//
//
//            String images = cod_eligible_items_images_cart.get(position);
//        cod_eligible_items_images_cart.remove(position);
//            Iterator<String> itr_string_ig = cod_eligible_items_images_cart.iterator();
//            while (itr_string_ig.hasNext()) {
//
//                it_ig.append(itr_string_ig.next());
//                if (itr_string_ig.hasNext()) {
//                    it_ig.append(",");
//                }
//            }
//            SharedPreferences.Editor editor11 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor11.putString("cart_item_image", it_ig.toString());
//            editor11.putString("cart_Items_toolbar_count", String.valueOf(cod_eligible_items_images_cart.size()));
//            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_ig.toString());
//            editor11.apply();
//
//
//
//
//
//       cod_eligible_items_name_quantity_cartadapter.remove(position);
//
//            Iterator<String> itr_string_qty = cod_eligible_items_name_quantity_cartadapter.iterator();
//            while (itr_string_qty.hasNext()) {
//                Log.e("item", "q==" + itr_string_qty);
//
//                it_qnty.append(itr_string_qty.next());
//                Log.e("item", "q==" + it_qnty);
//                if (itr_string_qty.hasNext()) {
//                    it_qnty.append(",");
//                    Log.e("item", "q==" + it_qnty);
//                }
//                Log.e("item", "q==" + it_qnty);
//            }
//            SharedPreferences.Editor editor2 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor2.putString("cart_item_qnty", it_qnty.toString());
//            Log.e("homefragment", "the catgeories shared preference are  login quantity =" + it_qnty.toString());
//            editor2.apply();
//
//
//
//        cod_eligible_items_name_cod_cartadapter.remove(position);
//
//
//            Iterator<String> itr_string_cod = cod_eligible_items_name_cod_cartadapter.iterator();
//            while (itr_string_cod.hasNext()) {
//
//                it_cod.append(itr_string_cod.next());
//                if (itr_string_cod.hasNext()) {
//                    it_cod.append(",");
//                }
//            }
//            SharedPreferences.Editor editor8 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor8.putString("cart_item_cod", it_cod.toString());
//            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_cod.toString());
//            editor8.apply();
//
//
//
//
//        cod_eligible_items_name_price_cartadapter.remove(position);
//            Iterator<String> itr_string_price = cod_eligible_items_name_price_cartadapter.iterator();
//            while (itr_string_price.hasNext()) {
//
//                it_price.append(itr_string_price.next());
//                if (itr_string_price.hasNext()) {
//                    it_price.append(",");
//                }
//            }
//            SharedPreferences.Editor editor3 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor3.putString("cart_item_price", it_price.toString());
//            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_price.toString());
//            editor3.apply();
//
//
//
//        cod_eligible_items_name_offer_percentage_cartadapter.remove(position);
//            Iterator<String> itr_string_offerpercentage = cod_eligible_items_name_offer_percentage_cartadapter.iterator();
//            while (itr_string_offerpercentage.hasNext()) {
//
//                it_offer_percnt.append(itr_string_offerpercentage.next());
//                if (itr_string_offerpercentage.hasNext()) {
//                    it_offer_percnt.append(",");
//                }
//            }
//            SharedPreferences.Editor editor4 = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            editor4.putString("cart_item_offer_percent", it_offer_percnt.toString());
//            Log.e("homefragment", "the catgeories shared preference are  login  =" + it_offer_percnt.toString());
//            editor4.apply();














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
