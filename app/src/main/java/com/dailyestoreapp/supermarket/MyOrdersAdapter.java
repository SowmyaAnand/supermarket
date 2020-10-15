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

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder>
{

    ArrayList<String> personNames = new ArrayList<String>();
    Context context;

    ArrayList<String> lts=new ArrayList<String>();
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    int quantity=1;
    ArrayList<String> items_name_price_myorders = new ArrayList<>();
    ArrayList<String> items_image_myorders = new ArrayList<>();
    ArrayList<String> items_name_myorders; ArrayList<String>items_name_quantity_myorders;ArrayList<Integer> items_name_status_myorders;ArrayList<Integer> items_count_myorders;
    public MyOrdersAdapter(Context context,ArrayList<String> items_name_myorders, ArrayList<String>items_name_quantity_myorders,ArrayList<Integer> items_name_status_myorders,ArrayList<Integer> items_count_myorders, ArrayList<String> items_name_price_myorders,ArrayList<String> items_image_myorders) {
        this.context = context;
        this.personNames = personNames;
        this.lts.addAll(personNames);
        this.items_name_myorders=items_name_myorders;
        this.items_count_myorders=items_count_myorders;
        this.items_name_quantity_myorders=items_name_quantity_myorders;
        this.items_name_status_myorders=items_name_status_myorders;
        this.items_name_price_myorders=items_name_price_myorders;
        this.items_image_myorders=items_image_myorders;
        Log.e("myorders","adapter orders are ="+items_name_quantity_myorders);
    }
    @Override
    public MyOrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderslist, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyOrdersAdapter.MyViewHolder vh = new MyOrdersAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyOrdersAdapter.MyViewHolder holder, final int position) {

        // set the data in items
        String name = (String) items_name_myorders.get(position);
        String quan = (String) items_name_quantity_myorders.get(position);
        String pr =items_name_price_myorders.get(position);
        String img =items_image_myorders.get(position);
        Integer count = items_count_myorders.get(position);
        Integer st = items_name_status_myorders.get(position);
        holder.name.setText(name);
        holder.quantityy.setText(quan);



        String cntt = "COUNT :"+count;
        holder.count.setText(cntt);
        if(st==0)
        {

        holder.status.setText("PENDING FOR APPROVAL");
        }
        else if(st==1)
        {
            holder.status.setText("APPROVED");
        }


        String[] separated = pr .split(" ");
        Log.e("cart","the value is "+separated[1] );
        String val = separated[1];
        Log.e("cart","the value is "+val );
        Integer int_pr_initial = Integer.valueOf(val);

        Integer tot_count_price = count*int_pr_initial;
        String string_tot_count_price= "Rs: "+String.valueOf(tot_count_price);
        holder.price.setText(string_tot_count_price);






        String Sb_img =items_image_myorders.get(position);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_my);

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
        return items_name_myorders.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantityy,price,status,count;// init the item view's
        Button addition,substraction,addbtn;
        ImageView img_my;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title_myorder);
            quantityy = (TextView) itemView.findViewById(R.id.qty_myorder);
           price=(TextView) itemView.findViewById(R.id.prce_myorder);
           status=(TextView) itemView.findViewById(R.id.status_myorder);
           count=(TextView) itemView.findViewById(R.id.count_myorder);
           img_my=itemView.findViewById(R.id.img_myorder);
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

