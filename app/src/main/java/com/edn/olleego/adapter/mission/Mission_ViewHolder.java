package com.edn.olleego.adapter.mission;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-06-28.
 */
public class Mission_ViewHolder {



    @BindView(R.id.mission_img) ImageView mImg;
    @BindView(R.id.mission_title) TextView mTitle;
    @BindView(R.id.mission_target) TextView mTarget;
    public Mission_ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
