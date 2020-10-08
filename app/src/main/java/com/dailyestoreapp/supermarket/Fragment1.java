package com.dailyestoreapp.supermarket;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager mviewpager,flyerpager;
    ArrayList<String> categoriesEditCategies = new ArrayList<>();
    ArrayList<String> categoriesEditCategies_image = new ArrayList<>();
    ArrayList<String> firstflyer_image = new ArrayList<>();
    ArrayList<String> secondflyer_image = new ArrayList<>();
    ArrayList<String> deal_image = new ArrayList<>();
    ArrayList<Integer> categoriescatno_edit = new ArrayList<>();
    ArrayList<Integer> Sub_categories_id = new ArrayList<>();
    private Integer selectedSubCategoryNo;
    ArrayList<String> Sub_categories = new ArrayList<>();
    public static final String MY_PREFS_NAME = "CustomerApp";
    ImageAdapter adapterview;
    int firstttflyerid;
    int popupid;
    int intialsub=1;
    int cno;
    int secondddflyerid;
    String popup_img;
    ArrayList<String> Images_sub = new ArrayList<>();
    ExpandableHeightGridView gridview,dealsview;
    Imageadapterforflyers flyeradapterview;
    SearchView sr;
    ArrayList<String> item_image = new ArrayList<>();
    ArrayList<String> original_item_image_lts = new ArrayList<>();
    TextView t;
    GridviewAdapter gridadpt;
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    ACProgressFlower dialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //FirstViewFlyers("0");
        SharedPreferences shared = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String savedcatString = shared.getString("categories_new", "");
        String[] cats = savedcatString.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : cats) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                categoriesEditCategies.add(ct);
            }

        }

        SharedPreferences shared4 = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String savedcatString_image = shared4.getString("categories_image_new", "");
        String[] cats_image = savedcatString_image.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : cats_image) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                categoriesEditCategies_image.add(ct);
            }

        }

        SharedPreferences shared2 = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String savedString = shared2.getString("categories_no_new", "");
        Log.e("editcategories","the categories are images arae "+categoriesEditCategies_image);
        String[] numbers = savedString.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String number : numbers) {
            if(!(numbers.equals("")||numbers.equals(null)))
            {
                categoriescatno_edit.add(Integer.valueOf(number));
            }

        }
        SharedPreferences shared_cat_id = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int intial_cat_id =shared_cat_id.getInt("categories_intial",0);
        SharedPreferences shared_firstflyer = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String shared_firstflyer_img = shared_firstflyer.getString("first_flyer_new", "");
        String[] firstflyer_img = shared_firstflyer_img.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : firstflyer_img) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                firstflyer_image.add(ct);
            }

        }
        String shared_secondflyer = shared_firstflyer.getString("sec_flyers_images_new", "");
        String[] shared_secondflyer_img = shared_secondflyer.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : shared_secondflyer_img) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                secondflyer_image.add(ct);
            }

        }
        String shared_dealflyer = shared_firstflyer.getString("viewalldeals_img", "");
        String[] shared_dealflyer_img = shared_secondflyer.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : shared_dealflyer_img) {
            if(!(ct.equals("")||ct.equals(null)))
            {
                deal_image.add(ct);
            }

        }
        popup_img = shared_firstflyer.getString("firstpop_img", "");
        int popup_id = shared_firstflyer.getInt("popup_initalval",0);
        popupid=popup_id;


        int intial_firstflyer_id = shared_firstflyer.getInt("first_flyer_initalval",0);
        firstttflyerid=intial_firstflyer_id;
        Log.e("splash","the catgeories intital flag_ct  ="+intial_cat_id);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        int intial_secondflyer_id = shared_firstflyer.getInt("second_flyer_initalval",0);
        secondddflyerid=intial_secondflyer_id;
        Log.e("splash","the catgeories intital flag_ct  ="+intial_cat_id);

        int intial_deal_id = shared_firstflyer.getInt("deal_flyer_initalval",0);


        mviewpager = (ViewPager)rootView.findViewById(R.id.viewpager);
        flyerpager = (ViewPager)rootView.findViewById(R.id.viewpager2);
        sr = (SearchView)rootView.findViewById(R.id.searchview);

        sr.setQueryHint("Type here to search");
        sr.setBackgroundColor(getResources().getColor(R.color.white));
        sr.setIconifiedByDefault(false);
        EditText txtSearch = ((EditText)sr.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint("Type here to search");
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.black));
        txtSearch.setTextSize(12);

        flyeradapterview = new Imageadapterforflyers(getContext(),firstflyer_image,intial_firstflyer_id);
        flyerpager.setAdapter(flyeradapterview);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new Mytimertask2(),900,1800);
       adapterview = new ImageAdapter(getContext(),secondflyer_image,intial_secondflyer_id);
        mviewpager.setAdapter(adapterview);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),1500,3000);
        gridview = (ExpandableHeightGridView) rootView.findViewById(R.id.categories);
        gridview.setAdapter(new GridviewAdapter(rootView.getContext(),categoriesEditCategies,categoriesEditCategies_image,categoriescatno_edit,intial_cat_id));
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                cno = categoriescatno_edit.get(position);
                subcategoryactivate();
