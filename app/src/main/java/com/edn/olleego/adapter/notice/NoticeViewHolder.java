package com.edn.olleego.adapter.notice;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-08-12.
 */
public class NoticeViewHolder {

    @BindView(R.id.notice_all_layout)
    LinearLayout notice_all_layout;

    @BindView(R.id.notice_title)
    TextView notice_title;
    @BindView(R.id.notice_body)
    TextView notice_body;
    @BindView(R.id.notice_day)
    TextView notice_day;

    @BindView(R.id.notice_sun)
    View notice_sun;

    @BindView(R.id.notice_layout)
    LinearLayout notice_layout;

    @BindView(R.id.notice_img)
    ImageView notice_img;



    public NoticeViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
