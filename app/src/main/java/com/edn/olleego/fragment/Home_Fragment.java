package com.edn.olleego.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.main.MainMiddleViewPagerAdapter;
import com.edn.olleego.adapter.main.MainTopViewPagerAdapter;
import com.edn.olleego.adapter.mission.Mission_Adapter;
import com.edn.olleego.common.CircleProgressBar;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.server.DiaryAPI;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.FoodsAPI;
import com.edn.olleego.server.LifesAPI;

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


    SharedPreferences olleego_SP;
    CircleProgressBar circleProgressBar;

    ViewPager viewPager, viewPager2;
    CircleIndicator circleIndicator, circleIndicator2;


    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;
    Retrofit retrofit;
    View rootView;
    int waters;
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




        /*
        RoundCornerProgressBar progress1 = (RoundCornerProgressBar) rootView.findViewById(R.id.progress22);
        progress1.setProgressColor(Color.parseColor("#ed3b27"));
        progress1.setProgressBackgroundColor(Color.parseColor("#808080"));
        progress1.setMax(100);
        progress1.setProgress(45);
*/

        //로그인 상태
        if(olleego_SP.getString("login_chk", "").equals("true")) {

            viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
            viewPager2 = (ViewPager) rootView.findViewById(R.id.viewPager2);
            circleIndicator = (CircleIndicator) rootView.findViewById(R.id.inco);
            circleIndicator2 = (CircleIndicator) rootView.findViewById(R.id.inco2);


            viewPager.setAdapter(new MainTopViewPagerAdapter(inflater, olleego_SP));
            circleIndicator.setViewPager(viewPager);


            today_mission(inflater);

            today_diary();




        }
        // 비로그인 상태
        else {
            Toast.makeText(getContext(),"비로그인중", Toast.LENGTH_SHORT).show();


        }


        return rootView;
    }


    public void actionbar_init() {

        //getActivity().findViewById(R.id.toolbar_back).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_left_menu).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.toolbar_right_menu).setVisibility(View.VISIBLE);


    }

/*
    @OnClick(R.id.home_mission_choise)
    void mission_btn_click() {

        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        MissionFragment missionFragment = new MissionFragment();
        transaction.setCustomAnimations(R.anim.slide_in_left, 0);
        transaction.replace(R.id.content_main, missionFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Intent intent = new Intent(getActivity(), Mission.class);
        startActivity(intent);
        getActivity().finish();
    }


    @OnClick(R.id.button2)
    void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.button22)
    void logout() {

        SharedPreferences.Editor editor = olleego_SP.edit();
        //editor.remove("login_chk");
        editor.clear();
        editor.commit();
    }

    @OnClick(R.id.main_custom_mission)
    void click() {
        Intent intent = new Intent(getActivity(), MissionCustomized1Activity.class);
        getActivity().startActivity(intent);
    }
    */


    public void today_mission(final LayoutInflater inflater) {


        if(olleego_SP.getString("user_mission_today_rest", "").equals("false")) {

            //운동 조회


            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ExgroupsAPI exgroupsAPI = retrofit.create(ExgroupsAPI.class);
            final Call<ExgroupsModel> repos2 = exgroupsAPI.listRepos(olleego_SP.getInt("user_mission_today_exgroup", 0));

            repos2.enqueue(new Callback<ExgroupsModel>() {
                @Override
                public void onResponse(Call<ExgroupsModel> call, Response<ExgroupsModel> response) {

                    exgroupsModel = response.body();
                    today_foods(inflater);
                }




                @Override
                public void onFailure(Call<ExgroupsModel> call, Throwable t) {

                }
            });
        }

        //쉬는날일경우
        else {
            Toast.makeText(getContext(),"쉬는날임다", Toast.LENGTH_SHORT).show();
        }
    }


    public void today_foods( final LayoutInflater inflater) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        FoodsAPI foodsAPI = retrofit.create(FoodsAPI.class);
        final Call<FoodsModel> foodPos = foodsAPI.listRepos(olleego_SP.getInt("user_mission_today_food", 0));

        foodPos.enqueue(new Callback<FoodsModel>() {
            @Override
            public void onResponse(Call<FoodsModel> call, Response<FoodsModel> response) {
                foodsModel = response.body();
                today_lifes(inflater);
            }

            @Override
            public void onFailure(Call<FoodsModel> call, Throwable t) {

            }
        });
    }


    public void today_lifes(final LayoutInflater inflater) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        LifesAPI lifesAPI = retrofit.create(LifesAPI.class);
        final Call<LifesModel> lifePos = lifesAPI.listRepos(olleego_SP.getInt("user_mission_today_life", 0));

        lifePos.enqueue(new Callback<LifesModel>() {
            @Override
            public void onResponse(Call<LifesModel> call, Response<LifesModel> response) {
                lifesModel = response.body();
                viewPager2.setAdapter(new MainMiddleViewPagerAdapter(inflater,exgroupsModel,foodsModel,lifesModel));
                viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                circleIndicator2.setViewPager(viewPager2);
            }

            @Override
            public void onFailure(Call<LifesModel> call, Throwable t) {

            }
        });
    }


    public void today_diary() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        Date date = new Date();

        String token = "ollego " + olleego_SP.getString("login_token", "");;
        DiaryAPI diaryAPI = retrofit.create(DiaryAPI.class);

        final Call<DiaryModel> diaryPos = diaryAPI.listRepos(dateFormat.format(date), token);
        Log.e("zz", String.valueOf(diaryPos.request()));

        diaryPos.enqueue(new Callback<DiaryModel>() {
            @Override
            public void onResponse(Call<DiaryModel> call, Response<DiaryModel> response) {

                TextView sleep = (TextView) rootView.findViewById(R.id.main_diary_sleep);
                sleep.setText(response.body().getResult().getSleep());

                TextView walking = (TextView) rootView.findViewById(R.id.main_diary_walking);
                walking.setText(response.body().getResult().getWalking());



                for (int a=0; a<response.body().getResult().getFood().size(); a++) {

                    switch (response.body().getResult().getFood().get(a).getSort().get_id()) {
                        case 1:
                            rootView.findViewById(R.id.main_diary_morning).setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            rootView.findViewById(R.id.main_diary_lunch).setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            rootView.findViewById(R.id.main_diary_dinner).setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            rootView.findViewById(R.id.main_diary_snack).setVisibility(View.VISIBLE);
                            break;

                        default:
                            break;

                    }

                }
            }

            @Override
            public void onFailure(Call<DiaryModel> call, Throwable t) {

            }
        });
    }
}