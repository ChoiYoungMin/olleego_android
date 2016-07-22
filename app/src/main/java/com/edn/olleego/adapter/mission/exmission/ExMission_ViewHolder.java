package com.edn.olleego.adapter.mission.exmission;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-07-22.
 */
public class ExMission_ViewHolder {

    @BindView(R.id.exmission_img)
    ImageView mImg;

    @BindView(R.id.exmission_title)
    TextView title;

    @BindView(R.id.exmission_title2)
    TextView title2;

    @BindView(R.id.exmission_next)
    ImageView next;

    public ExMission_ViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
