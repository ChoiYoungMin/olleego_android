package com.edn.olleego.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.edn.olleego.R;
import com.edn.olleego.activity.mission.missionlist.MissionListActivity;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.common.Percent;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.custom.VerticalProgressBar;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.model.UserMissionModel;
import com.edn.olleego.server.UserMissionAPI;
import com.edn.olleego.server.UserRestMissionAPI;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MainTopViewPagerAdapter extends PagerAdapter {

    boolean mission;

    int complete = 0;
    int i = 0;

    private LayoutInflater inflater;
    private final Random random = new Random();
    private int mSize;

    Retrofit retrofit;

    SharedPreferences olleego_SP;

    String mission_title ;
    String mission_myday;
    int mission_allday;
    int nowDate;
    long diffDays;
    Context context;
    ViewPager viewPager;
    LoadingBarDialog loadingBarDialog;
    long diff;
    public MainTopViewPagerAdapter(LayoutInflater inflater, SharedPreferences olleego_SP, ViewPager viewPager, Context context, LoadingBarDialog loadingBarDialog) {
        this.inflater = inflater;
        this.olleego_SP = olleego_SP;
        this.viewPager = viewPager;
        this.context = context;
        mSize = 2;
        this.loadingBarDialog = loadingBarDialog;
    }

    public MainTopViewPagerAdapter(int count) {
        mSize = count;
    }

    @Override public int getCount() {
        return mSize;
    }


    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override public Object instantiateItem(ViewGroup view, int position) {




        View convertView = null;
        if(position == 0) {


            convertView = inflater.inflate(R.layout.item_main_top_one, null);

            final TextView main_top_mission_title = (TextView) convertView.findViewById(R.id.main_top_mission_title);
            final TextView main_top_mission_day= (TextView) convertView.findViewById(R.id.main_top_mission_myday);
            final TextView main_top_mission_allday= (TextView) convertView.findViewById(R.id.main_top_mission_allday);
            final TextView main_top_mission_detail = (TextView)convertView.findViewById(R.id.main_top_mission_detail);

            mission_title = null;
            mission_myday= null;
            mission_allday= 0;




            view.addView(convertView);

            final CircleProgressBar circleProgressBar = (CircleProgressBar) convertView.findViewById(R.id.main_top_bar);
            //circleProgressBar.setColor(R.color.com_facebook_blue);
            final TextView main_top_bar_text = (TextView)convertView.findViewById(R.id.main_top_bar_text);



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

            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            UserMissionAPI missionAPI = retrofit.create(UserMissionAPI.class);
            String token = "olleego " + olleego_SP.getString("login_token", "");
            final Call<UserMissionModel> repos2 = missionAPI.listRepos(token);
            final View ConvertView2 = convertView;
            repos2.enqueue(new Callback<UserMissionModel>() {
                @Override
                public void onResponse(Call<UserMissionModel> call, Response<UserMissionModel> response) {
                    SharedPreferences.Editor editor = olleego_SP.edit();

                    if(response.code() == 404) {
                        ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                        ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                        editor.putString("user_mission_today_onoff", "off");

                    }
                    else if(response.code() == 500) {
                        ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                        ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                        editor.putString("user_mission_today_onoff", "off");
                    }
                    else if (response.isSuccessful()) {


                        try {
                            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
                            Date date = new Date();
                            nowDate =  Integer.parseInt(dateFormat.format(date));

                            // int myDate = Integer.parseInt();

                            //nowDate = nowDate - myDate;
                            Date beginDate = null;
                            Date endDate = null;

                            String a = dateFormat.format(response.body().getResult().getCreated());
                            String b = dateFormat.format(date);
                            beginDate = dateFormat.parse(a);
                            endDate = dateFormat.parse(b);

                            diff = endDate.getTime() - beginDate.getTime();
                            diffDays = (diff / (24 * 60 * 60 * 1000)+1);


                            if(diffDays-1 > response.body().getResult().getMission().getMi_term()*7){ // 임시용 미션 끝
                                ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                                ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                                editor.putString("user_mission_today_onoff", "off");
                            }
                            else {
                                if(response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getRest()== true) {
                                    editor.putString("user_mission_today_onoff", "on");


                                    final boolean comlete22 = response.body().getResult().getMi_days().get((int)diffDays-1).getDay_complete();
                                    if(comlete22 == false) {
                                        complete++;
                                    }
                                    retrofit = new Retrofit.Builder()
                                            .baseUrl(ServerInfo.OLLEEGO_HOST)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();




                                    UserRestMissionAPI userAPI = retrofit.create(UserRestMissionAPI.class);
                                    String token = "olleego " + olleego_SP.getString("login_token", "");
                                    final Call<UserMissionModel> repos2 = userAPI.listRepos(token, response.body().getResult().get_id(), Integer.valueOf((int) diffDays));

                                    repos2.enqueue(new Callback<UserMissionModel>() {
                                        @Override
                                        public void onResponse(Call<UserMissionModel> call, Response<UserMissionModel> response) {
                                            if(response.isSuccessful()){




                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserMissionModel> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    editor.putString("user_mission_today_onoff", "on");
                                    editor.putInt("user_mission_id", response.body().getResult().get_id());
                                    editor.putInt("user_mission_today_food", response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getFood().get_id());
                                    editor.putInt("user_mission_today_life", response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getLife().get_id());


                                    for(int i=0; i< response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getExgroup().size(); i++) {
                                        if(response.body().getResult().getTime() == response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getExgroup().get(i).getTime()) {
                                            editor.putInt("user_mission_today_exgroup", response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getExgroup().get(i).get_id());
                                        } else {
                                        }
                                    }
                                }


                                editor.putString("user_mission_today_rest", String.valueOf(response.body().getResult().getMission().getMi_days().get((int) diffDays-1).getRest()));
                                editor.putInt("user_mission_today_time", response.body().getResult().getTime());



                                ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.VISIBLE);
                                ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.GONE);
                            }


                            editor.commit();



                            mission_title = response.body().getResult().getMission().getTitle();
                            mission_myday = response.body().getResult().getMission().getTitle();
                            //현재 날짜와 계산하여 몇이차인지 확인
                            mission_allday = response.body().getResult().getMission().getMi_term();


                            /*
                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getExgroup().size() == 0) {
                                editor.putString("user_mission_today_exgroup_complete", "false");
                            } else {
                                for (int i = 0; i < response.body().getResult().getMi_days().get((int) (diffDays - 1)).getExgroup().size(); i++) {
                                    if (response.body().getResult().getMi_days().get((int) (diffDays - 1)).getExgroup().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_exgroup", 0)) + ".0")) {
                                        editor.putString("user_mission_today_exgroup_complete", "true");
                                    }
                                }
                            }*/

                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getEx_complete() == true) {
                                editor.putString("user_mission_today_exgroup_complete", "true");
                            } else {
                                editor.putString("user_mission_today_exgroup_complete", "false");
                            }



                            /*
                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getFood().size() == 0) {
                                editor.putString("user_mission_today_food_complete", "false");
                            } else {
                                for (int i = 0; i < response.body().getResult().getMi_days().get((int) (diffDays - 1)).getFood().size(); i++) {
                                    if (response.body().getResult().getMi_days().get((int) (diffDays - 1)).getFood().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_food", 0)) + ".0")) {
                                        editor.putString("user_mission_today_food_complete", "true");
                                    }
                                }
                            }*/

                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getFd_complete() == true) {
                                editor.putString("user_mission_today_food_complete", "true");
                            } else {
                                editor.putString("user_mission_today_food_complete", "false");
                            }



                            /*
                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getLife().size() == 0) {
                                editor.putString("user_mission_today_life_complete", "false");
                            }else {
                                for (int i = 0; i < response.body().getResult().getMi_days().get((int) (diffDays - 1)).getLife().size(); i++) {
                                    if (response.body().getResult().getMi_days().get((int) (diffDays - 1)).getLife().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_life", 0)) + ".0")) {
                                        editor.putString("user_mission_today_life_complete", "true");
                                    }
                                }
                            }*/

                            if(response.body().getResult().getMi_days().get((int) (diffDays-1)).getLf_complete() == true) {
                                editor.putString("user_mission_today_life_complete", "true");
                            } else {
                                editor.putString("user_mission_today_life_complete", "false");
                            }


                            for(int i=0; i<response.body().getResult().getMi_days().size(); i++) {
                                if(response.body().getResult().getMi_days().get(i).getDay_complete() == true) {
                                    complete++;
                                }
                            }
                            //전체미션션

                            float temp = (float) (((double)complete/(double)response.body().getResult().getMi_days().size())*100);

                           circleProgressBar.setProgressWithAnimation(temp);
                            circleProgressBar.setProgress(temp);
                            circleProgressBar.invalidate();
                            //(현재/전체*100)
                            main_top_bar_text.setText(String.valueOf((int)temp+" %"));

                            main_top_mission_title.setText(mission_title);
                            main_top_mission_day.setText(String.valueOf(diffDays));
                            editor.putString("user_mission_today", String.valueOf(diffDays));
                            main_top_mission_allday.setText(String.valueOf((mission_allday*7)));


                            editor.commit();


                            main_top_mission_detail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, MissionListActivity.class);
                                    context.startActivity(intent);
                                }
                            });

                        } catch (IndexOutOfBoundsException e) {
                            ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                            ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                            editor.putString("user_mission_today_onoff", "off");

                            editor.commit();
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e ) {
                            ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                            ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                            editor.putString("user_mission_today_rest", "off");

                            editor.commit();
                        }




                    }




                    boolean type = false;
                    if(olleego_SP.getString("user_mission_today_onoff", "").equals("on")) {
                        type = true;


                        viewPager.setAdapter(new MainMiddleViewPagerAdapter(inflater, type, 1 ,olleego_SP,context, loadingBarDialog));
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                if(i==0) {
                                    viewPager.setCurrentItem(1,false);
                                    i++;
                                }
                            }

                            @Override
                            public void onPageSelected(int position) {

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });

                    } else {
                        type = false;


                        MainMiddleViewPagerAdapter mainMiddleViewPagerAdapter = new MainMiddleViewPagerAdapter(inflater, type, context , loadingBarDialog);
                        viewPager.setAdapter(mainMiddleViewPagerAdapter);


                    }


                }

                @Override
                public void onFailure(Call<UserMissionModel> call, Throwable t) {
                    //서버 연결 ㄴㄴ


                }


            });


        } else if(position == 1) {
            convertView = inflater.inflate(R.layout.item_main_top_two, null);

            if(olleego_SP.getFloat("user_bmi", 0) != 0) {
                float i_bim = olleego_SP.getFloat("user_bmi", 0);
                int i_health_temp = olleego_SP.getInt("user_health_temp", 0);


                String s_bim = null;
                String s_health_temp = null;

                if (i_bim > 0 && i_bim < 18.5) {
                    s_bim = String.valueOf(i_bim + " (저체중)");
                } else if (i_bim >= 18.5 && i_bim < 23) {
                    s_bim = String.valueOf(i_bim + " (정상)");
                } else if (i_bim >= 23 && i_bim < 25) {
                    s_bim = String.valueOf(i_bim + " (과체중)");
                } else if (i_bim >= 25 && i_bim < 30) {
                    s_bim = String.valueOf(i_bim + " (경도비만)");
                } else {
                    s_bim = String.valueOf(i_bim + " (고도비만)");
                }

                VerticalProgressBar progressBar = (VerticalProgressBar) convertView.findViewById(R.id.main_top_health_bar);


                if (i_health_temp > 0 && i_health_temp < 20) {
                    s_health_temp = "건강위험";
                    progressBar.setCurrentValue(Percent.PERCENT_10);
                } else if (i_health_temp >= 20 && i_health_temp < 40) {
                    s_health_temp = "주의";
                    progressBar.setCurrentValue(Percent.PERCENT_25);
                } else if (i_health_temp >= 40 && i_health_temp < 60) {
                    s_health_temp = "건강관심";
                    progressBar.setCurrentValue(Percent.PERCENT_50);
                } else if (i_health_temp >= 60 && i_health_temp < 80) {
                    s_health_temp = "건강양호";
                    progressBar.setCurrentValue(Percent.PERCENT_75);
                } else {
                    s_health_temp = "건강좋음";
                    progressBar.setCurrentValue(Percent.PERCENT_100);
                }


                TextView tv_bmi = (TextView) convertView.findViewById(R.id.main_top_bmi);
                tv_bmi.setText(s_bim);

                TextView health_temp = (TextView) convertView.findViewById(R.id.main_top_health_temp);
                health_temp.setText(String.valueOf(i_health_temp) + "˚");

                TextView health_temp2 = (TextView) convertView.findViewById(R.id.main_top_health_temp2);
                health_temp2.setText(s_health_temp);

                convertView.findViewById(R.id.main_top_health_no).setVisibility(View.GONE);
                convertView.findViewById(R.id.main_top_health_yes).setVisibility(View.VISIBLE);
            } else if(olleego_SP.getFloat("user_bmi", 0) == 0) {
                convertView.findViewById(R.id.main_top_health_no).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.main_top_health_yes).setVisibility(View.GONE);

            }

            view.addView(convertView);
        }




        return convertView;
    }



    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
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