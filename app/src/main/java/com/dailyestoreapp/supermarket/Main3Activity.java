package com.dailyestoreapp.supermarket;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import static com.dailyestoreapp.supermarket.Login.MY_PREFS_NAME;

public class Main3Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
TextView selectedorderdate,selecteddt,select_time;
EditText cnt,nm,mob,plce,address,pincode;
Button pre;
LinearLayout linear2;
    private String  TAG ="Main3Activity";
    private ProgressBar progressBar;
    private EditText txnAmount;
    private String midString ="VjyeQj84716826222804", txnAmountString="", orderIdString="", txnTokenString="";
    private Button btnPayNow;
    private Integer ActivityRequestCode = 2;
    String selected_pincode;
    DatePickerDialog datePickerDialog;
    int year;
    ACProgressFlower dialog;
    int month;
    int dayOfMonth;
    Calendar calendar;
    ImageView imgdt,select_time_img;
    Spinner spin;
    Toolbar t;
    int mHour,mMinute;
    ArrayList<Integer> item_id_main3 = new ArrayList<>();
    ArrayList<Integer> ccount_arry = new ArrayList<>();
    ArrayList<String> item_price_main3 = new ArrayList<>();
    ArrayList<String> item_quant_main3 = new ArrayList<>();
    String []pincodess = {"SELECT PINCODE","679335","679502","679503","679504","679505","679506"};
int item_id_intent,pi;
String selected_date;
String selected_time;
String price_intent;
String quant;
Button preorder_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        t = (Toolbar)findViewById(R.id.toolbar_new);
        t.setTitle("PREORDER YOUR CAKES");
        setSupportActionBar(t);
        cnt= findViewById(R.id.count_number);
        nm= findViewById(R.id.edit_text_name_booking);
                mob= findViewById(R.id.mobile_booking);
                plce= findViewById(R.id.edit_text5_place_booking);
                address= findViewById(R.id.edit_text10_address_booking);
                selecteddt=findViewById(R.id.selectdt);
                preorder_continue=findViewById(R.id.preoder_continue);
               linear2=findViewById(R.id.lin2);
        select_time=findViewById(R.id.select_time);
        select_time_img=findViewById(R.id.select_time_img);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
