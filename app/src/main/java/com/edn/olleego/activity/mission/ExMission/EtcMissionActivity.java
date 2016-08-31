package com.edn.olleego.activity.mission.exmission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.MissionSuccessDialog;
import com.edn.olleego.model.Lifes.LfList;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.model.exgroups.Ex;
import com.edn.olleego.model.foods.FdList;
import com.edn.olleego.server.MissionSuccessAPI;
import com.edn.olleego.server.request.MissionSuccess;

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

public class EtcMissionActivity extends AppCompatActivity {

    Intent intent;

    FdList fdList;
    LfList lfList;

    String type;

    @BindView(R.id.etc_mission_img)
    ImageView etc_mission_img;

    @BindView(R.id.etc_mission_type)
    TextView mission_type;

    @BindView(R.id.etc_mission_title)
    TextView mission_title;

    @BindView(R.id.etc_mission_description)
    TextView mission_description;

    @BindView(R.id.etc_mission_level)
    TextView mission_level;

    @BindView(R.id.etc_mission_time)
    TextView mission_time;

    @BindView(R.id.etc_mission_tip)
    TextView mission_tip;

    String tokens;
    MissionSuccessDialog missionSuccessDialog;
    int mission_id;
    int mission_today;
    int food_id;
    int life_id;
    int gg =3;
    int types =0;

    SharedPreferences olleego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_mission);
        ButterKnife.bind(this);
        olleego = getSharedPreferences("olleego", MODE_PRIVATE);

        intent = getIntent();

        type= intent.getStringExtra("type");


        switch (type) {
            case "food":
                fdList = (FdList) intent.getSerializableExtra("food");
                mission_type.setText("식습관");
                mission_title.setText(fdList.getTitle());
                mission_level.setText(getlevel(fdList.getFd_level(),1));
                mission_time.setText(gettime(fdList.getFd_time(),1));
                mission_description.setText(Html.fromHtml(fdList.getDescription2()));
                mission_tip.setText(fdList.getTip());
                Glide.with(this).load(fdList.getDescription_img().get(0)).into(etc_mission_img);

                tokens = intent.getStringExtra("token");
                mission_id = intent.getIntExtra("mission_id", 0);
                mission_today = intent.getIntExtra("mission_today", 0);
                food_id = intent.getIntExtra("food_id", 0);



                break;

            case "life":
                lfList =  (LfList) intent.getSerializableExtra("life");
                mission_type.setText("생활습관");

                mission_title.setText(lfList.getTitle());
                mission_level.setText(getlevel(lfList.getLf_level(),2));
                mission_time.setText(gettime(lfList.getLf_time(),2));
                mission_description.setText(lfList.getDescription1());
                mission_tip.setText(lfList.getTip());
                Glide.with(this).load(lfList.getDescription_img().get(0)).into(etc_mission_img);


                tokens = intent.getStringExtra("token");
                mission_id = intent.getIntExtra("mission_id", 0);
                mission_today = intent.getIntExtra("mission_today", 0);
                life_id = intent.getIntExtra("life_id", 0);
                break;
        }


    }

    @OnClick(R.id.etc_mission_ok)
    void mission_ok_click() {

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
        String temp = null;

        MissionSuccess missionSuccess = null;
        if( type.equals("food")) {
             missionSuccess = new MissionSuccess(mission_today,fdList.get_id(), food_id, "food");
            temp="food";
            types= 1;
        } else if(type.equals("life")) {
             missionSuccess = new MissionSuccess(mission_today,lfList.get_id(),life_id , "life");
            temp="life";
            types=2;
        }
        String token = "olleego " + tokens;
        MissionSuccessAPI missionSuccessAPI = retrofit_diary.create(MissionSuccessAPI.class);

        final Call<MissionsModel> diaryPos = missionSuccessAPI.listRepos( token,olleego.getInt("user_mission_id",0), temp,missionSuccess);
        diaryPos.enqueue(new Callback<MissionsModel>() {
            @Override
            public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                if(response.isSuccessful()) {

                    if(types == 1) {
                        missionSuccessDialog = new MissionSuccessDialog(EtcMissionActivity.this, "etc" , 2);
                        missionSuccessDialog.show();
                    } else {

                        missionSuccessDialog = new MissionSuccessDialog(EtcMissionActivity.this, "etc" , 3);
                        missionSuccessDialog.show();
                    }

                    mHandler2.sendEmptyMessage(0);



                }
            }

            @Override
            public void onFailure(Call<MissionsModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }

    public String getlevel(int level, int type) {

        if(type == 1) {
            switch (level) {
                case 216:
                    return "쉬워요";

                case 217:
                    return "보통이에요";

                case 218:
                    return "조금 어려워요.";

                case 219:
                    return "어려워요.";
            }
        }
        else if(type==2) {
            switch (level) {
                case 260:
                    return "쉬워요";

                case 261:
                    return "보통이에요";

                case 262:
                    return "조금 어려워요.";

                case 263:
                    return "어려워요.";
            }
        }

        return "";
    }


    public String gettime(int time, int type) {

        if(type == 1) {
            switch (time) {

                case 220:
                    return "즉시완료";
                case 221:
                    return "하루종일";
                case 222:
                    return "반나절";
                case 223:
                    return "10분";
                case 224:
                    return "20분";
                case 225:
                    return "30분";
                case 226:
                    return "40분";
                case 227:
                    return "50분";
                case 228:
                    return "60분";
                case 229:
                    return "90분";
            }
        }
        else if(type == 2) {
            switch (time) {

                case 264:
                    return "즉시완료";
                case 265:
                    return "하루종일";
                case 266:
                    return "오전시간";
                case 267:
                    return "저녁시간";
                case 268:
                    return "10분";
                case 269:
                    return "20분";
                case 270:
                    return "30분";
                case 271:
                    return "40분";
                case 272:
                    return "50분";
                case 273:
                    return "60분";
                case 274:
                    return "90분";
            }
        }

        return "";
    }
    Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            if(gg ==0){
                missionSuccessDialog.dismiss();
                mHandler2.removeMessages(0);
                if(types==1) {

                    setResult(2);
                } else if(types==2) {

                    setResult(3);
                }
                finish();
            } else {
                gg--;
            }




            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler2.sendEmptyMessageDelayed(0,1000);
        }
    };


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
