package com.edn.olleego.adapter.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_ViewHolder;

import java.util.ArrayList;

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

        holder.notice_title.setText(noticeData.notice_title);
        holder.notice_day.setText(noticeData.notice_day);
        holder.notice_body.setText(noticeData.notice_body);


        if(noticeData.getVisibility() == View.GONE) {
            holder.notice_body.setVisibility(View.GONE);
            holder.notice_sun.setVisibility(View.GONE);
            holder.notice_img.setImageResource(R.drawable.ic_keyboard_arrow_down);
        } else {
            holder.notice_body.setVisibility(View.VISIBLE);
            holder.notice_sun.setVisibility(View.VISIBLE);
            holder.notice_img.setImageResource(R.drawable.ic_keyboard_arrow_up);
        }



        return convertView;
    }


    public void add(String title, String day, String body) {
        NoticeData noticeData = new NoticeData(title,day,body,View.GONE);

        noticeDatas.add(noticeData);
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        noticeDatas.get(position).setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }
}
