package com.edn.olleego.adapter.mission;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-06-28.
 */
public class Mission_Adapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private Context context = null;
    private ArrayList<Mission_Data> mission_data = new ArrayList<Mission_Data>();

    public Mission_Adapter(LayoutInflater inflater) {
        super();
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return mission_data.size();
    }

    @Override
    public Object getItem(int position) {
        return mission_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mission_ViewHolder holder = null;


        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_missionlist, null);

            holder = new Mission_ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (Mission_ViewHolder)convertView.getTag();
        }


        Mission_Data gymDatas = mission_data.get(position);

        holder.mImg.setImageResource(gymDatas.mImg);
        holder.mTitle.setText(gymDatas.mTitle);
        holder.mTarget.setText(gymDatas.mTarget);



        return convertView;
    }

    public void addItem(int mImg, String mTitle, String mTarget) {
        Mission_Data missionData = new Mission_Data(mImg, mTitle, mTarget);

        mission_data.add(missionData);
    }
}
