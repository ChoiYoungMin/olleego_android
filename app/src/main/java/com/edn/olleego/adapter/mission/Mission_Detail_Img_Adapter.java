package com.edn.olleego.adapter.mission;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-07-20.
 */
public class Mission_Detail_Img_Adapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context = null;
    private ArrayList<String> mission_detail_img = new ArrayList<String>();

    public Mission_Detail_Img_Adapter(LayoutInflater inflater, Context context) {
        super();
        this.inflater = inflater;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mission_detail_img.size();
    }

    @Override
    public Object getItem(int position) {
        return mission_detail_img.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder2 holder = null;

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_detail_img, null);

            holder = new holder2();
            holder.mimg = (ImageView)convertView.findViewById(R.id.mission_detail_img);

            convertView.setTag(holder);
        }else {
            holder = (holder2)convertView.getTag();
        }





        //포지션이 안올라감
        Glide.with(context).load(mission_detail_img.get(position))
                .into(holder.mimg);

        holder.mimg.setScaleType(ImageView.ScaleType.FIT_XY);



        return convertView;
    }

    public void addItem(String mImg) {
        mission_detail_img.add(mImg);
        notifyDataSetChanged();
    }

    public void ItemRemove() {
        mission_detail_img.clear();
    }




}

class holder2{
    ImageView mimg;

}
