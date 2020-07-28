package com.dailyestoreapp.supermarket;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView call,whats,email;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  EditText name_account,mob_account,email_account,pincode_account,place_account,address_account;
    TextView edit_account ;
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
        name_account = (EditText) rootView.findViewById(R.id.Name_account);
        mob_account = (EditText) rootView.findViewById(R.id.Mobilenumber_account);
        email_account = (EditText)rootView.findViewById(R.id.emailid_account);
        pincode_account = (EditText) rootView.findViewById(R.id.Pincode_account);
        place_account = (EditText) rootView.findViewById(R.id.Place_account);
        address_account = (EditText)rootView.findViewById(R.id.edtxtDescr_account);
        edit_account = (TextView)rootView.findViewById(R.id.edit_acount);

        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_account.setEnabled(true);name_account.setFocusable(true);
                mob_account.setEnabled(true);mob_account.setFocusable(true);
                email_account.setEnabled(true);email_account.setFocusable(true);
                pincode_account.setEnabled(true);pincode_account.setFocusable(true);
                place_account.setEnabled(true);place_account.setFocusable(true);
                address_account.setEnabled(true);address_account.setFocusable(true);

            }
        });
        return rootView;
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
