package com.edn.olleego.fragment.diary;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.activity.chart.MyBarValueFormatter;
import com.edn.olleego.activity.chart.MyYAxisValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.SleepValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.SleepYAxisValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.WalkingValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.WalkingYAxisValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.WaterValueFormatter;
import com.edn.olleego.activity.diary.chartformatter.WaterYAxisValueFormatter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryChartModel;
import com.edn.olleego.model.Report_WeightModel;
import com.edn.olleego.server.DiaryChartAPI;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryChartFragment extends Fragment {

    int type;
    String type_tmp;
    SharedPreferences olleego_SP;
    android.support.v7.app.ActionBar supportActionBar ;

    public DiaryChartFragment(int type, android.support.v7.app.ActionBar supportActionBar) {
        // Required empty public constructor
        this.type = type;
        this.supportActionBar = supportActionBar;

    }

    @BindView(R.id.diray_barchart)
    BarChart diray_barchart;


    final ArrayList<String> labels = new ArrayList<String>();

     ArrayList<BarEntry> group1 =null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diary_chart, container, false);
        ButterKnife.bind(this,view);

        supportActionBar.setDisplayHomeAsUpEnabled(true);

        olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);

        switch (type) {
            case 1:
                type_tmp="walking";
                break;
            case 2:
                type_tmp="sleep";
                break;
            case 3:
                type_tmp="water";
                break;
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        String token = "olleego " + olleego_SP.getString("login_token", "");;
        DiaryChartAPI diaryAPI = retrofit.create(DiaryChartAPI.class);

        final Call<DiaryChartModel> diaryPos = diaryAPI.listRepos(token, type_tmp);

        diaryPos.enqueue(new Callback<DiaryChartModel>() {
            @Override
            public void onResponse(Call<DiaryChartModel> call, Response<DiaryChartModel> response) {
                if(response.isSuccessful()) {
                    DiaryChartModel diaryChartModel = response.body();
                    int temp = diaryChartModel.getResult().size()-1;

                    group1= new ArrayList<>();
                    for(int i=0; i<diaryChartModel.getResult().size(); i++ ) {
                        SimpleDateFormat dateFormat = new  SimpleDateFormat("MM-dd", java.util.Locale.getDefault());
                        String labelz = dateFormat.format(diaryChartModel.getResult().get(temp).getDay());
                        labels.add(labelz);

                        if(type == 1) {
                            int walking = diaryChartModel.getResult().get(temp).getWalking();
                            group1.add(new BarEntry(walking, i, ""));
                        } else if(type == 2){

                            float sleep = diaryChartModel.getResult().get(temp).getSleep();
                            group1.add(new BarEntry(sleep, i, ""));
                        } else if(type == 3) {
                            int water = diaryChartModel.getResult().get(temp).getWater();
                            group1.add(new BarEntry(water, i, ""));
                        }

                        temp--;
                    }

                    ValueFormatter custom = null;
                    if(type == 1) {
                         custom = new WalkingValueFormatter(getContext());
                    } else if(type == 2){
                         custom = new SleepValueFormatter(getContext());
                    } else if(type == 3) {
                         custom = new WaterValueFormatter(getContext());
                    }




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



                    diray_barchart.setData(data);
                    diray_barchart.animateY(2000);
                    diray_barchart.setDrawBarShadow(false);
                    diray_barchart.setDrawValueAboveBar(true);
                    diray_barchart.setPinchZoom(false);
                    diray_barchart.setDrawGridBackground(false);
                    diray_barchart.setDescription("");



                    XAxis xAxis = diray_barchart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setSpaceBetweenLabels(2);

                    YAxis leftAxis = diray_barchart.getAxisLeft();
                    leftAxis.setLabelCount(5, true);
                    leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                    leftAxis.setSpaceTop(30f);
                    leftAxis.setAxisMinValue(0);
                    leftAxis.setDrawGridLines(false);


                    if(type == 1) {
                        leftAxis.setValueFormatter(new WalkingYAxisValueFormatter());
                    } else if(type == 2){
                        leftAxis.setValueFormatter(new SleepYAxisValueFormatter());
                    } else if(type == 3) {
                        leftAxis.setValueFormatter(new WaterYAxisValueFormatter());
                    }





                    diray_barchart.getAxisRight().setDrawLabels(false);

                    Legend l = diray_barchart.getLegend();
                    l.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<DiaryChartModel> call, Throwable t) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}
