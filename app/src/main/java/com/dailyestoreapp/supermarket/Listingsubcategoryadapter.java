package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Listingsubcategoryadapter extends RecyclerView.Adapter<Listingsubcategoryadapter.MyViewHolder> {
    ArrayList personNames;
    Context context;
    public Listingsubcategoryadapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
    }
    @Override
    public Listingsubcategoryadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Listingsubcategoryadapter.MyViewHolder vh = new Listingsubcategoryadapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(Listingsubcategoryadapter.MyViewHolder holder, final int position) {

        // set the data in items
        String name = (String) personNames.get(position);
        // holder.name.setText(name);
        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // display a toast with person name on item click
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return personNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's

        }
    }
}
