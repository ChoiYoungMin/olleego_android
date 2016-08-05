package com.edn.olleego.activity.report;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.fragment.report.MyBarValueFormatter;
import com.edn.olleego.fragment.report.MyYAxisValueFormatter;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.model.Report_WeightModel;
import com.edn.olleego.server.ReportAPI;
import com.edn.olleego.server.Report_WeightAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportWaistDetailActivity extends AppCompatActivity {

    @BindView(R.id.lifestyle_report_waist_chart)
    BarChart lifestyle_report_waist_chart;

    final ArrayList<String> labels = new ArrayList<String>();

    final ArrayList<BarEntry> group1 = new ArrayList<>();

    SharedPreferences olleego_SP;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_waist_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);



        String token = "olleego " + olleego_SP.getString("login_token", "");;
        Report_WeightAPI diaryAPI = retrofit.create(Report_WeightAPI.class);

        final Call<Report_WeightModel> diaryPos = diaryAPI.listRepos(token);

        diaryPos.enqueue(new Callback<Report_WeightModel>() {
            @Override
            public void onResponse(Call<Report_WeightModel> call, Response<Report_WeightModel> response) {
                if(response.isSuccessful()) {
                    Report_WeightModel reportWeightModel = response.body();
                    int temp = reportWeightModel.getResult().size()-1;

                    for(int i=0; i<reportWeightModel.getResult().size(); i++ ) {
                        String labelz = reportWeightModel.getResult().get(temp).getDay().substring(5);
                        labels.add(labelz);
                        float weight = reportWeightModel.getResult().get(temp).getWeight();
                        group1.add(new BarEntry(weight, i, ""));
                        temp--;
                    }


                    ValueFormatter custom = new MyBarValueFormatter(getApplicationContext());

                    BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
                    //barDataSet1.setColor(Color.rgb(0, 155, 0));
                    barDataSet1.setBarSpacePercent(40f);
                    barDataSet1.setHighlightEnabled(false);
                    barDataSet1.setValueFormatter(custom);
                    barDataSet1.setDrawValues(true);
                    barDataSet1.setColors(new int[]{Color.parseColor("#cde860")});
                    barDataSet1.setValueTextSize(12);
                    barDataSet1.setValueTextColor(Color.parseColor("#606060"));

                    BarData data = new BarData(labels, barDataSet1);
                    // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                    data.setGroupSpace(0);



                    lifestyle_report_waist_chart.setData(data);
                    lifestyle_report_waist_chart.animateY(2000);
                    lifestyle_report_waist_chart.setDrawBarShadow(false);
                    lifestyle_report_waist_chart.setDrawValueAboveBar(true);
                    lifestyle_report_waist_chart.setPinchZoom(false);
                    lifestyle_report_waist_chart.setDrawGridBackground(false);
                    lifestyle_report_waist_chart.setDescription("");



                    XAxis xAxis = lifestyle_report_waist_chart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setSpaceBetweenLabels(2);

                    YAxis leftAxis = lifestyle_report_waist_chart.getAxisLeft();
                    leftAxis.setLabelCount(5, true);
                    leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                    leftAxis.setSpaceTop(30f);
                    leftAxis.setDrawGridLines(false);
                    leftAxis.setValueFormatter(new MyYAxisValueFormatter());




                    lifestyle_report_waist_chart.getAxisRight().setDrawLabels(false);

                    Legend l = lifestyle_report_waist_chart.getLegend();
                    l.setEnabled(false);
                }


            }

            @Override
            public void onFailure(Call<Report_WeightModel> call, Throwable t) {

            }
        });




    }
}
