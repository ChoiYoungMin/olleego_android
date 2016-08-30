package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.DiaryAddAPI;
import com.edn.olleego.server.request.DiaryAdd;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
 * Created by Antonio on 2016-07-27.
 */
public class DiaryWaterAddDialog extends Dialog{

    @BindView(R.id.water_add)
    ImageView water_add;
    @BindView(R.id.water_remove)
    ImageView water_remove;

    @BindView(R.id.water_text)
    TextView water_text;



    Boolean type = false;
    int water;
    String token;
    int user_id;
    String day;

    float sleep;
    int walking;




    public DiaryWaterAddDialog(Context context, String token, int user_id, String day, int nowWater, float sleep, int walking) {
        super(context);

        this.token = token;
        this.user_id = user_id;
        this.day = day+ " 00:00:00";
        this.water = nowWater/250;
        this.sleep = sleep;
        this.walking = walking;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_diary_water);
        ButterKnife.bind(this);

        water_text.setText(String.valueOf(water));

    }

    @OnClick(R.id.water_add_layout)
    void water_add_click() {
        water++;
        water_text.setText(String.valueOf(water));
    }

    @OnClick(R.id.water_remove_layout)
    void water_remove_click() {
        water--;
        water_text.setText(String.valueOf(water));
    }

    @OnClick(R.id.dialog_diary_water_add)
    void dialog_diary_water_add_click() {

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

        String tokens = "olleego " + token;
        DiaryAddAPI diaryAddAPI = retrofit_diary.create(DiaryAddAPI.class);

        DiaryAdd diaryAdd = new DiaryAdd();

        diaryAdd.DiaryWaterAdd(user_id,day,water*250,sleep,walking);

        final Call<MissionsModel> diaryPos = diaryAddAPI.listRepos(tokens, "water",diaryAdd);

        diaryPos.enqueue(new Callback<MissionsModel>() {
            @Override
            public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(),"추가 완료", Toast.LENGTH_SHORT).show();
                    type = true;
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<MissionsModel> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.dialog_diary_water_no)
    void exit() {
        type = false;
        dismiss();
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


    public boolean getType() {
        return type;
    }

    public int getWater() {
        return water;
    }
}
