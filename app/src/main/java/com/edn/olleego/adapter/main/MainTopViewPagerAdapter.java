package com.edn.olleego.adapter.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.edn.olleego.R;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.common.Percent;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.custom.VerticalProgressBar;
import com.edn.olleego.model.UserMissionModel;
import com.edn.olleego.server.UserMissionAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    long diff;
    public MainTopViewPagerAdapter(LayoutInflater inflater, SharedPreferences olleego_SP, ViewPager viewPager, Context context) {
        this.inflater = inflater;
        this.olleego_SP = olleego_SP;
        this.viewPager = viewPager;
        this.context = context;
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

                    } else if (response.isSuccessful()) {


                        try {
                            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
                            Date date = new Date();
                            nowDate =  Integer.parseInt(dateFormat.format(date));

                            // int myDate = Integer.parseInt();

                            //nowDate = nowDate - myDate;
                            Date beginDate = null;
                            Date endDate = null;

                            String a = dateFormat.format(response.body().getResult().get(0).getCreated());
                            String b = dateFormat.format(date);
                            beginDate = dateFormat.parse(a);
                            endDate = dateFormat.parse(b);

                            diff = endDate.getTime() - beginDate.getTime();
                            diffDays = (diff / (24 * 60 * 60 * 1000)+1);


                            if(diffDays-1 > response.body().getResult().get(0).getMission().getMi_term()*7){ // 임시용 미션 끝
                                ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.GONE);
                                ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.VISIBLE);
                                editor.putString("user_mission_today_onoff", "off");
                            }
                            else {
                                if(response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getRest()== true) {

                                } else {
                                    editor.putString("user_mission_today_onoff", "on");
                                    editor.putInt("user_mission_id", response.body().getResult().get(0).get_id());
                                    editor.putInt("user_mission_today_food", response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getFood().get_id());
                                    editor.putInt("user_mission_today_life", response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getLife().get_id());


                                    for(int i=0; i< response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getExgroup().size(); i++) {
                                        if(response.body().getResult().get(0).get_time() == response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getExgroup().get(i).getTime()) {
                                            editor.putInt("user_mission_today_exgroup", response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getExgroup().get(i).get_id());
                                        } else {
                                        }
                                    }
                                }


                                editor.putString("user_mission_today_rest", String.valueOf(response.body().getResult().get(0).getMission().getMi_days().get((int) diffDays-1).getRest()));
                                editor.putInt("user_mission_today_time", response.body().getResult().get(0).get_time());


                                ConvertView2.findViewById(R.id.main_top_mission_yes).setVisibility(View.VISIBLE);
                                ConvertView2.findViewById(R.id.main_top_mission_no).setVisibility(View.GONE);
                            }


                            editor.commit();



                            mission_title = response.body().getResult().get(0).getMission().getTitle();
                            mission_myday = response.body().getResult().get(0).getMission().getTitle();
                            //현재 날짜와 계산하여 몇이차인지 확인
                            mission_allday = response.body().getResult().get(0).getMission().getMi_term();


                            if(response.body().getResult().get(0).getMi_days().get((int) (diffDays-1)).getExgroup().size() == 0) {
                                editor.putString("user_mission_today_exgroup_complete", "false");
                            } else {
                                for (int i = 0; i < response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getExgroup().size(); i++) {
                                    if (response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getExgroup().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_exgroup", 0)) + ".0")) {
                                        editor.putString("user_mission_today_exgroup_complete", "true");
                                    }
                                }
                            }


                            if(response.body().getResult().get(0).getMi_days().get((int) (diffDays-1)).getFood().size() == 0) {
                                editor.putString("user_mission_today_food_complete", "false");
                            } else {
                                for (int i = 0; i < response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getFood().size(); i++) {
                                    if (response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getFood().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_food", 0)) + ".0")) {
                                        editor.putString("user_mission_today_food_complete", "true");
                                    }
                                }
                            }




                            if(response.body().getResult().get(0).getMi_days().get((int) (diffDays-1)).getLife().size() == 0) {
                                editor.putString("user_mission_today_life_complete", "false");
                            }else {
                                for (int i = 0; i < response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getLife().size(); i++) {
                                    if (response.body().getResult().get(0).getMi_days().get((int) (diffDays - 1)).getLife().get(i).toString().equals(String.valueOf(olleego_SP.getInt("user_mission_today_life", 0)) + ".0")) {
                                        editor.putString("user_mission_today_life_complete", "true");
                                    }
                                }
                            }





                            main_top_mission_title.setText(mission_title);
                            main_top_mission_day.setText(String.valueOf(diffDays));
                            editor.putString("user_mission_today", String.valueOf(diffDays));
                            main_top_mission_allday.setText(String.valueOf((mission_allday*7)));

                            editor.commit();

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

                        viewPager.setAdapter(new MainMiddleViewPagerAdapter(inflater, type, 1 ,olleego_SP,context));
                    } else {
                        type = false;

                        MainMiddleViewPagerAdapter mainMiddleViewPagerAdapter = new MainMiddleViewPagerAdapter(inflater, type, context );
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

}