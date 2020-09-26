package com.dailyestoreapp.supermarket;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;


public class ImageAdapter extends PagerAdapter {
    Context mcontext;
    ArrayList<String> item_image_flyer1 = new ArrayList<>();
    //private ArrayList slideImageId = new ArrayList<>();
   private ArrayList slideImageId = new ArrayList<>(Arrays.asList( R.drawable.secondflyer,R.drawable.sbcat,R.drawable.subcategory));
    ImageAdapter(Context context, ArrayList<String> item_image)
    {
        this.mcontext=context;
        this.item_image_flyer1=item_image;
        Log.e("imageadpater","flyers="+item_image_flyer1+item_image_flyer1.size());
        if(item_image_flyer1.size()>0)
        {
            Log.e("imageadpater","flyers if="+item_image_flyer1+item_image_flyer1.size());
            slideImageId.addAll(item_image_flyer1);
        }
    }



    @Override
    public int getCount() {

        return slideImageId.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }



    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {
        Log.e("imagedadpter","position ="+position+slideImageId);
        ImageView imageview = new ImageView(mcontext);
        // ImageView imageview = new ImageView(mcontext);
        //imageview.setRadius(30);
        imageview.setScaleType(ImageView.ScaleType.FIT_XY);

        imageview.setImageResource((Integer) slideImageId.get(position));

        ((ViewPager) container).addView(imageview,0);
        return imageview;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        ((ViewPager)container).removeView((ImageView)object);
    }


}
