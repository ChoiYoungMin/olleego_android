package com.edn.olleego.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edn.olleego.R;
import com.edn.olleego.activity.login.LoginActivity;
import com.edn.olleego.fragment.Mission.MissionFragment;
import com.edn.olleego.fragment.chart.ChartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Antonio on 2016-06-23.
 */
public class Home_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Home_Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Home_Fragment newInstance(int sectionNumber) {
        Home_Fragment fragment = new Home_Fragment();
        return fragment;
    }


    @BindView(R.id.home_mission_choise)
    ImageView mission_chise;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);


        ButterKnife.bind(this,rootView);




        return rootView;
    }


    @OnClick(R.id.home_mission_choise)
    void mission_btn_click() {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();





        getActivity().findViewById(R.id.toolbar_home2).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.toolbar_home).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_home22).setVisibility(View.GONE);




        MissionFragment missionFragment = new MissionFragment();

        transaction.setCustomAnimations(R.anim.slide_in_left, 0);

        transaction.replace(R.id.content_main, missionFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }


    //임시 로그인 화면 이동
    @OnClick(R.id.button2)
    void logingo_lick() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}