final Calendar c = Calendar.getInstance();
                mHour= c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(Main3Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if(hourOfDay<12)
                        {
                            AM_PM="AM";
                        }
                        else
                        {
                            AM_PM="PM";
                        }
                        select_time.setText(hourOfDay+":"+minute+":00");
                        selected_time=select_time.getText().toString();
                    }
                },mHour,mMinute,false);
              timePickerDialog.show();
            }
        });
        select_time_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour= c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(Main3Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM;
                        if(hourOfDay<12)
                        {
                            AM_PM="AM";
                        }
                        else
                        {
                            AM_PM="PM";
                        }
                        select_time.setText(hourOfDay+":"+minute+":00");
                        selected_time=select_time.getText().toString();
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
                    pre= findViewById(R.id.preoder_button);
                    pre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name_val= nm.getText().toString();
                            String  mobile_val= mob.getText().toString();
                            String  place_val= plce.getText().toString();
                            String  addr_val= address.getText().toString();
                            String seldt_val = selecteddt.getText().toString();
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
                            String date = df.format(c.getTime());
                            Random rand = new Random();
                            int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
                            int randomNum = rand.nextInt((max - min) + 1) + min;
                            orderIdString =  date+String.valueOf(randomNum);
                            Intent in = getIntent();
                            Bundle extras = in.getExtras();
                            int pricee=extras.getInt("prce");
                            String count = cnt.getText().toString();
                            int cc = Integer.parseInt(count);
                            int tot = (cc*pricee)+20;
                            String tot_amount_booking= String.valueOf(tot);
                            txnAmountString = tot_amount_booking;
                            //txnAmountString = "0";
                            String errors = "";
                            if(orderIdString.equalsIgnoreCase("")){
                                errors ="Enter valid Order ID here\n";
                                Toast.makeText(Main3Activity.this, errors, Toast.LENGTH_SHORT).show();

                            }else
                            if(txnAmountString.equalsIgnoreCase("")){
                                errors ="Enter valid Amount here\n";
                                Toast.makeText(Main3Activity.this, errors, Toast.LENGTH_SHORT).show();

                            }
                            else if(selected_pincode=="SELECT PINCODE")
                            {
                                Toast.makeText(Main3Activity.this,"SELECT PINCODE",Toast.LENGTH_SHORT).show();
                            }
                            else if(name_val.equals("")||mobile_val.equals("")||place_val.equals("")||addr_val.equals("")||seldt_val.equals("SELECT DATE"))
                            {

                                Toast.makeText(Main3Activity.this,"PLEASE ENTER ALL FIELDS",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                getToken();

                            }
                        }
                    });
        preorder_continue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name_val= nm.getText().toString();
                            String  mobile_val= mob.getText().toString();
                            String  place_val= plce.getText().toString();
                            String  addr_val= address.getText().toString();
                            String seldt_val = selecteddt.getText().toString();
                            if(selected_pincode=="SELECT PINCODE")
                            {
Toast.makeText(Main3Activity.this,"SELECT PINCODE",Toast.LENGTH_SHORT).show();
                            }
                            else if(name_val.equals("")||mobile_val.equals("")||place_val.equals("")||addr_val.equals("")||seldt_val.equals("SELECT DATE"))
                            {

                                Toast.makeText(Main3Activity.this,"PLEASE ENTER ALL FIELDS",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                preoders("1");
                            }

                        }
                    });
        Intent in = getIntent();
        Bundle extras = in.getExtras();

        item_id_intent = extras.getInt("item_id");
        item_id_main3.add(item_id_intent);
        pi=extras.getInt("prce");
        String new_price = "RS: "+ pi;
        price_intent= String.valueOf(new_price);
        item_price_main3.add(price_intent);
        quant=extras.getString("quant");
        String fullQuantity="QUANTITY: "+quant;
        item_quant_main3.add(fullQuantity);
        selectedorderdate=findViewById(R.id.selectdt);
        imgdt=findViewById(R.id.tmedt);
        spin= findViewById(R.id.spinner_pincode);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pincodess);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin.setAdapter(aa);
        selectedorderdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Main3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectedorderdate.setText(day + "-" + (month + 1) + "-" + year);
                                selected_date=year + "-" + (month + 1) + "-" + day;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        imgdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Main3Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectedorderdate.setText(day + "/" + (month + 1) + "/" + year);
                                selected_date=year + "-" + (month + 1) + "-" + day;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_pincode=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private  void getToken(){
        dialog = new ACProgressFlower.Builder(Main3Activity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .borderPadding(1)
                .fadeColor(Color.WHITE).build();
        dialog.show();
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

                        }else {
                            Log.e(TAG, " Token status false");
                            Toast.makeText(Main3Activity.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, " error in Token Res "+e.toString());
                    Toast.makeText(Main3Activity.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {

                Log.e(TAG, " response error "+t.toString());
                Toast.makeText(Main3Activity.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
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
//                    Toast.makeText(Main3Activity.this,"Transaction successful", Toast.LENGTH_SHORT).show();
                    linear2.setVisibility(View.GONE);
                    preorder_continue.setVisibility(View.VISIBLE);
                    pre.setVisibility(View.GONE);



                }
                else {
                    Toast.makeText(Main3Activity.this,"NETWORK ERROR PLEASE TRY AGAIN ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });
        Log.e(TAG, " transaction reached here ");

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(Main3Activity.this, ActivityRequestCode);
        //transactionsuccess();
    }



    public void preoders(String pay) {

        dialog = new ACProgressFlower.Builder(Main3Activity.this)
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
        SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userid = fullname_shared.getString("logged_in_userId","");
        Integer uid = Integer.valueOf(userid);
        String name= nm.getText().toString();
       String  mobile= mob.getText().toString();
       String  place= plce.getText().toString();
       String  addr= address.getText().toString();
       String seldt = selected_date+" "+selected_time;
        ccount_arry.clear();
String count = cnt.getText().toString();
int c = Integer.parseInt(count);
String tot_addr = name+" "+place+" "+addr+" "+mobile;
ccount_arry.add(c);
        Log.e("cart","checkout param=    itemid ====>"+item_id_main3);
        Log.e("cart","checkout param=    count====>"+ccount_arry);
        Log.e("cart","checkout param=    quantity ====>"+item_quant_main3);
        Log.e("cart","checkout param=    type ====>0");
        Log.e("cart","checkout param=    type ====>0");
        Log.e("cart","checkout param=    price ====>"+item_price_main3);
        Log.e("cart","checkout param=    userid ====>"+uid);
        Log.e("cart","checkout param=    address ====> "+tot_addr);
        Log.e("cart","checkout param=    postcode ====> "+selected_pincode);
        Log.e("cart","checkout param=    preBookingDate ====> "+seldt);
        Log.e("cart","checkout param=    bookingType ====> "+"1");
        Call call = mainInterface.preBooking(item_id_main3,ccount_arry,item_quant_main3,0,item_price_main3,uid,tot_addr,selected_pincode,1,seldt,"1");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
               Log.e("cart","response preorderes="+response.body());


               Toast.makeText(Main3Activity.this,"Successfully Placed Orders",Toast.LENGTH_SHORT).show();
//
               dialog.dismiss();
                linear2.setVisibility(View.VISIBLE);
                preorder_continue.setVisibility(View.GONE);
                pre.setVisibility(View.VISIBLE);
                push_notif();

//                //clearance of cart items
//
//                SharedPreferences fullname_shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                String fullname = fullname_shared.getString("fullusername","");
//
//
//                //retrieve item names for getting specific count
//                SharedPreferences shared_tot = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                Log.e("cart","fullname name ="+fullname);
//                String cart_item_names = fullname+"cart_item_names";
//                Log.e("cart","fullname name="+fullname);
//                String itemSingle_name_old = shared_tot.getString(cart_item_names, "");
//
//                if(!(itemSingle_name_old==null)||(itemSingle_name_old.length()==0))
//                {
//                    String[] cats = itemSingle_name_old .split(",");//if spaces are uneven, use \\s+ instead of " "
//                    for (String ct : cats) {
//                        if(!(ct.equals("")||ct.equals(null)))
//                        {
//                            items_name_old_cart_payment.add(ct);
//                        }
//
//                    }
//                }
//
//
//                SharedPreferences.Editor editor_payment = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                for(int i=0;i<items_name_old_cart_payment.size();i++) {
//                    String nm = items_name_old_cart_payment.get(i);
//                    //specific count
//                    String sharepreferencename_count_payment = fullname + nm + "_count";
//                    editor_payment.putString(sharepreferencename_count_payment,"");
//                }
//                //your cart text count
//                String total_count_cart_payment = fullname+"total_count_cart";
//                String cart_Items_toolbar_count_payment= fullname+"cart_Items_toolbar_count";
//                String cart_item_names_id_payment = fullname+"cart_item_names_id";
//                String cart_item_image_payment = fullname+"cart_item_image";
//                String cart_item_qnty_payment =fullname+"cart_item_qnty";
//                String cart_item_cod_payment = fullname+"cart_item_cod";
//                String cart_item_price_payment = fullname+"cart_item_price";
//                String cart_item_offer_percent_payment = fullname+"cart_item_offer_percent";
//                String cart_item_names_payment = fullname+"cart_item_names";
//                String t ="0";
//                Main2Activity.update_counter(t);
//
//                editor_payment.putString(cart_item_names_payment,"");
//                editor_payment.putString(total_count_cart_payment,"");
//                editor_payment.putString(cart_Items_toolbar_count_payment,"");
//                editor_payment.putString(cart_item_names_id_payment,"");
//                editor_payment.putString(cart_item_image_payment,"");
//                editor_payment.putString(cart_item_qnty_payment,"");
//                editor_payment.putString(cart_item_cod_payment,"");
//                editor_payment.putString(cart_item_price_payment,"");
//                editor_payment.putString(cart_item_offer_percent_payment,"");
//
//                editor_payment.apply();
//
//                push_notif();
//                Toast.makeText(Payment.this,"Your order has been placed successfully",Toast.LENGTH_LONG).show();
//                Intent next = new Intent(Payment.this,Thankyou.class);
//                startActivity(next);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
               Toast.makeText(Main3Activity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
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

}
