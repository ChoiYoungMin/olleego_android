package com.edn.olleego.fragment.report;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.chart.*;
import com.edn.olleego.activity.chart.MyBarValueFormatter;
import com.edn.olleego.activity.chart.MyYAxisValueFormatter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.custom.CircleProgressBar_sleep;
import com.edn.olleego.custom.CircleProgressBar_water;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.model.Report_FoodModel;
import com.edn.olleego.model.Report_LifeModel;
import com.edn.olleego.model.report_food.Report;
import com.edn.olleego.server.ReportAPI;
import com.edn.olleego.server.Report_FoodAPI;
import com.edn.olleego.server.Report_LifeAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
public class LifeStyleReportFragment extends Fragment {

    @Nullable
    @BindView(R.id.lifestyle_report_food_chart1)
    BarChart lifestyle_report_food_chart1;

    @Nullable
    @BindView(R.id.lifestyle_report_food_chart2)
    BarChart lifestyle_report_food_chart2;

    @BindView(R.id.lifestyle_report_sleep_chk)
    TextView lifestyle_report_sleep_chk;
    @BindView(R.id.lifestyle_report_water_chk)
    TextView lifestyle_report_water_chk;


    @BindView(R.id.lifestyle_report_water_bar)
    CircleProgressBar_water lifestyle_report_water_bar;
    @BindView(R.id.lifestyle_report_sleep_bar)
    CircleProgressBar_sleep lifestyle_report_sleep_bar;

    @BindView(R.id.lifestyle_report_sleep_bar_text)
    TextView lifestyle_report_sleep_bar_text;
    @BindView(R.id.lifestyle_report_water_bar_text)
    TextView lifestyle_report_water_bar_text;

    @BindView(R.id.lifestyle_report_sleep_count)
    TextView lifestyle_report_sleep_count;
    @BindView(R.id.lifestyle_report_sleep_text)
    TextView lifestyle_report_sleep_text;

    @BindView(R.id.lifestyle_report_water_count)
    TextView lifestyle_report_water_count;
    @BindView(R.id.lifestyle_report_water_text)
    TextView lifestyle_report_water_text;

    ViewPager viewPager;
    Context context;

    public LifeStyleReportFragment(Context context, ViewPager viewPager) {

        this.viewPager = viewPager;
        this.context = context;
        // Required empty public constructor
    }

    SharedPreferences Olleego_SP;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int px = Math.round(1800 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            params.height = px;
            viewPager.setLayoutParams(params);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_life_style_report_fragment, container, false);
        ButterKnife.bind(this, rootview);

        Olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String token = "olleego " + Olleego_SP.getString("login_token", "");;
        Report_LifeAPI diaryAPI = retrofit.create(Report_LifeAPI.class);

        final Call<Report_LifeModel> diaryPos = diaryAPI.listRepos(token, "water");

        diaryPos.enqueue(new Callback<Report_LifeModel>() {
            @Override
            public void onResponse(Call<Report_LifeModel> call, Response<Report_LifeModel> response) {
                if(response.isSuccessful()) {
                    DecimalFormat df = new DecimalFormat("#,###");


                    lifestyle_report_water_count.setText(String.valueOf(response.body().getResult().getTotal_count().getCount()) + "회 입력");
                    lifestyle_report_water_text.setText(String.valueOf(df.format(response.body().getResult().getReport().getSum())) + " ml");

                    int water_ber = (int) ((response.body().getResult().getReport().getSum() / 60000) * 100);

                    if(response.body().getResult().getReport().getSum() < 20000) {
                        lifestyle_report_water_chk.setText("물이 부족해요.");
                    } else  if(response.body().getResult().getReport().getSum() > 20000&&response.body().getResult().getReport().getSum() < 40000) {
                        lifestyle_report_water_chk.setText("물을 좀더 마셔보세요");
                    } else {
                        lifestyle_report_water_chk.setText("충분히 잘하고 있어요");

                    }

                    lifestyle_report_water_bar.setProgressWithAnimation(water_ber);
                    lifestyle_report_water_bar.setProgress(water_ber);
                    lifestyle_report_water_bar.invalidate();
                    lifestyle_report_water_bar_text.setText(water_ber+"%");
                }
            }

            @Override
            public void onFailure(Call<Report_LifeModel> call, Throwable t) {

            }
        });

