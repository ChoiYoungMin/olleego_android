package com.edn.olleego.adapter.mission.missionlist;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-08-09.
 */
public class MissionListViewHolder {

    @BindView(R.id.mission_list_day)
    TextView mission_list_day;

    @BindView(R.id.mission_list_title_1)
    TextView mission_list_title_1;
    @BindView(R.id.mission_list_title_2)
    TextView mission_list_title_2;
    @BindView(R.id.mission_list_title_3)
    TextView mission_list_title_3;

    @BindView(R.id.mission_list_listview_1)
    ListView mission_list_listview_1;
    @BindView(R.id.mission_list_listview_2)
    ListView mission_list_listview_2;
    @BindView(R.id.mission_list_listview_3)
    ListView mission_list_listview_3;

    public MissionListViewHolder(View view){
        ButterKnife.bind(this, view);
    }
}
