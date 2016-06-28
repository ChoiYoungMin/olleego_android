package com.edn.olleego.adapter.olleegogym;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-06-23.
 */
public class OlleegGym_Adapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context mContext = null;
    private ArrayList<OlleegoGym_Data> mGymData = new ArrayList<OlleegoGym_Data>();

    public OlleegGym_Adapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return mGymData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGymData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OlleegGym_ViewHolder holder = new OlleegGym_ViewHolder();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_gymlist, null);

            holder.aimg = (ImageView) convertView.findViewById(R.id.gym_img);
            holder.gym_type = (TextView)convertView.findViewById(R.id.gym_type);
            holder.gym_name= (TextView)convertView.findViewById(R.id.gym_name);
            holder.gym_pay= (TextView)convertView.findViewById(R.id.gym_pay2);
            holder.gym_pt= (TextView)convertView.findViewById(R.id.gym_pt2);

            convertView.setTag(holder);

        }
        else {
            holder = (OlleegGym_ViewHolder)convertView.getTag();
        }

        OlleegoGym_Data gymDatas = mGymData.get(position);


        holder.aimg.setImageResource(mGymData.get(position).mImg);
        holder.gym_type.setText(gymDatas.gym_type);
        holder.gym_name.setText(gymDatas.gym_name);
        holder.gym_pay.setText(gymDatas.gym_pay);
        holder.gym_pt.setText(gymDatas.gym_pt);



        return convertView;
    }

    public void add(int img, String test1, String test2, String test3, String test4) {
        OlleegoGym_Data olleegoGym_data = new OlleegoGym_Data(img, test1, test2, test3, test4);

        mGymData.add(olleegoGym_data);
    }

}