        final Call<Report_LifeModel> diaryPos2 = diaryAPI.listRepos(token, "sleep");
        diaryPos2.enqueue(new Callback<Report_LifeModel>() {
            @Override
            public void onResponse(Call<Report_LifeModel> call, Response<Report_LifeModel> response) {
                if(response.isSuccessful()) {
                    double temp = response.body().getResult().getReport().getSum();


                    String str = String.format("%.2f", (temp - (int)temp));

                    if(str.equals("0.00")) {
                        str = "00";
                    } else if(str.equals("0.10")) {
                        str = "10";

                    } else if(str.equals("0.20")) {
                        str = "20";

                    } else if(str.equals("0.30")) {
                        str = "30";

                    } else if(str.equals("0.40")) {
                        str = "40";

                    } else if(str.equals("0.50")) {
                        str = "50";

                    } else if(str.equals("0.60")) {
                        str = "60";

                    } else if(str.equals("0.70")) {
                        str = "70";

                    } else if(str.equals("0.80")) {
                        str = "80";

                    } else if(str.equals("0.90")) {
                        str = "90";

                    }


                    lifestyle_report_sleep_count.setText(String.valueOf(response.body().getResult().getTotal_count().getCount()) + "회 입력");
                    lifestyle_report_sleep_text.setText("총 "+String.valueOf((int)temp) + " 시간 " + str +" 분");

                    if((int)temp < 70) {
                        lifestyle_report_sleep_chk.setText("수면 부족");

                    } else if(temp > 70 &&(int)temp < 140) {
                        lifestyle_report_sleep_chk.setText("적당한 수면");

                    } else {
                        lifestyle_report_sleep_chk.setText("건강한 수면");
                    }

                    int sleep_bar = (int) ((response.body().getResult().getReport().getSum() / 210) * 100);

                    lifestyle_report_sleep_bar.setProgressWithAnimation(sleep_bar);
                    lifestyle_report_sleep_bar.setProgress(sleep_bar);
                    lifestyle_report_sleep_bar.invalidate();
                    lifestyle_report_sleep_bar_text.setText(sleep_bar+"%");
                }
            }

            @Override
            public void onFailure(Call<Report_LifeModel> call, Throwable t) {

            }
        });



        food_chart_init();


