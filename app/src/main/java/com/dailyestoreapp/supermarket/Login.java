package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
RelativeLayout r;
Button login;
EditText usr,pswd;
    public static final String MY_PREFS_NAME = "CustomerApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

TextView sngup= (TextView) findViewById(R.id.newuser);
login =findViewById(R.id.login_btn);
usr=findViewById(R.id.edit_text_user_login);
pswd = findViewById(R.id.edit_text2_pswd_login);

login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String username = usr.getText().toString();
        final String passwd = pswd.getText().toString();
        if( (username==null)||(username.length()==0)||(passwd==null)|(passwd.length()==0))
        {
            Log.e("login","the value is"+username);
            Log.e("login","the pswd value is"+passwd);
            Toast.makeText(Login.this,"Please enter valid username and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                login.setClickable(false);
                login.setEnabled(false);

                login_call(username,passwd);
            }
            catch (Exception e)
            {
                Toast.makeText(Login.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }

        }

    }
});
sngup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent next = new Intent(Login.this,Signup.class);
        startActivity(next);
    }
});
    }
    void login_call(String usname, String pass)
    {
        String login_type="1";
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
        Call<CustomerAppResponseLogin> call = mainInterface.Loginapi(usname,pass,login_type);
        call.enqueue(new Callback<CustomerAppResponseLogin>() {
            @Override
            public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                CustomerAppResponseLogin obj =response.body();
                Log.e("login","success="+response.body().getResponsedata());
                int success = Integer.parseInt(obj.getResponsedata().getSuccess());
                Log.e("login","success="+success);

                if(success==1)
                {

                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor_frst.putString("logged_in_flag", "1");
                    editor_frst.apply();
                    Intent next = new Intent(Login.this,Main2Activity.class);
                    startActivity(next);
                }
                else
                {

                    Toast.makeText(Login.this,"Invalid Username and Password",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        login.setClickable(true);
        login.setEnabled(true);

    }
}
