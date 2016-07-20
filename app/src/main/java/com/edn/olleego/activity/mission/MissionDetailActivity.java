package com.edn.olleego.activity.mission;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.edn.olleego.adapter.mission.Mission_Detail_Img_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.MissionStartDialog;
import com.edn.olleego.model.AllMissionModel;

import butterknife.BindView;
import butterknife.ButterKnife;
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



    AllMissionModel allMissionModel;

    String mission_ids;
    Mission_Detail_Img_Adapter mission_detail_img_adapter;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);
        ButterKnife.bind(this);

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

        findViewById(R.id.mission_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MissionStartDialog dialog = new MissionStartDialog(MissionDetailActivity.this);

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