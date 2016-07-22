package com.edn.olleego.adapter.mission.exmission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-07-22.
 */
public class ExMission_Adapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private Context mContext = null;
    private ArrayList<ExMission_Data> mExData = new ArrayList<ExMission_Data>();

    public ExMission_Adapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return mExData.size();
    }

    @Override
    public Object getItem(int position) {
        return mExData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExMission_ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_exmission, null);

            //holder.aimg = (ImageView) convertView.findViewById(R.id.gym_img);
            holder = new ExMission_ViewHolder(convertView);

            convertView.setTag(holder);

        }
        else {
            holder = (ExMission_ViewHolder)convertView.getTag();
        }

        ExMission_Data gymDatas = mExData.get(position);


        //holder.gym_pt.setText(gymDatas.gym_pt);



        return convertView;
    }

    public void add(String img, String title, String title2) {
        ExMission_Data olleegoGym_data = new ExMission_Data(img, title, title2);

        mExData.add(olleegoGym_data);
    }
}
