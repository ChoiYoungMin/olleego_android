package com.edn.olleego.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.common.BaseRoundCornerProgressBar;
import com.edn.olleego.R;
import com.edn.olleego.activity.login.LoginActivity;
import com.edn.olleego.adapter.main.MainMiddleViewPagerAdapter;
import com.edn.olleego.adapter.main.MainTopViewPagerAdapter;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.server.DiaryAPI;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Antonio on 2016-06-23.
 */
public class Home_Fragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    int today_com = 0;

    SharedPreferences olleego_SP;
    CircleProgressBar circleProgressBar;

    ViewPager viewPager, viewPager2;
    CircleIndicator circleIndicator, circleIndicator2;


    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;


    Retrofit retrofit_home;


    Retrofit retrofit_diary;
    Retrofit retrofit_lifes;
    Retrofit retrofit_foods;


    MainMiddleViewPagerAdapter mainMiddleViewPagerAdapter;
    MainTopViewPagerAdapter mainTopViewPagerAdapter;
    View rootView;
    int waters;

    RoundCornerProgressBar main_today_bar ;
    TextView main_today_bar_text ;

    boolean replay;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == 1 || resultCode == 2|| resultCode == 3) {
            today_com= 0;
            if(olleego_SP.getString("user_mission_today_exgroup_complete", "").equals("true")) {
                today_com++;
            }
            if(olleego_SP.getString("user_mission_today_life_complete", "").equals("true")) {
                today_com++;
            }
            if(olleego_SP.getString("user_mission_today_food_complete", "").equals("true")) {
                today_com++;
            }

            final float temp = (float)(((double)today_com / (double)3 ) * 100);
            main_today_bar.setProgress(temp);
            main_today_bar.setSecondaryProgress(temp);
            main_today_bar_text.setText(String.valueOf((int)temp)+"%");


            viewPager.removeAllViews();

            viewPager.setAdapter(mainTopViewPagerAdapter);
            mainTopViewPagerAdapter.notifyDataSetChanged();
            circleIndicator.setViewPager(viewPager);

            replay = true;

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        ButterKnife.bind(this,rootView);
        actionbar_init();
        olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);

        final TextView water = (TextView) rootView.findViewById(R.id.main_diary_water);


        rootView.findViewById(R.id.main_diary_water_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waters = waters -1;
                water.setText(String.valueOf(waters));
            }
        });
        rootView.findViewById(R.id.main_diary_water_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waters = waters +1;
                water.setText(String.valueOf(waters));
            }
        });

        //initSP();




        /*
        RoundCornerProgressBar progress1 = (RoundCornerProgressBar) rootView.findViewById(R.id.progress22);
        progress1.setProgressColor(Color.parseColor("#ed3b27"));
        progress1.setProgressBackgroundColor(Color.parseColor("#808080"));
        progress1.setMax(100);
        progress1.setProgress(45);
*/

        //로그인 상태
        if(olleego_SP.getString("login_chk", "").equals("true")) {


            rootView.findViewById(R.id.main_login_yes).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.main_login_no).setVisibility(View.GONE);



            viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
            viewPager2 = (ViewPager) rootView.findViewById(R.id.viewPager2);
            circleIndicator = (CircleIndicator) rootView.findViewById(R.id.inco);
            circleIndicator2 = (CircleIndicator) rootView.findViewById(R.id.inco2);


            viewPager.removeAllViews();
            mainTopViewPagerAdapter = new MainTopViewPagerAdapter(inflater, olleego_SP, viewPager2, getContext());
            viewPager.setAdapter(mainTopViewPagerAdapter);
            mainTopViewPagerAdapter.notifyDataSetChanged();
            circleIndicator.setViewPager(viewPager);



            today_diary();

            main_today_bar = (RoundCornerProgressBar)rootView.findViewById(R.id.main_today_bar);
            main_today_bar_text = (TextView) rootView.findViewById(R.id.main_today_bar_text);



            if(replay == true) {

            } else {
                today_com= 0;
                if(olleego_SP.getString("user_mission_today_exgroup_complete", "").equals("true")) {
                    today_com++;
                }
                if(olleego_SP.getString("user_mission_today_life_complete", "").equals("true")) {
                    today_com++;
                }
                if(olleego_SP.getString("user_mission_today_food_complete", "").equals("true")) {
                    today_com++;
                }

                final float temp = (float)(((double)today_com / (double)3 ) * 100);
                main_today_bar.setProgress(temp);
                main_today_bar.setSecondaryProgress(temp);
                main_today_bar_text.setText(String.valueOf((int)temp)+"%");
            }
            replay = false;



        }

        // 비로그인 상태
        else {
            Toast.makeText(getContext(),"비로그인중", Toast.LENGTH_SHORT).show();
            viewPager2 = (ViewPager) rootView.findViewById(R.id.viewPager2);
            viewPager2.removeAllViews();
            mainMiddleViewPagerAdapter = new MainMiddleViewPagerAdapter(inflater,false, getContext() );
            viewPager2.setAdapter(mainMiddleViewPagerAdapter);
            mainMiddleViewPagerAdapter.notifyDataSetChanged();


            rootView.findViewById(R.id.main_login_yes).setVisibility(View.GONE);

            rootView.findViewById(R.id.main_login_no).setVisibility(View.VISIBLE);

            rootView.findViewById(R.id.main_login_go).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
        }


        return rootView;
    }

    public void initSP() {

        SharedPreferences.Editor editor = olleego_SP.edit();
        editor.remove("user_mission_today_food");
        editor.remove("user_mission_today_life");
        editor.remove("user_mission_today_exgroup");
        editor.remove("user_mission_today_rest");
        editor.commit();

    }

    public void actionbar_init() {

        //getActivity().findViewById(R.id.toolbar_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_left_menu).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.toolbar_right_menu).setVisibility(View.VISIBLE);


    }









    public void today_diary() {
        retrofit_diary = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        Date date = new Date();

        String token = "ollego " + olleego_SP.getString("login_token", "");;
        DiaryAPI diaryAPI = retrofit_diary.create(DiaryAPI.class);

        final Call<DiaryModel> diaryPos = diaryAPI.listRepos(dateFormat.format(date), token);

        diaryPos.enqueue(new Callback<DiaryModel>() {
            @Override
            public void onResponse(Call<DiaryModel> call, Response<DiaryModel> response) {

                if(response.isSuccessful()) {

                    TextView sleep = (TextView) rootView.findViewById(R.id.main_diary_sleep);
                    TextView walking = (TextView) rootView.findViewById(R.id.main_diary_walking);
                    float time;
                    int walkings;

                    if (response.body().getResult().getSleep() == 0) {
                        time = (float) 00.00;
                    } else {
                        time = response.body().getResult().getSleep();
                    }
                    if (response.body().getResult().getWalking() == 0) {

                        walkings = 0;
                    } else {
                        walkings = response.body().getResult().getWalking();
                    }

                    sleep.setText(String.valueOf(time));
                    walking.setText(String.valueOf(walkings));


                    try {

                        for (int a = 0; a < response.body().getResult().getFood().size(); a++) {

                            switch (response.body().getResult().getFood().get(a).getSort()) {
                                case "아침":
                                    rootView.findViewById(R.id.main_diary_morning).setVisibility(View.VISIBLE);
                                    break;
                                case "점심":
                                    rootView.findViewById(R.id.main_diary_lunch).setVisibility(View.VISIBLE);
                                    break;
                                case "저녁":
                                    rootView.findViewById(R.id.main_diary_dinner).setVisibility(View.VISIBLE);
                                    break;
                                case "간식":
                                    rootView.findViewById(R.id.main_diary_snack).setVisibility(View.VISIBLE);
                                    break;

                                default:
                                    break;

                            }

                        }

                        rootView.findViewById(R.id.main_diary_food_no).setVisibility(View.GONE);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        rootView.findViewById(R.id.main_diary_food_no).setVisibility(View.VISIBLE);
                    }


                } else {
                    rootView.findViewById(R.id.main_diary_food_no).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<DiaryModel> call, Throwable t) {

            }
        });
    }
}