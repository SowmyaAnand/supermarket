package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DealsdayAdapter extends BaseAdapter {
    private LayoutInflater mContext;
    int cat_deal_id;
    private Context ctx;
    ArrayList<String> deal_image;
    public Integer[] mThumbIds = {R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday};
    private final List<Item> mItems = new ArrayList<Item>();
    private final List<DealsdayAdapter.Modified_Item_Deal> modified_mItems = new ArrayList<DealsdayAdapter.Modified_Item_Deal>();

    @Override
    public int getCount() {

        if (this.cat_deal_id == 0) {
            return this.deal_image.size();
        }
        return mThumbIds.length;
    }

    public DealsdayAdapter(Context c, ArrayList<String> deal_image, int initalid) {
        mContext = LayoutInflater.from(c);
        ctx = c;
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

                modified_mItems.add(new DealsdayAdapter.Modified_Item_Deal(imageurl_total));
            }
        }

    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    private Modified_Item_Deal getItemModified(int position) {
        return modified_mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//
//        if (convertView == null) {
//
//           // imageView = new ImageView(mContext);
//          //  imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//          //  imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            //imageView.setPadding(8, 8, 8, 8);
//
//        }
//        else
//        {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;

        // }
        View v = convertView;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mContext.inflate(R.layout.categoriescelldeals, parent, false);
            v.setTag(R.id.category, v.findViewById(R.id.category));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.category);


        if (this.cat_deal_id == 1) {
            Item it = getItem(position);

            picture.setImageResource(it.drawableId);

        } else {
            Modified_Item_Deal mit = getItemModified(position);
            Glide.with(ctx).load(mit.img_deal_mod)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(picture);


        }


        return v;
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