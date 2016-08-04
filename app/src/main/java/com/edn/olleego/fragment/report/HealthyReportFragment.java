package com.edn.olleego.fragment.report;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.report.ReportBMIDetailActivity;
import com.edn.olleego.activity.report.ReportHealthDetailActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.ReportAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class HealthyReportFragment extends Fragment {

    @BindView(R.id.report_healthy_mission1_img)
    ImageView report_healthy_mission1_img;
    @BindView(R.id.report_healthy_mission2_img)
    ImageView report_healthy_mission2_img;
    @BindView(R.id.report_healthy_mission1_text)
    TextView report_healthy_mission1_text;
    @BindView(R.id.report_healthy_mission2_text)
    TextView report_healthy_mission2_text;




    @BindView(R.id.report_healthy_report1_title)
    TextView report_healthy_report1_title;

    @BindView(R.id.report_healthy_report1_bmiimg)
    ImageView report_healthy_report1_bmiimg;

    @BindView(R.id.report_healthy_report1_bmiimg2)
    ImageView report_healthy_report1_bmiimg2;

    @BindView(R.id.report_healthy_report1_content)
    TextView report_healthy_report1_content;
    @BindView(R.id.report_healthy_report1_allbmi)
    TextView report_healthy_report1_allbmi;
    @BindView(R.id.report_healthy_report1_mybmi)
    TextView report_healthy_report1_mybmi;
    @BindView(R.id.report_healthy_report1_allkg)
    TextView report_healthy_report1_allkg;
    @BindView(R.id.report_healthy_report1_chkkg)
    TextView report_healthy_report1_chkkg;


    @BindView(R.id.report_healthy_report2_title)
    TextView report_healthy_report2_title;
    @BindView(R.id.report_healthy_report2_content)
    TextView report_healthy_report2_content;

    @BindView(R.id.report_healthy_report2_waist_text)
    TextView report_healthy_report2_waist_text;
    @BindView(R.id.report_healthy_report2_bp_text)
    TextView report_healthy_report2_bp_text;
    @BindView(R.id.report_healthy_report2_bs_text)
    TextView report_healthy_report2_bs_text;


    @BindView(R.id.report_healthy_report2_waist_img)
    ImageView report_healthy_report2_waist_img;
    @BindView(R.id.report_healthy_report2_bp_img)
    ImageView report_healthy_report2_bp_img;
    @BindView(R.id.report_healthy_report2_bs_img)
    ImageView report_healthy_report2_bs_img;


    public HealthyReportFragment() {
        // Required empty public constructor
    }

    SharedPreferences olleego_SP;
    ReportModel reportModel;
    Double bmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_healthy_report, container, false);

        ButterKnife.bind(this, rootview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);



        String token = "olleego " + olleego_SP.getString("login_token", "");;
        ReportAPI diaryAPI = retrofit.create(ReportAPI.class);

        final Call<ReportModel> diaryPos = diaryAPI.listRepos(token);

        diaryPos.enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                if(response.isSuccessful()) {
                    reportModel = response.body();

                    bmi =  reportModel.getResult().getBmi();

                    if(18.5 > bmi ) {
                        report_healthy_report1_bmiimg.setImageResource(R.drawable.ic_chevron_right);
                    } else if(22.9 < bmi) {
                        report_healthy_report1_bmiimg.setImageResource(R.drawable.ic_chevron_left);

                    } else {
                        report_healthy_report1_bmiimg.setImageResource(R.drawable.ic_equal);

                    }

                    if (bmi > 0 && bmi < 18.5) {
                        report_healthy_report1_bmiimg2.setImageResource(R.drawable.ic_status_x);
                    } else if (bmi >= 18.5 && bmi < 23) {
                        report_healthy_report1_bmiimg2.setImageResource(R.drawable.ic_status_2);


                    } else if (bmi >= 23 && bmi < 25) {
                        report_healthy_report1_bmiimg2.setImageResource(R.drawable.ic_status_3);


                    } else if (bmi >= 25 && bmi < 30) {
                        report_healthy_report1_bmiimg2.setImageResource(R.drawable.ic_status_4);


                    } else {
                        report_healthy_report1_bmiimg2.setImageResource(R.drawable.ic_status_5);

                    }






                    report_healthy_report1_title.setText(reportModel.getResult().getWhr_eval().getData_a());
                    report_healthy_report1_content.setText(reportModel.getResult().getWhr_eval().getData_b());

                    report_healthy_report1_allkg.setText("약 "+ reportModel.getResult().getWhr_eval().getData_c());
                    report_healthy_report1_chkkg.setText(reportModel.getResult().getWhr_eval().getData_d());

                    report_healthy_report2_title.setText(reportModel.getResult().getMs_status().getData_e());

                    if(reportModel.getResult().getMs_status().getCheck() < 1) {

                        report_healthy_report2_content.setText("아래의 건강수치를 분석해본 결과\n 건강상태가 아주 양호한 편입니다.");
                    } else if(reportModel.getResult().getMs_status().getCheck() == 2) {
                        report_healthy_report2_content.setText("아래의 건강수치를 분석해본 결과\n 대사증후군 관리 대상입니다. ");
                    } else if(reportModel.getResult().getMs_status().getCheck() == 3) {
                        report_healthy_report2_content.setText("아래의 건강수치를 분석해본 결과\n대사증후군이 의심되며 가까운\n의료기관에서 검진을 받아 보시기\n바랍니다");
                    }


                    if(reportModel.getResult().getMs_status().getWaist() == true ) {
                        report_healthy_report2_waist_img.setImageResource(R.drawable.ic_check_circle);
                        report_healthy_report2_waist_text.setTextColor(Color.parseColor("#606060"));
                    }

                    if(reportModel.getResult().getMs_status().getBp() == true ) {
                        report_healthy_report2_bp_img.setImageResource(R.drawable.ic_check_circle);
                        report_healthy_report2_bp_text.setTextColor(Color.parseColor("#606060"));
                    }

                    if(reportModel.getResult().getMs_status().getBs() == true ) {
                        report_healthy_report2_bs_img.setImageResource(R.drawable.ic_check_circle);
                        report_healthy_report2_bs_text.setTextColor(Color.parseColor("#606060"));
                    }


                    Glide.with(getContext()).load(reportModel.getResult().getMission1().getTitle_img()).into(report_healthy_mission1_img);
                    Glide.with(getContext()).load(reportModel.getResult().getMission2().getTitle_img()).into(report_healthy_mission2_img);

                    report_healthy_report1_mybmi.setText(String.valueOf(reportModel.getResult().getBmi()));
                    report_healthy_mission1_text.setText(reportModel.getResult().getMission1().getTitle());
                    report_healthy_mission2_text.setText(reportModel.getResult().getMission2().getTitle());




                }
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {

            }
        });
        return rootview;
    }

    @OnClick(R.id.report_healthy_report1_detail)
    void report1_bmi_click() {
        Intent intent = new Intent(getActivity(), ReportBMIDetailActivity.class);
        intent.putExtra("bmi", bmi);
        intent.putExtra("whr", reportModel.getResult().getWhr());
        getContext().startActivity(intent);

    }

    @OnClick(R.id.report_healthy_report2_detail)
    void report2_detail_click() {
        Intent intent = new Intent(getActivity(), ReportHealthDetailActivity.class);
        intent.putExtra("waist", reportModel.getResult().getWaist());
        intent.putExtra("blood_pressure_max", reportModel.getResult().getBlood_pressure().getMax());
        intent.putExtra("blood_pressure_min", reportModel.getResult().getBlood_pressure().getMin());
        intent.putExtra("blood_sugar", reportModel.getResult().getBlood_sugar());

        getContext().startActivity(intent);
    }

}
