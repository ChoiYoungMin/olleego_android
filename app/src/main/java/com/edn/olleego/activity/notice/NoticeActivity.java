package com.edn.olleego.activity.notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.notice.NoticeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.notice_listview)
    ListView notice_listview;

    NoticeAdapter noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

        noticeAdapter = new NoticeAdapter(getLayoutInflater(),getApplicationContext());

        notice_listview.setAdapter(noticeAdapter);
        noticeAdapter.add("버전이 업데이트 되었습니다", "2016년 08월 02일", "버전이 업데이트 되었소");
        noticeAdapter.add("데이터 업데이트 되었습니다", "2016-08-03", "버전이 업데이트 되었소");
        noticeAdapter.add("버전이 업데이트 되었습니다", "2016년 08월 02일", "버전이 업데이트 되었소");
        noticeAdapter.add("데이터 업데이트 되었습니다", "2016-08-03", "버전이 업데이트 되었소");
    }
}
