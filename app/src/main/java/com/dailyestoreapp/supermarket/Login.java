package com.dailyestoreapp.supermarket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity  {
RelativeLayout r;
Button login;
EditText usr,pswd;
TextView frgt_pswd;
    ACProgressFlower dialog;
    Button google_login;
    String email_val;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    public static final String MY_PREFS_NAME = "CustomerApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

google_login= findViewById(R.id.google_login);
TextView sngup= (TextView) findViewById(R.id.newuser);
login =findViewById(R.id.login_btn);
usr=findViewById(R.id.edit_text_user_login);
pswd = findViewById(R.id.edit_text2_pswd_login);
frgt_pswd=findViewById(R.id.frgt_pswd);
frgt_pswd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent n = new Intent(Login.this,ForgotPswd.class);
startActivity(n);
    }
});
google_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
});
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
        dialog = new ACProgressFlower.Builder(Login.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .borderPadding(1)
                .fadeColor(Color.WHITE).build();
        dialog.show();
        String login_type="0";
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
                String userid = obj.getResponsedata().getData();
                String fullusername = "username"+userid;
                dialog.dismiss();
                if(success==1)
                {

                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor_frst.putString("logged_in_flag", "1");
                    editor_frst.putString("logged_in_userId", userid);
                    editor_frst.putString("fullusername",fullusername);
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
dialog.dismiss();
            }
        });
        login.setClickable(true);
        login.setEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("google account","google account"+account.getEmail());
            Log.e("google account","google account"+account.getFamilyName());
            Log.e("google account","google account"+account.getDisplayName());
            Log.e("google account","google account"+account.getGivenName());
            email_val=account.getEmail();
            String ph_no="1234567890";
            int pincode=123456;
            Signup_call(email_val,email_val,email_val,ph_no,"google account",pincode,"00-00-0000",email_val,"test123");

            // Signed in successfully, show authenticated UI.

//            Log.e("google account","google account"+account.getEmail());
            //gotoProfile();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("Log.e", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();

        }
    }
    void Signup_call(String firstname,String lastname,String email,String phone_num,String address,int pincode,String dob,String usernme,String pswd)
    {
        dialog = new ACProgressFlower.Builder(Login.this)
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
                String data_val = obj.getResponsedata().getData();
                dialog.dismiss();
                if(success==1)
                {
                    Log.e("login","success="+success);
                    String userid = obj.getResponsedata().getData();
                    String fullusername = "username"+userid;

                    Toast.makeText(getApplicationContext(),"Signed in Successfully",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor_frst.putString("logged_in_flag", "1");
                    editor_frst.putString("logged_in_userId", userid);
                    editor_frst.putString("fullusername", fullusername);
                    editor_frst.apply();
                    Intent next = new Intent(Login.this,Main2Activity.class);
                    startActivity(next);
                }
                else if(success==0)
                {
                    if(data_val.contains("user exist"))
                    {
                        String[] separated = data_val.split(",");
                        String usr_id = separated[1];
                        String fullusername = "username"+usr_id;
                        Log.e("login","google account sucess"+fullusername);
                        Toast.makeText(getApplicationContext(),"Signed in Successfully",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor_frst = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor_frst.putString("logged_in_flag", "1");
                        editor_frst.putString("logged_in_userId", usr_id);
                        editor_frst.putString("fullusername", fullusername);
                        editor_frst.apply();
                        Intent next = new Intent(Login.this,Main2Activity.class);
                        startActivity(next);
                    }
                    else
                    {
                        Toast.makeText(Login.this,"Something went wrong please try after some time",Toast.LENGTH_SHORT).show();
                    }

                }



            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                Toast.makeText(Login.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

}
