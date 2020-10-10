package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
RadioButton quarantine_yes,quarantine_no,cashondelivery,gpay;
RadioGroup quarantine,payment;
Button order;
    final int GOOGLE_PAY_REQUEST_CODE = 123;
    String upi = "anandc17@okaxis";
    boolean gpay_value=false;
    boolean payment_selected=false;
    boolean quarantine_selected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        String cod_eligible_pay = extras.getString("cod_eligible_pay");

        Toast.makeText(Payment.this,cod_eligible_pay,Toast.LENGTH_SHORT).show();
        quarantine_yes=(RadioButton)findViewById(R.id.q_yes);
        quarantine_no=(RadioButton)findViewById(R.id.q_no);
        cashondelivery=(RadioButton)findViewById(R.id.cod);
        gpay=(RadioButton)findViewById(R.id.gpay_radio);
        payment=(RadioGroup) findViewById(R.id.payment_radio);
        quarantine=(RadioGroup) findViewById(R.id.quarantine_radio);
        order = (Button)findViewById(R.id.proceed);
        if(cod_eligible_pay.equals("1"))
        {
        cashondelivery.setVisibility(View.VISIBLE);
        }
        else
        {
            cashondelivery.setVisibility(View.INVISIBLE);
        }
        payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.cod:
                        gpay_value=false;
                        payment_selected=true;
                        break;
                    case R.id.gpay_radio:
                        gpay_value=true;
                        payment_selected=true;
                        break;
                }
            }
        });
        quarantine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.q_yes:
                        cashondelivery.setVisibility(View.GONE);
                        quarantine_selected=true;
                        break;
                    case R.id.q_no:
                        cashondelivery.setVisibility(View.VISIBLE);
                        quarantine_selected=true;
                        break;
                }
            }
        });
        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Boolean validate = validaion();
            Log.e("tag","payment order "+validate);
            if(validate)
            {
                if(gpay_value)
                {
                    payUsingUpi("CustomerName", upi,
                            "payment", "5");
                }
                else
                {
                    Intent next = new Intent(Payment.this,Thankyou.class);
                    startActivity(next);
                }
            }
            else
            {

                Toast.makeText(Payment.this,"Please fill all options",Toast.LENGTH_LONG).show();

            }


            }
        });
    }
     Boolean validaion()
     {
         if(!(payment_selected))
         {
             Toast.makeText(Payment.this,"select payment option",Toast.LENGTH_LONG).show();
             return false;
         }
         else if(!(quarantine_selected))
         {
             Toast.makeText(Payment.this,"select quarantine option",Toast.LENGTH_LONG).show();
             return false;
         }
         return true;
     }

    void payUsingUpi(  String name,String upiId, String note, String amount) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
// this code is only for google play
        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        //  int GOOGLE_PAY_REQUEST_CODE = 123;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        Payment.this.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

        // this code is for all UPI id installed  in phone

        // Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        //upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        //Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        //if(null != chooser.resolveActivity(getPackageManager())) {
        //   startActivityForResult(chooser, UPI_PAYMENT);
        //} else {
        //    Toast.makeText(Payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        // }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);



        Log.e("main ", "response in activity "+requestCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case GOOGLE_PAY_REQUEST_CODE:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Payment.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
                Intent next = new Intent(Payment.this,Thankyou.class);
                startActivity(next);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(Payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(Payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
