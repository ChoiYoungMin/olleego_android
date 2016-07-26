package com.edn.olleego.fragment.diary;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.calendar.Calender_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.CalendarModel;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.DiaryMonthModel;
import com.edn.olleego.server.CalenderAPI;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.DiaryMonthAPI;

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

import butterknife.BindView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Diary_Fragment extends Fragment {

    @BindView(R.id.diary_walking_icon)
    ImageView diary_walking_icon;

    @BindView(R.id.diary_walking_text)
    TextView diary_walking_text;

    @BindView(R.id.diary_food_icon)
    ImageView diary_food_icon;

    @BindView(R.id.diary_food_text)
    TextView diary_food_text;

    @BindView(R.id.diary_sleep_icon)
    ImageView diary_sleep_icon;

    @BindView(R.id.diary_sleep_text)
    TextView diary_sleep_text;

    @BindView(R.id.diary_water_icon)
    ImageView diary_water_icon;

    @BindView(R.id.diary_water_text)
    TextView diary_water_text;



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

    public DiaryMonthModel diaryMonthModels;


    long now ;

    Date date;



    SimpleDateFormat curYearFormat;

    SimpleDateFormat curMonthFormat;

    SimpleDateFormat curDayFormat;
    int dayNum;


    String su = null;
    String day1= null;
    String day2= null;


    SharedPreferences Olleego_SP;
    View rootView;

    TextView daytext;

    public Diary_Fragment(SharedPreferences Olleego_SP) {
        // Required empty public constructor
        this.Olleego_SP = Olleego_SP;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, rootView);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);

        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView2 = (GridView) rootView.findViewById(R.id.gridview2);
        daytext = (TextView)rootView.findViewById(R.id.diary_day);


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
        dayList2.add("S");
        dayList2.add("M");
        dayList2.add("T");
        dayList2.add("W");
        dayList2.add("T");
        dayList2.add("F");
        dayList2.add("S");



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
        onCalendar(curYearFormat.format(date) + "-" + curMonthFormat.format(date), curYearFormat.format(date) + curMonthFormat.format(date));

        //
        daydetail(curMonthFormat.format(date)+"월 "+curDayFormat.format(date)+"일", curYearFormat.format(date)+"-"+curMonthFormat.format(date)+"-"+curDayFormat.format(date));

        return rootView;
    }



    @OnItemClick(R.id.gridview)
    void calender_click(int position) {

        day1= String.valueOf(a);
        day2= String.valueOf(b);


        if (Integer.parseInt(day2) < 10) day2 = "0"+day2;


        if(Integer.parseInt(gridAdapter.getItem(position)) < 10) {
            su = day2+"월 "+"0"+gridAdapter.getItem(position)+"일";
        } else {
            su = day2+"월 "+gridAdapter.getItem(position)+"일";
        }

        String day3;

        if(Integer.parseInt(gridAdapter.getItem(position)) < 10) {
            day3 = "0"+gridAdapter.getItem(position);
        } else {
            day3 = gridAdapter.getItem(position);
        }

        daydetail(su, day1+"-"+day2+"-"+day3);
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

        String day_prev = day1+"-"+day2;
        onCalendar(day_prev, day1+day2);


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

        String day_prev = day1+"-"+day2;
        onCalendar(day_prev, day1+day2);

    }

    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);


        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));

        }




    }

    public void onCalendar(String day, final String day_type) {

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
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        DiaryMonthAPI calenderAPI = retrofit.create(DiaryMonthAPI.class);
        //Calender calender = new Calender("mdjk1@naver.com","20160610");
        String token = "olleego " + Olleego_SP.getString("login_token", "");
        String days = day;
        final Call<DiaryMonthModel> repos = calenderAPI.listRepos(token, days);

        repos.enqueue(new Callback<DiaryMonthModel>() {
            @Override
            public void onResponse(Call<DiaryMonthModel> call, Response<DiaryMonthModel> response) {
                diaryMonthModels = response.body();

                gridAdapter2 = new Calender_Adapter(getActivity(), dayList2, "1", 0, diaryMonthModels, day_type);
                gridAdapter = new Calender_Adapter(getActivity(), dayList, "0", dayNum, diaryMonthModels, day_type);

                gridView2.setAdapter(gridAdapter2);
                gridView.setAdapter(gridAdapter);


            }

            @Override
            public void onFailure(Call<DiaryMonthModel> call, Throwable t) {

            }
        });

        /*

         */
    }



    private void daydetail(String day, String day2) {
        Log.e("sdsad", day2);

        daytext.setText(day);

        Retrofit retrofit_diary = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String token = "olleego " + Olleego_SP.getString("login_token", "");
        DiaryAPI diaryAPI = retrofit_diary.create(DiaryAPI.class);

        final Call<DiaryModel> diaryPos = diaryAPI.listRepos(day2, token);

        diaryPos.enqueue(new Callback<DiaryModel>() {
            @Override
            public void onResponse(Call<DiaryModel> call, Response<DiaryModel> response) {

                String strColors = "#606060";
                String strColorsUn = "#cbcbc9";

                if(response.isSuccessful()) {

                        if (response.body().getResult().getWalking() != 0) {
                            diary_walking_icon.setImageResource(R.drawable.diary_step_1);
                            diary_walking_text.setTextColor(Color.parseColor(strColors));
                            diary_walking_text.setText(String.valueOf(response.body().getResult().getWalking()));
                        } else {
                            diary_walking_icon.setImageResource(R.drawable.diary_step_1_unchecked);
                            diary_walking_text.setTextColor(Color.parseColor(strColorsUn));
                            diary_walking_text.setText("0");
                        }


                        if (response.body().getResult().getWater() != 0) {
                            diary_water_icon.setImageResource(R.drawable.diary_water_3);
                            diary_water_text.setTextColor(Color.parseColor(strColors));
                            diary_water_text.setText(String.valueOf(response.body().getResult().getWater()) + " 잔");
                        } else {
                            diary_water_icon.setImageResource(R.drawable.diary_water_3_unchecked);
                            diary_water_text.setTextColor(Color.parseColor(strColorsUn));
                            diary_water_text.setText("0 잔");
                        }


                    if(response.body().getResult().getSleep() != 0) {
                        diary_sleep_icon.setImageResource(R.drawable.diary_sleep_2);
                        diary_sleep_text.setTextColor(Color.parseColor(strColors));
                        diary_sleep_text.setText(String.valueOf(response.body().getResult().getWater()));
                    } else {
                        diary_sleep_icon.setImageResource(R.drawable.diary_sleep_2_unchecked);
                        diary_sleep_text.setTextColor(Color.parseColor(strColorsUn));
                        diary_sleep_text.setText("00:00");
                    }

                    if(response.body().getResult().getFood().size() != 0) {
                        diary_food_icon.setImageResource(R.drawable.diary_meal_4);
                        diary_food_text.setVisibility(View.GONE);
                    } else {
                        diary_food_icon.setImageResource(R.drawable.diary_meal_4_unchecked);
                        diary_food_text.setVisibility(View.VISIBLE);
                    }



                }
                else if(response.code() == 404) {
                    diary_walking_icon.setImageResource(R.drawable.diary_step_1_unchecked);
                    diary_walking_text.setTextColor(Color.parseColor(strColorsUn));
                    diary_walking_text.setText("0");
                    diary_water_icon.setImageResource(R.drawable.diary_water_3_unchecked);
                    diary_water_text.setTextColor(Color.parseColor(strColorsUn));
                    diary_water_text.setText("0 잔");
                    diary_sleep_icon.setImageResource(R.drawable.diary_sleep_2_unchecked);
                    diary_sleep_text.setTextColor(Color.parseColor(strColorsUn));
                    diary_sleep_text.setText("00:00");
                    diary_food_icon.setImageResource(R.drawable.diary_meal_4_unchecked);
                    diary_food_text.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<DiaryModel> call, Throwable t) {

            }
        });


    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);
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
