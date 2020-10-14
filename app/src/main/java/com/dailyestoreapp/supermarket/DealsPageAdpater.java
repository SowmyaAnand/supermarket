package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealsPageAdpater extends RecyclerView.Adapter<DealsPageAdpater.MyViewHolder> {

    Context context;
    int cat_deal_id;
    private Context ctx;
    ArrayList<String> deal_image;
    public Integer[] mThumbIds = {R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday};
    private final List<Item> mItems = new ArrayList<Item>();
    private final List<Modified_Item_Deal> modified_mItems = new ArrayList<Modified_Item_Deal>();
    public DealsPageAdpater(Context context, ArrayList<String> deal_image, int initalid) {
        this.context = context;
        this.cat_deal_id = initalid;
        this.deal_image = deal_image;
        if (this.cat_deal_id == 1) {
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
        } else {
            for (int i = 0; i < deal_image.size(); i++) {
                String imageurl_total = deal_image.get(i);

                modified_mItems.add(new Modified_Item_Deal(imageurl_total));
            }
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_new_page, parent, false);
        // set the view's size, margins, paddings and layout parameters
       MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (this.cat_deal_id == 1) {
            Item it = getItem(position);

           holder.img.setImageResource(it.drawableId);

        } else {
            Modified_Item_Deal mit = getItemModified(position);
            Glide.with(ctx).load(mit.img_deal_mod)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);


        }

    }

    public Item getItem(int position) {
        return mItems.get(position);
    }
    @Override
    public int getItemCount() {
        if (this.cat_deal_id == 0) {
            return this.deal_image.size();
        }
        return mThumbIds.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.category_deal);

            // get the reference of item view's

        }
    }
    private Modified_Item_Deal getItemModified(int position) {
        return modified_mItems.get(position);
    }
    private static class Item {

        public final int drawableId;

        Item(int drawableId) {

            this.drawableId = drawableId;
        }
    }

    private static class Modified_Item_Deal {

        public final String img_deal_mod;

        Modified_Item_Deal(String img_ctt_mod) {

            this.img_deal_mod = img_ctt_mod;

        }
    }
}
