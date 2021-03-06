package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {
EditText fname,lname,email,phone_num,address,pincode,dob,usernme,pswd;
Button sgnup;
    ACProgressFlower dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fname = findViewById(R.id.edit_text_firstname);
        lname=findViewById(R.id.edit_text_sec_lastname);
        email=findViewById(R.id.edit_text3_email);
        phone_num=findViewById(R.id.edit_text5_mobile);
        address=findViewById(R.id.edit_text10_address);
        pincode=findViewById(R.id.edit_text2_pincode);
       usernme=findViewById(R.id.edit_text5_username);
       pswd =findViewById(R.id.edit_text5_pswd);
       sgnup=findViewById(R.id.edit_text2_signup);

        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname_val = fname.getText().toString();
                final String lastname_val = lname.getText().toString();
                final String email_val =email.getText().toString();
                String ph =phone_num.getText().toString();

                if(ph.length()==0)
                {
                  ph="0";
                }
                else
                {

                }
                String pin = pincode.getText().toString();
                final int pincode_val;
                if(pin.length()==0)
                {
                    pincode_val=0;
                }
                else
                {
                   pincode_val= Integer.parseInt(pin);
                }
                final String address_val =address.getText().toString();


                final String dob_val ="00-00-0000";
                final String usernme_val =usernme.getText().toString();
                final String pswd_val =pswd.getText().toString();
                if((firstname_val.length()==0||(lastname_val.length()==0)||(email_val.length()==0)||(pin.length()==0)||(ph.length()==0)||(address_val.length()==0)||(dob_val.length()==0)||(usernme_val.length()==0)||(pswd_val.length()==0)))
                {
                    Toast.makeText(Signup.this,"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Signup_call(firstname_val,lastname_val,email_val,ph,address_val,pincode_val,dob_val,usernme_val,pswd_val);

                }

            }
        });

    }
    void Signup_call(String firstname,String lastname,String email,String phone_num,String address,int pincode,String dob,String usernme,String pswd)
        {
            dialog = new ACProgressFlower.Builder(Signup.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .borderPadding(1)
                    .fadeColor(Color.WHITE).build();
            dialog.show();
        String signup_type="0";

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
        Call<CustomerAppResponseLogin> call = mainInterface.signUpapi(firstname,lastname,email,phone_num,address,pincode,dob,usernme,pswd,signup_type);
        call.enqueue(new Callback<CustomerAppResponseLogin>() {
            @Override
            public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                CustomerAppResponseLogin obj =response.body();
                Log.e("login","success="+response.body().getResponsedata());
                int success = Integer.parseInt(obj.getResponsedata().getSuccess());
                Log.e("login","success="+success);
                String userid = obj.getResponsedata().getData();
                String fullusername = "username"+userid;
                dialog.dismiss();
                if(success==1)
                {

                    Toast.makeText(Signup.this,"Signup Successful",Toast.LENGTH_SHORT).show();

//                    SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                    editor_frst.putString("logged_in_flag", "1");
//                    editor_frst.putString("fullusername",fullusername);
//                    editor_frst.apply();
//                    Intent next = new Intent(Login.this,Main2Activity.class);
//                    startActivity(next);

                }
                else
                {

                    Toast.makeText(Signup.this,"User Already Exists",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                Toast.makeText(Signup.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


    }

}
