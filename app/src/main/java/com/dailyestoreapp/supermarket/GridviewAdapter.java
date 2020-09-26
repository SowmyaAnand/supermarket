
package com.dailyestoreapp.supermarket;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class GridviewAdapter extends BaseAdapter {
    private LayoutInflater mContext;
    private Context context;
    public Integer[] mThumbIds = {R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1, R.drawable.vg1,R.drawable.vg1,R.drawable.vg1,R.drawable.vg1};
    ArrayList mthumb = new ArrayList<>(Arrays.asList("FOOD","VEGETABLES","GROCERY","CAKES","FASHION","MEAT","ELECTRONICS","MOBILE","HOME ACCESSORIES"));
    ArrayList<String> categories;
    ArrayList<String> category_image;
    ArrayList<Integer> cat_id;
    int cat_int_id;
    final String url1 = "http://dailyestoreapp.com/dailyestore/";
   private final List<Item> mItems = new ArrayList<Item>();
    private final List<Modified_Item> modified_mItems = new ArrayList<Modified_Item>();
    @Override
    public int getCount() {
if(this.cat_int_id==0)
{
return this.categories.size();
}
        return mThumbIds.length;
    }

    public GridviewAdapter(Context c,ArrayList<String> categories,ArrayList<String> category_image,ArrayList<Integer> cat_id,int cat_initial_id) {
        mContext = LayoutInflater.from(c);
        context=c;
        this.categories=categories;
        this.category_image=category_image;
        this.cat_id=cat_id;
       this.cat_int_id=cat_initial_id;
        Log.e("splash","the catgeories intital flag_ct  ="+cat_initial_id);
       if(this.cat_int_id==1)
       {
           String ct = String.valueOf(mthumb.get(0));
           Log.e("text","flyer++"+c);

           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(0))));
           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(1))));
           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(2))));
           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(3))));
           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(4))));
           mItems.add(new Item(R.drawable.categorypage,String.valueOf(mthumb.get(5))));
           mItems.add(new Item(R.drawable.vg,String.valueOf(mthumb.get(6))));
           mItems.add(new Item(R.drawable.vg,String.valueOf(mthumb.get(7))));
           mItems.add(new Item(R.drawable.vg,String.valueOf(mthumb.get(8))));
       }
       else
       {
        for(int i=0;i<categories.size();i++)
            {
                String imageurl_total = url1 + category_image.get(i);
                String cat_name_api =this.categories.get(i);
    modified_mItems.add(new Modified_Item(imageurl_total,cat_name_api));
            }
       }


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

        View v = convertView;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mContext.inflate(R.layout.categoriescell, parent, false);
            v.setTag(R.id.category, v.findViewById(R.id.category));
           v.setTag(R.id.cat_name,v.findViewById(R.id.cat_name));
           // v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.category);

        name=(TextView) v.getTag(R.id.cat_name);
        Log.e("splash","the catgeories intital flag_ct  ="+this.cat_int_id);
        if(this.cat_int_id==1)
        {
            Item it = getItem(position);

            picture.setImageResource(it.drawableId);
            name.setText(it.name);
        }
else
        {
            Modified_Item mit =getItemModified(position);
            Glide.with(context).load(mit.img_ct_mod)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(picture);
            name.setText(mit.name_mod);

        }


        return v;
    }

    private Modified_Item getItemModified(int position) {
        return modified_mItems.get(position);
    }

    private static class Item {

        public final int drawableId;
        public final String name;
        Item(int drawableId,String nm) {

            this.drawableId = drawableId;
            this.name=nm;
        }
    }
    private static class Modified_Item {

        public final String img_ct_mod;
        public final String name_mod;
        Modified_Item(String img_ctt_mod,String namee_mod) {

            this.img_ct_mod = img_ctt_mod;
            this.name_mod=namee_mod;
        }
    }
}