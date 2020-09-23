package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuContactUs extends AppCompatActivity {
    ImageView call,whats,email;
    final String number = "+917510237377";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contact_us);
        Toolbar toolbar = findViewById(R.id.toolbar21);
        setSupportActionBar(toolbar);
        call = (ImageView)findViewById(R.id.call);
        whats = (ImageView)findViewById(R.id.whatsapp);
        email = (ImageView) findViewById(R.id.email);
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
                    String ph = "+917510237377";
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
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "dailyestore@gmail.com"));
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

}
