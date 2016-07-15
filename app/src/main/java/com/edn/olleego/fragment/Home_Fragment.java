package com.edn.olleego.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.main.MainMiddleViewPagerAdapter;
import com.edn.olleego.adapter.main.MainTopViewPagerAdapter;
import com.edn.olleego.adapter.mission.Mission_Adapter;
import com.edn.olleego.common.CircleProgressBar;

import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Antonio on 2016-06-23.
 */
public class Home_Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";


    SharedPreferences olleego_SP;
    CircleProgressBar circleProgressBar;

    ViewPager viewPager, viewPager2;
    CircleIndicator circleIndicator, circleIndicator2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        ButterKnife.bind(this,rootView);
        actionbar_init();
        olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);



        /*
        RoundCornerProgressBar progress1 = (RoundCornerProgressBar) rootView.findViewById(R.id.progress22);
        progress1.setProgressColor(Color.parseColor("#ed3b27"));
        progress1.setProgressBackgroundColor(Color.parseColor("#808080"));
        progress1.setMax(100);
        progress1.setProgress(45);
*/

        //로그인 상태
        if(olleego_SP.getString("login_chk", "").equals("true")) {

            viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
            viewPager2 = (ViewPager) rootView.findViewById(R.id.viewPager2);
            circleIndicator = (CircleIndicator) rootView.findViewById(R.id.inco);
            circleIndicator2 = (CircleIndicator) rootView.findViewById(R.id.inco2);


            viewPager.setAdapter(new MainTopViewPagerAdapter(inflater, olleego_SP));
            circleIndicator.setViewPager(viewPager);

            viewPager2.setAdapter(new MainMiddleViewPagerAdapter(inflater));
            circleIndicator2.setViewPager(viewPager2);

        }
        // 비로그인 상태
        else {
            Toast.makeText(getContext(),"비로그인중", Toast.LENGTH_SHORT).show();


        }


        return rootView;
    }


    public void actionbar_init() {

        //getActivity().findViewById(R.id.toolbar_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_left_menu).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.toolbar_right_menu).setVisibility(View.VISIBLE);


    }

/*
    @OnClick(R.id.home_mission_choise)
    void mission_btn_click() {

        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        MissionFragment missionFragment = new MissionFragment();
        transaction.setCustomAnimations(R.anim.slide_in_left, 0);
        transaction.replace(R.id.content_main, missionFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Intent intent = new Intent(getActivity(), Mission.class);
        startActivity(intent);
        getActivity().finish();
    }


    @OnClick(R.id.button2)
    void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.button22)
    void logout() {

        SharedPreferences.Editor editor = olleego_SP.edit();
        //editor.remove("login_chk");
        editor.clear();
        editor.commit();
    }

    @OnClick(R.id.main_custom_mission)
    void click() {
        Intent intent = new Intent(getActivity(), MissionCustomized1Activity.class);
        getActivity().startActivity(intent);
    }
    */

}