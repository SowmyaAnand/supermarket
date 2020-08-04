package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Thankyou extends AppCompatActivity {
TextView home,contactus,assistance;
ImageView wt,cl;
    final String number = "+917510237377";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        contactus = (TextView)findViewById(R.id.contact);
        wt= (ImageView)findViewById(R.id.whatsapp_thanks);
        cl= (ImageView)findViewById(R.id.call_thanks);

        wt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm=Thankyou.this.getPackageManager();
                try {
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(Intent.createChooser(i, ""));
                }
                catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(Thankyou.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ph = "+917510237377";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(Thankyou.this,""+e,Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
