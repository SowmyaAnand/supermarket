package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    ArrayList personNames;
    Context context;
    private ArrayList<persons> arraylist;
    public ItemAdapter(Context context, ArrayList personNames) {
        this.context = context;
        this.personNames = personNames;
        this.arraylist.addAll(personNames);

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // set the data in items
        String name = (String) personNames.get(position);
       holder.name.setText(name);
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
            name = (TextView) itemView.findViewById(R.id.Title);
            // get the reference of item view's

        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        personNames.clear();
        Iterator itr=personNames.iterator();
        if (charText.length() == 0) {
            personNames.addAll(arraylist);
        } else {
            for (persons wp : arraylist) {
                if (wp.getAnimalName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    personNames.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
