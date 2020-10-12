package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPswd extends AppCompatActivity {
EditText frgt_mail;
Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pswd);
        frgt_mail=findViewById(R.id.frgt_email);
        send=findViewById(R.id.frgt_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(frgt_mail.getText());
                forgot_email(email);
            }
        });
    }
    void forgot_email(String email)
    {

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
        Call<CustomerAppResponseLogin> call = mainInterface.Forgot_pswdapi(email);
        call.enqueue(new Callback<CustomerAppResponseLogin>() {
            @Override
            public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                CustomerAppResponseLogin obj =response.body();
                Log.e("login","success="+response.body().getResponsedata());
                int success = Integer.parseInt(obj.getResponsedata().getSuccess());

                if(success==1)
                {

                    Toast.makeText(ForgotPswd.this,"Login Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(ForgotPswd.this,"Invalid Username and Password",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                Toast.makeText(ForgotPswd.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
