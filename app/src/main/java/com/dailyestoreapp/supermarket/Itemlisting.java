package com.dailyestoreapp.supermarket;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.ToLongBiFunction;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Itemlisting extends AppCompatActivity {
    ArrayList personNames = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    public static final String MY_PREFS_NAME = "CustomerApp";
    // ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7"));
    RecyclerView recyclerView,itemlistingcategory;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    static ItemAdapter customAdapter;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int subidd;
    int selectedsub_cat;
    ACProgressFlower dialog;
    int initialid;
    ArrayList<String> SubcategoriesEditCategies = new ArrayList<>();
    ArrayList<String> SubcategoriesEditCategies_image = new ArrayList<>();
    ArrayList<Integer> Subcategoriescatno_edit = new ArrayList<>();
    Listingsubcategoryadapter customadapter2;
    ArrayList<String> Images_sub = new ArrayList<>();
    ArrayList<String> Sub_categories = new ArrayList<>();
    ArrayList<String> item_image = new ArrayList<>();
    ArrayList<String> Item_categories = new ArrayList<>();
    ArrayList<String> cod_eligible = new ArrayList<>();
    ArrayList<String> refund_eligible = new ArrayList<>();
    ArrayList<String> Item_categories_offer_desc = new ArrayList<>();
    //    ArrayList<Integer> Item_Quantity = new ArrayList<>();
    ArrayList<String> Item_Quantity = new ArrayList<>();
    ArrayList<String> Item_description = new ArrayList<>();
    ArrayList<Integer> Item_Price = new ArrayList<>();
    ArrayList<Integer> Sub_categories_id = new ArrayList<>();
    ArrayList<Integer> item_id = new ArrayList<>();
    ArrayList<Integer> item_id_offer = new ArrayList<>();
    ArrayList<Integer> item_id_status = new ArrayList<>();
    ArrayList<String> original_Item_categories_lts = new ArrayList<>();
    ArrayList<String> item_image_lts = new ArrayList<>();
    ArrayList<Integer> Item_Quantity_lts = new ArrayList<>();
    ArrayList<Integer> Item_Price_lts = new ArrayList<>();
    ArrayList<Integer> item_id_lts = new ArrayList<>();
    ArrayList<Integer> item_id_status_lts = new ArrayList<>();
    ArrayList<String> Item_categories_offer_desc_lts = new ArrayList<>();
    ArrayList<Integer> item_id_offer_lts = new ArrayList<>();
    ArrayList<String> original_item_image_lts = new ArrayList<>();
    //    ArrayList<Integer> original_Item_Quantity_lts = new ArrayList<>();
    ArrayList<String> original_Item_Quantity_lts = new ArrayList<>();
    ArrayList<Integer> original_Item_Price_lts = new ArrayList<>();
    ArrayList<Integer> original_item_id_lts = new ArrayList<>();
    ArrayList<Integer> original_item_id_status_lts = new ArrayList<>();
    ArrayList<String> original_Item_categories_offer_desc_lts = new ArrayList<>();
    ArrayList<Integer> original_item_id_offer_lts = new ArrayList<>();
    int start = 0,limit=5;
    int mCartItemCount = 10;
    Toolbar t;
    String fullname;
    static String flag;
    static TextView txt;
    TextView preorders;
    String  cat_name_for_validatingcakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlisting);
        final SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullname = fullname_shared.getString("fullusername","");
        t = (Toolbar)findViewById(R.id.toolbar_itemlisting_item);
        setSupportActionBar(t);
        preorders = findViewById(R.id.preorder);
        nestedScrollView=(NestedScrollView)findViewById(R.id.scroll_view);
        progressBar=findViewById(R.id.progress_bar);
        //nestedScrollView=(NestedScrollView)findViewById(R.id.scroll_view);
        progressBar=findViewById(R.id.progress_bar);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        flag= extras.getString("backpressed");
        String sb_names = extras.getString("subCatName");
        String sb_id = extras.getString("subCatId");
        String sb_img = extras.getString("subCatImage");
        String  sb_inital =extras.getString("subInitial");
        cat_name_for_validatingcakes =extras.getString("cat_name_for_intent");
        if(cat_name_for_validatingcakes.equals("Cakes")||cat_name_for_validatingcakes.equals("CAKES")||cat_name_for_validatingcakes.equals("Cake")||cat_name_for_validatingcakes.equals("CAKE"))
        {
            preorders.setVisibility(View.VISIBLE);
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(120); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            preorders.startAnimation(anim);
        }
        else
        {
            preorders.setVisibility(View.GONE);
        }

        String[] cats = sb_names.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : cats) {
            if (!(ct.equals("") || ct.equals(null))) {
                SubcategoriesEditCategies.add(ct);
            }
        }
        String[] numbers = sb_id.split(",");
        for (String number : numbers) {
            if(!(numbers.equals("")||numbers.equals(null)))
            {
                Subcategoriescatno_edit.add(Integer.valueOf(number));
            }

        }
        String[] sb_imagess = sb_img.split(",");//if spaces are uneven, use \\s+ instead of " "
        for (String ct : sb_imagess) {
            if (!(ct.equals("") || ct.equals(null))) {
                SubcategoriesEditCategies_image.add(ct);
            }
        }
        initialid= Integer.parseInt(sb_inital);
        Log.e("ItemListing","the values are "+sb_names+sb_id+sb_img+sb_inital);

        t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prev= new Intent(Itemlisting.this,Main2Activity.class);
                startActivity(prev);
            }
        });

