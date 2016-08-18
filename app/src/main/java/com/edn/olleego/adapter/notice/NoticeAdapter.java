package com.edn.olleego.adapter.notice;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Antonio on 2016-08-12.
 */
public class NoticeAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    LayoutInflater inflater;
    Context context;
    NoticeViewHolder holder;
    ArrayList<NoticeData> noticeDatas = new ArrayList<NoticeData>();


    public NoticeAdapter(LayoutInflater inflater, Context context){
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return noticeDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.item_notice_list, null);

            holder = new NoticeViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder = (NoticeViewHolder)convertView.getTag();
        }


        NoticeData noticeData = noticeDatas.get(position);

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault());

        holder.notice_title.setText(noticeData.notice_title);
        holder.notice_day.setText(dateFormat.format(noticeData.notice_day));
        holder.notice_body.setText(noticeData.notice_body);


        if(noticeData.getVisibility() == View.GONE) {
            holder.notice_body.setVisibility(View.GONE);
            holder.notice_sun.setVisibility(View.GONE);
            holder.notice_img.setImageResource(R.drawable.ic_keyboard_arrow_down);

            holder.notice_all_layout.setBackgroundColor(Color.parseColor("#f0f1ed"));
        } else {
            holder.notice_body.setVisibility(View.VISIBLE);
            holder.notice_sun.setVisibility(View.VISIBLE);
            holder.notice_img.setImageResource(R.drawable.ic_keyboard_arrow_up);
            holder.notice_all_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }



        return convertView;
    }


    public void add(String title, Date day, String body) {
        NoticeData noticeData = new NoticeData(title,day,body,View.GONE);

        noticeDatas.add(noticeData);
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(noticeDatas.get(position).getVisibility() == View.GONE) {

            noticeDatas.get(position).setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        } else if(noticeDatas.get(position).getVisibility() == View.VISIBLE) {

            noticeDatas.get(position).setVisibility(View.GONE);
            notifyDataSetChanged();
        }
    }
}
