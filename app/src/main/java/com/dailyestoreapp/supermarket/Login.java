package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
RelativeLayout r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
TextView sngup= (TextView) findViewById(R.id.newuser);
sngup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent next = new Intent(Login.this,Signup.class);
        startActivity(next);
    }
});
    }
}
