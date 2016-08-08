package com.edn.olleego.activity.report;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ReportInputModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.ReportInputAPI;
import com.edn.olleego.server.request.ReportInput;
import com.edn.olleego.server.request.blood_pressure;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
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

public class ReportHealthyInputActivity extends AppCompatActivity {

    @BindView(R.id.report_healthy_input_ok)
    LinearLayout report_healthy_input_ok;

    @BindView(R.id.report_input_waist_text)
    TextView report_input_waist_text;
    @BindView(R.id.report_input_hip_text)
    TextView report_input_hip_text;


    @BindView(R.id.report_input_height)
    EditText report_input_height;
    @BindView(R.id.report_input_weight)
    EditText report_input_weight;
    @BindView(R.id.report_input_hip)
    EditText report_input_hip;
    @BindView(R.id.report_input_waist)
    EditText report_input_waist;
    @BindView(R.id.report_input_blood_sugar)
    EditText report_input_blood_sugar;
    @BindView(R.id.report_input_blood_pressure_max)
    EditText report_input_blood_pressure_max;
    @BindView(R.id.report_input_blood_pressure_min)
    EditText report_input_blood_pressure_min;
    @BindView(R.id.report_input_body_fat)
    EditText report_input_body_fat;
    @BindView(R.id.report_input_body_fat_per)
    EditText report_input_body_fat_per;
    @BindView(R.id.report_input_muscle)
    EditText report_input_muscle;

    @BindView(R.id.report_input_group)
    RadioGroup report_input_group;

    @BindView(R.id.report_input_inch)
    RadioButton report_input_inch;
    @BindView(R.id.report_input_cm)
    RadioButton report_input_cm;

    @BindView(R.id.report_input_bmi)
    TextView report_input_bmi;
    @BindView(R.id.report_input_whr)
    TextView report_input_whr;


    boolean type = false;
    double waist_inch =0;
    double hip_inch =0;

    /*
          intent.putExtra("", reportModel.getResult().getHeight());
        intent.putExtra("weight", reportModel.getResult().getWeight());
        intent.putExtra("hip", reportModel.getResult().getHip());
        intent.putExtra("waist", reportModel.getResult().getWaist());
        intent.putExtra("blood_sugar", reportModel.getResult().getBlood_sugar());
        intent.putExtra("blood_pressure_max", reportModel.getResult().getBlood_pressure().getMax());
        intent.putExtra("blood_pressure_min", reportModel.getResult().getBlood_pressure().getMin());
        intent.putExtra("body_fat", reportModel.getResult().getBody_fat());
        intent.putExtra("body_fat_per", reportModel.getResult().getBody_fat_per());
        intent.putExtra("muscle", reportModel.getResult().getMuscle());
     */


    int height;
    int weight;
    int hip;
    int waist;
    int blood_sugar;
    int blood_pressure_max;
    int blood_pressure_min;
    int body_fat;
    int body_fat_per;
    int muscle;

    int nowtype;

