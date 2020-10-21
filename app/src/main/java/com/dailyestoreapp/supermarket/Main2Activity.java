package com.dailyestoreapp.supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    Dialog dialog;
    String popup_img;
    int popupid;
    String fullname;
    static TextView txt;
    public static final String MY_PREFS_NAME = "CustomerApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        dialog = new Dialog(this);
        final SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        fullname = fullname_shared.getString("fullusername","");
        SharedPreferences shared_firstpop = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        popup_img = shared_firstpop.getString("firstpop_img", "");
        int popup_id = shared_firstpop.getInt("popup_initalval",0);
        popupid=popup_id;
        ShowPopup(this);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,new hmefragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
                Intent cart = new Intent(Main2Activity.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","0");
                cart.putExtras(b);
                startActivity(cart);

            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(Main2Activity.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","0");
                cart.putExtras(b);
                startActivity(cart);
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
//            case R.id.nav_home:getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,new hmefragment()).commit();
//                break;
            case R.id.nav_todays_deal:
                Intent n = new Intent(Main2Activity.this,test.class);
                startActivity(n);
                break;
//            case R.id.nav_my_account:
//                Intent n1 = new Intent(Main2Activity.this,test.class);
//                startActivity(n1);
//                break;
            case R.id.nav_customer_support:
                Intent n3 = new Intent(Main2Activity.this,MenuContactUs.class);
                startActivity(n3);
                break;
            case R.id.nav_my_orders:
                Intent n4 = new Intent(Main2Activity.this,Myorders.class);
                startActivity(n4);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.e("MAin","Item selected ="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.cart:
                Intent cart = new Intent(Main2Activity.this,CartPage.class);
                Bundle b = new Bundle();
                b.putString("from_item_flag","0");
                cart.putExtras(b);
                startActivity(cart);
                return true;
            case R.id.account:
                // do whatever
                Intent cartp = new Intent(Main2Activity.this,Login.class);
                startActivity(cartp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void ShowPopup(Main2Activity v) {
        TextView txtclose;
        ImageView p_image;
        dialog.setContentView(R.layout.custom);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose);
        p_image=(ImageView)dialog.findViewById(R.id.imgpopup);
if(popupid==1)
{
    p_image.setImageResource(R.drawable.firstad);
}
else
{
    Glide.with(getApplicationContext()).load(popup_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(p_image);

}

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

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

}
