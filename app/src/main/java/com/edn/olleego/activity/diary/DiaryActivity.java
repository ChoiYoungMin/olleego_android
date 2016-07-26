package com.edn.olleego.activity.diary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.calendar.Calender_Adapter;
import com.edn.olleego.model.CalendarModel;
import com.edn.olleego.server.CalenderAPI;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast toast;
    private TextView tvDate;

    private Calender_Adapter gridAdapter, gridAdapter2;

    private ArrayList<String> dayList, dayList2;

    private GridView gridView, gridView2;

    private Calendar mCal;
    public int a,b;


    Retrofit retrofit;
    private static OkHttpClient client;

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;

    public CalendarModel calendarModels;


    long now ;

    Date date;



    SimpleDateFormat curYearFormat;

    SimpleDateFormat curMonthFormat;

    SimpleDateFormat curDayFormat;
    int dayNum;


    String su = null;
    String day1= null;
    String day2= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);

        tvDate = (TextView) findViewById(R.id.tv_date);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView2 = (GridView) findViewById(R.id.gridview2);



        // 오늘에 날짜를 세팅 해준다.

        now = System.currentTimeMillis();

        date = new Date(now);

        //연,월,일을 따로 저장

        curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);


        //현재 날짜 텍스트뷰에 뿌려줌

        tvDate.setText(curYearFormat.format(date) + "년 " + curMonthFormat.format(date) + "월");


        //gridview 요일 표시

        dayList = new ArrayList<String>();
        dayList2 = new ArrayList<String>();
        dayList2.add("일");
        dayList2.add("월");
        dayList2.add("화");
        dayList2.add("수");
        dayList2.add("목");
        dayList2.add("금");
        dayList2.add("토");



        mCal = Calendar.getInstance();


        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        a = Integer.parseInt(curYearFormat.format(date));
        b = Integer.parseInt(curMonthFormat.format(date));

        mCal.set(a, b - 1, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        // 사용자 다이어리 데이터를 가져온다.
        onCalendar();

    }

    @OnItemClick(R.id.gridview)
    void calender_click(int position) {

        day1= String.valueOf(a);
        day2= String.valueOf(b);


        if (Integer.parseInt(day2) < 10) day2 = "0"+day2;

        if(Integer.parseInt(gridAdapter.getItem(position)) < 10) {
            su = day1+day2+"0"+gridAdapter.getItem(position);
        } else {
            su = day1+day2+gridAdapter.getItem(position);
        }

        Toast.makeText(getApplicationContext(), su, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.prev)
    void prev() {

        b -= 1;
        if(b == 0){
            a -= 1;
            b = 12;
        }

        mCal.set(a, b - 1, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        dayList = new ArrayList<String>();
        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        tvDate.setText(a+"년 "+b+"월");


        day1= String.valueOf(a);
        day2= String.valueOf(b);

        if (Integer.parseInt(day2) < 10) day2 = "0"+day2;

      //  gridAdapter = new Calender_Adapter(getApplicationContext(), dayList, "0", dayNum, calendarModels, day1+day2);

        gridView.setAdapter(gridAdapter);


    }

    @OnClick(R.id.next)
    void next() {

        b += 1;
        if(b == 13){
            a += 1;
            b = 1;
        }

        mCal.set(a, b - 1, 1);

        dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        dayList = new ArrayList<String>();
        //1일 - 요일 매칭 시키기 위해 공백 add

        for (int i = 1; i < dayNum; i++) {

            dayList.add("");

        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);


        tvDate.setText(a+"년 "+b+"월");

        day1= String.valueOf(a);
        day2= String.valueOf(b);

        if (Integer.parseInt(day2) < 10) day2 = "0"+day2;
        //gridAdapter = new Calender_Adapter(getApplicationContext(), dayList, "0", dayNum, calendarModels, day1+day2);

        gridView.setAdapter(gridAdapter);

    }


    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);


        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));

        }


    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(getApplicationContext(),
                    "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {

            finishAffinity();
            toast.cancel();
        }
    }


    public void onCalendar() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://olleego.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CalenderAPI calenderAPI = retrofit.create(CalenderAPI.class);
        //Calender calender = new Calender("mdjk1@naver.com","20160610");
        final Call<List<CalendarModel>> repos = calenderAPI.listRepos("mdjk1@naver.com","20160610");

        repos.enqueue(new Callback<List<CalendarModel>>() {
            @Override
            public void onResponse(Call<List<CalendarModel>> call, Response<List<CalendarModel>> response) {
                //calendarModels = response.body();

               // gridAdapter2 = new Calender_Adapter(getApplicationContext(), dayList2, "1", 0, calendarModels, curYearFormat.format(date)+curMonthFormat.format(date));
                //gridAdapter = new Calender_Adapter(getApplicationContext(), dayList, "0", dayNum, calendarModels, curYearFormat.format(date)+curMonthFormat.format(date));

                gridView2.setAdapter(gridAdapter2);
                gridView.setAdapter(gridAdapter);

            }

            @Override
            public void onFailure(Call<List<CalendarModel>> call, Throwable t) {

            }
        });
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