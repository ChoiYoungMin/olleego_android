package com.edn.olleego.adapter.calendar.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.activity.diary.DiaryFoodActivity;
import com.edn.olleego.adapter.mission.Mission_Data;
import com.edn.olleego.adapter.mission.Mission_ViewHolder;
import com.edn.olleego.fragment.diary.Diary_Fragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-27.
 */
public class Diary_Food_Adapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private Context context = null;
    private ArrayList<Diary_Food_Data> diary_food_datas = new ArrayList<Diary_Food_Data>();
    Diary_Fragment fragment;
    SharedPreferences olleego_sp;
    List<String> foodlist;


    public Diary_Food_Adapter(LayoutInflater inflater, Context context, Diary_Fragment fragment) {
        super();
        this.inflater = inflater;
        this.context = context;
        this.fragment = fragment;
        this.olleego_sp = context.getSharedPreferences("olleego", Context.MODE_PRIVATE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        Diary_Food_ViewHolder holder = null;
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_diary_food, null);

            holder = new Diary_Food_ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (Diary_Food_ViewHolder)convertView.getTag();
        }

        Diary_Food_Data diary_food_data = diary_food_datas.get(position);


        Glide.with(context).load(diary_food_data.mImg).into(holder.diary_food_img);
        holder.diary_food_sort.setText(diary_food_data.mSort);
        holder.diary_food_foods.setText(diary_food_data.mFoods);
        holder.diary_food_memo.setText(diary_food_data.mMemo);

        switch (diary_food_data.mIcon) {
            case "부족해":
                holder.diary_food_icon.setImageResource(R.drawable.hungry_list);
                break;

            case "기분좋아":
                holder.diary_food_icon.setImageResource(R.drawable.happy_list);
                break;

            case "배불러요":
                holder.diary_food_icon.setImageResource(R.drawable.full_list);
                break;

            case "과식!":
                holder.diary_food_icon.setImageResource(R.drawable.overeat_list);
                break;
        }

        holder.diary_food_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.food_detail(diary_food_datas.get(position).mImg,diary_food_datas.get(position).mSort,diary_food_datas.get(position).mFoods,diary_food_datas.get(position).mMemo);


            }
        });

        holder.diary_food_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiaryFoodActivity.class);
                intent.putExtra("user", olleego_sp.getString("user_id", ""));
                SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                String mday = dateFormat.format(diary_food_datas.get(position).mDay);
                intent.putExtra("day", mday);
                intent.putExtra("token", olleego_sp.getString("login_token", ""));
                intent.putExtra("type", "diary");
                intent.putExtra("img_src", diary_food_datas.get(position).mImg);
                intent.putExtra("sort", diary_food_datas.get(position).mSort);
                intent.putExtra("foods", diary_food_datas.get(position).mFoods);
                intent.putExtra("memo", diary_food_datas.get(position).mMemo);
                intent.putExtra("icon", diary_food_datas.get(position).mIcon);
                intent.putExtra("foodlist", (Serializable) diary_food_datas.get(position).foodlist);

                ((MainActivity)context).startActivityForResult(intent, 0);

            }
        });
        //holder.diary_food_img
        //holder.diary_food_icon.setImageDrawable(diary_food_data.mIcon);



        return convertView;
    }


    public void additem(String mImg, String mSort, String mFoods, String mMemo, String mIcon, Date day, List<String> foodlist){

        Diary_Food_Data diary_food_data = new Diary_Food_Data(mImg, mSort, mFoods,mMemo,mIcon,day, foodlist);
        diary_food_datas.add(diary_food_data);
        notifyDataSetChanged();

    }

    public void ItemRemove() {
        diary_food_datas.clear();
        notifyDataSetChanged();

    }

}
