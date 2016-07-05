package com.edn.olleego.fragment.Mission;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.edn.olleego.R;
import com.edn.olleego.activity.mission.MissionActivity;

public class MissionDetailFragment extends Fragment {

    public MissionDetailFragment() {
        super();
    }

    public static MissionDetailFragment newInstance(int sectionNumber) {
        MissionDetailFragment fragment = new MissionDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mission_detail, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.ratingBar3) ;

        ratingBar.setStepSize((float) 0.5);
        ratingBar.setRating((float) 2.5);
        ratingBar.setIsIndicator(true);


        return rootView;
    }
}