//                Intent next = new Intent(getContext(),Itemlisting.class);
//                String cname = categoriesEditCategies.get(position);
//                 cno = categoriescatno_edit.get(position);
//                Log.e("frag1","the category name and no is "+cname+cno);
//                startActivity(next);
            }
        });
        dealsview = (ExpandableHeightGridView) rootView.findViewById(R.id.dealsoftheday);
        dealsview.setAdapter(new DealsdayAdapter(rootView.getContext(),deal_image,intial_deal_id));
        dealsview.setExpanded(true);
        dealsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });


        return rootView;
    }

    public class Mytimertask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("text","flyer++"+mviewpager.getCurrentItem());
                    if(secondddflyerid==1)
                    {
                        if(mviewpager.getCurrentItem() ==0)
                        {
                            mviewpager.setCurrentItem(1);
                        }
                        else if(mviewpager.getCurrentItem()==1)
                        {
                            mviewpager.setCurrentItem(2);

                        }
                        else if(mviewpager.getCurrentItem()==2)
                        {
                            mviewpager.setCurrentItem(3);

                        }
                        else
                        {
                            mviewpager.setCurrentItem(0);
                        }
                    }
                    else
                    {
                        int sec_length = secondflyer_image.size();

                        if (mviewpager.getCurrentItem() == 0) {
                            mviewpager.setCurrentItem(1);
                        }
                        else if ((mviewpager.getCurrentItem() == 1)&&(sec_length<2)) {
                            mviewpager.setCurrentItem(2);
                        }
                        else if ((mviewpager.getCurrentItem() == 2)&&(sec_length<3)) {
                            mviewpager.setCurrentItem(3);
                        }
                        else
                        {
                            mviewpager.setCurrentItem(0);

                        }
                    }

                }
            });
        }
    }
    public class Mytimertask2 extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("text","flyer++"+flyerpager.getCurrentItem());
                    if(firstttflyerid==1)
                    {
                        if (flyerpager.getCurrentItem() == 0) {
                            flyerpager.setCurrentItem(1);
                        } else if (flyerpager.getCurrentItem() == 1) {
                            flyerpager.setCurrentItem(2);

                        } else if (flyerpager.getCurrentItem() == 2) {
                            flyerpager.setCurrentItem(3);

                        } else {
                            flyerpager.setCurrentItem(0);
                        }
                    }
                    else {

                       int firstflyer_length = firstflyer_image.size();

                        if (flyerpager.getCurrentItem() == 0) {
                            flyerpager.setCurrentItem(1);
                        }
                        else if ((flyerpager.getCurrentItem() == 1)&&(firstflyer_length<2)) {
                            flyerpager.setCurrentItem(2);
                        }
                       else if ((flyerpager.getCurrentItem() == 2)&&(firstflyer_length<3)) {
                            flyerpager.setCurrentItem(3);
                        }
                        else
                        {
                            flyerpager.setCurrentItem(0);

                        }


                        }
                }
            });
        }
    }


    private void subcategoryactivate()
    {
        final StringBuilder strbul  = new StringBuilder();
        final StringBuilder sb  = new StringBuilder();
        final StringBuilder sb_images  = new StringBuilder();

        if(Sub_categories.size()>0)
        {
            Sub_categories.clear();
            intialsub=1;

        }
        if(Sub_categories_id.size()>0)
        {
            Sub_categories_id.clear();
        }
        if(Images_sub.size()>0)
        {
            Images_sub.clear();
        }

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
        Log.e("frag1","cno="+cno);
        Call<CustomerAppResponse> call = mainInterface.SubCategory(cno);
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
                        if(!Sub_categories.contains(sub_name))
                        {
                            Sub_categories.add(sub_name);
                            Images_sub.add(sub_item);
                            int sub_Cat_id = Integer.parseInt(catObj1.getSubId());
                            Sub_categories_id.add(sub_Cat_id);
                            String intial_pop =catObj1.getinitialSetidSub();
                            Log.e("frG1","Values="+catObj1+intial_pop);
                            if(intial_pop.equals("0"))
                            {
                                intialsub = 0;
                            }
                        }

                    }


                    Log.e("fragment1","sub_cat inside Activate"+Sub_categories);
//                    dialog.dismiss();
                    selectedSubCategoryNo=1;
                    Iterator<Integer> iter = Sub_categories_id.iterator();
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
                    Iterator<String> itr_string = Sub_categories.iterator();
                    while (itr_string.hasNext())
                    {

                        sb.append(itr_string.next());
                        if(itr_string.hasNext()){
                            sb.append(",");
                        }
                    }


                    Iterator<String> itr_string_image = Images_sub.iterator();
                    while (itr_string_image.hasNext())
                    {

                        sb_images.append(itr_string_image.next());
                        if(itr_string_image.hasNext()){
                            sb_images.append(",");
                        }
                    }
                    Intent next = new Intent(getContext(),Itemlisting.class);
                    Bundle b = new Bundle();
                    b.putString("backpressed","0");
                    b.putString("subCatName",sb.toString());
                    b.putString("subCatId",strbul.toString());
                    b.putString("subCatImage",sb_images.toString());
                    b.putString("subInitial", String.valueOf(intialsub));
                   next.putExtras(b);
                    startActivity(next);


                    //personNames_offers = new ArrayList<>(Arrays.asList("farg4ITEM1", "frag4ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6"));



            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

            }
        });


    }




}
