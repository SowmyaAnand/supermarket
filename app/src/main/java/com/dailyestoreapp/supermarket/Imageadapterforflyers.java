package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class Imageadapterforflyers extends PagerAdapter {
    Context mcontext;
    Imageadapterforflyers(Context context)
    {
        this.mcontext=context;
    }



    @Override
    public int getCount() {
        return slideImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }

    private int[] slideImageId = new int[]{
            R.drawable.bannerthin1,R.drawable.bannerthin2,R.drawable.bannerthin3,R.drawable.banner1,R.drawable.banner2,R.drawable.banner3
    };

    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {
        ImageView imageview = new ImageView(mcontext);
        // ImageView imageview = new ImageView(mcontext);
      //  imageview.setRadius(30);
        imageview.setScaleType(ImageView.ScaleType.FIT_XY);

        imageview.setImageResource(slideImageId[position]);
        ((ViewPager) container).addView(imageview,0);
        return imageview;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        ((ViewPager)container).removeView((ImageView)object);
    }

}
