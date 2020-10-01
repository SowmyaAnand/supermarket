package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listingsubcategoryadapter extends RecyclerView.Adapter<Listingsubcategoryadapter.MyViewHolder> {
    ArrayList personNames;
    Context context;
    ArrayList<String> Subcategories;
    ArrayList<String> Subcategory_image;
    public Integer[] mThumbIds = {R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1,R.drawable.vg1,R.drawable.vg1,R.drawable.vg1};

    ArrayList mthumb = new ArrayList<>(Arrays.asList("FOOD","VEGETABLES","GROCERY","CAKES","FASHION","MEAT","ELECTRONICS","MOBILE","HOME ACCESSORIES"));
    private final List<Item_sub> mItems_sub = new ArrayList<Item_sub>();
    ArrayList<Integer> Sub_id;
    int sub_int_id;
    public Listingsubcategoryadapter(Context context, ArrayList<String> Subcategories,ArrayList<String> Subcategory_image,ArrayList<Integer> Subcat_id,int sub_initial_id) {
        this.context = context;
        this.Subcategories=Subcategories;
        this.Sub_id=Subcat_id;
        this.Subcategory_image=Subcategory_image;
        this.sub_int_id=sub_initial_id;
        Log.e("list","cvalue="+sub_initial_id);
        if(this.sub_int_id==1)
        {
            String ct = String.valueOf(mthumb.get(0));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(0))));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(1))));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(2))));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(3))));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(4))));
            mItems_sub.add(new Item_sub(R.drawable.categorypage,String.valueOf(mthumb.get(5))));
            mItems_sub.add(new Item_sub(R.drawable.vg,String.valueOf(mthumb.get(6))));
            mItems_sub.add(new Item_sub(R.drawable.vg,String.valueOf(mthumb.get(7))));
            mItems_sub.add(new Item_sub(R.drawable.vg,String.valueOf(mthumb.get(8))));
        }
    }
    @Override
    public Listingsubcategoryadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the Item_sub Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trial, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Listingsubcategoryadapter.MyViewHolder vh = new Listingsubcategoryadapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(Listingsubcategoryadapter.MyViewHolder holder, final int position) {
        final String url1 = "http://dailyestoreapp.com/dailyestore/";
        // set the data in items


        if(this.sub_int_id==1)
        {
            Item_sub it = getItem(position);

            holder.img_sub.setImageResource(it.drawableId_sub);
            holder.name.setText(it.name_sub);
        }
        else
        {
            String sb_name = Subcategories.get(position);
            String Sb_img =url1+Subcategory_image.get(position);
            Log.e("liastsub","the image is "+Sb_img);
            holder.name.setText(sb_name);

        Glide.with(context).load(Sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_sub);

        }


    }


    public Item_sub getItem(int position) {

        return mItems_sub.get(position);
    }


    @Override
    public int getItemCount() {
        if(this.sub_int_id==0)
        {
            return this.Subcategories.size();
        }
        return mThumbIds.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the Item_sub view's
        ImageView img_sub;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sbname);
            img_sub = itemView.findViewById(R.id.sbimg);
            // get the reference of Item_sub view's

        }
    }
    private static class Item_sub {

        public final int drawableId_sub;
        public final String name_sub;
        Item_sub(int drawableId,String nm) {

            this.drawableId_sub = drawableId;
            this.name_sub=nm;
        }
    }
}
