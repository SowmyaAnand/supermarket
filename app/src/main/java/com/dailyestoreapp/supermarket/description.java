package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class description extends AppCompatActivity {
Button plus,mnus;
TextView qnty;
int quantity_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        plus = (Button) findViewById(R.id.plus2);
        mnus = (Button) findViewById(R.id.minus2);
        qnty = (TextView)findViewById(R.id.quantity2);
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
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = String.valueOf(qnty.getText());
                quantity_desc= Integer.parseInt(q);
                quantity_desc=quantity_desc+1;
                String stringquantity = String.valueOf(quantity_desc);
                qnty.setText(stringquantity);
            }
        });
        mnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q2 = String.valueOf(qnty.getText());
                quantity_desc= Integer.parseInt(q2);
                if(quantity_desc>1)
                {
                    quantity_desc=quantity_desc-1;
                }
                String stringquantity2 = String.valueOf(quantity_desc);
                qnty.setText(stringquantity2);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
}
