package com.edn.olleego.activity.report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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


    @BindView(R.id.report_bmi_detail_mybmi)
    TextView report_bmi_detail_mybmi;
    @BindView(R.id.report_bmi_detail_mybmi2)
    TextView report_bmi_detail_mybmi2;

    @BindView(R.id.report_bmi_detail_mytemp)
    TextView report_bmi_detail_mytemp;
    @BindView(R.id.report_bmi_detail_mytemp2)
    TextView report_bmi_detail_mytemp2;


    @BindView(R.id.report_bmi_detail_temp_me)
    LinearLayout report_bmi_detail_temp_me;
    @BindView(R.id.report_bmi_detail_temp_me2)
    LinearLayout report_bmi_detail_temp_me2;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return true;

    }

    Double bmi;
    Double whr;

    LinearLayout.LayoutParams layoutParams;
    DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bmidetail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        bmi = intent.getDoubleExtra("bmi" , 0);
        whr = intent.getDoubleExtra("whr" , 0);


        if (bmi > 0 && bmi < 18.5) {
            //저체중
            report_bmi_detail_mybmi2.setText("[저체중]");
            report_bmi_detail_me.setVisibility(View.VISIBLE);
        } else if (bmi >= 18.5 && bmi < 23) {
            report_bmi_detail_mybmi2.setText("[정상]");

            report_bmi_detail_me2.setVisibility(View.VISIBLE);


        } else if (bmi >= 23 && bmi < 25) {
            //과체중
            report_bmi_detail_mybmi2.setText("[과체중]");

            report_bmi_detail_me3.setVisibility(View.VISIBLE);

        } else if (bmi >= 25 && bmi < 30) {
            report_bmi_detail_mybmi2.setText("[경도비만]");

            report_bmi_detail_me4.setVisibility(View.VISIBLE);


        } else {
            report_bmi_detail_mybmi2.setText("[고도비만]");

            report_bmi_detail_me5.setVisibility(View.VISIBLE);

        }

        if(whr > 1.0) {
            report_bmi_detail_mytemp2.setText("[복부비만]");
            report_bmi_detail_temp_me2.setVisibility(View.VISIBLE);

        } else {
            report_bmi_detail_mytemp2.setText("[정상]");
            report_bmi_detail_temp_me.setVisibility(View.VISIBLE);
        }


        report_bmi_detail_mybmi.setText(String.valueOf(bmi));
        report_bmi_detail_mytemp.setText(String.valueOf(whr)+"%");







    }
}
