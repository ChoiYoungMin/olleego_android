package com.edn.olleego.activity.mission.exmission;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.exmission.ExMission_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.MissionStartDialog;
import com.edn.olleego.dialog.MissionSuccessDialog;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.model.exgroups.Ex;
import com.edn.olleego.model.exgroups.ExList;
import com.edn.olleego.model.exgroups.Result;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.MissionSuccessAPI;
import com.edn.olleego.server.request.MissionSuccess;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExMissionActivity extends AppCompatActivity {

    @BindView(R.id.ex_mission_listview)
    ListView list_View;

    @BindView(R.id.ex_mission_step)
    TextView ex_mission_step;

    @BindView(R.id.ex_mission_title)
    TextView ex_mission_title;

    @BindView(R.id.ex_mission_method)
    TextView ex_mission_method;

    @BindView(R.id.ex_mission_warning)
    TextView ex_mission_warning;

    @BindView(R.id.ex_mission_time)
    TextView ex_mission_time;

    int su;
    int size;
    int value= 301;
    int value2 = 1;
    String time;

    Ex exmodel;

    Intent intent;

    String tokens;

    int mission_id;
    int mission_today;
    int exgroup_id;

    int gg = 3;
    MissionSuccessDialog missionSuccessDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_mission);

        ButterKnife.bind(this);

        intent = getIntent();

        su = intent.getIntExtra("step",0);
        exmodel = (Ex) intent.getSerializableExtra("ex"+su);
        size = intent.getIntExtra("size",0);
        tokens = intent.getStringExtra("token");
        mission_id = intent.getIntExtra("mission_id", 0);
        mission_today = intent.getIntExtra("mission_today", 0);
        exgroup_id = intent.getIntExtra("exgroup_id", 0);


            ex_mission_step.setText((su+1)+"-"+size);
            ex_mission_title.setText(exmodel.getTitle());
            ex_mission_method.setText(exmodel.getMethod());
            ex_mission_warning.setText(exmodel.getWarning());









        ExMission_Adapter exMission_adapter = new ExMission_Adapter(getLayoutInflater());
        list_View.setAdapter(exMission_adapter);

        for(int i=0; i< size; i++ ) {
            Ex exmodels = (Ex) intent.getSerializableExtra("ex"+i);
            exMission_adapter.add(exmodels.getMovie_url(), exmodels.getTitle(), exmodels.getWarning(),su);
        }

        listViewHeightSet(exMission_adapter, list_View);


        mHandler.sendEmptyMessage(0);
    }

    @OnClick(R.id.testbtn)
    void testbtn_click() {
        if(su == size-1) {

            mHandler.removeMessages(0);

            Retrofit retrofit_diary = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final MissionSuccess missionSuccess = new MissionSuccess(mission_today,exgroup_id, 1);

            String token = "ollego " + tokens;
            MissionSuccessAPI missionSuccessAPI = retrofit_diary.create(MissionSuccessAPI.class);

            final Call<MissionsModel> diaryPos = missionSuccessAPI.listRepos( token,mission_id, missionSuccess);

            diaryPos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                    missionSuccessDialog = new MissionSuccessDialog(ExMissionActivity.this);
                    missionSuccessDialog.show();
                    mHandler2.sendEmptyMessage(0);
                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });



        }
        else {

            mHandler.removeMessages(0);
            Intent intent2 = new Intent(getApplicationContext(), ExMissionActivity.class);
            for (int i = 0; i < size; i++) {
                intent.putExtra("ex" + i, (Serializable) intent.getSerializableExtra("ex" + i));
            }
            intent.putExtra("step", su + 1);
            intent.putExtra("size", size);
            intent.putExtra("token", tokens);
            intent.putExtra("mission_id", mission_id);
            intent.putExtra("mission_today", mission_today);
            intent.putExtra("exgroup_id", exgroup_id);
            startActivity(intent);
            finish();
        }
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

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            value--;
            value2--;
            if (value2 <10) {
                time ="0"+String.valueOf(value2);
            } else {
                time = String.valueOf(value2);
            }
            ex_mission_time.setText("0"+String.valueOf(value % 3600 / 60)+":"+time);
            if(value == 0) testbtn_click();
            if(value2==0) value2 = 60;



            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };

    Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            if(gg ==0){
                missionSuccessDialog.dismiss();
                mHandler2.removeMessages(0);
                finish();
            } else {
                gg--;
            }




            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler2.sendEmptyMessageDelayed(0,1000);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeMessages(0);
    }
}
