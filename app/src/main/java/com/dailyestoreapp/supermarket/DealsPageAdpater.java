package com.dailyestoreapp.supermarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DealsPageAdpater extends RecyclerView.Adapter<DealsPageAdpater.MyViewHolder> {

    Context context;
    int cat_deal_id;
    private Context ctx;
    ArrayList<String> deal_image;
    ArrayList<Integer> deals_type_id_for_click_test_adapter = new ArrayList<>();

    int intialsub=1;
    public Integer[] mThumbIds = {R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday, R.drawable.dealoftheday};
    private final List<Item> mItems = new ArrayList<Item>();
    private final List<Modified_Item_Deal> modified_mItems = new ArrayList<Modified_Item_Deal>();
    public DealsPageAdpater(Context context, ArrayList<String> deal_image, int initalid,ArrayList<Integer> deals_type_id_for_click_test_adapter) {
        this.context = context;
        this.cat_deal_id = initalid;

        this.deal_image = deal_image;
        this.deals_type_id_for_click_test_adapter=deals_type_id_for_click_test_adapter;
        if (this.cat_deal_id == 1) {
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
            mItems.add(new Item(R.drawable.dealoftheday));
        } else {
            for (int i = 0; i < deal_image.size(); i++) {
                String imageurl_total = deal_image.get(i);

                modified_mItems.add(new Modified_Item_Deal(imageurl_total));
            }
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_new_page, parent, false);
        // set the view's size, margins, paddings and layout parameters
       MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (this.cat_deal_id == 1) {
            Item it = getItem(position);

           holder.img.setImageResource(it.drawableId);

        } else {
            Modified_Item_Deal mit = getItemModified(position);
            Log.e("dealsadapter","img_deal_mod"+mit.img_deal_mod);
            Glide.with(context).load(mit.img_deal_mod)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);


        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deals_typeid_pageadapter;
                deals_typeid_pageadapter=deals_type_id_for_click_test_adapter.get(position);
                subcategoryactivate_for_dealsclickontestactivity(deals_typeid_pageadapter);
            }
        });

    }

    public Item getItem(int position) {
        return mItems.get(position);
    }
    @Override
    public int getItemCount() {
        if (this.cat_deal_id == 0) {
            return this.deal_image.size();
        }
        return mThumbIds.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.category_deal);

            // get the reference of item view's

        }
    }
    private Modified_Item_Deal getItemModified(int position) {
        return modified_mItems.get(position);
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
    private void subcategoryactivate_for_dealsclickontestactivity(int deal_typeidd_number)
    {
        final ArrayList<String> Sub_categories_deals = new ArrayList<>();
        final ArrayList<Integer> Sub_categories_id_deals = new ArrayList<>();
        final ArrayList<String> Images_sub_deals = new ArrayList<>();
        final StringBuilder strbul  = new StringBuilder();
        final StringBuilder sb  = new StringBuilder();
        final StringBuilder sb_images  = new StringBuilder();

        int type =1;
        String url = "http://dailyestoreapp.com/dailyestore/api/";

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ResponseInterface mainInterface = retrofit.create(ResponseInterface.class);
        Log.e("frag1","cno="+deal_typeidd_number);

        Call<CustomerAppResponse> call = mainInterface.SubCategory(deal_typeidd_number);
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse catObj = response.body();
                int cat_length = catObj.getResponsedata().getData().size();



                for(int i=0; i<cat_length; i++)
                {
                    Datum catObj1 = catObj.getResponsedata().getData().get(i);
                    String sub_name = catObj1.getSubName();
                    String sub_item =catObj1.getsubItemImage();
                    if(!Sub_categories_deals.contains(sub_name))
                    {
                        Sub_categories_deals.add(sub_name);
                        Images_sub_deals.add(sub_item);
                        int sub_Cat_id = Integer.parseInt(catObj1.getSubId());
                        Sub_categories_id_deals.add(sub_Cat_id);
                        String intial_pop =catObj1.getinitialSetidSub();
                        Log.e("frG1","Values="+catObj1+intial_pop);
                        if(intial_pop.equals("0"))
                        {
                            intialsub = 0;
                        }
                    }

                }


                Log.e("fragment1","sub_cat inside Activate"+Sub_categories_deals);
//                    dialog.dismiss();

                Iterator<Integer> iter = Sub_categories_id_deals.iterator();
                while(iter.hasNext())
                {
                    strbul.append(iter.next());
                    if(iter.hasNext()){
                        strbul.append(",");
                    }
                }
                strbul.toString();

                // to store categories
                Log.e("res","res="+strbul);
                Iterator<String> itr_string = Sub_categories_deals.iterator();
                while (itr_string.hasNext())
                {

                    sb.append(itr_string.next());
                    if(itr_string.hasNext()){
                        sb.append(",");
                    }
                }


                Iterator<String> itr_string_image = Images_sub_deals.iterator();
                while (itr_string_image.hasNext())
                {

                    sb_images.append(itr_string_image.next());
                    if(itr_string_image.hasNext()){
                        sb_images.append(",");
                    }
                }
                Intent next = new Intent(context,Itemlisting.class);
                Bundle b = new Bundle();
                b.putString("backpressed","0");
                b.putString("subCatName",sb.toString());
                b.putString("subCatId",strbul.toString());
                b.putString("subCatImage",sb_images.toString());
                b.putString("subInitial", String.valueOf(intialsub));
                next.putExtras(b);
                context.startActivity(next);


                //personNames_offers = new ArrayList<>(Arrays.asList("farg4ITEM1", "frag4ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6"));



            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

            }
        });


    }

}
