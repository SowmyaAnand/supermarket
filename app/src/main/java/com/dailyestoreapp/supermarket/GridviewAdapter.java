package com.dailyestoreapp.supermarket;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class GridviewAdapter extends BaseAdapter {
    private LayoutInflater mContext;
    public Integer[] mThumbIds = {R.drawable.vegetables, R.drawable.food, R.drawable.cakes, R.drawable.fashion, R.drawable.electronics, R.drawable.grocery};
    private final List<Item> mItems = new ArrayList<Item>();
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    public GridviewAdapter(Context c) {
        mContext = LayoutInflater.from(c);
        mItems.add(new Item(R.drawable.vegetables));
        mItems.add(new Item(R.drawable.food));
        mItems.add(new Item(R.drawable.cakes));
        mItems.add(new Item(R.drawable.fashion));
        mItems.add(new Item(R.drawable.electronics));
        mItems.add(new Item(R.drawable.grocery));

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
            v = mContext.inflate(R.layout.categoriescell, parent, false);
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
