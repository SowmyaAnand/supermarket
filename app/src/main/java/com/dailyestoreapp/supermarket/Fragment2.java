package com.dailyestoreapp.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Fragment2 extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    ArrayList Images_offers = new ArrayList<>(Arrays.asList(R.drawable.newvegetable,R.drawable.sweets_cakes_strawberry_chocolate_white_background_564093_1280x1024_removebg_preview_1, R.drawable.brochure_vector_vegetable_2_1, R.drawable.newvegetable, R.drawable.sweets_cakes_strawberry_chocolate_white_background_564093_1280x1024_removebg_preview_1));
ArrayList Images_offers = new ArrayList<>(Arrays.asList(R.drawable.subcategory,R.drawable.subcategory, R.drawable.subcategory, R.drawable.subcategory, R.drawable.subcategory));

    ArrayList personNames_offers = new ArrayList<>(Arrays.asList("ITEM1", "ITEM2", "ITEM3", "ITEM4", "ITEM5", "ITEM6", "ITEM7"));
    RecyclerView recyclerView_offers,itemlistingcategory_offers;
    LinearLayoutManager linearLayoutManager_offers,linearLayoutManager2_offers;
    Offers_ItemAdapter customAdapter_offers;
    OffersListingsubcategoryadapter customadapter2_offers;
    public Fragment2() {
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
        View rootView = inflater.inflate(R.layout.fragment2, container, false);

        itemlistingcategory_offers = (RecyclerView) rootView.findViewById(R.id.recyclerView_categories_offer);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager2_offers = new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        itemlistingcategory_offers.setLayoutManager(linearLayoutManager2_offers);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        customadapter2_offers = new OffersListingsubcategoryadapter(rootView.getContext(), personNames_offers,Images_offers);
        itemlistingcategory_offers.setAdapter(customadapter2_offers);
        //second recyclerview
        recyclerView_offers = (RecyclerView) rootView.findViewById(R.id.itemrecycler_offers);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView_offers.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        customAdapter_offers = new Offers_ItemAdapter(rootView.getContext(), personNames_offers);
        recyclerView_offers.setAdapter(customAdapter_offers);
        // GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        // gridview.setAdapter(new ImageAdapter(rootView.getContext()));
        return rootView;
    }
}
