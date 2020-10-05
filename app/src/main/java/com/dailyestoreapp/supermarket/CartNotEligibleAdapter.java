package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.content.Intent;
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

public class CartNotEligibleAdapter extends RecyclerView.Adapter<CartNotEligibleAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    ArrayList<String> lts=new ArrayList<String>();
    private ArrayList<String> cod_eligible_items_name_old_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_quantity_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_name_cod_cartadapter = new ArrayList<>();
    private ArrayList<String> cod_eligible_items_images_cart = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_price_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter = new ArrayList<>();
    private  ArrayList<String> cod_eligible_items_name_count_cartadapter = new ArrayList<>();
    ArrayList pnames = new ArrayList<>(Arrays.asList("ITEM NAME", "ITEM NAME", "ITEM NAME" ));
    int quantity=1;
    public CartNotEligibleAdapter(Context context,ArrayList<String> cod_eligible_items_name_old_cartadapter,ArrayList<String> cod_eligible_items_name_quantity_cartadapter,ArrayList<String> cod_eligible_items_name_cod_cartadapter,ArrayList<String> cod_eligible_items_name_price_cartadapter,ArrayList<String> cod_eligible_items_name_offer_percentage_cartadapter, ArrayList<String> cod_eligible_items_name_count_cartadapter,ArrayList<String> cod_eligible_items_images_cart) {
        this.context = context;
        this.cod_eligible_items_name_old_cartadapter=cod_eligible_items_name_old_cartadapter;
        this.cod_eligible_items_name_quantity_cartadapter=cod_eligible_items_name_quantity_cartadapter;
        this.cod_eligible_items_name_cod_cartadapter=cod_eligible_items_name_cod_cartadapter;
        this.cod_eligible_items_name_price_cartadapter=cod_eligible_items_name_price_cartadapter;
        this.cod_eligible_items_name_offer_percentage_cartadapter=cod_eligible_items_name_offer_percentage_cartadapter;
        this.cod_eligible_items_name_count_cartadapter=cod_eligible_items_name_count_cartadapter;
        this.cod_eligible_items_images_cart=cod_eligible_items_images_cart;
        this.lts.addAll(personNames);

        Log.e("noteligible","entered adapter"+cod_eligible_items_name_old_cartadapter);
    }
    @Override
    public CartNotEligibleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CartNotEligibleAdapter.MyViewHolder vh = new CartNotEligibleAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final CartNotEligibleAdapter.MyViewHolder holder, final int position) {

        // set the data in items
        String name = (String) cod_eligible_items_name_old_cartadapter.get(position);
        String qy = cod_eligible_items_name_quantity_cartadapter.get(position);
        String pr = cod_eligible_items_name_price_cartadapter.get(position);
        String cnt =cod_eligible_items_name_count_cartadapter.get(position);
        String pr_initial = cod_eligible_items_name_price_cartadapter.get(position);
        String cnt1 ="QUANTITY :"+cnt;

        String[] separated = pr_initial .split(":");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Log.e("cart","the value is "+val );
        Integer int_pr_initial = Integer.valueOf(val);
        Integer int_cnt = Integer.valueOf(cnt);
        Integer tot_count_price = int_cnt*int_pr_initial;
        String string_tot_count_price= "Rs : "+String.valueOf(tot_count_price);
        holder.name_cart.setText(name);
        holder.price_cart.setText(string_tot_count_price);
        holder.qty_cart.setText(cnt1);
        holder.name_cart.setText(name);
        holder.price_cart.setText(pr);
        holder.qty_cart.setText(qy);

        String Sb_img =cod_eligible_items_images_cart.get(position);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_cart_adapter);

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent next= new Intent(context,description.class);
                context.startActivity(next);

            }
        });
    }


    @Override
    public int getItemCount() {
        return cod_eligible_items_name_old_cartadapter.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_cart,price_cart,qty_cart;// init the item view's

        ImageView img_cart_adapter;
        public MyViewHolder(View itemView) {
            super(itemView);
            name_cart = (TextView) itemView.findViewById(R.id.Title_cart);
            price_cart = (TextView) itemView.findViewById(R.id.prce_cart);
            qty_cart = (TextView) itemView.findViewById(R.id.Qty_cart);

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
