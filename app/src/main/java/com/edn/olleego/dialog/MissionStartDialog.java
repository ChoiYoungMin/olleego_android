package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.ChoiceGetMissionAPI;
import com.edn.olleego.server.ChoicePutMissionAPI;
import com.edn.olleego.server.request.ChoiceMission;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Antonio on 2016-07-20.
 */
public class MissionStartDialog extends Dialog {

    String title;
    int time;
    Boolean type;
    String token;
    int user, mission;
    int day;
    int end_day;
    int mission_id;

    @BindView(R.id.missionstart_mission_title)
    TextView mission_title;

    @BindView(R.id.missionstart_mission_time)
    TextView mission_time;
    SharedPreferences olleego_SP;

    public MissionStartDialog(Context context, String title, int time, Boolean type, String token, int user, int mission, int day, int mission_id) {
        super(context);
        this.title = title;
        this.time = time;
        this.type = type;
        this.token=  token;
        this.user= user;
        this.mission = mission;
        this.day= day;
        this.mission_id=  mission_id;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_missionstart);
        ButterKnife.bind(this);


        olleego_SP = getContext().getSharedPreferences("olleego", getContext().MODE_PRIVATE);



        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);

        mission_title.setText(title);
        mission_time.setText(String.valueOf(time)+"분 ]");





    }

    @OnClick(R.id.missionstart_mission_no)
    void mission_no() {
        Toast.makeText(getContext(), "취소잼!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @OnClick(R.id.missionstart_mission_start)
    void mission_stert() {
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        Date date = new Date();
        switch(day) {
            case 1:
                end_day = 7;
                break;
            case 2:
                end_day = 14;
                break;
            case 3:
                end_day = 21;
                break;
            case 4:
                end_day = 28;
                break;
            case 5:
                end_day = 35;
                break;
            case 6:
                end_day = 42;
                break;

        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, end_day);

        Date endd = cal.getTime();
        if(type == true) { //PUT

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ChoicePutMissionAPI PutMissionAPI = retrofit.create(ChoicePutMissionAPI.class);



            ChoiceMission newMission = new ChoiceMission(user, mission,time,date,endd,date);
            final Call<MissionsModel> repos = PutMissionAPI.listRepos("olleego "+ token,newMission,mission_id);

            repos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                    Toast.makeText(getContext(), "미션 변경 완료", Toast.LENGTH_SHORT).show();


                    SharedPreferences.Editor editor = olleego_SP.edit();
                    editor.putString("user_mission_today_onoff", "on");
                    editor.commit();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });
        } else { // POST




            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ChoiceGetMissionAPI newMissionAPI = retrofit.create(ChoiceGetMissionAPI.class);



            ChoiceMission newMission = new ChoiceMission(user, mission,time,date,endd,date);
            final Call<MissionsModel> repos = newMissionAPI.listRepos("olleego "+ token,newMission);

            repos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                    Toast.makeText(getContext(), "미션이 추가 완료", Toast.LENGTH_SHORT).show();


                    SharedPreferences.Editor editor = olleego_SP.edit();
                    editor.putString("user_mission_today_onoff", "on");
                    editor.putString("user_mission_today_rest", "false");

                    editor.commit();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);


                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });
        }
        dismiss();
    }

}
