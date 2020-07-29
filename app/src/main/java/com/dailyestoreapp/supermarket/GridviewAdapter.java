//package com.dailyestoreapp.supermarket;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.widget.BaseAdapter;
//import android.content.Context;
//
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//
//public class GridviewAdapter extends BaseAdapter {
//    private LayoutInflater mContext;
//    ArrayList<String> lts=new ArrayList<String>();
//
//    public Integer[] mThumbIds = {R.drawable.vegetables, R.drawable.food, R.drawable.cakes, R.drawable.fashion, R.drawable.electronics, R.drawable.grocery};
//    private final List<Item> mItems = new ArrayList<Item>();
//    @Override
//    public int getCount() {
//        return categorynames.size();
//    }
//
//    public GridviewAdapter(Context c) {
//        mContext = LayoutInflater.from(c);
//        mItems.add(new Item(R.drawable.vegetables));
//        mItems.add(new Item(R.drawable.food));
//        mItems.add(new Item(R.drawable.cakes));
//        mItems.add(new Item(R.drawable.fashion));
//        mItems.add(new Item(R.drawable.electronics));
//        mItems.add(new Item(R.drawable.grocery));
//        this.lts.addAll(categorynames);
//    }
//
//    @Override
//    public Item getItem(int position) {
//        return mItems.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
////        ImageView imageView;
////
////        if (convertView == null) {
////
////           // imageView = new ImageView(mContext);
////          //  imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
////          //  imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////            //imageView.setPadding(8, 8, 8, 8);
////
////        }
////        else
////        {
////            imageView = (ImageView) convertView;
////        }
////        imageView.setImageResource(mThumbIds[position]);
////        return imageView;
//
//        // }
//        View v = convertView;
//        ImageView picture;
//
//
//        if (v == null) {
//            v = mContext.inflate(R.layout.categoriescell, parent, false);
//            v.setTag(R.id.category, v.findViewById(R.id.category));
//
//        }
//
//        picture = (ImageView) v.getTag(R.id.category);
//
//
//        Item it = getItem(position);
//
//        picture.setImageResource(it.drawableId);
//
//
//        return v;
//    }
//
//    private static class Item {
//
//        public final int drawableId;
//
//        Item(int drawableId) {
//
//            this.drawableId = drawableId;
//        }
//    }
//    public void filter(String charText) {
//        Log.e("texting if","persons="+charText);
//        charText = charText.toLowerCase(Locale.getDefault());
//        Log.e("texting if2","persons="+charText);
//        categorynames.clear();
//        Iterator itr=categorynames.iterator();
//        if (charText.length() == 0) {
//            Log.e("texting if3","persons="+charText);
//            categorynames.addAll(lts);
//        } else {
//            for(int i =0;i<lts.size();i++) {
//                Log.e("texting else","persons="+lts.get(i));
//                String s = (String) lts.get(i);
//                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
//                    categorynames.add(s);
//                }
//            }
//        }
//        Log.e("text","persons="+categorynames);
//        notifyDataSetChanged();
//    }
//}
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
    public Integer[] mThumbIds = {R.drawable.newvegetable, R.drawable.newvegetable, R.drawable.newvegetable, R.drawable.fashion, R.drawable.electronics, R.drawable.grocery};
    private final List<Item> mItems = new ArrayList<Item>();
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    public GridviewAdapter(Context c) {
        mContext = LayoutInflater.from(c);
        mItems.add(new Item(R.drawable.newvegetable));
        mItems.add(new Item(R.drawable.newvegetable));
        mItems.add(new Item(R.drawable.newvegetable));
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