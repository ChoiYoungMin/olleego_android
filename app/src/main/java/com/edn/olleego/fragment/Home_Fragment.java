package com.edn.olleego.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.login.LoginActivity;
import com.edn.olleego.activity.mission.MissionCustomized1Activity;
import com.edn.olleego.fragment.Mission.MissionFragment;
import com.edn.olleego.fragment.chart.ChartFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Antonio on 2016-06-23.
 */
public class Home_Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    @BindView(R.id.home_mission_choise)
    ImageView mission_chise;

    SharedPreferences olleego_SP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        ButterKnife.bind(this,rootView);
        actionbar_init();
        olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);


        //로그인 상태
        if(olleego_SP.getString("login_chk", "").equals("true")) {

            rootView.findViewById(R.id.main_login_no).setVisibility(View.GONE);
            Toast.makeText(getContext(),"로그인중", Toast.LENGTH_SHORT).show();

            /*
            //최초 로그인
            if() {

            }
            //미션 선택 안한 상태
            else if () {

            }
            //미션 선택한 상태
            else if() {

            }
            //미션 끝난 상태
            else if() {

            }
            */

        }
        // 비로그인 상태
        else {
            rootView.findViewById(R.id.main_login_ok_type1).setVisibility(View.GONE);
            Toast.makeText(getContext(),"비로그인중", Toast.LENGTH_SHORT).show();


        }
        return rootView;
    }


    public void actionbar_init() {

        getActivity().findViewById(R.id.toolbar_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_left_menu).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.toolbar_right_menu).setVisibility(View.VISIBLE);


    }


    @OnClick(R.id.home_mission_choise)
    void mission_btn_click() {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        MissionFragment missionFragment = new MissionFragment();
        transaction.setCustomAnimations(R.anim.slide_in_left, 0);
        transaction.replace(R.id.content_main, missionFragment);
        transaction.addToBackStack(null);
        transaction.commit();

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

}