    SharedPreferences Olleego_SP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_healthy_input);
        ButterKnife.bind(this);

        Olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        init();


    }


    public void init() {
        report_input_cm.setChecked(true);

        Intent intent = getIntent();

        height = intent.getIntExtra("height",0);
        weight = intent.getIntExtra("weight",0);
        hip  = intent.getIntExtra("hip",0);
        waist = intent.getIntExtra("waist",0);
        blood_sugar = intent.getIntExtra("blood_sugar",0);
        blood_pressure_max = intent.getIntExtra("blood_pressure_max",0);
        blood_pressure_min = intent.getIntExtra("blood_pressure_min",0);
        body_fat = intent.getIntExtra("body_fat",0);
        body_fat_per = intent.getIntExtra("body_fat_per",0);
        muscle = intent.getIntExtra("muscle",0);


        init_layout();

    }

    public void init_layout() {
         report_input_height.setText(String.valueOf(height));
         report_input_weight.setText(String.valueOf(weight));
         report_input_hip.setText(String.valueOf(hip));
         report_input_waist.setText(String.valueOf(waist));
         report_input_blood_sugar.setText(String.valueOf(blood_sugar));
         report_input_blood_pressure_max.setText(String.valueOf(blood_pressure_max));
         report_input_blood_pressure_min.setText(String.valueOf(blood_pressure_min));
         report_input_body_fat.setText(String.valueOf(body_fat));
         report_input_body_fat_per.setText(String.valueOf(body_fat_per));
         report_input_muscle.setText(String.valueOf(muscle));

    }

    public void  text_chk(){

        if(report_input_height.length() != 0) {
            height = Integer.parseInt(report_input_height.getText().toString());
        }

        if(report_input_weight.length() != 0) {
            weight = Integer.parseInt(report_input_weight.getText().toString());
        }

        if(report_input_hip.length() != 0) {
            hip = (int) Double.parseDouble(report_input_hip.getText().toString());
        }

        if(report_input_waist.length() != 0) {
            waist = (int) Double.parseDouble(report_input_waist.getText().toString());
        }

        if(report_input_blood_sugar.length() != 0) {
            blood_sugar = Integer.parseInt(report_input_blood_sugar.getText().toString());
        }

        if(report_input_blood_pressure_max.length() != 0) {
            blood_pressure_max = Integer.parseInt(report_input_blood_pressure_max.getText().toString());
        }

        if(report_input_blood_pressure_min.length() != 0) {
            blood_pressure_min = Integer.parseInt(report_input_blood_pressure_min.getText().toString());
        }

        if(report_input_body_fat.length() != 0) {
            body_fat = Integer.parseInt(report_input_body_fat.getText().toString());
        }

        if(report_input_body_fat_per.length() != 0) {
            body_fat_per = Integer.parseInt(report_input_body_fat_per.getText().toString());
        }

        if(report_input_muscle.length() != 0) {
            muscle = Integer.parseInt(report_input_muscle.getText().toString());
        }


    }

    @OnClick(R.id.report_healthy_input_ok)
    void report_healthy_input_ok(){

        text_chk();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                .connectTimeout(15, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(15, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(15, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();


        Retrofit retrofit_diary = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "olleego " + Olleego_SP.getString("login_token", "");
        ReportInputAPI reportInputAPI = retrofit_diary.create(ReportInputAPI.class);

        blood_pressure blood_pressure = new blood_pressure(blood_pressure_max, blood_pressure_min);

        if(report_input_waist_text.getText().equals("inch")) {
            waist = (int) (waist * 2.5);
        }

        if(report_input_hip_text.getText().equals("inch")) {
            hip = (int) (hip * 2.5);
        }

        ReportInput reportInput = new ReportInput(height, weight, hip, waist, blood_sugar, blood_pressure, body_fat, body_fat_per, muscle);


        Call<ReportInputModel> diaryPos = reportInputAPI.listRepos(token, Olleego_SP.getString("user_id", ""), reportInput);

        diaryPos.enqueue(new Callback<ReportInputModel>() {
            @Override
            public void onResponse(Call<ReportInputModel> call, Response<ReportInputModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "저장 성공!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ReportInputModel> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.report_input_inch)
    void report_input_inch() {
        if(nowtype != 1) {
            nowtype = 1;
            report_input_waist_text.setText("inch");
            report_input_hip_text.setText("inch");

            waist_inch = waist / 2.5;
            hip_inch = hip / 2.5;


            report_input_waist.setText(String.valueOf(waist_inch));
            report_input_hip.setText(String.valueOf(hip_inch));

            report_input_hip.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        hip_inch = Double.parseDouble(report_input_hip.getText().toString());

                        hip = (int) Double.parseDouble(report_input_hip.getText().toString());
                    } catch (NumberFormatException e) {

                        hip = 0;

                        hip_inch = 0;
                    }
                }
            });

            report_input_waist.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        waist_inch = Double.parseDouble(report_input_waist.getText().toString());
                        waist = (int) Double.parseDouble(report_input_waist.getText().toString());

                    } catch (NumberFormatException e) {
                        waist_inch = 0;
                        waist = 0;
                    }

                }
            });

            waist = (int) Double.parseDouble(report_input_waist.getText().toString());
            hip = (int) Double.parseDouble(report_input_hip.getText().toString());
        }
    }

    @OnClick(R.id.report_input_cm)
    void report_input_cm() {
        if(nowtype != 2) {
            nowtype = 2;

            if (waist_inch != 0) {
                report_input_waist.setText(String.valueOf((int) (waist * 2.5)));
            } else {
                report_input_waist.setText(String.valueOf(waist));
            }

            if (hip_inch != 0) {
                report_input_hip.setText(String.valueOf((int) (hip * 2.5)));
            } else {
                report_input_hip.setText(String.valueOf(hip));
            }

            report_input_waist_text.setText("cm");
            report_input_hip_text.setText("cm");

            waist = Integer.valueOf((report_input_waist.getText().toString()));
            hip = Integer.valueOf((report_input_hip.getText().toString()));
        }
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