//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemlistingcategory = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        itemlistingcategory.setLayoutManager(linearLayoutManager2);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customadapter2 = new Listingsubcategoryadapter(Itemlisting.this,SubcategoriesEditCategies,SubcategoriesEditCategies_image,Subcategoriescatno_edit,initialid,communication);
        itemlistingcategory.setAdapter(customadapter2);
        //second recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.itemrecycler);
        // set a LinearLayoutManager with default vertical orientation
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        Log.e("names","the names =="+personNames);
//        customAdapter = new ItemAdapter(Itemlisting.this, personNames);
//        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        subidd = Subcategoriescatno_edit.get(0);
        selectedsub_cat=subidd;
        Log.e("itemlist","selected sub id before itemlist="+subidd);
        SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor_frst.putInt("selected_sub_id_items", subidd);
        editor_frst.apply();
        ItemsList(subidd,start,limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY== v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
                {
                    // page++;
                    start=start+limit;
                    progressBar.setVisibility(View.VISIBLE);
                    Log.e("itemlist","itemlist=="+start+limit);
                    ItemsList(subidd,start,limit);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem mc =menu.findItem(R.id.cart3);
        View v  = mc.getActionView();
        txt = mc.getActionView().findViewById(R.id.cart_badge3);
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String cart_Items_toolbar_count = fullname+"cart_Items_toolbar_count";
        String itemSingle_name_old = shared.getString(cart_Items_toolbar_count, "");
        if(itemSingle_name_old.equals(""))
        {
            txt.setText("0");
        }
        else
        {

            txt.setText(itemSingle_name_old);
        }

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(Itemlisting.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","1");
                cart.putExtras(b);
                startActivity(cart);

            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(Itemlisting.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","1");
                cart.putExtras(b);
                startActivity(cart);
            }
        });
        //SearchView searchView = (SearchView)menuItem.getActionView();
//        searchView.setQueryHint("Type here to search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String text = newText;
//           customAdapter.filter(text);
//                return true;
//            }
        //  });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.e("MAin","Item selected ="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.cart3:
                Intent cart = new Intent(Itemlisting.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","1");
                cart.putExtras(b);
                startActivity(cart);
                return true;
            case R.id.account:
                // do whatever
                Intent cartp = new Intent(Itemlisting.this,Login.class);
                startActivity(cartp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ItemsList(Integer subId ,int st,int lmt)
    {
        Log.e("items","the items are"+st+lmt);
//        dialog = new ACProgressFlower.Builder(Itemlisting.this)
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .borderPadding(1)
//
//                .fadeColor(Color.DKGRAY).build();
//        dialog.show();


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
        Call<CustomerAppResponse> call = mainInterface.Items(subId,st,lmt);
        call.enqueue(new Callback<CustomerAppResponse>() {
            @Override
            public void onResponse(Call<CustomerAppResponse> call, retrofit2.Response<CustomerAppResponse> response) {
                CustomerAppResponse listCategoryResponseobject = response.body();
                String success = response.body().getResponsedata().getSuccess();
                progressBar.setVisibility(View.GONE);
//                dialog.dismiss();
                //  dialog.dismiss();
                //    Log.e("frag4","success="+response.body());
//                if(success==1)
//                {
//                    int data_length = response.body().getResponsedata().getData().size();
//                    S
//                String rtring item_name = response.body().getResponsedata().getData().get(1).getItemName();
////                }es= new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getResponsedata());
//                JsonObject obj = new JsonParser().parse(res).getAsJsonObject();

                try {
//                    JSONObject jo2 = new JSONObject(obj.toString());
//                    JSONArray categoriesarray = jo2.getJSONArray("data");
//                    Log.e(tag,"items="+jo2);
//                    Item_categories.clear();
//                    item_image.clear();
//                    Item_Quantity.clear();
//                    Item_description.clear();
//                    cod_eligible.clear();
//                    refund_eligible.clear();
//                    Item_Price.clear();
//                    item_id.clear();
//                    item_id_status.clear();
//                    Item_categories_offer_desc.clear();
//                    item_id_offer.clear();
                    original_Item_categories_lts.clear();
                    original_Item_Quantity_lts.clear();
                    original_Item_Price_lts.clear();
                    original_item_id_lts.clear();
                    original_item_id_status_lts.clear();
                    original_item_image_lts.clear();
                    original_Item_categories_offer_desc_lts.clear();
                    original_item_id_offer_lts.clear();
                    if(success.equals("1"))
                    {
                        int data_length = response.body().getResponsedata().getData().size();



                        for(int i=0; i<data_length; i++) {
//                        JSONObject j1= categoriesarray.getJSONObject(i);
//                        String item_name = j1.getString("itemName");
                            String Stats_active = response.body().getResponsedata().getData().get(i).getStatus();
                            Log.e("frag4","success="+response.body().getResponsedata().getData().get(i).getItemName());
                            if (Stats_active.equals("1"))
                            {

                                String item_name = response.body().getResponsedata().getData().get(i).getItemName();
                                int it_id = Integer.parseInt(response.body().getResponsedata().getData().get(i).getItemId());

                                String item_quant = response.body().getResponsedata().getData().get(i).getQuantity();
                                Integer item_price = Integer.valueOf(response.body().getResponsedata().getData().get(i).getPrice());
                                Integer item_status = Integer.valueOf(response.body().getResponsedata().getData().get(i).getStatus());
                                String imageurl = response.body().getResponsedata().getData().get(i).getImage();
                                Integer offer_percent = Integer.valueOf(response.body().getResponsedata().getData().get(i).getoffer());
                                String offer_desc = response.body().getResponsedata().getData().get(i).getofdescription();
                                String cod_elgble = response.body().getResponsedata().getData().get(i).getCashOnDelivery();
                                String refund_elgble = response.body().getResponsedata().getData().get(i).getRefund();
                                String desc = response.body().getResponsedata().getData().get(i).getDescription();
                                String imageurl_total = url1 + imageurl;
                                Log.e("itemlisting", "imageurl" + url1 + imageurl);
                                Item_categories.add(item_name);
                                Item_Quantity.add(item_quant);
                                Item_Price.add(item_price);
                                item_id.add(it_id);
                                item_id_status.add(item_status);
                                item_image.add(imageurl_total);
                                Item_categories_offer_desc.add(offer_desc);
                                item_id_offer.add(offer_percent);
                                cod_eligible.add(cod_elgble);
                                refund_eligible.add(refund_elgble);
                                Item_description.add(desc);
                            }
                            customAdapter = new ItemAdapter(Itemlisting.this, Item_categories,Item_Quantity,Item_Price,item_id,item_image,Item_categories_offer_desc,item_id_offer,cod_eligible,refund_eligible,Item_description,cat_name_for_validatingcakes);
                            recyclerView.setAdapter(customAdapter);
                        }
                        original_Item_categories_lts.addAll(Item_categories);
                        original_Item_Quantity_lts.addAll(Item_Quantity);
                        original_Item_Price_lts.addAll(Item_Price);
                        original_item_id_lts.addAll(item_id);
                        original_item_id_status_lts.addAll(item_id_status);
                        original_item_image_lts.addAll(item_image);
                        original_Item_categories_offer_desc_lts.addAll(Item_categories_offer_desc);
                        original_item_id_offer_lts.addAll(item_id_offer);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                        recyclerView.setLayoutManager(linearLayoutManager);
                        //  call the constructor of CustomAdapter to send the reference and data to Adapter
                        // Log.e("names","the names =="+personNames);
                        // parseJson( Item_categories,Item_Quantity,Item_Price,item_id,item_image,Item_categories_offer_desc,item_id_offer,cod_eligible,refund_eligible,Item_description,cat_name_for_validatingcakes);



                    }
                    else {
                        //  Toast.makeText(getContext(),"No data found in next category",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Itemlisting","error="+e.getMessage());
                    Toast.makeText(Itemlisting.this,"something went wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponse> call, Throwable t) {
                //  dialog.dismiss();
                // Toast.makeText(Itemlisting.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void parseJson(ArrayList Item_categories_adapter,ArrayList Item_Quantity_adapter,ArrayList Item_Price_adapter,ArrayList item_id_adapter,ArrayList item_image_adapter,ArrayList Item_categories_offer_desc_adapter,ArrayList item_id_offer_adapter, ArrayList Item_cod_adapter,
                           ArrayList Item_Refunf_adapter,  ArrayList Item_description, String cat_nane) {
        customAdapter = new ItemAdapter(Itemlisting.this, Item_categories_adapter,Item_Quantity_adapter, Item_Price_adapter,item_id_adapter, item_image_adapter,Item_categories_offer_desc_adapter,item_id_offer_adapter,Item_cod_adapter,
                Item_Refunf_adapter,  Item_description, cat_nane);
        recyclerView.setAdapter(customAdapter);


    }
    SubCategoryClickItem communication=new SubCategoryClickItem() {
        @Override
        public void respond(Integer name) {
            start=0;
            Log.e("Itemlisting"," sub name is"+name);
            Item_categories.clear();
            item_image.clear();
            Item_Quantity.clear();
            cod_eligible.clear();
            refund_eligible.clear();
            Item_Price.clear();
            item_id.clear();
            item_id_status.clear();
            Item_description.clear();
            Item_categories_offer_desc.clear();
            item_id_offer.clear();
            SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor_frst.putInt("selected_sub_id_items", name);
            editor_frst.apply();
            selectedsub_cat=name;
            ItemsList(name,0,5);
            //  customAdapter_offers.notifyDataSetChanged();


        }

    };
    public static void update_counter(String value){
        try{
            txt.setText(value);
        }
        catch (Exception ex){
            Log.d("Exception","Exception of type"+ex.getMessage());
        }
    }
    public static Integer Get_counter(){
        Integer n = Integer.valueOf(txt.getText().toString());
        return n;
    }

    public void  updaterecyclerview()
    {
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        int sb_item = shared.getInt("selected_sub_id_items",0);
        Log.e("itemlist","selected sub id after itemlist="+sb_item);
        Log.e("itemlist","selected sub id after itemlist="+sb_item);
//    Item_categories.clear();
//    item_image.clear();
//    Item_Quantity.clear();
//    cod_eligible.clear();
//    refund_eligible.clear();
//    Item_Price.clear();
//    item_id.clear();
//    item_id_status.clear();
//    Item_description.clear();
//    Item_categories_offer_desc.clear();
//    item_id_offer.clear();
        // ItemsList(sb_item,0,3);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updaterecyclerview();
    }

}