package com.edn.olleego.activity.notice;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.notice.NoticeAdapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.NoticesModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.NoticesAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticeActivity extends AppCompatActivity {

    @BindView(R.id.notice_listview)
    ListView notice_listview;

    NoticeAdapter noticeAdapter;

    SharedPreferences olleego_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

        olleego_sp = getSharedPreferences("olleego", MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("공지사항");

        noticeAdapter = new NoticeAdapter(getLayoutInflater(),getApplicationContext());
        notice_listview.setOnItemClickListener(noticeAdapter);
        notice_listview.setAdapter(noticeAdapter);

        Retrofit retrofit_diary = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "olleego " + olleego_sp.getString("login_token", "");;
        NoticesAPI diaryAPI = retrofit_diary.create(NoticesAPI.class);

        final Call<NoticesModel> diaryPos = diaryAPI.listRepos(token);

        diaryPos.enqueue(new Callback<NoticesModel>() {
            @Override
            public void onResponse(Call<NoticesModel> call, Response<NoticesModel> response) {
                for(int i=0; i<response.body().getResult().size(); i++) {
                    noticeAdapter.add(response.body().getResult().get(i).getTitle(), response.body().getResult().get(i).getCreated(), response.body().getResult().get(i).getBody());
                }
            }

            @Override
            public void onFailure(Call<NoticesModel> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this) 를 사용할 경우 모든 Activity를 Destroy 시키고 부모 Activity로 돌아간다.
                // NavUtils.navigateUpFromSameTask(this);
                // finish() 를 사용할 경우 현재 Activity를 Destroy하고 부모 Activity로 돌아간다.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
