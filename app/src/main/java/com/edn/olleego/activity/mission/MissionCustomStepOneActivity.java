package com.edn.olleego.activity.mission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edn.olleego.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MissionCustomStepOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_custom_step_one);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.cancel_custom)
    void cancel_click() {
        finish();
    }
}
