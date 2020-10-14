package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> lts=new ArrayList<String>();
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    int quantity=1;

    ArrayList<String>coupon_names=new ArrayList<>();
    ArrayList<String>coupon_desc=new ArrayList<>();
    ArrayList<Integer> coupon_percent = new ArrayList<>();
    ArrayList<Integer> coupon_status = new ArrayList<>();
    ArrayList<Integer> coupon_id = new ArrayList<>();
    public CouponAdapter(Context context, ArrayList<String> couponNames, ArrayList<String> couponDesc , ArrayList<Integer> coupon_percent,ArrayList<Integer> coupon_status,ArrayList<Integer>coupon_id) {
        this.context = context;
        this.coupon_names = couponNames;
        this.coupon_desc=couponDesc;
        this.coupon_percent=coupon_percent;
        this.coupon_status=coupon_status;
        this.coupon_id=coupon_id;

    }
    @Override
    public CouponAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CouponAdapter.MyViewHolder vh = new CouponAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final CouponAdapter.MyViewHolder holder, final int position) {

        // set the data in items

        String coup_name = coupon_names.get(position);
        String coup_desc = coupon_desc.get(position);
        Integer coup_pr = coupon_percent.get(position);
        holder.coupon_name.setText(coup_name);
        holder.coupon_desc.setText(coup_desc);

        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // display a toast with person name on item click
//                Intent next= new Intent(context,description.class);
//                context.startActivity(next);
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return coupon_names.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coupon_name,coupon_desc;// init the item view's
        Button addition,substraction,addbtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            coupon_name = (TextView) itemView.findViewById(R.id.coupon_id);
            coupon_desc=(TextView) itemView.findViewById(R.id.coupon_desc);

            // get the reference of item view's

        }
    }
//    public void filter(String charText) {
//        Log.e("texting if","persons="+charText);
//        charText = charText.toLowerCase(Locale.getDefault());
//        Log.e("texting if2","persons="+charText);
//        personNames.clear();
//        Iterator itr=personNames.iterator();
//        if (charText.length() == 0) {
//            Log.e("texting if3","persons="+charText);
//            personNames.addAll(lts);
//        } else {
//            for(int i =0;i<lts.size();i++) {
//                Log.e("texting else","persons="+lts.get(i));
//                String s = (String) lts.get(i);
//                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    personNames.add(s);
//                }
//            }
//        }
//        Log.e("text","persons="+personNames);
//        notifyDataSetChanged();
//    }
}

