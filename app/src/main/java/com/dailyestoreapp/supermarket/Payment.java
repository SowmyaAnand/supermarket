package com.dailyestoreapp.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Payment extends AppCompatActivity {
RadioButton quarantine_yes,quarantine_no,cashondelivery,gpay,paytm;
RadioGroup quarantine,payment;
Button order;

    private String  TAG ="Payment";
    private ProgressBar progressBar;
    private EditText txnAmount;
    private String midString ="VjyeQj84716826222804", txnAmountString="", orderIdString="", txnTokenString="";
    private Button btnPayNow;
    private Integer ActivityRequestCode = 2;


    public static final String MY_PREFS_NAME = "CustomerApp";
TextView total,sub_total,delivery;
    final int GOOGLE_PAY_REQUEST_CODE = 123;
    String upi = "noufalcps-1@okicici";
    boolean gpay_value=false;
    boolean paytm_value=false;
    boolean payment_selected=false;
    boolean quarantine_selected=false;
    String address_booking;
    ACProgressFlower dialog;
    String pincode_booking;
    String customer_booking_name;
    String coupon_name_for_intent_pay;
    String tot_for_intent_pay;
    String coupon_applid_amount_for_intent_pay;
     ArrayList<Integer> cod_items_name_count_cart_integer = new ArrayList<>();
     ArrayList<String> cod_eligible_items_name_count_cart = new ArrayList<>();
   ArrayList<String> cod_eligible_items_name_quantity_cart_new = new ArrayList<>();
    ArrayList<String> cod_eligible_items_name_quantity_cart = new ArrayList<>();
    ArrayList<Integer> cod_eligible_items_name_old_cart_id = new ArrayList<>();
     ArrayList<String> cod_eligible_items_name_price_cart = new ArrayList<>();
    ArrayList<Integer> cod_eligible_items_name_old_cart_id_new = new ArrayList<>();
    ArrayList<String> cod_eligible_items_name_price_cart_new = new ArrayList<>();
   String cod_eligible_pay;
    ArrayList<String> items_name_old_cart_payment = new ArrayList<>();
    ArrayList<Integer> cod_not_items_name_count_cart_integer = new ArrayList<>();
    ArrayList<String> cod_not_eligible_items_name_count_cart = new ArrayList<>();
    ArrayList<String> cod_not_eligible_items_name_quantity_cart_new = new ArrayList<>();
    ArrayList<String> cod_not_eligible_items_name_quantity_cart = new ArrayList<>();
    ArrayList<Integer> cod_not_eligible_items_name_old_cart_id = new ArrayList<>();
    ArrayList<String> cod_not_eligible_items_name_price_cart = new ArrayList<>();
    Integer cod_total_payment,not_cod_total_payment;
    int transaction_msg=0;
    RelativeLayout r1;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        r1=findViewById(R.id.relative_payment);
        proceed=findViewById(R.id.proceed_paytm);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
transactionsuccess();
            }
        });
        Intent in = getIntent();
        Bundle extras = in.getExtras();
         cod_eligible_pay = extras.getString("cod_eligible_pay");
         Log.e("pay","cod_eligible pay=="+cod_eligible_pay);
        String sb_tot =extras.getString("sub_txt_val");
        String tot_tot =extras.getString("tot_val");
        String delivery_tot =extras.getString("delivery");
        address_booking =extras.getString("book_address");
        pincode_booking =extras.getString("pincode_book");
        customer_booking_name =extras.getString("name_booking");
        coupon_name_for_intent_pay=extras.getString("coupon_name_for_intent");
        tot_for_intent_pay=extras.getString("tot_for_intent");

        coupon_applid_amount_for_intent_pay=extras.getString("coupon_applid_amount_for_intent");

