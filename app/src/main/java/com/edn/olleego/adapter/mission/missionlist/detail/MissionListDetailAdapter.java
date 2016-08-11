package com.edn.olleego.adapter.mission.missionlist.detail;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_ViewHolder;
import com.edn.olleego.adapter.mission.missionlist.MissionListData;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-08-09.
 */
public class MissionListDetailAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<MissionListDetailData> missionListDetailDatas = new ArrayList<MissionListDetailData>();
    MissionListDetailViewHolder holder=null;

    public MissionListDetailAdapter(LayoutInflater inflater, Context context){
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return missionListDetailDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return missionListDetailDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null ){

            convertView = inflater.inflate(R.layout.item_mission_list_detail, null);

            holder = new MissionListDetailViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            holder = (MissionListDetailViewHolder)convertView.getTag();
        }

        MissionListDetailData missionListDetailData = missionListDetailDatas.get(position);

        holder.mission_list_detail_text.setText(missionListDetailData.text);

        if(missionListDetailData.type == true) {
            holder.mission_list_detail_text.setPaintFlags(holder.mission_list_detail_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {

        }


        return convertView;
    }

    public void add(String text, boolean type) {
        MissionListDetailData missionListData = new MissionListDetailData(text,type);
        missionListDetailDatas.add(missionListData);
        notifyDataSetChanged();
    }
}
