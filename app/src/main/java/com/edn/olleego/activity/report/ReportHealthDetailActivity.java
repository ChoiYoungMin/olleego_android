package com.edn.olleego.activity.report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportHealthDetailActivity extends AppCompatActivity {

    @BindView(R.id.report_bmi_detail_waist_me)
    LinearLayout report_bmi_detail_waist_me;
    @BindView(R.id.report_bmi_detail_waist_me2)
    LinearLayout report_bmi_detail_waist_me2;

    @BindView(R.id.report_bmi_detail_blood_pressure_max_me)
    LinearLayout report_bmi_detail_blood_pressure_max_me;
    @BindView(R.id.report_bmi_detail_blood_pressure_max_me2)
    LinearLayout report_bmi_detail_blood_pressure_max_me2;
    @BindView(R.id.report_bmi_detail_blood_pressure_max_me3)
    LinearLayout report_bmi_detail_blood_pressure_max_me3;
    @BindView(R.id.report_bmi_detail_blood_pressure_max_me4)
    LinearLayout report_bmi_detail_blood_pressure_max_me4;

    @BindView(R.id.report_bmi_detail_blood_pressure_min_me)
    LinearLayout report_bmi_detail_blood_pressure_min_me;
    @BindView(R.id.report_bmi_detail_blood_pressure_min_me2)
    LinearLayout report_bmi_detail_blood_pressure_min_me2;
    @BindView(R.id.report_bmi_detail_blood_pressure_min_me3)
    LinearLayout report_bmi_detail_blood_pressure_min_me3;
    @BindView(R.id.report_bmi_detail_blood_pressure_min_me4)
    LinearLayout report_bmi_detail_blood_pressure_min_me4;

    @BindView(R.id.report_bmi_detail_blood_sugar_me)
    LinearLayout report_bmi_detail_blood_sugar_me;
    @BindView(R.id.report_bmi_detail_blood_sugar_me2)
    LinearLayout report_bmi_detail_blood_sugar_me2;
    @BindView(R.id.report_bmi_detail_blood_sugar_me3)
    LinearLayout report_bmi_detail_blood_sugar_me3;

    @BindView(R.id.report_bmi_detail_waist)
    TextView report_bmi_detail_waist;
    @BindView(R.id.report_bmi_detail_waist2)
    TextView report_bmi_detail_waist2;

    @BindView(R.id.report_bmi_detail_blood_pressure_max)
    TextView report_bmi_detail_blood_pressure_max;
    @BindView(R.id.report_bmi_detail_blood_pressure_max2)
    TextView report_bmi_detail_blood_pressure_max2;

    @BindView(R.id.report_bmi_detail_blood_pressure_min)
    TextView report_bmi_detail_blood_pressure_min;
    @BindView(R.id.report_bmi_detail_blood_pressure_min2)
    TextView report_bmi_detail_blood_pressure_min2;

    @BindView(R.id.report_bmi_detail_blood_sugar)
    TextView report_bmi_detail_blood_sugar;
    @BindView(R.id.report_bmi_detail_blood_sugar2)
    TextView report_bmi_detail_blood_sugar2;




    int waist;
    int blood_pressure_max;
    int blood_pressure_min;
    int blood_sugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_health_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        waist = intent.getIntExtra("waist",0);
        blood_pressure_max = intent.getIntExtra("blood_pressure_max", 0);
        blood_pressure_min = intent.getIntExtra("blood_pressure_min", 0);
        blood_sugar = intent.getIntExtra("blood_sugar", 0);

        report_bmi_detail_waist.setText(String.valueOf(waist)+"cm");
        report_bmi_detail_blood_pressure_max.setText(String.valueOf(blood_pressure_max)+"mmHg");
        report_bmi_detail_blood_pressure_min.setText(String.valueOf(blood_pressure_min)+"mmHg");
        report_bmi_detail_blood_sugar.setText(String.valueOf(blood_sugar)+"mg/dL");


        if(waist < 90) {
            report_bmi_detail_waist2.setText("[정상]");
            report_bmi_detail_waist_me.setVisibility(View.VISIBLE);
        } else {
            report_bmi_detail_waist2.setText("[복부비만]");
            report_bmi_detail_waist_me2.setVisibility(View.VISIBLE);
        }

        if(blood_pressure_max < 120) {
            report_bmi_detail_blood_pressure_max2.setText("[정상]");
            report_bmi_detail_blood_pressure_max_me.setVisibility(View.VISIBLE);
        } else if(blood_pressure_max > 120&&blood_pressure_max < 140) {
            report_bmi_detail_blood_pressure_max2.setText("[고혈압 전단계]");
            report_bmi_detail_blood_pressure_max_me2.setVisibility(View.VISIBLE);

        } else if(blood_pressure_max > 140&&blood_pressure_max < 160) {
            report_bmi_detail_blood_pressure_max2.setText("[1단계 고혈압]");
            report_bmi_detail_blood_pressure_max_me3.setVisibility(View.VISIBLE);

        } else {
            report_bmi_detail_blood_pressure_max2.setText("[2단계 고혈압]");            report_bmi_detail_blood_pressure_max_me.setVisibility(View.VISIBLE);
            report_bmi_detail_blood_pressure_max_me4.setVisibility(View.VISIBLE);


        }

        if(blood_pressure_min < 120) {
            report_bmi_detail_blood_pressure_max2.setText("[정상]");
            report_bmi_detail_blood_pressure_min_me.setVisibility(View.VISIBLE);

        } else if(blood_pressure_min > 120&&blood_pressure_min < 140) {
            report_bmi_detail_blood_pressure_max2.setText("[고혈압 전단계]");
            report_bmi_detail_blood_pressure_min_me2.setVisibility(View.VISIBLE);

        } else if(blood_pressure_min > 140&&blood_pressure_min < 160) {
            report_bmi_detail_blood_pressure_max2.setText("[1단계 고혈압]");
            report_bmi_detail_blood_pressure_min_me3.setVisibility(View.VISIBLE);

        } else {
            report_bmi_detail_blood_pressure_max2.setText("[2단계 고혈압]");
            report_bmi_detail_blood_pressure_min_me4.setVisibility(View.VISIBLE);

        }

        if(blood_sugar < 100) {
            report_bmi_detail_blood_sugar2.setText("[정상]");
            report_bmi_detail_blood_sugar_me.setVisibility(View.VISIBLE);

        } else if(blood_sugar > 100&&blood_sugar < 126) {
            report_bmi_detail_blood_sugar2.setText("[당뇨 전단계]");
            report_bmi_detail_blood_sugar_me2.setVisibility(View.VISIBLE);

        } else {
            report_bmi_detail_blood_sugar2.setText("[당뇨]");
            report_bmi_detail_blood_sugar_me3.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return true;

    }
}