//       tot_for_intent_pay=extras.getInt("cod_total");
//       not_cod_total_payment=extras.getInt("no_cod_total");
        cod_eligible_items_name_count_cart = (ArrayList<String>)extras.getSerializable("cod_eligible_items_name_count_cart");
        Log.e("payment","items name ="+cod_eligible_items_name_count_cart);
        cod_eligible_items_name_quantity_cart=(ArrayList<String>)extras.getSerializable("cod_eligible_items_name_quantity_cart");
        Log.e("payment","items name ="+cod_eligible_items_name_quantity_cart);
        cod_eligible_items_name_old_cart_id = (ArrayList<Integer>)extras.getSerializable("cod_eligible_items_name_old_cart_id");
        Log.e("payment","items name ="+cod_eligible_items_name_old_cart_id);
        cod_eligible_items_name_price_cart = (ArrayList<String>)extras.getSerializable("cod_eligible_items_name_price_cart");
        Log.e("payment","items name ="+cod_eligible_items_name_price_cart);

        cod_not_eligible_items_name_count_cart = (ArrayList<String>)extras.getSerializable("cod_not_eligible_items_name_count_cart");
        Log.e("payment","items not name ="+cod_not_eligible_items_name_count_cart);
        cod_not_eligible_items_name_quantity_cart=(ArrayList<String>)extras.getSerializable("cod_not_eligible_items_name_quantity_cart");
        Log.e("payment","items not name ="+cod_not_eligible_items_name_quantity_cart);
        cod_not_eligible_items_name_old_cart_id = (ArrayList<Integer>)extras.getSerializable("cod_not_eligible_items_name_old_cart_id");
        Log.e("payment","items  not name ="+cod_not_eligible_items_name_old_cart_id);
        cod_not_eligible_items_name_price_cart = (ArrayList<String>)extras.getSerializable("cod_not_eligible_items_name_price_cart");
        Log.e("payment","items not name ="+cod_not_eligible_items_name_price_cart);
        total=findViewById(R.id.tot_val2_payment_tot);
        sub_total=findViewById(R.id.heading_total_val_payment_sub);
        delivery = findViewById(R.id.cart_delivery_val_payment_del);
        sub_total.setText(sb_tot);
        delivery.setText(delivery_tot);
        total.setText(tot_tot);

        quarantine_yes=(RadioButton)findViewById(R.id.q_yes);
        quarantine_no=(RadioButton)findViewById(R.id.q_no);
        cashondelivery=(RadioButton)findViewById(R.id.cod);
       // gpay=(RadioButton)findViewById(R.id.gpay_radio);
        paytm=findViewById(R.id.paytm_radio);
        payment=(RadioGroup) findViewById(R.id.payment_radio);
        quarantine=(RadioGroup) findViewById(R.id.quarantine_radio);
        order = (Button)findViewById(R.id.proceed);

        if(cod_eligible_pay.equals("1"))
        {
        cashondelivery.setVisibility(View.VISIBLE);
      // Book_cod_now();
        }
        else
        {
            cashondelivery.setVisibility(View.INVISIBLE);
           //Book_no_cod_now();
        }
//        if(cod_not_eligible_items_name_count_cart.size()>0)
//        {
//            cashondelivery.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            cashondelivery.setVisibility(View.VISIBLE);
//        }
        payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.cod:
                        gpay_value=false;
                        payment_selected=true;
                        break;
                    case  R.id.paytm_radio:
                        paytm_value=true;
                        payment_selected=true;
                        break;
