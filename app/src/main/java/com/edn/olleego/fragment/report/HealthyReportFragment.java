package com.edn.olleego.fragment.report;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.diary.DiaryChartActivity;
import com.edn.olleego.activity.mission.MissionDetailActivity;
import com.edn.olleego.activity.report.ReportBMIDetailActivity;
import com.edn.olleego.activity.report.ReportHealthDetailActivity;
import com.edn.olleego.activity.report.ReportHealthyInputActivity;
import com.edn.olleego.activity.report.ReportWaistDetailActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ReportModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.ReportAPI;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthyReportFragment extends Fragment {

    @BindView(R.id.report_healthy_layout1)
    LinearLayout report_healthy_layout1;
    @BindView(R.id.report_healthy_layout2)
    LinearLayout report_healthy_layout2;
    @BindView(R.id.report_healthy_layout3)
    LinearLayout report_healthy_layout3;

    @BindView(R.id.report_healthy_report_input)
    LinearLayout report_healthy_report_input;

    @BindView(R.id.report_healthy_report1_wiast_layout)
    LinearLayout report_healthy_report1_wiast_layout;

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

    ViewPager viewPager;
    Context context;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_report:
                Intent intent = new Intent(getActivity(), ReportHealthyInputActivity.class);
                try {

                    intent.putExtra("height", reportModel.getResult().getHeight());
                    intent.putExtra("weight", reportModel.getResult().getWeight());
                    intent.putExtra("hip", reportModel.getResult().getHip());
                    intent.putExtra("waist", reportModel.getResult().getWaist());
                    intent.putExtra("blood_sugar", reportModel.getResult().getBlood_sugar());
                    intent.putExtra("blood_pressure_max", reportModel.getResult().getBlood_pressure().getMax());
                    intent.putExtra("blood_pressure_min", reportModel.getResult().getBlood_pressure().getMin());
                    intent.putExtra("body_fat", reportModel.getResult().getBody_fat());
                    intent.putExtra("body_fat_per", reportModel.getResult().getBody_fat_per());
                    intent.putExtra("muscle", reportModel.getResult().getMuscle());
                } catch (NullPointerException e) {
                    intent.putExtra("height", 0);
                    intent.putExtra("weight", 0);
                    intent.putExtra("hip", 0);
                    intent.putExtra("waist", 0);
                    intent.putExtra("blood_sugar", 0);
                    intent.putExtra("blood_pressure_max", 0);
                    intent.putExtra("blood_pressure_min", 0);
                    intent.putExtra("body_fat",0);
                    intent.putExtra("body_fat_per", 0);
                    intent.putExtra("muscle", 0);
                }
                getContext().startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public HealthyReportFragment(Context context, ViewPager viewPager) {
        // Required empty public constructor
        this.viewPager = viewPager;
        this.context = context;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int px = Math.round(1500 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            ViewGroup.LayoutParams params = viewPager.getLayoutParams();
            params.height = px;
            viewPager.setLayoutParams(params);
        }
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

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                .connectTimeout(15, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(15, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(15, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .client(client)
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
                    report_healthy_report_input.setVisibility(View.GONE);
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

                    try{
                        Glide.with(getContext()).load(reportModel.getResult().getMission1().getTitle_img()).into(report_healthy_mission1_img);
                        Glide.with(getContext()).load(reportModel.getResult().getMission2().getTitle_img()).into(report_healthy_mission2_img);
                        report_healthy_mission1_text.setText(reportModel.getResult().getMission1().getTitle());
                        report_healthy_mission2_text.setText(reportModel.getResult().getMission2().getTitle());

                    } catch (NullPointerException e) {

                        report_healthy_layout3.setVisibility(View.GONE);
                    }

                    report_healthy_report1_mybmi.setText(String.valueOf(reportModel.getResult().getBmi()));



                } else {
                    report_healthy_layout1.setVisibility(View.GONE);
                    report_healthy_layout2.setVisibility(View.GONE);
                    report_healthy_layout3.setVisibility(View.GONE);



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

    @OnClick(R.id.report_healthy_report1_wiast_layout)
    void report_healthy_report1_wiast_layout() {
        Intent intent = new Intent(getActivity(), ReportWaistDetailActivity.class);
        getContext().startActivity(intent);
    }


    @OnClick(R.id.report_healthy_report_input)
    void report_healthy_report_input() {
        Intent intent = new Intent(getActivity(), ReportHealthyInputActivity.class);
        try {

            intent.putExtra("height", reportModel.getResult().getHeight());
            intent.putExtra("weight", reportModel.getResult().getWeight());
            intent.putExtra("hip", reportModel.getResult().getHip());
            intent.putExtra("waist", reportModel.getResult().getWaist());
            intent.putExtra("blood_sugar", reportModel.getResult().getBlood_sugar());
            intent.putExtra("blood_pressure_max", reportModel.getResult().getBlood_pressure().getMax());
            intent.putExtra("blood_pressure_min", reportModel.getResult().getBlood_pressure().getMin());
            intent.putExtra("body_fat", reportModel.getResult().getBody_fat());
            intent.putExtra("body_fat_per", reportModel.getResult().getBody_fat_per());
            intent.putExtra("muscle", reportModel.getResult().getMuscle());
        } catch (NullPointerException e) {
            intent.putExtra("height", 0);
            intent.putExtra("weight", 0);
            intent.putExtra("hip", 0);
            intent.putExtra("waist", 0);
            intent.putExtra("blood_sugar", 0);
            intent.putExtra("blood_pressure_max", 0);
            intent.putExtra("blood_pressure_min", 0);
            intent.putExtra("body_fat",0);
            intent.putExtra("body_fat_per", 0);
            intent.putExtra("muscle", 0);
        }
        getContext().startActivity(intent);
    }

    @OnClick(R.id.report_healthy_mission1_layout)
    void report_healthy_mission1_layout() {
        Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
        intent.putExtra("mission_id", reportModel.getResult().getMission1().get_id());
        intent.putExtra("mission_type", 2);
        getActivity().startActivity(intent);
    }


    @OnClick(R.id.report_healthy_mission2_layout)
    void report_healthy_mission2_layout() {
        Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
        intent.putExtra("mission_id", reportModel.getResult().getMission2().get_id());
        intent.putExtra("mission_type", 2);
        getActivity().startActivity(intent);
    }

    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder;
    }

}
