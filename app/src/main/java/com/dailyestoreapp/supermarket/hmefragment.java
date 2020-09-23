package com.dailyestoreapp.supermarket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class hmefragment extends Fragment {
    String category_selected;
    int category_selected_no;
    private String tag = "HomeFragment";
    public static final String MY_PREFS_NAME = "AdminApp";

    Fragment4 frag4;
    private int[] tabIcons = {

            R.drawable.ic_home_black_24dp,
            R.drawable.ic_local_offer_black_24dp,
            R.drawable.ic_contact_phone_black_24dp,
            R.drawable.ic_person_black_24dp

    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(),getChildFragmentManager());
        sectionsPagerAdapter.addFragment(new Fragment1(),"Home");
        sectionsPagerAdapter.addFragment(new Fragment2(),"Offers");
        sectionsPagerAdapter.addFragment(new Fragment3(),"Book On Calls");
        sectionsPagerAdapter.addFragment(new Fragment4(),"My Account");
        ViewPager viewPager = root.findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
        tabs.getTabAt(3).setIcon(tabIcons[3]);

        return root;
    }

}