//                    case R.id.gpay_radio:
//                        gpay_value=true;
//                        payment_selected=true;
//                        break;

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
                        if(cod_eligible_pay.equals("1"))
                        {
                            cashondelivery.setVisibility(View.VISIBLE);
                        }

                        quarantine_selected=true;
                        break;
                }
            }
        });
        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Book_cod_now();
               // Book_no_cod_now();
            Boolean validate = validaion();
            Log.e("tag","payment order "+validate);
            if(validate)
            {

            int selectedid = payment.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton)findViewById(selectedid);
            Log.e("pay","the selected radiobutton is "+radioButton.getText());
               if(radioButton.getText().equals("CASH ON DELIVERY"))
                {
                    if(cod_eligible_pay.equals("1"))
                    {

                        Book_cod_now("0");
                    }
                    else

                    {
                        Book_no_cod_now("0");
                    }

                }
                else
                {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
                    String date = df.format(c.getTime());
                    Random rand = new Random();
                    int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
                    int randomNum = rand.nextInt((max - min) + 1) + min;
                    orderIdString =  date+String.valueOf(randomNum);

                    String tot_amount_booking= String.valueOf(total.getText());
                    txnAmountString = tot_amount_booking;
                    //txnAmountString = "0";
                    String errors = "";
                    if(orderIdString.equalsIgnoreCase("")){
                        errors ="Enter valid Order ID here\n";
                        Toast.makeText(Payment.this, errors, Toast.LENGTH_SHORT).show();

                    }else
                    if(txnAmountString.equalsIgnoreCase("")){
                        errors ="Enter valid Amount here\n";
                        Toast.makeText(Payment.this, errors, Toast.LENGTH_SHORT).show();

                    }else{

                        getToken();

                    }

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
    private  void getToken(){
        Log.e(TAG, " get token start");

        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", midString, orderIdString, txnAmountString);
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e(TAG, " respo "+ response.isSuccessful() );

                try {

                    if (response.isSuccessful() && response.body()!=null){
                        if (response.body().getBody().getTxnToken()!="") {
                            Log.e(TAG, " transaction token success : "+response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken());
                            Log.e("pay","after gettoken is"+transaction_msg);
                        }else {
                            Log.e(TAG, " Token status false");
                            Toast.makeText(Payment.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, " error in Token Res "+e.toString());
                    Toast.makeText(Payment.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
                Toast.makeText(Payment.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                Log.e(TAG, " response error "+t.toString());
            }
        });

    }
    public void startPaytmPayment (String token){

        txnTokenString = token;
        // for test mode use it
        // String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw-stage.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;
        //Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
        Log.e(TAG, " callback URL "+callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
       final TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response success (onTransactionResponse) : "+bundle.toString());
                Log.e(TAG, "Response success (onTransactionResponse) : "+bundle.getString("STATUS"));

                if(bundle.getString("STATUS").equals("TXN_SUCCESS"))
                {
                    Toast.makeText(Payment.this,"Transaction successfull", Toast.LENGTH_SHORT).show();
                    r1.setVisibility(View.GONE);
                    proceed.setVisibility(View.VISIBLE);
                    order.setVisibility(View.GONE);
                    transaction_msg = 1;


                }
                else {
                    Toast.makeText(Payment.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();
                    transaction_msg = 0;
                }
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
                Toast.makeText(Payment.this,"network not available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
                Toast.makeText(Payment.this,"onErrorProcess "+s.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
                Toast.makeText(Payment.this,"Clientauth "+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
                Toast.makeText(Payment.this," UI error "+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
                Toast.makeText(Payment.this," error loading web "+s+"--"+s1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
                Toast.makeText(Payment.this,"backPress ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
                Toast.makeText(Payment.this," transaction cancel ", Toast.LENGTH_SHORT).show();
            }
        });
        Log.e(TAG, " transaction reached here ");

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(Payment.this, ActivityRequestCode);
        //transactionsuccess();
    }
//    void payUsingUpi(  String name,String upiId, String note, String amount) {
//        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
//        Uri uri = Uri.parse("upi://pay").buildUpon()
//                .appendQueryParameter("pa", upiId)
//                .appendQueryParameter("pn", name)
//                //.appendQueryParameter("mc", "")
//                //.appendQueryParameter("tid", "02125412")
//                //.appendQueryParameter("tr", "25584584")
//                .appendQueryParameter("tn", note)
//                .appendQueryParameter("am", amount)
//                .appendQueryParameter("cu", "INR")
//                //.appendQueryParameter("refUrl", "blueapp")
//                .build();
//// this code is only for google play
//        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//
//        //  int GOOGLE_PAY_REQUEST_CODE = 123;
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//try {
//    Payment.this.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
//}
//catch (Exception e)
//{
//    Toast.makeText(Payment.this, "Please install gpay", Toast.LENGTH_SHORT).show();
//}
//
//
//        // this code is for all UPI id installed  in phone
//
//        // Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
//        //upiPayIntent.setData(uri);
//
//        // will always show a dialog to user to choose an app
//        //Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//
//        // check if intent resolves
//        //if(null != chooser.resolveActivity(getPackageManager())) {
//        //   startActivityForResult(chooser, UPI_PAYMENT);
//        //} else {
//        //    Toast.makeText(Payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        // }
//
//    }

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
//        switch (requestCode) {
//            case GOOGLE_PAY_REQUEST_CODE:
//                Log.e("gpay","the gpay result code is "+resultCode);
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.e("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.e("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    //when user simply back without payment
//                    Log.e("UPI", "onActivityResult: " + "Return data is null");
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }


        if (requestCode == ActivityRequestCode) {
            Log.e("pay","onActivityResult"+transaction_msg);
//            Bundle bundle = data.getExtras();
//            Log.e(TAG,"bundle entered here"+bundle);
//            if (bundle != null) {
//                for (String key : bundle.keySet()) {
//                    Log.e(TAG,"bundle entered here");
//                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
//                }
//            }
//            Log.e(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
//            Log.e(TAG, " data response success - "+data.getStringExtra("response"));
///*
// data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
// "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmMP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
// "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcR4116","ORDERID":"100620202152",
// "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
// "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"202006101112128001101683631290118"}
//  */
//            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
//                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
//        }else{
//            Log.e(TAG, " payment failed");
       }

    }





//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (isConnectionAvailable(Payment.this)) {
//            String str = data.get(0);
//            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(Payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "payment successfull: "+approvalRefNo);
////                Intent next = new Intent(Payment.this,Thankyou.class);
////                startActivity(next);
//                if(cod_eligible_pay.equals("1"))
//                {
//
//                    Book_cod_now("1");
//                }
//                else
//                {
//                    Book_no_cod_now("1");
//                }
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
//
//            }
//            else {
//                Toast.makeText(Payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "failed payment: "+approvalRefNo);
//
//            }
//        } else {
//            Log.e("UPI", "Internet issue: ");
//
//            Toast.makeText(Payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public static boolean isConnectionAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }
    public void Book_cod_now(String pay) {
        dialog = new ACProgressFlower.Builder(Payment.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .borderPadding(1)
                .fadeColor(Color.WHITE).build();
        dialog.show();
        int paymentType = Integer.parseInt(pay);
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


        cod_eligible_items_name_old_cart_id_new.clear();
        for(int k=0;k<cod_eligible_items_name_old_cart_id.size();k++)
        {
            Integer g = Integer.valueOf(cod_eligible_items_name_old_cart_id.get(k));
            cod_eligible_items_name_old_cart_id_new.add(g);
        }


        cod_eligible_items_name_price_cart_new.clear();
        for(int k=0;k<cod_eligible_items_name_price_cart.size();k++)
        {
            String price = cod_eligible_items_name_price_cart.get(k);
            cod_eligible_items_name_price_cart_new.add(price);
        }








        cod_items_name_count_cart_integer.clear();
        for(int k=0;k<cod_eligible_items_name_count_cart.size();k++)
        {
            Integer g = Integer.valueOf(cod_eligible_items_name_count_cart.get(k));
            cod_items_name_count_cart_integer.add(g);
        }
        cod_eligible_items_name_quantity_cart_new.clear();
        for(int l=0;l<cod_eligible_items_name_quantity_cart.size();l++)
        {
            String q =cod_eligible_items_name_quantity_cart.get(l);
            String[] separated = q.split(" ");
            Log.e("cart","the value is "+separated[1] );
            String val = separated[1];
            cod_eligible_items_name_quantity_cart_new.add(val);
        }

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String useridd = shared.getString("logged_in_userId","");
        int user_int_id = Integer.parseInt(useridd);
        final ArrayList<Integer> user_cart_id = new ArrayList<>();
        final ArrayList<Integer> type_cart_id = new ArrayList<>();
        for(int g =0;g<cod_eligible_items_name_old_cart_id.size();g++)
        {
            user_cart_id.add(user_int_id);
            type_cart_id.add(0);
        }
        Log.e("cart","checkout param=    itemid ====>"+cod_eligible_items_name_old_cart_id_new);
        Log.e("cart","checkout param=    count====>"+cod_items_name_count_cart_integer);
        Log.e("cart","checkout param=    quantity ====>"+cod_eligible_items_name_quantity_cart_new);
        Log.e("cart","checkout param=    type ====>0");
        Log.e("cart","checkout param=    userid ====>"+user_int_id);
        Log.e("cart","checkout param=    address ====> "+address_booking);
        Log.e("cart","checkout param=    itemid ====>"+pincode_booking);
        Log.e("cart","checkout param=    itemid ====>"+pincode_booking);
        Log.e("cart","checkout prce new ====>"+coupon_name_for_intent_pay);
        Log.e("cart","checkout prce new====>"+coupon_applid_amount_for_intent_pay);
        Log.e("cart","checkout prce new ====>"+tot_for_intent_pay);
        Call call = mainInterface.checkoutapi(cod_eligible_items_name_old_cart_id,cod_items_name_count_cart_integer,cod_eligible_items_name_quantity_cart,0,cod_eligible_items_name_price_cart,user_int_id,address_booking,pincode_booking,paymentType,tot_for_intent_pay,coupon_applid_amount_for_intent_pay,coupon_name_for_intent_pay);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Log.e("cart","response orderes="+response.body());
                Toast.makeText(Payment.this,"Successfully Placed Orders",Toast.LENGTH_SHORT).show();

dialog.dismiss();
                //clearance of cart items

                SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String fullname = fullname_shared.getString("fullusername","");


                //retrieve item names for getting specific count
                SharedPreferences shared_tot = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Log.e("cart","fullname name ="+fullname);
                String cart_item_names = fullname+"cart_item_names";
                Log.e("cart","fullname name="+fullname);
                String itemSingle_name_old = shared_tot.getString(cart_item_names, "");

                if(!(itemSingle_name_old==null)||(itemSingle_name_old.length()==0))
                {
                    String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                    for (String ct : cats) {
                        if(!(ct.equals("")||ct.equals(null)))
                        {
                            items_name_old_cart_payment.add(ct);
                        }

                    }
                }


                SharedPreferences.Editor editor_payment = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                for(int i=0;i<items_name_old_cart_payment.size();i++) {
                    String nm = items_name_old_cart_payment.get(i);
                    //specific count
                    String sharepreferencename_count_payment = fullname + nm + "_count";
                    editor_payment.putString(sharepreferencename_count_payment,"");
                }
                //your cart text count
                String total_count_cart_payment = fullname+"total_count_cart";
                String cart_Items_toolbar_count_payment= fullname+"cart_Items_toolbar_count";
                String cart_item_names_id_payment = fullname+"cart_item_names_id";
                String cart_item_image_payment = fullname+"cart_item_image";
                String cart_item_qnty_payment =fullname+"cart_item_qnty";
                String cart_item_cod_payment = fullname+"cart_item_cod";
                String cart_item_price_payment = fullname+"cart_item_price";
                String cart_item_offer_percent_payment = fullname+"cart_item_offer_percent";
                String cart_item_names_payment = fullname+"cart_item_names";
                String t ="0";
                Main2Activity.update_counter(t);

                editor_payment.putString(cart_item_names_payment,"");
                editor_payment.putString(total_count_cart_payment,"");
                editor_payment.putString(cart_Items_toolbar_count_payment,"");
                editor_payment.putString(cart_item_names_id_payment,"");
                editor_payment.putString(cart_item_image_payment,"");
                editor_payment.putString(cart_item_qnty_payment,"");
                editor_payment.putString(cart_item_cod_payment,"");
                editor_payment.putString(cart_item_price_payment,"");
                editor_payment.putString(cart_item_offer_percent_payment,"");

                editor_payment.apply();

                push_notif();
                Toast.makeText(Payment.this,"Your order has been placed successfully",Toast.LENGTH_LONG).show();
                Intent next = new Intent(Payment.this,Thankyou.class);
                startActivity(next);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(Payment.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("cart","error"+t.getMessage()+t.getLocalizedMessage()+t.getCause()+t.getStackTrace()+t.getClass());
dialog.dismiss();
            }
        });

    }

    public void Book_no_cod_now(String pay) {
        dialog = new ACProgressFlower.Builder(Payment.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .borderPadding(1)
                .fadeColor(Color.WHITE).build();
        dialog.show();
        String login_type="0";
        int paymentType = Integer.parseInt(pay);
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

//        cod_not_items_name_count_cart_integer.clear();
//        for(int k=0;k<cod_eligible_items_name_count_cart.size();k++)
//       // for(int k=0;k<cod_not_eligible_items_name_count_cart.size();k++)
//        {
//           // Integer g = Integer.valueOf(cod_not_eligible_items_name_count_cart.get(k));
//            Integer g = Integer.valueOf(cod_eligible_items_name_count_cart.get(k));
//            cod_not_items_name_count_cart_integer.add(g);
//        }
//        cod_not_eligible_items_name_quantity_cart_new.clear();
//        //for(int l=0;l<cod_not_eligible_items_name_quantity_cart.size();l++)
//            for(int l=0;l<cod_eligible_items_name_quantity_cart.size();l++)
//        {
//           // String q =cod_not_eligible_items_name_quantity_cart.get(l);
//            String q =cod_eligible_items_name_quantity_cart.get(l);
//
//            String[] separated = q.split(" ");
//            Log.e("cart","the value is "+separated[1] );
//            String val = separated[1];
//            cod_not_eligible_items_name_quantity_cart_new.add(val);
//        }
//
//        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String useridd = shared.getString("logged_in_userId","");
//        int user_int_id = Integer.parseInt(useridd);
//        final ArrayList<Integer> user_cart_id = new ArrayList<>();
//        final ArrayList<Integer> type_cart_id = new ArrayList<>();
//       // for(int g =0;g<cod_not_eligible_items_name_old_cart_id.size();g++)
//        for(int g =0;g<cod_eligible_items_name_old_cart_id.size();g++)
//
//        {
//            user_cart_id.add(user_int_id);
//            type_cart_id.add(0);
//        }
//        Log.e("cart","checkout param=    itemid ====>"+cod_eligible_items_name_old_cart_id);
//        Log.e("cart","checkout param=    count====>"+cod_not_items_name_count_cart_integer);
//        Log.e("cart","checkout param=    quantity ====>"+cod_not_eligible_items_name_quantity_cart_new);
//        Log.e("cart","checkout param=    type ====>"+type_cart_id);
//        Log.e("cart","checkout param=    userid ====>"+user_cart_id);
//        Log.e("cart","checkout param=    address ====> "+address_booking);
//        Log.e("cart","checkout param=    itemid ====>"+pincode_booking);
//        Log.e("cart","checkout prce ====>"+cod_not_eligible_items_name_price_cart);
//       // Call call = mainInterface.checkoutapi(cod_not_eligible_items_name_old_cart_id,cod_not_items_name_count_cart_integer,cod_not_eligible_items_name_quantity_cart,0,cod_not_eligible_items_name_price_cart,user_int_id,address_booking,pincode_booking,paymentType,tot_for_intent_pay,coupon_applid_amount_for_intent_pay,coupon_name_for_intent_pay);
//        Call call = mainInterface.checkoutapi(cod_eligible_items_name_old_cart_id,cod_not_items_name_count_cart_integer,cod_not_eligible_items_name_quantity_cart,0,cod_eligible_items_name_price_cart,user_int_id,address_booking,pincode_booking,paymentType,tot_for_intent_pay,coupon_applid_amount_for_intent_pay,coupon_name_for_intent_pay);

        cod_eligible_items_name_old_cart_id_new.clear();
        for(int k=0;k<cod_eligible_items_name_old_cart_id.size();k++)
        {
            Integer g = Integer.valueOf(cod_eligible_items_name_old_cart_id.get(k));
            cod_eligible_items_name_old_cart_id_new.add(g);
        }


        cod_eligible_items_name_price_cart_new.clear();
        for(int k=0;k<cod_eligible_items_name_price_cart.size();k++)
        {
            String price = cod_eligible_items_name_price_cart.get(k);
            cod_eligible_items_name_price_cart_new.add(price);
        }








        cod_items_name_count_cart_integer.clear();
        for(int k=0;k<cod_eligible_items_name_count_cart.size();k++)
        {
            Integer g = Integer.valueOf(cod_eligible_items_name_count_cart.get(k));
            cod_items_name_count_cart_integer.add(g);
        }
        cod_eligible_items_name_quantity_cart_new.clear();
        for(int l=0;l<cod_eligible_items_name_quantity_cart.size();l++)
        {
            String q =cod_eligible_items_name_quantity_cart.get(l);
            String[] separated = q.split(" ");
            Log.e("cart","the value is "+separated[1] );
            String val = separated[1];
            cod_eligible_items_name_quantity_cart_new.add(val);
        }

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String useridd = shared.getString("logged_in_userId","");
        int user_int_id = Integer.parseInt(useridd);
        final ArrayList<Integer> user_cart_id = new ArrayList<>();
        final ArrayList<Integer> type_cart_id = new ArrayList<>();
        for(int g =0;g<cod_eligible_items_name_old_cart_id.size();g++)
        {
            user_cart_id.add(user_int_id);
            type_cart_id.add(0);
        }
        Log.e("cart","checkout param=    itemid ====>"+cod_eligible_items_name_old_cart_id_new);
        Log.e("cart","checkout param=    count====>"+cod_items_name_count_cart_integer);
        Log.e("cart","checkout param=    quantity ====>"+cod_eligible_items_name_quantity_cart_new);
        Log.e("cart","checkout param=    type ====>0");
        Log.e("cart","checkout param=    userid ====>"+user_int_id);
        Log.e("cart","checkout param=    address ====> "+address_booking);
        Log.e("cart","checkout param=    itemid ====>"+pincode_booking);
        Log.e("cart","checkout param=    itemid ====>"+pincode_booking);
        Log.e("cart","checkout prce new ====>"+coupon_name_for_intent_pay);
        Log.e("cart","checkout prce new====>"+coupon_applid_amount_for_intent_pay);
        Log.e("cart","checkout prce new ====>"+tot_for_intent_pay);
        Call call = mainInterface.checkoutapi(cod_eligible_items_name_old_cart_id,cod_items_name_count_cart_integer,cod_eligible_items_name_quantity_cart,0,cod_eligible_items_name_price_cart,user_int_id,address_booking,pincode_booking,paymentType,tot_for_intent_pay,coupon_applid_amount_for_intent_pay,coupon_name_for_intent_pay);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                dialog.dismiss();
                Log.e("cart","response orderes="+response.body());
                Toast.makeText(Payment.this,"Successfully Placed Orders",Toast.LENGTH_SHORT).show();
                SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String fullname = fullname_shared.getString("fullusername","");


                //retrieve item names for getting specific count
                SharedPreferences shared_tot = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                Log.e("cart","fullname name ="+fullname);
                String cart_item_names = fullname+"cart_item_names";
                Log.e("cart","fullname name="+fullname);
                String itemSingle_name_old = shared_tot.getString(cart_item_names, "");

                if(!(itemSingle_name_old==null)||(itemSingle_name_old.length()==0))
                {
                    String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
                    for (String ct : cats) {
                        if(!(ct.equals("")||ct.equals(null)))
                        {
                            items_name_old_cart_payment.add(ct);
                        }

                    }
                }


                SharedPreferences.Editor editor_payment = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                for(int i=0;i<items_name_old_cart_payment.size();i++) {
                    String nm = items_name_old_cart_payment.get(i);
                    //specific count
                    String sharepreferencename_count_payment = fullname + nm + "_count";
                    editor_payment.putString(sharepreferencename_count_payment,"");
                }
                //your cart text count
                String total_count_cart_payment = fullname+"total_count_cart";
                String cart_Items_toolbar_count_payment= fullname+"cart_Items_toolbar_count";
                String cart_item_names_id_payment = fullname+"cart_item_names_id";
                String cart_item_image_payment = fullname+"cart_item_image";
                String cart_item_qnty_payment =fullname+"cart_item_qnty";
                String cart_item_cod_payment = fullname+"cart_item_cod";
                String cart_item_price_payment = fullname+"cart_item_price";
                String cart_item_offer_percent_payment = fullname+"cart_item_offer_percent";
                String cart_item_names_payment = fullname+"cart_item_names";
                String t ="0";
                Main2Activity.update_counter(t);

                editor_payment.putString(cart_item_names_payment,"");
                editor_payment.putString(total_count_cart_payment,"");
                editor_payment.putString(cart_Items_toolbar_count_payment,"");
                editor_payment.putString(cart_item_names_id_payment,"");
                editor_payment.putString(cart_item_image_payment,"");
                editor_payment.putString(cart_item_qnty_payment,"");
                editor_payment.putString(cart_item_cod_payment,"");
                editor_payment.putString(cart_item_price_payment,"");
                editor_payment.putString(cart_item_offer_percent_payment,"");

                editor_payment.apply();
                push_notif();


                Intent next = new Intent(Payment.this,Thankyou.class);
                Bundle b = new Bundle();
                b.putString("cod_eligible_pay",cod_eligible_pay);
                next.putExtras(b);
                startActivity(next);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(Payment.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("cart","error"+t.getMessage()+t.getLocalizedMessage()+t.getCause()+t.getStackTrace()+t.getClass());
dialog.dismiss();
            }
        });

    }

    private void push_notif()
    {

        String url_push = "http://dailyestoreapp.com/dailyestore/";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_push)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        ResponseInterface mainInterface = retrofit.create(ResponseInterface.class);
        Call<PushNotificationadaptertrial> call = mainInterface.pushnotificationtrial();
        call.enqueue(new Callback<PushNotificationadaptertrial>() {
            @Override
            public void onResponse(Call<PushNotificationadaptertrial> call, retrofit2.Response<PushNotificationadaptertrial> response) {




            }

            @Override
            public void onFailure(Call<PushNotificationadaptertrial> call, Throwable t) {

            }
        });


    }

public void transactionsuccess()
{

        Log.e("payment","entered new fundction");
        if(cod_eligible_pay.equals("1"))
        {

            Book_cod_now("1");
        }
        else
        {
            Book_no_cod_now("1");
        }

}

}
