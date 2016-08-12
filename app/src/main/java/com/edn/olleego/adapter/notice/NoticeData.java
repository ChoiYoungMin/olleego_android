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


    public NoticeData(String notice_title, String notice_day, String notice_body) {
        this.notice_title = notice_title;
        this.notice_day = notice_day;
        this.notice_body = notice_body;
    }

}
