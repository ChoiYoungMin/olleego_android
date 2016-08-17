package com.edn.olleego.adapter.notice;

import android.view.View;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;

/**
 * Created by Antonio on 2016-08-12.
 */
public class NoticeData {
    String notice_title;
    String notice_body;
    String notice_day;
    int visibility;


    public NoticeData(String notice_title, String notice_day, String notice_body, int visibility) {
        this.notice_title = notice_title;
        this.notice_day = notice_day;
        this.notice_body = notice_body;
        this.visibility = visibility;

    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }

}
