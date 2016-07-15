package com.edn.olleego.adapter.main;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.edn.olleego.R;
import com.edn.olleego.common.CircleProgressBar;
import com.edn.olleego.common.Percent;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.common.VerticalProgressBar;
import com.edn.olleego.model.mission.MissionModel;
import com.edn.olleego.model.user.UserModel;
import com.edn.olleego.server.MissionAPI;
import com.edn.olleego.server.UserAPI;

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

    long diff;
    public MainTopViewPagerAdapter(LayoutInflater inflater, SharedPreferences olleego_SP) {
        this.inflater = inflater;
        this.olleego_SP = olleego_SP;
        mSize = 2;
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

            mission_title = null;
            mission_myday= null;
            mission_allday= 0;




            view.addView(convertView);

            CircleProgressBar circleProgressBar = (CircleProgressBar) convertView.findViewById(R.id.main_top_bar);
            RoundCornerProgressBar roundCornerProgressBar = (RoundCornerProgressBar) convertView.findViewById(R.id.bars);
            //circleProgressBar.setColor(R.color.com_facebook_blue);


            circleProgressBar.setProgressWithAnimation(50);
            circleProgressBar.setProgress(50);
            circleProgressBar.invalidate();


            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            MissionAPI missionAPI = retrofit.create(MissionAPI.class);
            String token = "ollego " + olleego_SP.getString("login_token", "");;
            final Call<MissionModel> repos2 = missionAPI.listRepos(token);

            repos2.enqueue(new Callback<MissionModel>() {
                @Override
                public void onResponse(Call<MissionModel> call, Response<MissionModel> response) {
                    SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
                    Date date = new Date();
                    nowDate =  Integer.parseInt(dateFormat.format(date));
                   // int myDate = Integer.parseInt();

                    //nowDate = nowDate - myDate;

                    Date beginDate = null;
                    Date endDate = null;
                    try {
                        String a = dateFormat.format(response.body().getResults().get(0).getMission().getCreated());
                        String b = dateFormat.format(date);
                        beginDate = dateFormat.parse(a);
                        endDate = dateFormat.parse(b);

                        diff = endDate.getTime() - beginDate.getTime();
                        diffDays = diff / (24 * 60 * 60 * 1000);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    mission_title = response.body().getResults().get(0).getMission().getTitle();
                    mission_myday = response.body().getResults().get(0).getMission().getTitle();
                    //현재 날짜와 계산하여 몇이차인지 확인
                    mission_allday = response.body().getResults().get(0).getMission().getMi_term();


                    main_top_mission_title.setText(mission_title);
                    main_top_mission_day.setText(String.valueOf(diffDays));
                    main_top_mission_allday.setText(String.valueOf((mission_allday*7)));
                }

                @Override
                public void onFailure(Call<MissionModel> call, Throwable t) {

                }


            });


        } else if(position == 1) {
            convertView = inflater.inflate(R.layout.item_main_top_two, null);
            float i_bim = olleego_SP.getFloat("user_bmi",0);
            int i_health_temp = olleego_SP.getInt("user_health_temp",0);


            String s_bim = null;
            String s_health_temp = null;

            if(i_bim > 0 && i_bim < 18.5) {
                s_bim = String.valueOf(i_bim +" (저체중)");
            } else if (i_bim >= 18.5 && i_bim < 23) {
                s_bim = String.valueOf(i_bim +" (정상)");
            } else if(i_bim >= 23 && i_bim < 25) {
                s_bim = String.valueOf(i_bim +" (과체중)");
            }else if(i_bim >= 25 && i_bim < 30) {
                s_bim = String.valueOf(i_bim +" (경도비만)");
            }else {
                s_bim = String.valueOf(i_bim +" (고도비만)");
            }

            VerticalProgressBar progressBar = (VerticalProgressBar) convertView.findViewById(R.id.main_top_health_bar);


            if(i_health_temp > 0 && i_health_temp < 20) {
                s_health_temp = "건강위험";
                progressBar.setCurrentValue(Percent.PERCENT_10);
            } else if (i_health_temp >= 20 && i_health_temp < 40) {
                s_health_temp = "주의";
                progressBar.setCurrentValue(Percent.PERCENT_25);
            } else if(i_health_temp >= 40 && i_health_temp < 60) {
                s_health_temp = "건강관심";
                progressBar.setCurrentValue(Percent.PERCENT_50);
            }else if(i_health_temp >= 60 && i_health_temp < 80) {
                s_health_temp = "건강양호";
                progressBar.setCurrentValue(Percent.PERCENT_75);
            }else {
                s_health_temp = "건강좋음";
                progressBar.setCurrentValue(Percent.PERCENT_100);
            }





            view.addView(convertView);

            TextView tv_bmi = (TextView)convertView.findViewById(R.id.main_top_bmi);
            tv_bmi.setText(s_bim);

            TextView health_temp = (TextView)convertView.findViewById(R.id.main_top_health_temp);
            health_temp.setText(String.valueOf(i_health_temp)+ "˚");

            TextView health_temp2 = (TextView)convertView.findViewById(R.id.main_top_health_temp2);
            health_temp2.setText(s_health_temp);
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

}