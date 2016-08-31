package com.edn.olleego.activity.mission.exmission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.adapter.mission.exmission.ExMission_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.LoadingBarDialog;
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
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    @BindView(R.id.ex_mission_ok)
    LinearLayout ex_mission_ok;

    @BindView(R.id.ex_mission_img)
    ImageView ex_mission_img;

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
    String exgroup_title;
    Ex exmodels;
    int gg = 3;
    List<ExList> img_src;

    String mission_ok;

    SharedPreferences olleego;

    boolean oncom =false;

    ArrayList<Integer> ex_id = new ArrayList<Integer>();

    MissionSuccessDialog missionSuccessDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_mission);

        ButterKnife.bind(this);

        olleego = getSharedPreferences("olleego", MODE_PRIVATE);

        intent = getIntent();

        su = intent.getIntExtra("step",0);
        exmodel = (Ex) intent.getSerializableExtra("ex"+su);
        size = intent.getIntExtra("size",0);
        tokens = intent.getStringExtra("token");
        mission_id = intent.getIntExtra("mission_id", 0);
        mission_today = intent.getIntExtra("mission_today", 0);
        exgroup_id = intent.getIntExtra("exgroup_id", 0);
        exgroup_title = intent.getStringExtra("exgroup_title");
        mission_ok = intent.getStringExtra("mission_ok");


        ex_mission_step.setText((su+1)+"-"+size);
        ex_mission_title.setText(exmodel.getTitle());
        ex_mission_method.setText(exmodel.getMethod());
        ex_mission_warning.setText(exmodel.getWarning());





        if(mission_ok.equals("true")) {
            ex_mission_ok.setVisibility(View.VISIBLE);
        }



        ExMission_Adapter exMission_adapter = new ExMission_Adapter(getLayoutInflater(), this);
        list_View.setAdapter(exMission_adapter);

        for(int i=0; i< size; i++ ) {
            exmodels = (Ex) intent.getSerializableExtra("ex"+i);
            ex_id.add(exmodels.get_id());
            exMission_adapter.add(exmodels.getThum_jpg(), exmodels.getTitle(), exmodels.getWarning(),su);
        }

        exmodels = (Ex) intent.getSerializableExtra("ex"+su);
        Glide.with(this).load(exmodels.getThum_gif()).into(ex_mission_img);



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

            final MissionSuccess missionSuccess = new MissionSuccess(mission_today,ex_id.get(su),exgroup_id, "exgroup");

            String token = "olleego " + tokens;
            MissionSuccessAPI missionSuccessAPI = retrofit_diary.create(MissionSuccessAPI.class);

            final Call<MissionsModel> diaryPos = missionSuccessAPI.listRepos( token, olleego.getInt("user_mission_id",0),"ex", missionSuccess);


            diaryPos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                    if(olleego.getString("user_mission_today_exgroup_complete","").equals("true")) {
                        LoadingBarDialog loadingBarDialog = new LoadingBarDialog(ExMissionActivity.this, "food_add");
                        loadingBarDialog.show();
                        Toast.makeText(getApplicationContext(), "운동끝!! 이미 포인트 받음" ,Toast.LENGTH_SHORT).show();
                        oncom=true;
                        setResult(1);
                        finish();

                    } else {

                        missionSuccessDialog = new MissionSuccessDialog(ExMissionActivity.this, exgroup_title, 1);
                        missionSuccessDialog.show();
                        mHandler2.sendEmptyMessage(0);
                    }
                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });



        }
        else {



            Retrofit retrofit_diary = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final MissionSuccess missionSuccess = new MissionSuccess(mission_today,ex_id.get(su),exgroup_id, "exgroup");

            String token = "olleego " + tokens;
            MissionSuccessAPI missionSuccessAPI = retrofit_diary.create(MissionSuccessAPI.class);

            final Call<MissionsModel> diaryPos = missionSuccessAPI.listRepos( token, olleego.getInt("user_mission_id",0),"ex", missionSuccess);

            diaryPos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                    mHandler.removeMessages(0);
                    Intent intent2 = new Intent(getApplicationContext(), ExMissionActivity.class);
                    for (int i = 0; i < size; i++) {
                        intent2.putExtra("ex" + i, (Serializable) intent.getSerializableExtra("ex" + i));
                    }
                    intent2.putExtra("step", su + 1);
                    intent2.putExtra("size", size);
                    intent2.putExtra("token", tokens);
                    intent2.putExtra("mission_id", mission_id);
                    intent2.putExtra("mission_today", mission_today);
                    intent2.putExtra("exgroup_id", exgroup_id);
                    intent2.putExtra("mission_ok", mission_ok);



                    setResult(1);
                    startActivity(intent2);
                    finish();
                    //피니쉬떄문에 종료후 새로고침 인식못함
                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });


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
                if(oncom==false) {
                    missionSuccessDialog.dismiss();

                }
                mHandler2.removeMessages(0);
                setResult(1);
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
        finish();
    }


}
