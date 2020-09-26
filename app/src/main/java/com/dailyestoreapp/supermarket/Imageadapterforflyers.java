package com.dailyestoreapp.supermarket;

import android.content.Context;
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


public class Imageadapterforflyers extends PagerAdapter {
    Context mcontext;
    ArrayList<String> firstflyerimage;
    private LayoutInflater inflater;
    int firstflyerid;
    private LayoutInflater layoutInflater;
    Imageadapterforflyers(Context context,ArrayList<String> firstflyerimage, int ffid)
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
            R.drawable.firstflyer,R.drawable.firstflyer,R.drawable.firstflyer,R.drawable.firstflyer
    };

    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {
        ImageView imageview = new ImageView(mcontext);

//        View imageLayout = inflater.inflate(R.layout.firstflyerimagelayout, container, false);
//        final ImageView imageview = (ImageView) imageLayout
//                .findViewById(R.id.image);

        // ImageView imageview = new ImageView(mcontext);
      //  imageview.setRadius(30);
        if(firstflyerid==0)
        {
//            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
//
//            Glide.with(mcontext).load(firstflyerimage.get(position))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageview);
//            ((ViewPager) container).addView(imageview,0);
        }
        else
        {

        }
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
