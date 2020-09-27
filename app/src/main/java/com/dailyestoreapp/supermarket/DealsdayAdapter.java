package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DealsdayAdapter extends BaseAdapter {
    private LayoutInflater mContext;
    int cat_deal_id;
    ArrayList<String> deal_image;
    public Integer[] mThumbIds = {R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday};
    private final List<Item> mItems = new ArrayList<Item>();
    @Override
    public int getCount() {

        if(this.cat_deal_id==0)
        {
            return this.deal_image.size();
        }
        return mThumbIds.length;
    }

    public DealsdayAdapter(Context c,ArrayList<String> deal_image,int initalid) {
        mContext = LayoutInflater.from(c);
        this.cat_deal_id=initalid;
        this.deal_image=deal_image;
        mItems.add(new Item(R.drawable.dealoftheday));
        mItems.add(new Item(R.drawable.dealoftheday));
        mItems.add(new Item(R.drawable.dealoftheday));
        mItems.add(new Item(R.drawable.dealoftheday));
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
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


        Item it = getItem(position);

        picture.setImageResource(it.drawableId);


        return v;
    }

    private static class Item {

        public final int drawableId;

        Item(int drawableId) {

            this.drawableId = drawableId;
        }
    }
}