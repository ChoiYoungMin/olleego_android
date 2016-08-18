package com.edn.olleego.fragment.report;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.report.Report_PagerAdapter;
import com.edn.olleego.common.Percent;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.custom.VerticalProgressBar;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.dialog.Report_HealthyDialog;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.server.ReportAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    @BindView(R.id.report_bmi)
    TextView report_bmi;

    @BindView(R.id.report_health_temp)
    TextView report_health_temp;

    @BindView(R.id.report_health_bar)
    VerticalProgressBar report_health_bar;

    SharedPreferences Olleego_SP;

    double bmi;
    int temp;
    LoadingBarDialog loadingBarDialog;


    private Report_PagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    android.support.v4.app.FragmentTransaction transaction;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);

        loadingBarDialog = new LoadingBarDialog(getContext());
        loadingBarDialog.show();
        loadingBarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mSectionsPagerAdapter = new Report_PagerAdapter(getChildFragmentManager(), mViewPager, getContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String token = "olleego " + Olleego_SP.getString("login_token", "");;
        ReportAPI diaryAPI = retrofit.create(ReportAPI.class);

        final Call<ReportModel> diaryPos = diaryAPI.listRepos(token);

        diaryPos.enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                bmi = response.body().getResult().getBmi();
                temp = response.body().getResult().getHealth_temp();

                String s_bim = null;
                String s_health_temp = null;

                if (bmi > 0 && bmi < 18.5) {
                    s_bim = String.valueOf(bmi + " (저체중)");
                    report_bmi.setTextColor(Color.parseColor("#ccd6a1"));
                } else if (bmi >= 18.5 && bmi < 23) {
                    s_bim = String.valueOf(bmi + " (정상)");
                    report_bmi.setTextColor(Color.parseColor("#cde860"));

                } else if (bmi >= 23 && bmi < 25) {
                    s_bim = String.valueOf(bmi + " (과체중)");
                    report_bmi.setTextColor(Color.parseColor("#ffa9a5"));

                } else if (bmi >= 25 && bmi < 30) {
                    s_bim = String.valueOf(bmi + " (경도비만)");
                    report_bmi.setTextColor(Color.parseColor("#ff8681"));

                } else {
                    s_bim = String.valueOf(bmi + " (고도비만)");
                    report_bmi.setTextColor(Color.parseColor("#ff5b53"));

                }


                if (temp > 0 && temp < 20) {
                    s_health_temp = "˚ [건강위험]";
                    report_health_temp.setTextColor(Color.parseColor("#ff5b53"));
                    report_health_bar.setCurrentValue(Percent.PERCENT_10);
                } else if (temp >= 20 && temp < 40) {
                    s_health_temp = "˚ [주의]";
                    report_health_temp.setTextColor(Color.parseColor("#ff8681"));
                    report_health_bar.setCurrentValue(Percent.PERCENT_25);
                } else if (temp >= 40 && temp < 60) {
                    s_health_temp = "˚ [건강관심]";
                    report_health_bar.setCurrentValue(Percent.PERCENT_50);
                    report_health_temp.setTextColor(Color.parseColor("#ffa9a5"));

                } else if (temp >= 60 && temp < 80) {
                    s_health_temp = "˚ [건강양호]";
                    report_health_bar.setCurrentValue(Percent.PERCENT_75);
                    report_health_temp.setTextColor(Color.parseColor("#cde860"));

                } else {
                    s_health_temp = "˚ [건강좋음]";
                    report_health_bar.setCurrentValue(Percent.PERCENT_100);
                    report_health_temp.setTextColor(Color.parseColor("#cde860"));

                }

                report_bmi.setText(s_bim);
                report_health_temp.setText(String.valueOf(temp) + s_health_temp);
                loadingBarDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {

            }
        });





        return view;
    }

    @OnClick(R.id.report_health_img)
    void report_health_img() {
        Report_HealthyDialog reportHealthyDialog = new Report_HealthyDialog(getContext());
        reportHealthyDialog.show();

    }

}
