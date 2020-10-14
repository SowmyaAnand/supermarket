package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class description extends AppCompatActivity {
Button plus,mnus;
TextView qnty,itemName,itemPrice,itemDesc,refund;
ImageView ig;
int quantity_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        itemName=findViewById(R.id.item_nm);
        itemPrice=findViewById(R.id.descprice);
        ig=findViewById(R.id.item_image_desc);
refund=findViewById(R.id.refundnotEligigble);
itemDesc=findViewById(R.id.itemdesc);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        String sb_names = extras.getString("itemNameBundle");
        String sb_img = extras.getString("itemImageBundle");
        String refundd = extras.getString("refund");
        String descc = extras.getString("desc");
        String  sb_price = "Rs:" + String.valueOf(extras.getInt("itemPrice"));
        itemPrice.setText(sb_price);
        itemName.setText(sb_names);
        Glide.with(description.this).load(sb_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ig);
        Toolbar desc = (Toolbar)findViewById(R.id.toolbar_itemdesc);
        setSupportActionBar(desc);
        desc.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        desc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prev= new Intent(description.this,Itemlisting.class);
                startActivity(prev);
            }
        });
        itemDesc.setText(descc);
        if(refundd.equals("1"))
        {
            refund.setText("REPLACEMENT ELIGIBLE");
        }
        else
        {
            refund.setText("REPLACEMENT NOT ELIGIBLE");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
}
