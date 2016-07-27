package com.edn.olleego.adapter.calendar.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Data;
import com.edn.olleego.adapter.mission.Mission_ViewHolder;

import java.util.ArrayList;

/**
 * Created by Antonio on 2016-07-27.
 */
public class Diary_Food_Adapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private Context context = null;
    private ArrayList<Diary_Food_Data> diary_food_datas = new ArrayList<Diary_Food_Data>();

    public Diary_Food_Adapter(LayoutInflater inflater, Context context) {
        super();
        this.inflater = inflater;
        this.context = context;
    }


    @Override
    public int getCount() {
        return diary_food_datas.size();
    }

    @Override
    public Object getItem(int position) {
        return diary_food_datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Diary_Food_ViewHolder holder = null;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_diary_food, null);

            holder = new Diary_Food_ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (Diary_Food_ViewHolder)convertView.getTag();
        }

        Diary_Food_Data diary_food_data = diary_food_datas.get(position);


        holder.diary_food_sort.setText(diary_food_data.mSort);
        holder.diary_food_foods.setText(diary_food_data.mFoods);
        holder.diary_food_memo.setText(diary_food_data.mMemo);

        //holder.diary_food_img
        //holder.diary_food_icon.setImageDrawable(diary_food_data.mIcon);



        return convertView;
    }


    public void additem(String mImg, String mSort, String mFoods, String mMemo, String mIcon){
        Diary_Food_Data diary_food_data = new Diary_Food_Data(mImg, mSort, mFoods,mMemo,mIcon);

        diary_food_datas.add(diary_food_data);
        notifyDataSetChanged();

    }

    public void ItemRemove() {
        diary_food_datas.clear();
        notifyDataSetChanged();

    }

}
