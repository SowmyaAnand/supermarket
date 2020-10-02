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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    ArrayList<String> lts=new ArrayList<String>();
    ArrayList pnames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    ArrayList<String> item_image_adapter = new ArrayList<>();
    ArrayList<String> Item_categories_adapter = new ArrayList<>();
    ArrayList<String> Item_categories_offer_desc_adapter = new ArrayList<>();
    ArrayList<Integer> Item_Quantity_adapter = new ArrayList<>();
    ArrayList<Integer> Item_Price_adapter = new ArrayList<>();
    ArrayList<Integer> Sub_categories_id_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_offer_adapter = new ArrayList<>();
    ArrayList<Integer> item_id_status_adapter = new ArrayList<>();
    int quantity=1;
    final String url1 = "http://dailyestoreapp.com/dailyestore/";
    public ItemAdapter(Context context,ArrayList Item_categories_adapter,ArrayList Item_Quantity_adapter,ArrayList Item_Price_adapter,ArrayList item_id_adapter,ArrayList item_image_adapter,ArrayList Item_categories_offer_desc_adapter,ArrayList item_id_offer_adapter) {
        this.context = context;
        this.Item_categories_adapter=Item_categories_adapter;
        this.Item_Quantity_adapter=Item_Quantity_adapter;
        this.Item_Price_adapter=Item_Price_adapter;
        this.item_id_adapter=item_id_adapter;
        this.item_image_adapter=item_image_adapter;
        this.Item_categories_offer_desc_adapter=Item_categories_offer_desc_adapter;
        this.item_id_offer_adapter=item_id_offer_adapter;
        this.lts.addAll(personNames);

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

        // set the data in items
        String name =  Item_categories_adapter.get(position);
       holder.name.setText(name);
       String item_qty = "QUANTITY: "+String.valueOf(Item_Quantity_adapter.get(position));
       holder.item_inital_qty.setText(item_qty);
       String amt = String.valueOf(Item_Price_adapter.get(position));
       String at = "RS : "+amt;
       holder.amont.setText(at);
       int o = item_id_offer_adapter.get(position);


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
               holder.addbtn.setVisibility(View.GONE);
           }
       });
       holder.addition.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String q = String.valueOf(holder.quantityy.getText());
               quantity= Integer.parseInt(q);
               quantity=quantity+1;
               String stringquantity = String.valueOf(quantity);
              holder.quantityy.setText(stringquantity);
           }
       });
        holder.substraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q2 = String.valueOf(holder.quantityy.getText());
                quantity= Integer.parseInt(q2);
                if(quantity>1)
                {
                    quantity=quantity-1;
                }
                String stringquantity2 = String.valueOf(quantity);
                holder.quantityy.setText(stringquantity2);
            }
        });
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
        return Item_categories_adapter.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantityy,item_inital_qty,amont,offer_percent;// init the item view's
        Button addition,substraction,addbtn;
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
