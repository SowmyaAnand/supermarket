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
    ArrayList<String> item_image_flyer1 = new ArrayList<>();
    //private ArrayList slideImageId = new ArrayList<>();
    ArrayList<String> secondflyerimage;
    int secid;
    private LayoutInflater inflater;
   private ArrayList slideImageId = new ArrayList<>(Arrays.asList( R.drawable.secondflyer,R.drawable.sbcat,R.drawable.subcategory,R.drawable.secondflyer));
    ImageAdapter(Context context, ArrayList<String> secondflyerimage, int secid)
    {
        inflater = LayoutInflater.from(context);
        this.mcontext=context;
        this.secondflyerimage=secondflyerimage;
        this.secid=secid;
    }



    @Override
    public int getCount() {
        if(this.secid==0)
        {
            return secondflyerimage.size();
        }
        return slideImageId.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }



    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {
        View imageLayout = inflater.inflate(R.layout.firstflyerimagelayout, container, false);
        final ImageView imageview = (ImageView) imageLayout.findViewById(R.id.image);

        if(secid==0)
        {
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);

            Glide.with(mcontext).load(secondflyerimage.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageview);
            ((ViewPager) container).addView(imageview,0);
        }
        else
        {
            ImageView imageview2 = new ImageView(mcontext);

            imageview2.setScaleType(ImageView.ScaleType.FIT_XY);

            imageview2.setImageResource((Integer) slideImageId.get(position));

            ((ViewPager) container).addView(imageview2,0);
        }

        return imageview;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object)
    {
        ((ViewPager)container).removeView((ImageView)object);
    }


}
