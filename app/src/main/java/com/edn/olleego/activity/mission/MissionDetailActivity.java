package com.edn.olleego.activity.mission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.adapter.mission.Mission_Data;
import com.edn.olleego.adapter.mission.Mission_Detail_Img_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.dialog.MissionStartDialog;
import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.model.ExdetailModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.SelectMissionModel;
import com.edn.olleego.server.ExdetailAPI;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.MissionAPI;
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

    LoadingBarDialog loadingBarDialog;
    int times;

    int types;

    private ArrayList<String> ex_lists = new ArrayList<String>();
    private ArrayList<Integer> time = new ArrayList<Integer>();
    private ArrayList<Integer> ex_list = new ArrayList<Integer>();

    String ex_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);
        ButterKnife.bind(this);

        loadingBarDialog = new LoadingBarDialog(this);

        time.add(0, R.id.mission_detail_time_one);
        time.add(1, R.id.mission_detail_time_two);
        time.add(2, R.id.mission_detail_time_three);
        time.add(3, R.id.mission_detail_time_four);
        time.add(4, R.id.mission_detail_time_five);
        time.add(5, R.id.mission_detail_time_six);
        times = 10;


        mission_detail_img_adapter = new Mission_Detail_Img_Adapter(getLayoutInflater(), getApplicationContext());
        listView.setAdapter(mission_detail_img_adapter);


        Intent intent = getIntent();
        mission_ids = String.valueOf(intent.getIntExtra("mission_id", 0));

        types = intent.getIntExtra("mission_type", 0);

        if(types == 1) {
            Glide.with(getApplicationContext()).load(intent.getStringExtra("mission_type_img"))
                    .into(type_img);
            Glide.with(getApplicationContext()).load(intent.getStringExtra("mission_title_img"))
                    .into(mission_title_img);

            mission_title_img.setScaleType(ImageView.ScaleType.FIT_XY);

            type_name.setText(intent.getStringExtra("mission_type_name"));
            mission_title.setText(intent.getStringExtra("mission_title"));
            mission_detail_level.setText(intent.getStringExtra("mission_level"));
            mission_detail_day.setText(intent.getStringExtra("mission_day")+"주");
            //ission_detail_su.setText(); 주 몇회?
            mission_detail_description.setText(Html.fromHtml(intent.getStringExtra("mission_description")));


             mission_rating.setRating(intent.getIntExtra("mission_rating", 0));

             mission_rating_people.setText("("+intent.getIntExtra("mission_rating_people", 0)+")");

             mission_people.setText(intent.getIntExtra("mission_people", 0) +" 명이 이 미션을 수행하였습니다.");





            for(int i=0; i< intent.getIntExtra("mission_img_size",0); i++) {
                mission_detail_img_adapter.addItem(intent.getStringExtra("mission_img"+i));

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


            title = intent.getStringExtra("mission_title");
        } else {

        }




        init_list();

        listViewHeightSet(mission_detail_img_adapter, listView);


    }


    public void selectExgroups(final int i) {

        mission_detail_list.setText("");
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ExgroupsAPI exgroupsModel = retrofit2.create(ExgroupsAPI.class);
        final Call<ExgroupsModel> repos2 = exgroupsModel.listRepos(i);
        repos2.enqueue(new Callback<ExgroupsModel>() {
            @Override
            public void onResponse(Call<ExgroupsModel> call, Response<ExgroupsModel> response) {

                    for (int g = 0; g < response.body().getResult().getEx_list().size(); g++) {
                        ex_text = ex_text + "\n" + response.body().getResult().getEx_list().get(g).getEx().getTitle();
                    }

                    mission_detail_list.setText(ex_text);
                ex_text="";

                loadingBarDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ExgroupsModel> call, Throwable t) {

            }
        });


        //for(int i=0; i<ex_list.size(); i++) {

        /*
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
                    loadingBarDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ExdetailModel> call, Throwable t) {

                }


            });
        } catch (IndexOutOfBoundsException e ) {

        }
       // }
       */
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
                if(types == 2) {
                    //mission_detail_level.setText(intent.getStringExtra("mission_level"));
                    //type_name.setText(intent.getStringExtra("mission_type_name"));
                    //Glide.with(getApplicationContext()).load(intent.getStringExtra("mission_type_img"); ).into(type_img);
                    //mission_detail_day.setText(intent.getStringExtra("mission_day")+"주");
                    /*
                if(intent.getStringExtra("mission_type_name").equals("다이어트")) {
                    Glide.with(getApplicationContext()).load(R.drawable.diet)
                            .into(type_img);
                } else if(intent.getStringExtra("mission_type_name").equals("필라테스")) {
                    Glide.with(getApplicationContext()).load(R.drawable.pilates)
                            .into(type_img);
                } else if(intent.getStringExtra("mission_type_name").equals("건강관리")) {
                    Glide.with(getApplicationContext()).load(R.drawable.health_managing)
                            .into(type_img);
                }*/




                    Glide.with(getApplicationContext()).load(response.body().getResult().getTitle_img())
                            .into(mission_title_img);

                    mission_title_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    mission_title.setText(response.body().getResult().getTitle());

                    //ission_detail_su.setText(); 주 몇회?
                    mission_detail_description.setText(Html.fromHtml(response.body().getResult().getDescription1()));

                    for(int i=0; i< response.body().getResult().getDescription_img().size(); i++) {
                        mission_detail_img_adapter.addItem(response.body().getResult().getDescription_img().get(i));

                    }



                    title = response.body().getResult().getTitle();
                }



                selectMissionModel = response.body();



                selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(0).get_id());
            }

            @Override
            public void onFailure(Call<SelectMissionModel> call, Throwable t) {

            }
        });


    }


    @OnClick(R.id.mission_start)
    void start_click() {
        boolean type = false;

        SharedPreferences olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        if(olleego_SP.getString("user_mission_today_onoff", "").equals("on")){
            type = true;
        } else if(olleego_SP.getString("user_mission_today_onoff", "").equals("off")) {
            type = false;
        }
        int mission_id = 0;
        try {
             mission_id = olleego_SP.getInt("user_mission_id", 0);

        }catch (NullPointerException e) {
             mission_id= 0;
        }
            Intent intent = getIntent();
            final MissionStartDialog dialog = new MissionStartDialog(MissionDetailActivity.this, title ,times, type, olleego_SP.getString("login_token", ""),Integer.valueOf(olleego_SP.getString("user_id", "")), Integer.valueOf(mission_ids),  Integer.valueOf(intent.getStringExtra("mission_day")),mission_id);

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dia) {

                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dia) {
                    boolean end = dialog.getEnd();
                    if(end == true) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            });

            dialog.show();
    }

    public void list_go(int i) {
        /*
        ex_list.clear();
        for(int j=0; j<selectMissionModel.getResult().getMi_days().get(0).getExgroup().size(); j++) {
            if(times == selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getTime()) {
                for(int k=0; k<selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getEx_list().size(); k++) {
                    ex_list.add(k, selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(j).getEx_list().get(k).getEx());
                }

            }
        }
        */
    }

    @OnClick(R.id.mission_detail_time_one)
    void time_one_click() {
        loadingBarDialog.show();
        times = 10;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_one) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(0).get_id());



    }

    @OnClick(R.id.mission_detail_time_two)
    void time_two_click() {
        loadingBarDialog.show();

        times = 20;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_two) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }
        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(1).get_id());

    }

    @OnClick(R.id.mission_detail_time_three)
    void time_three_click() {
        loadingBarDialog.show();

        times = 30;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_three) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }
        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(2).get_id());

    }

    @OnClick(R.id.mission_detail_time_four)
    void time_four_click() {
        loadingBarDialog.show();

        times = 40;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_four) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }
        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(3).get_id());

    }

    @OnClick(R.id.mission_detail_time_five)
    void time_five_click() {
        loadingBarDialog.show();

        times = 50;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_five) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }
        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(4).get_id());

    }

    @OnClick(R.id.mission_detail_time_six)
    void time_six_click() {
        loadingBarDialog.show();

        times = 60;
        for(int i=0; i<6; i++) {
            findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_no);

            if(time.get(i) == R.id.mission_detail_time_six) {
                findViewById(time.get(i)).setBackgroundResource(R.drawable.mission_detail_radius);
            }
        }

        selectExgroups(selectMissionModel.getResult().getMi_days().get(0).getExgroup().get(5).get_id());

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