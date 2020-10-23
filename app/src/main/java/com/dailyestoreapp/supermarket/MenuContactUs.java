package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuContactUs extends AppCompatActivity {
    ImageView call,whats,email;
    Button msg_send;
    EditText address,email_id,mobile,msg;
    public static final String MY_PREFS_NAME = "CustomerApp";
    String number = "+917510237377";
    String emailidd;
    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contact_us);
        Toolbar toolbar = findViewById(R.id.toolbar21);
        setSupportActionBar(toolbar);
        UserDetails();
        call = (ImageView)findViewById(R.id.call);
        rel = findViewById(R.id.totalcontactudcard);
        whats = (ImageView)findViewById(R.id.whatsapp);
        msg_send=findViewById(R.id.msg_send);
        address = findViewById(R.id.edit_text_address_contactus);
        email_id=findViewById(R.id.edit_text2_contactus_email);
        mobile=findViewById(R.id.edit_text_mob_contactus);
        msg=findViewById(R.id.edtxtDescr_contactus);
        email = (ImageView) findViewById(R.id.email);
        msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String useridd = shared.getString("logged_in_userId","");
                int user_idd = Integer.parseInt(useridd);
                String email_text = email_id.getText().toString();
                String address_text = address.getText().toString();
                String mobile_text = mobile.getText().toString();
                String msg_text = msg.getText().toString();
                contactus(user_idd,email_text,address_text,mobile_text,msg_text);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm=getApplicationContext().getPackageManager();
                try {
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(Intent.createChooser(i, ""));
                }
                catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ph = number;
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT);
                }
            }
        });


    }
    private void sendMail() {

        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + emailidd));
            intent.putExtra(Intent.EXTRA_SUBJECT, "DailyeStore");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "No application found to support ", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.e("MAin","Item selected ="+item.getItemId());
        switch (item.getItemId()) {
            case R.id.cart:
                Intent cart = new Intent(MenuContactUs.this,CartPage.class);
                startActivity(cart);
                return true;
            case R.id.account:
                // do whatever
                Intent cartp = new Intent(MenuContactUs.this,Login.class);
                startActivity(cartp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void contactus(int useridd,String email,String address,String mobile,String messages)
    {    ArrayList<String> contactus_string = new ArrayList<>();

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
        int mob_num = Integer.parseInt(mobile);
        String userid = String.valueOf(useridd);
        contactus_string.add(userid);
        contactus_string.add(email);
        contactus_string.add(mobile);
        contactus_string.add(address);
        contactus_string.add(messages);

        Call<CustomerAppResponseLogin> call = mainInterface.contactus_send(useridd,email,mob_num,address,messages);
        call.enqueue(new Callback<CustomerAppResponseLogin>() {
            @Override
            public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                CustomerAppResponseLogin obj =response.body();
                Log.e("login","success="+response.body().getResponsedata());
                int success = Integer.parseInt(obj.getResponsedata().getSuccess());
                Log.e("login","success="+success);
                String userid = obj.getResponsedata().getData();
                String fullusername = "username"+userid;
                if(success==1)
                {

                    Toast.makeText(MenuContactUs.this,"Message Sent",Toast.LENGTH_SHORT).show();



                }
                else
                {

                    Toast.makeText(MenuContactUs.this,"Error Please try after some time",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
              //  Toast.makeText(MenuContactUs.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void UserDetails()
    {


        int user_idd = 1;
        final StringBuilder frst_flyer_images  = new StringBuilder();
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

        Call<CustomerAppResponseMyAccount> call = mainInterface.userDetails(user_idd);
        call.enqueue(new Callback<CustomerAppResponseMyAccount>() {
            @Override
            public void onResponse(Call<CustomerAppResponseMyAccount> call, retrofit2.Response<CustomerAppResponseMyAccount> response) {
                CustomerAppResponseMyAccount listCategoryResponseobject = response.body();
                int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());

                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata());



                try {


                    if(success==1) {

//                        String firstname = listCategoryResponseobject.getResponsedata().getData().getFirstName();
//                        firstname_main=firstname;
//                        String lastname = listCategoryResponseobject.getResponsedata().getData().getLastName();
//                        lastname_main=lastname;
//                        String totl_name = firstname;
//                        String ph_no = listCategoryResponseobject.getResponsedata().getData().getPhone();
//
//
//                        String email = listCategoryResponseobject.getResponsedata().getData().getEmail();
//                        email_main=email;
//                        String pincode = listCategoryResponseobject.getResponsedata().getData().getPinCode();
//
//                        String address = listCategoryResponseobject.getResponsedata().getData().getAddress();
//                        address_main=address;
//                        String dobb =listCategoryResponseobject.getResponsedata().getData().getDob();
//                        dob_main=dobb;
//                        name_account.setText(totl_name);
//                        email_account.setText(email);
//                        mob_account.setText(ph_no);
//                        pincode_account.setText(pincode);
//                        address_account.setText(address);

                        number=listCategoryResponseobject.getResponsedata().getData().getPhone();
                        emailidd=listCategoryResponseobject.getResponsedata().getData().getEmail();
                        rel.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseMyAccount> call, Throwable t) {
                Log.e("frag","error="+t.getMessage());
            }
        });





    }
}
