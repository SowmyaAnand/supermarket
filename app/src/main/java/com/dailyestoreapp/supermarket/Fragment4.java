package com.dailyestoreapp.supermarket;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Fragment4 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView call,whats,email;
    RelativeLayout rel1,rel2;
    Button loginfromfrg;
    Button coupon,myorders,logout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String MY_PREFS_NAME = "CustomerApp";
  EditText name_account,mob_account,email_account,pincode_account,address_account;
    TextView edit_account,save_account ;
    String firstname_main;
    String lastname_main;String email_main;String phone_num_main;String address_main;String pincode_main;String dob_main;
    public Fragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment4 newInstance(String param1, String param2) {
        Fragment4 fragment = new Fragment4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String number = "+917510237377";


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment4, container, false);
        rel1=rootView.findViewById(R.id.relative1);
        rel2=rootView.findViewById(R.id.relative2);

        loginfromfrg=rootView.findViewById(R.id.loginfromfragment);
        name_account = (EditText) rootView.findViewById(R.id.Name_account);
        mob_account = (EditText) rootView.findViewById(R.id.Mobilenumber_account);
        email_account = (EditText)rootView.findViewById(R.id.emailid_account);
        pincode_account = (EditText) rootView.findViewById(R.id.Pincode_account);

        address_account = (EditText)rootView.findViewById(R.id.edtxtDescr_account);
        edit_account = (TextView)rootView.findViewById(R.id.edit_acount);
        save_account = (TextView)rootView.findViewById(R.id.edit_acount_save);
        coupon = (Button)rootView.findViewById(R.id.mycoupons);
        myorders = (Button)rootView.findViewById(R.id.myorders);
        logout = (Button)rootView.findViewById(R.id.logout);
        SharedPreferences login_flag = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String log_in_flag = login_flag.getString("logged_in_flag","");

        if(log_in_flag.equals("1"))
        {
            rel1.setVisibility(View.VISIBLE);
            rel2.setVisibility(View.GONE);
            UserDetails();
        }
        else
        {
            rel2.setVisibility(View.VISIBLE);
            rel1.setVisibility(View.GONE);
        }
        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You can edit now",Toast.LENGTH_SHORT).show();
                save_account.setVisibility(View.VISIBLE);
                edit_account.setVisibility(View.GONE);
                name_account.setEnabled(true);name_account.setFocusable(true);
                mob_account.setEnabled(true);mob_account.setFocusable(true);
                email_account.setEnabled(true);email_account.setFocusable(true);
                pincode_account.setEnabled(true);pincode_account.setFocusable(true);

                address_account.setEnabled(true);address_account.setFocusable(true);

            }
        });
        save_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_account.setVisibility(View.GONE);
                edit_account.setVisibility(View.VISIBLE);
                name_account.setEnabled(false);name_account.setFocusable(false);
                mob_account.setEnabled(false);mob_account.setFocusable(false);
                email_account.setEnabled(false);email_account.setFocusable(false);
                pincode_account.setEnabled(false);pincode_account.setFocusable(false);

                address_account.setEnabled(false);address_account.setFocusable(false);
                firstname_main= String.valueOf(name_account.getText());
                email_main=String.valueOf(email_account.getText());

                phone_num_main= String.valueOf(mob_account.getText());
                address_main=String.valueOf(address_account.getText());

                pincode_main= String.valueOf(pincode_account.getText());
                SharedPreferences shared = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String useridd = shared.getString("logged_in_userId","");
                int idd = Integer.parseInt(useridd);
                userdetailsEdit(idd,firstname_main,lastname_main,email_main,phone_num_main,address_main,pincode_main,dob_main);


            }
        });

        loginfromfrg.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent c = new Intent(getContext(),Login.class);
                startActivity(c);
            }
        });
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getContext(),MyCoupons.class);
                startActivity(c);
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getContext(),Myorders.class);
                startActivity(c);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getContext(),Login.class);
                startActivity(c);
            }
        });
        return rootView;
    }
    private void UserDetails()
    {

        SharedPreferences shared = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String useridd = shared.getString("logged_in_userId","");
        int user_idd = Integer.parseInt(useridd);
        final StringBuilder frst_flyer_images  = new StringBuilder();
        String url = "http://dailyestoreapp.com/dailyestore/api/";
        final String url1 = "http://dailyestoreapp.com/dailyestore/";
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

        Call<CustomerAppResponseMyAccount> call = mainInterface.userDetails(user_idd);
        call.enqueue(new Callback<CustomerAppResponseMyAccount>() {
            @Override
            public void onResponse(Call<CustomerAppResponseMyAccount> call, retrofit2.Response<CustomerAppResponseMyAccount> response) {
                CustomerAppResponseMyAccount listCategoryResponseobject = response.body();
                int success = Integer.parseInt(response.body().getResponsedata().getSuccess());
                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata().getSuccess());

                Log.e("firstpop","the succes value is "+listCategoryResponseobject.getResponsedata());



                try {


                    if(success==1) {

                        String firstname = listCategoryResponseobject.getResponsedata().getData().getFirstName();
                        firstname_main=firstname;
                        String lastname = listCategoryResponseobject.getResponsedata().getData().getLastName();
                        lastname_main=lastname;
                        String totl_name = firstname;
                        String ph_no = listCategoryResponseobject.getResponsedata().getData().getPhone();


                        String email = listCategoryResponseobject.getResponsedata().getData().getEmail();
                        email_main=email;
                        String pincode = listCategoryResponseobject.getResponsedata().getData().getPinCode();

                        String address = listCategoryResponseobject.getResponsedata().getData().getAddress();
                        address_main=address;
                        String dobb =listCategoryResponseobject.getResponsedata().getData().getDob();
                        dob_main=dobb;
                        name_account.setText(totl_name);
                        email_account.setText(email);
                        mob_account.setText(ph_no);
                        pincode_account.setText(pincode);
                        address_account.setText(address);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseMyAccount> call, Throwable t) {
                Log.e("frag","error="+t.getMessage());
            }
        });





    }


    private void userdetailsEdit(final int Id, String firstname, final String lastname, final String email,final String phone,final String address,final String pincode,final String dob)
    {



        String url = "http://dailyestoreapp.com/dailyestore/api/";
        final String url1 = "http://dailyestoreapp.com/dailyestore/";
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
        Call<CustomerAppResponseLogin> call = mainInterface.UpdateMyaccount(Id,firstname,lastname,email,phone,address,pincode,dob);
        call.enqueue(new Callback<CustomerAppResponseLogin>() {
            @Override
            public void onResponse(Call<CustomerAppResponseLogin> call, retrofit2.Response<CustomerAppResponseLogin> response) {
                CustomerAppResponseLogin listCategoryResponseobject = response.body();
                String success = response.body().getResponsedata().getSuccess();
                Log.e("frag4","error"+success);
                Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
                try {
//

                    if(success.equals("1"))
                    {
                        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_LONG).show();
//

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerAppResponseLogin> call, Throwable t) {
                Log.e("frag4","error"+t.getMessage());
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }

}
