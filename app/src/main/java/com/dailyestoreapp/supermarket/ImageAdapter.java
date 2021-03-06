package com.dailyestoreapp.supermarket;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;


public class ImageAdapter extends PagerAdapter {
    Context mcontext;
    ArrayList<String> firstflyerimage;
    private LayoutInflater inflater;
    int firstflyerid;
    private LayoutInflater layoutInflater;
    //second flyer
    ImageAdapter(Context context,ArrayList<String> firstflyerimage, int ffid)
    {
        inflater = LayoutInflater.from(context);
        this.mcontext=context;
        this.firstflyerimage=firstflyerimage;
        this.firstflyerid=ffid;
    }



    @Override
    public int getCount() {

        if(this.firstflyerid==0)
        {
            return firstflyerimage.size();
        }
        return slideImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }

    private int[] slideImageId = new int[]{
//            R.drawable.bannerthin1,R.drawable.bannerthin2,R.drawable.bannerthin3,R.drawable.banner2
            R.drawable.flyer21,R.drawable.flyer22,R.drawable.flyer23,R.drawable.flyer24
    };

    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {


        View imageLayout = inflater.inflate(R.layout.secondflyerimage, container, false);
        final ImageView imageview = (ImageView) imageLayout.findViewById(R.id.image);


        if(firstflyerid==0)
        {
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(mcontext).load(firstflyerimage.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageview);
            ((ViewPager) container).addView(imageview,0);
        }
        else
        {
            ImageView imageview2 = new ImageView(mcontext);
            imageview2.setScaleType(ImageView.ScaleType.FIT_XY);

            imageview2.setImageResource(slideImageId[position]);
            ((ViewPager) container).addView(imageview2,0);
            return imageview2;
        }

        return imageview;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        ((ViewPager)container).removeView((ImageView)object);
    }

}