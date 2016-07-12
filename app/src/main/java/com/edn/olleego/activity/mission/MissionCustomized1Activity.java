package com.edn.olleego.activity.mission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edn.olleego.R;

public class MissionCustomized1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_customized1);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
