package com.edn.olleego.activity.mission.missionlist;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.missionlist.MissionListAdapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.UserMissionModel;
import com.edn.olleego.server.UserMissionAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MissionListActivity extends AppCompatActivity {

    @BindView(R.id.mission_listviews)
    ListView mission_listviews;

    @BindView(R.id.mission_list_title)
    TextView mission_list_title;
    @BindView(R.id.mission_list_type)
    TextView mission_list_type;
    @BindView(R.id.mission_list_day)
    TextView mission_list_day;
    @BindView(R.id.mission_list_time)
    TextView mission_list_time;

    MissionListAdapter missionListAdapter;


    SharedPreferences olleego_SP;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("미션 전체 목록");


        missionListAdapter = new MissionListAdapter(getLayoutInflater(), getApplicationContext());

        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UserMissionAPI missionAPI = retrofit.create(UserMissionAPI.class);
        String token = "olleego " + olleego_SP.getString("login_token", "");

        Call<UserMissionModel> repos2 = missionAPI.listRepos(token);
        repos2.enqueue(new Callback<UserMissionModel>() {
            @Override
            public void onResponse(Call<UserMissionModel> call, Response<UserMissionModel> response) {
                if (response.isSuccessful()) {
                    UserMissionModel userMissionModel = response.body();

                    mission_list_title.setText(userMissionModel.getResult().getMission().getTitle());
                    mission_list_type.setText(userMissionModel.getResult().getMission().getMi_lg_sort().getValue()+"  |");
                    mission_list_day.setText(userMissionModel.getResult().getMission().getMi_term()+"주  |");
                    mission_list_time.setText(userMissionModel.getResult().getMission().getMi_days().get(0).getExgroup().get(0).getFreq().getValue()+" 운동");




                    mission_listviews.setAdapter(missionListAdapter);

                    for(int i=0; i<userMissionModel.getResult().getMission().getMi_days().size(); i++ ){
                        try {
                            missionListAdapter.add(String.valueOf(i + 1), userMissionModel.getResult().getMission().getMi_days().get(i).getLife().getTitle(), userMissionModel.getResult().getMission().getMi_days().get(i).getExgroup().get(0).getTitle(), userMissionModel.getResult().getMission().getMi_days().get(i).getFood().getTitle(),userMissionModel.getResult().getMission());
                        } catch (NullPointerException e ) {
                            missionListAdapter.add(String.valueOf(i + 1), "- 오늘의 생활습관 휴식 -", "- 오늘의 운동 휴식 -", "- 오늘은 식습관 휴식 -",userMissionModel.getResult().getMission() );

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<UserMissionModel> call, Throwable t) {

            }
        });
    }


    private void listViewHeightSet(Adapter listAdapter, ListView listView)
    {
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
