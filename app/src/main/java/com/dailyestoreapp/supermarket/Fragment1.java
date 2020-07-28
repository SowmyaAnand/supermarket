package com.dailyestoreapp.supermarket;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewPager mviewpager,flyerpager;
    ImageAdapter adapterview;
    ExpandableHeightGridView gridview;
    Imageadapterforflyers flyeradapterview;
    TextView t;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
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
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mviewpager = (ViewPager)rootView.findViewById(R.id.viewpager);
        flyerpager = (ViewPager)rootView.findViewById(R.id.viewpager2);
        flyeradapterview = new Imageadapterforflyers(getContext());
        flyerpager.setAdapter(flyeradapterview);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new Mytimertask2(),900,1800);
       adapterview = new ImageAdapter(getContext());
        mviewpager.setAdapter(adapterview);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Mytimertask(),1200,2400);
        gridview = (ExpandableHeightGridView) rootView.findViewById(R.id.categories);
        gridview.setAdapter(new GridviewAdapter(rootView.getContext()));
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent next = new Intent(getContext(),CartPage.class);
                startActivity(next);
            }
        });

        return rootView;
    }

    public class Mytimertask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mviewpager.getCurrentItem() ==0)
                    {
                        mviewpager.setCurrentItem(1);
                    }
                    else if(mviewpager.getCurrentItem()==1)
                    {
                        mviewpager.setCurrentItem(2);

                    }
                    else
                    {
                        mviewpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    public class Mytimertask2 extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(flyerpager.getCurrentItem() ==0)
                    {
                        flyerpager.setCurrentItem(1);
                    }
                    else if(flyerpager.getCurrentItem()==1)
                    {
                        flyerpager.setCurrentItem(2);

                    }
                    else
                    {
                        flyerpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
