package com.dailyestoreapp.supermarket;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Fragment3 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ImageView call,whats,email;
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
        final String number = "+917510237377";
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bookoncall, container, false);
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
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "dailyestore@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "DailyeStore");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getContext(), "No application found to support ", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}

