package com.edn.olleego.fragment.setting;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.mission.MissionCustomStepOneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.setting_name_text)
    TextView setting_name_text;
    @BindView(R.id.setting_email_text)
    TextView setting_email_text;
    @BindView(R.id.setting_gender_text)
    TextView setting_gender_text;
    @BindView(R.id.setting_birthday_text)
    TextView setting_birthday_text;
    @BindView(R.id.setting_img)
    ImageView setting_img;

    SharedPreferences olleego_sp;

    String user_name ;
    String user_email ;
    String user_gender;
    String user_birthday;



    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(int position) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        olleego_sp = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);

        ButterKnife.bind(this,view);


        init();


        Glide.with(getContext()).load(olleego_sp.getString("login_img", "")).bitmapTransform(new CropCircleTransformation(getContext())).into(setting_img);
        setting_name_text.setText(user_name);
        setting_email_text.setText(user_email);
        setting_gender_text.setText(user_gender);
        setting_birthday_text.setText(user_birthday);



        return view;
    }

    public void init() {
        user_name = olleego_sp.getString("login_name", "");
        user_email = olleego_sp.getString("login_email", "");



        if(olleego_sp.getString("login_gender", "").equals("M")) {
            user_gender ="남성";
        } else if(olleego_sp.getString("login_gender", "").equals("W")) {
            user_gender ="여성";
        }


        user_birthday = olleego_sp.getString("login_birthday", "");




    }

}
