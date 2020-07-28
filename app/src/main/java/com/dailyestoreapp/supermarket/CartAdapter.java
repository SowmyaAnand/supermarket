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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    ArrayList<String> personNames = new ArrayList<String>();
    Context context;
    ArrayList<String> lts=new ArrayList<String>();
    ArrayList pnames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    int quantity=1;
    public CartAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
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
        String name = (String) personNames.get(position);
        holder.name.setText(name);

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
        Button addition,substraction;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Title);
            addition=(Button) itemView.findViewById(R.id.plus_cart);
            substraction = (Button) itemView.findViewById(R.id.minus_cart);
            quantityy = (TextView) itemView.findViewById(R.id.quantity_cart);

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
