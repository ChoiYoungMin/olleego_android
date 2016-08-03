package com.edn.olleego.activity.report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportBMIDetailActivity extends AppCompatActivity {

    @BindView(R.id.report_bmi_detail_me)
    LinearLayout report_bmi_detail_me;

    @BindView(R.id.report_bmi_detail_me2)
    LinearLayout report_bmi_detail_me2;

    @BindView(R.id.report_bmi_detail_me3)
    LinearLayout report_bmi_detail_me3;

    @BindView(R.id.report_bmi_detail_me4)
    LinearLayout report_bmi_detail_me4;

    @BindView(R.id.report_bmi_detail_me5)
    LinearLayout report_bmi_detail_me5;




    Float bmi;
    Double whr;

    LinearLayout.LayoutParams layoutParams;
    DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bmidetail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        bmi = intent.getFloatExtra("bmi" , 0);
        whr = intent.getDoubleExtra("whr" , 0);


        if (bmi > 0 && bmi < 18.5) {
            //저체중
            report_bmi_detail_me.setVisibility(View.VISIBLE);
        } else if (bmi >= 18.5 && bmi < 23) {
            //정상
            report_bmi_detail_me2.setVisibility(View.VISIBLE);


        } else if (bmi >= 23 && bmi < 25) {
            //과체중
            report_bmi_detail_me3.setVisibility(View.VISIBLE);

        } else if (bmi >= 25 && bmi < 30) {
            report_bmi_detail_me4.setVisibility(View.VISIBLE);


        } else {
            report_bmi_detail_me5.setVisibility(View.VISIBLE);


        }






    }
}
