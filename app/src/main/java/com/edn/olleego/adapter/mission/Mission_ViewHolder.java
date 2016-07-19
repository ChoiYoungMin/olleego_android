package com.edn.olleego.adapter.mission;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-06-28.
 */
public class Mission_ViewHolder {



    @BindView(R.id.mission_img) ImageView mImg;
    @BindView(R.id.mission_type) TextView mission_type;
    @BindView(R.id.mission_title) TextView mission_title;
    @BindView(R.id.mission_rating) RatingBar rating;
    @BindView(R.id.rating_peple) TextView rating_peple;
    @BindView(R.id.mission_level) TextView mission_level;
    @BindView(R.id.mission_time) TextView mission_time;
    public Mission_ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }


}
