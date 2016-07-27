package com.edn.olleego.adapter.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.model.CalendarModel;
import com.edn.olleego.model.DiaryMonthModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-06-17.
 */

public class Calender_Adapter extends BaseAdapter {


    private final List<String> list;


    private final LayoutInflater inflater;

    private String type;
    private int num;

    private Calendar mCal;

    DiaryMonthModel calendar_;

    String day;

    boolean food;
    /**

     * 생성자

     *

     * @param context

     * @param list

     */

    public Calender_Adapter(Context context, List<String> list, String type, int num, DiaryMonthModel calendar_, String day) {

        this.list = list;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (type.equals("1")) {
            this.type = "no";
        } else {
            this.type = "null";
        }

        this.num = num;

        this.calendar_ = calendar_;

        this.day = day;

    }



    @Override

    public int getCount() {

        return list.size();

    }



    @Override

    public String getItem(int position) {

        return list.get(position);

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        food = false;
        Calender_ViewHolder holder = null;

        holder = new Calender_ViewHolder();

        if (convertView == null) {

            if (type.equals("no")) {
                convertView = inflater.inflate(R.layout.item_calender2, parent, false);
            }
            else {
                convertView = inflater.inflate(R.layout.item_calender, parent, false);
            }








            holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);
            holder.calender_walking = (ImageView) convertView.findViewById(R.id.calender_walking);
            holder.calender_sleep = (ImageView) convertView.findViewById(R.id.calender_sleep);
            holder.calender_water = (ImageView) convertView.findViewById(R.id.calender_water);
            holder.calender_food = (ImageView) convertView.findViewById(R.id.calender_food);

            convertView.setTag(holder);

        } else {

            holder = (Calender_ViewHolder)convertView.getTag();

        }
        holder.tvItemGridView.setText("" + getItem(position));

        String su = null;
        if(type.equals("null")) {
            if(getItem(position).equals("")) {
                su = day + getItem(position);
            }
            else if (Integer.parseInt(getItem(position)) < 10) {
                su = day + "0" + getItem(position);
            } else {
                su = day + getItem(position);
            }
        }

        /*
            for(int j=0; j < calendar_.size(); j++){

                if(calendar_.get(j).getDAY().equals(su)) {


                    food = true;
                } else {


                }

            }
*/





        //해당 날짜 텍스트 컬러,배경 변경

        mCal = Calendar.getInstance();

        //오늘 day 가져옴

        Integer today = mCal.get(Calendar.DAY_OF_MONTH);

        String sToday = String.valueOf(today);





        if(position < num-1) {

        }
        else {

            try {
                for (int i = 0; i < calendar_.getResult().size(); i++) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
                    String daya = null;

                    try {
                        if (Integer.valueOf(getItem(position)) < 10) {
                            daya = day + "0" + getItem(position);

                        } else {
                            daya = day + getItem(position);

                        }
                    } catch (NumberFormatException e) {

                    }

                    if (dateFormat.format(calendar_.getResult().get(i).getDay()).equals(daya)) {


                        String strColor = "#000000";
                        holder.tvItemGridView.setTextColor(Color.parseColor(strColor));


                        try {
                            if (calendar_.getResult().get(i).getWalking() != 0) {
                                holder.calender_walking.setImageResource(R.drawable.calendar_step);
                            }
                        } catch (NullPointerException e) {

                        }

                        try {
                            if (calendar_.getResult().get(i).getWater() != 0) {
                                holder.calender_water.setImageResource(R.drawable.calendar_water);
                            }
                        } catch (NullPointerException e) {

                        }

                        try {
                            if (calendar_.getResult().get(i).getSleep() != 0) {
                                holder.calender_sleep.setImageResource(R.drawable.calendar_sleep);
                            }
                        } catch (NullPointerException e) {

                        }


                        try {
                            if (calendar_.getResult().get(i).getFood().size() != 0) {
                                holder.calender_food.setImageResource(R.drawable.calendar_meal);
                            }
                        } catch (NullPointerException e) {

                        }


                        if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경

                            String strColors = "#0015ff";
                            holder.tvItemGridView.setTextColor(Color.parseColor(strColors));
                        }

                    }
                }
            } catch (NullPointerException e) {

            }
        }


        return convertView;

    }

}


