package com.edn.olleego.fragment.Mission;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Category_PagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionCategoryMainFragment extends Fragment {

    private Mission_Category_PagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public MissionCategoryMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mission_category_main, container, false);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new Mission_Category_PagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        return view;
    }

}
