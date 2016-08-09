package com.edn.olleego.adapter.mission.missionlist.detail;

import android.view.View;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-08-09.
 */
public class MissionListDetailViewHolder {

    @BindView(R.id.mission_list_detail_text)
    TextView mission_list_detail_text;


    public MissionListDetailViewHolder(View view) {
        ButterKnife.bind(this,view);
    }
}
