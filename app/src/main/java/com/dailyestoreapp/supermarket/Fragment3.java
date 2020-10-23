package com.dailyestoreapp.supermarket;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment3 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ImageView call,whats,email;
CardView call_card,whatsapp_card;
LinearLayout bookoncall_lin;
     String number = "+917510237377";
   String  emailidd="dailyestore@gmail.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
EditText ed,ed2;
    public Fragment3() {
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
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
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

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bookoncall, container, false);
        bookoncall_lin=rootView.findViewById(R.id.bookoncall);
        UserDetails();
        call_card=rootView.findViewById(R.id.oncall);
        whatsapp_card=rootView.findViewById(R.id.onwhatsapp);
        call_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   String ph = number;
                 Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                 startActivity(intent);
                  } catch (Exception e) {
                     Toast.makeText(getContext(),""+e,Toast.LENGTH_SHORT).show();
                  }
            }
        });
        whatsapp_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm=getContext().getPackageManager();
                try {
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(Intent.createChooser(i, ""));
                }
                catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
//        call = (ImageView) rootView.findViewById(R.id.call);
//        whats = (ImageView) rootView.findViewById(R.id.whatsapp);
//        email = (ImageView) rootView.findViewById(R.id.email);
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendMail();
//            }
//        });
//        whats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PackageManager pm=getContext().getPackageManager();
//                try {
//                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//                    Uri uri = Uri.parse("smsto:" + number);
//                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
//                    i.setPackage("com.whatsapp");
//                    startActivity(Intent.createChooser(i, ""));
//                }
//                catch (PackageManager.NameNotFoundException e) {
//                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
//                            .show();
//                }
//
//            }
//        });
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 try {
//                   String ph = "+917510237377";
//                 Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
//                 startActivity(intent);
//                  } catch (Exception e) {
//                     Toast.makeText(getContext(),""+e,Toast.LENGTH_SHORT);
//                  }
//            }
//        });

        return rootView;
    }
    private void sendMail() {
//        String recipientList = "anandc17@gmail.com";
//        String[] recipients = recipientList.split(",");
//        String subject = "DailyeStore";
//        String message = "Hi";
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        intent.setType("text/plain");
//        startActivity(intent);
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + emailidd));
            intent.putExtra(Intent.EXTRA_SUBJECT, "DailyeStore");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getContext(), "No application found to support ", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void UserDetails()
    {


        int user_idd = 1;
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

//                        String firstname = listCategoryResponseobject.getResponsedata().getData().getFirstName();
//                        firstname_main=firstname;
//                        String lastname = listCategoryResponseobject.getResponsedata().getData().getLastName();
//                        lastname_main=lastname;
//                        String totl_name = firstname;
//                        String ph_no = listCategoryResponseobject.getResponsedata().getData().getPhone();
//
//
//                        String email = listCategoryResponseobject.getResponsedata().getData().getEmail();
//                        email_main=email;
//                        String pincode = listCategoryResponseobject.getResponsedata().getData().getPinCode();
//
//                        String address = listCategoryResponseobject.getResponsedata().getData().getAddress();
//                        address_main=address;
//                        String dobb =listCategoryResponseobject.getResponsedata().getData().getDob();
//                        dob_main=dobb;
//                        name_account.setText(totl_name);
//                        email_account.setText(email);
//                        mob_account.setText(ph_no);
//                        pincode_account.setText(pincode);
//                        address_account.setText(address);

                        number=listCategoryResponseobject.getResponsedata().getData().getPhone();
                        Log.e("frag3","the number is "+number);
                        emailidd=listCategoryResponseobject.getResponsedata().getData().getEmail();
                        Log.e("frag3","the number is "+emailidd);
                        bookoncall_lin.setVisibility(View.VISIBLE);
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

}

