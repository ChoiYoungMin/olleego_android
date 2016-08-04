package com.edn.olleego.fragment.report;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.custom.CircleProgressBar_sleep;
import com.edn.olleego.custom.CircleProgressBar_water;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.model.Report_LifeModel;
import com.edn.olleego.server.ReportAPI;
import com.edn.olleego.server.Report_LifeAPI;

import java.text.DecimalFormat;

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


    public LifeStyleReportFragment() {
        // Required empty public constructor
    }

    SharedPreferences Olleego_SP;

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
                }
            }

            @Override
            public void onFailure(Call<Report_LifeModel> call, Throwable t) {

            }
        });


        return rootview;
    }

}
