package com.edn.olleego.activity.mission;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Adapter;
import com.edn.olleego.fragment.Mission.MissionDetailFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissionActivity extends AppCompatActivity {

    @BindView(R.id.mission_list) ListView mListView;


    private Mission_Adapter missionAdapter = null;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        ButterKnife.bind(this);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("미션리스트");

        missionAdapter = new Mission_Adapter(getLayoutInflater());
        mListView.setAdapter(missionAdapter);

        missionAdapter.addItem(R.drawable.kakao_default_profile_image,"쉽게 따라하는 초보용 미션 프로그램", "복근 , 하체 , 상체");

        missionAdapter.addItem(R.drawable.kakao_default_profile_image,"쉽게 따라하는 초보용 미션 프로그램", "복근 , 하체 , 상체");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragment = new MissionDetailFragment();
                MissionDetailFragment missionDetailFragment = new MissionDetailFragment();



                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.test1212, fragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

    }


    @Override
    public void onBackPressed() {



        super.onBackPressed();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
