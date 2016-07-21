package com.edn.olleego.activity.mission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Data;
import com.edn.olleego.adapter.mission.Mission_Detail_Img_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.MissionStartDialog;
import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.model.ExdetailModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.SelectMissionModel;
import com.edn.olleego.server.ExdetailAPI;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.SelectMissionAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MissionDetailActivity extends AppCompatActivity {

    @BindView(R.id.mission_detail_listview)
    ListView listView;

    @BindView(R.id.mission_detail_type_img)
    ImageView type_img;

    @BindView(R.id.mission_detail_type_name)
    TextView type_name;

    @BindView(R.id.mission_detail_title)
    TextView mission_title;

    @BindView(R.id.mission_detail_rating)
    RatingBar mission_rating;

    @BindView(R.id.mission_detail_rating_peple)
    TextView mission_rating_people;

    @BindView(R.id.mission_detail_peple)
    TextView mission_people;

    @BindView(R.id.mission_detail_level)
    TextView mission_detail_level;

    @BindView(R.id.mission_detail_day)
    TextView mission_detail_day;

    @BindView(R.id.mission_detail_su)
    TextView mission_detail_su;

    @BindView(R.id.mission_detail_description)
    TextView mission_detail_description;

    @BindView(R.id.mission_title_img)
    ImageView mission_title_img;

    @BindView(R.id.mission_detail_list)
    TextView mission_detail_list;

    @BindView(R.id.mission_detail_list_su)
    TextView mission_detail_list_su;

    String title;

    AllMissionModel allMissionModel;

    String mission_ids;
    Mission_Detail_Img_Adapter mission_detail_img_adapter;

    Intent intent;

    SelectMissionModel selectMissionModel;
    int times;

    private ArrayList<String> ex_lists = new ArrayList<String>();
    private ArrayList<Integer> time = new ArrayList<Integer>();
    private ArrayList<Integer> ex_list = new ArrayList<Integer>();

    String ex_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);
        ButterKnife.bind(this);


        time.add(0, R.id.mission_detail_time_one);
        time.add(1, R.id.mission_detail_time_two);
        time.add(2, R.id.mission_detail_time_three);
        time.add(3, R.id.mission_detail_time_four);
        time.add(4, R.id.mission_detail_time_five);
        time.add(5, R.id.mission_detail_time_six);
        times = 10;

        Intent intent = getIntent();
        mission_ids = String.valueOf(intent.getIntExtra("mission_id", 0));

        mission_detail_img_adapter = new Mission_Detail_Img_Adapter(getLayoutInflater(), getApplicationContext());
        listView.setAdapter(mission_detail_img_adapter);


        intent = getIntent();
        Glide.with(getApplicationContext()).load(intent.getStringExtra("mission_type_img"))
                .into(type_img);
        Glide.with(getApplicationContext()).load(ServerInfo.OLLEEGO_IMAGE+intent.getStringExtra("mission_title_img"))
                .into(mission_title_img);

        mission_title_img.setScaleType(ImageView.ScaleType.FIT_XY);

        type_name.setText(intent.getStringExtra("mission_type_name"));
        mission_title.setText(intent.getStringExtra("mission_title"));
        mission_detail_level.setText(intent.getStringExtra("mission_level"));
        mission_detail_day.setText(intent.getStringExtra("mission_day")+"주");
        //ission_detail_su.setText(); 주 몇회?
        mission_detail_description.setText(intent.getStringExtra("mission_description"));

        for(int i=0; i< intent.getIntExtra("mission_img_size",0); i++) {
            mission_detail_img_adapter.addItem(ServerInfo.OLLEEGO_IMAGE+intent.getStringExtra("mission_img"+i));

        }

        if(intent.getStringExtra("mission_type_name").equals("다이어트")) {
            Glide.with(getApplicationContext()).load(R.drawable.diet)
                    .into(type_img);
        } else if(intent.getStringExtra("mission_type_name").equals("필라테스")) {
            Glide.with(getApplicationContext()).load(R.drawable.pilates)
                    .into(type_img);
        } else if(intent.getStringExtra("mission_type_name").equals("관강관리")) {
            Glide.with(getApplicationContext()).load(R.drawable.health_managing)
                    .into(type_img);
        }

        listViewHeightSet(mission_detail_img_adapter, listView);

        title = intent.getStringExtra("mission_title");



        init_list();



    }

    public void selectEx(final int i) {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ExdetailAPI exdetailAPI = retrofit2.create(ExdetailAPI.class);

        //for(int i=0; i<ex_list.size(); i++) {

        try {
            final Call<ExdetailModel> repos2 = exdetailAPI.listRepos(ex_list.get(i));
            final int finalI = i;
            repos2.enqueue(new Callback<ExdetailModel>() {
                @Override
                public void onResponse(Call<ExdetailModel> call, Response<ExdetailModel> response) {
                    ex_lists.add(finalI, response.body().getResult().getTitle());
                    if (i + 1 == ex_list.size()) {

                        mission_detail_list.setText("");
                        for (int g = 0; g < ex_lists.size(); g++) {
                            ex_text = ex_text + "\n" + ex_lists.get(g);
                        }

                        mission_detail_list.setText(ex_text);
                        ex_text = "";

                    } else {
                        selectEx(i + 1);
                    }
                }

                @Override
                public void onFailure(Call<ExdetailModel> call, Throwable t) {

                }


            });
        } catch (IndexOutOfBoundsException e ) {

        }
       // }
    }

    public void init_list() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        SelectMissionAPI exgroupsAPI = retrofit.create(SelectMissionAPI.class);
        final Call<SelectMissionModel> repos2 = exgroupsAPI.listRepos(mission_ids);

        repos2.enqueue(new Callback<SelectMissionModel>() {
            @Override
            public void onResponse(Call<SelectMissionModel> call, Response<SelectMissionModel> response) {
                selectMissionModel = response.body();

                list_go(0);
                ex_lists.clear();
                selectEx(0);
            }

            @Override
            public void onFailure(Call<SelectMissionModel> call, Throwable t) {

            }
        });


    }


    @OnClick(R.id.mission_start)
    void start_click() {
        Boolean type = null;

        SharedPreferences olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        if(olleego_SP.getString("user_mission_today_onoff", "").equals("on")){
            type = true;
        } else if(olleego_SP.getString("user_mission_today_onoff", "").equals("off")) {
            type = false;
        }
            Intent intent = getIntent();
            final MissionStartDialog dialog = new MissionStartDialog(MissionDetailActivity.this, title ,times, type, olleego_SP.getString("login_token", ""),Integer.valueOf(olleego_SP.getString("user_id", "")), Integer.valueOf(mission_ids),  Integer.valueOf(intent.getStringExtra("mission_day")));

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dia) {

                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dia) {
                }
            });

            dialog.show();
    }

    public void list_go(int i) {
        ex_list.clear();
        for(int j=0; j<selectMissionModel.getResult().getMi_days().get(0).getExgroup().size(); j++) {
            if(times == selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getTime()) {
                for(int k=0; k<selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getEx_list().size(); k++) {
                    ex_list.add(k, selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getEx_list().get(k).getEx());
                }

            }
        }
    }

    @OnClick(R.id.mission_detail_time_one)
    void time_one_click() {
        times = 10;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_one) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(0);

        ex_lists.clear();
        selectEx(0);
         // 운동

    }

    @OnClick(R.id.mission_detail_time_two)
    void time_two_click() {
        times = 20;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_two) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(1);
        ex_lists.clear();
        selectEx(0);
    }

    @OnClick(R.id.mission_detail_time_three)
    void time_three_click() {
        times = 30;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_three) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(2);
        ex_lists.clear();
        selectEx(0);
    }

    @OnClick(R.id.mission_detail_time_four)
    void time_four_click() {
        times = 40;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_four) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(3);
        ex_lists.clear();
        selectEx(0);
    }

    @OnClick(R.id.mission_detail_time_five)
    void time_five_click() {
        times = 50;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_five) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(4);
        ex_lists.clear();
        selectEx(0);
    }

    @OnClick(R.id.mission_detail_time_six)
    void time_six_click() {
        times = 60;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_six) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        list_go(5);
        ex_lists.clear();
        selectEx(0);
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




/*


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

  SelectMissionAPI allMissionAPI2 = retrofit.create(SelectMissionAPI.class);
        final Call<SelectMission_Model> lifePos2 = allMissionAPI2.listRepos("", Integer.parseInt(mission_ids));
        lifePos2.enqueue(new Callback<SelectMission_Model>() {
            @Override
            public void onResponse(Call<SelectMission_Model> call, Response<SelectMission_Model> response) {
                for (int i=0; i < response.body().getResult().getDescription_img().size(); i++) {

                    mission_detail_img_adapter.addItem(response.body().getResult().getDescription_img().get(i).toString());

                }

            }

            @Override
            public void onFailure(Call<SelectMission_Model> call, Throwable t) {
                Log.e("z", t.getLocalizedMessage());
            }
        });

 */