        return rootview;
    }


    public void food_chart_init() {

        final ArrayList<String> labels = new ArrayList<String>();

        final ArrayList<BarEntry> group1 = new ArrayList<>();
        final ArrayList<String> labels2 = new ArrayList<String>();

        final ArrayList<BarEntry> group2 = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String token = "olleego " + Olleego_SP.getString("login_token", "");
        Report_FoodAPI diaryAPI = retrofit.create(Report_FoodAPI.class);

        final Call<Report_FoodModel> FoodPos = diaryAPI.listRepos(token);

        FoodPos.enqueue(new Callback<Report_FoodModel>() {
            @Override
            public void onResponse(Call<Report_FoodModel> call, Response<Report_FoodModel> response) {

                if(response.isSuccessful()){
                    Report_FoodModel reportFoodModel = response.body();

                    int ru=0;

                    for(int i=0; i<6; i++) {
                        labels.add(reportFoodModel.getResult().getReport().get(i).get_id());

                        float count = reportFoodModel.getResult().getReport().get(i).getCount();

                        group1.add(new BarEntry(count, i, "세트"));

                    }

                    for(int j= 6; j<reportFoodModel.getResult().getReport().size(); j++ ) {
                        labels2.add(reportFoodModel.getResult().getReport().get(j).get_id());

                        float count = reportFoodModel.getResult().getReport().get(j).getCount();

                        group2.add(new BarEntry(count, ru, "세트"));
                        ru++;
                    }


                    ValueFormatter custom = new com.edn.olleego.activity.chart.MyBarValueFormatter(getContext());

                    BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
                    //barDataSet1.setColor(Color.rgb(0, 155, 0));
                    barDataSet1.setBarSpacePercent(40f);
                    barDataSet1.setHighlightEnabled(false);
                    barDataSet1.setValueFormatter(custom);
                    barDataSet1.setDrawValues(true);
                    barDataSet1.setColors(new int[]{Color.parseColor("#cde860")});
                    barDataSet1.setValueTextSize(12);
                    barDataSet1.setValueTextColor(Color.parseColor("#a6c52a"));

                    BarData data = new BarData(labels, barDataSet1);
                    // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                    data.setGroupSpace(0);



                    lifestyle_report_food_chart1.setData(data);
                    lifestyle_report_food_chart1.animateY(2000);
                    lifestyle_report_food_chart1.setDrawBarShadow(false);
                    lifestyle_report_food_chart1.setDrawValueAboveBar(true);
                    lifestyle_report_food_chart1.setPinchZoom(false);
                    lifestyle_report_food_chart1.setDrawGridBackground(false);
                    lifestyle_report_food_chart1.setDescription("");



                    XAxis xAxis = lifestyle_report_food_chart1.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setSpaceBetweenLabels(2);

                    YAxis leftAxis = lifestyle_report_food_chart1.getAxisLeft();
                    leftAxis.setLabelCount(4, true);
                    leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                    leftAxis.setSpaceTop(30f);
                    leftAxis.setDrawGridLines(false);
                    leftAxis.setValueFormatter(new MyYAxisValueFormatter());




                    lifestyle_report_food_chart1.getAxisRight().setDrawLabels(false);

                    Legend l = lifestyle_report_food_chart1.getLegend();
                    l.setEnabled(false);



                    BarDataSet barDataSet11 = new BarDataSet(group2, "Group 1");
                    //barDataSet1.setColor(Color.rgb(0, 155, 0));
                    barDataSet11.setBarSpacePercent(80f);
                    barDataSet11.setHighlightEnabled(false);
                    barDataSet11.setValueFormatter(custom);
                    barDataSet11.setDrawValues(true);
                    barDataSet11.setColors(new int[]{Color.parseColor("#cde860")});
                    barDataSet11.setValueTextSize(12);
                    barDataSet11.setValueTextColor(Color.parseColor("#a6c52a"));

                    BarData data2 = new BarData(labels2, barDataSet11);
                    // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                    data2.setGroupSpace(0);



                    lifestyle_report_food_chart2.setData(data2);
                    lifestyle_report_food_chart2.animateY(2000);
                    lifestyle_report_food_chart2.setDrawBarShadow(false);
                    lifestyle_report_food_chart2.setDrawValueAboveBar(true);
                    lifestyle_report_food_chart2.setPinchZoom(false);
                    lifestyle_report_food_chart2.setDrawGridBackground(false);
                    lifestyle_report_food_chart2.setDescription("");



                    XAxis xAxis2 = lifestyle_report_food_chart2.getXAxis();
                    xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis2.setDrawGridLines(false);
                    xAxis2.setSpaceBetweenLabels(2);

                    YAxis leftAxis2 = lifestyle_report_food_chart2.getAxisLeft();
                    leftAxis2.setLabelCount(4, true);
                    leftAxis2.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                    leftAxis2.setSpaceTop(30f);
                    leftAxis2.setDrawGridLines(false);
                    leftAxis2.setValueFormatter(new MyYAxisValueFormatter());




                    lifestyle_report_food_chart2.getAxisRight().setDrawLabels(false);

                    Legend l2 = lifestyle_report_food_chart2.getLegend();
                    l2.setEnabled(false);

                }



            }

            @Override
            public void onFailure(Call<Report_FoodModel> call, Throwable t) {

            }
        });





    }

}
