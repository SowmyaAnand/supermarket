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

public class Offers_ItemAdapter extends RecyclerView.Adapter<Offers_ItemAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    ArrayList<String> lts=new ArrayList<String>();
    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    int quantity=1;
    public Offers_ItemAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
        this.lts.addAll(personNames);

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        // set the data in items
        String name = (String) personNames.get(position);
        holder.name.setText(name);
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
        return personNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantityy;// init the item view's
        Button addition,substraction,addbtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title);
            addition=(Button) itemView.findViewById(R.id.plus);
            substraction = (Button) itemView.findViewById(R.id.minus);
            quantityy = (TextView) itemView.findViewById(R.id.quantity);
            addbtn = (Button) itemView.findViewById(R.id.add);
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
