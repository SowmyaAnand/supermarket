package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class Listingsubcategoryadapter extends RecyclerView.Adapter<Listingsubcategoryadapter.MyViewHolder> {
    ArrayList personNames;
    Context context;
    ArrayList<String> Subcategories;
    ArrayList<String> Subcategory_image;


    ArrayList<Integer> Sub_id;
    int sub_int_id;
    public Listingsubcategoryadapter(Context context, ArrayList<String> Subcategories,ArrayList<String> Subcategory_image,ArrayList<Integer> Subcat_id,int sub_initial_id) {
        this.context = context;
        this.Subcategories=Subcategories;
        this.Sub_id=Subcat_id;
        this.Subcategory_image=Subcategory_image;
        this.sub_int_id=sub_initial_id;
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
        String sb_name = Subcategories.get(position);
        String Sb_img =Subcategory_image.get(position);
        holder.name.setText(sb_name);
        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);


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
        return Subcategories.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sbname);
            img = itemView.findViewById(R.id.sbimg);
            // get the reference of item view's

        }
    }
}
