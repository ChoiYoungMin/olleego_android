package com.edn.olleego.adapter.mission;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    public Mission_Adapter(LayoutInflater inflater, Context context) {
        super();
        this.inflater = inflater;
        this.context = context;
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

        Glide.with(context).load(gymDatas.mImg)
                .into(holder.mImg);

        holder.mImg.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.mission_type.setText(gymDatas.mission_type);
        holder.mission_title.setText(gymDatas.mission_title);
        holder.rating.setRating(gymDatas.rating);
        holder.rating_peple.setText(String.valueOf(gymDatas.rating_peple));
        holder.mission_level.setText(gymDatas.mission_level);
        holder.mission_time.setText(gymDatas.mission_time);


        return convertView;
    }

    public void addItem(String mImg, String mission_type, String mission_title, int rating, int rating_peple, String mission_level, String mission_time) {
        Mission_Data missionData = new Mission_Data(mImg, mission_type, mission_title, rating, rating_peple, mission_level, mission_time);

        mission_data.add(missionData);
        notifyDataSetChanged();
    }

    public void ItemRemove() {
        mission_data.clear();
    }


}
