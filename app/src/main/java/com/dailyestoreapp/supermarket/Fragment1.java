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

import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<Integer> categoriescatno_edit = new ArrayList<>();
    public static final String MY_PREFS_NAME = "CustomerApp";
    ImageAdapter adapterview;
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
        int intial_firstflyer_id = shared_firstflyer.getInt("first_flyer_initalval",0);

        Log.e("splash","the catgeories intital flag_ct  ="+intial_cat_id);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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
       adapterview = new ImageAdapter(getContext(),original_item_image_lts);
        mviewpager.setAdapter(adapterview);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),1500,3000);
        gridview = (ExpandableHeightGridView) rootView.findViewById(R.id.categories);
        gridview.setAdapter(new GridviewAdapter(rootView.getContext(),categoriesEditCategies,categoriesEditCategies_image,categoriescatno_edit,intial_cat_id));
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent next = new Intent(getContext(),Itemlisting.class);
                startActivity(next);
            }
        });
        dealsview = (ExpandableHeightGridView) rootView.findViewById(R.id.dealsoftheday);
        dealsview.setAdapter(new DealsdayAdapter(rootView.getContext()));
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
                    if(mviewpager.getCurrentItem() ==0)
                    {
                        mviewpager.setCurrentItem(1);
                    }
                    else if(mviewpager.getCurrentItem()==1)
                    {
                        mviewpager.setCurrentItem(2);

                    }

                    else
                    {
                        mviewpager.setCurrentItem(0);
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
                    if(flyerpager.getCurrentItem() ==0)
                    {
                        flyerpager.setCurrentItem(1);
                    }
                    else if(flyerpager.getCurrentItem()==1)
                    {
                        flyerpager.setCurrentItem(2);

                    }
                    else if(flyerpager.getCurrentItem()==2)
                    {
                        flyerpager.setCurrentItem(3);

                    }


                    else
                    {
                        flyerpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    private void FirstViewFlyers(final String tid)
    {
        dialog = new ACProgressFlower.Builder(getContext())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .borderPadding(1)

                .fadeColor(Color.DKGRAY).build();
        dialog.show();


        String url = "http://dailyestoreapp.com/dailyestore/api/";
        final String url1 = "http://dailyestoreapp.com/dailyestore/";
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
        if(tid.equals("0"))
        {
            Call<CustomerAppResponse> call = mainInterface.allFlyers();
            call.enqueue(new Callback<CustomerAppResponse>() {
                @Override
                public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                    CustomerAppResponse listCategoryResponseobject = response.body();
                    int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                    Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());
                    int data_length = response.body().getResponsedata().getData().size();


                    try {


                        if(success==1) {
                            for (int i = 0; i < data_length; i++) {
                                String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                                String imageurl_total=url1+imageurl;
                                item_image.add(imageurl_total);
                                Log.e("fragment1","the imageurl is===="+imageurl);
                            }
                            original_item_image_lts.addAll(item_image);
                            Log.e("fragment1","the imageurl is===="+original_item_image_lts);
                            adapterview.notifyDataSetChanged();

                        }
                        else {

                            Toast.makeText(getContext(),"No Data found",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CustomerAppResponse> call, Throwable t) {

                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }



      //  adapterview.notifyDataSetChanged();
        dialog.dismiss();

    }
}
