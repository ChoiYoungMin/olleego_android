package com.edn.olleego.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.common.BaseRoundCornerProgressBar;
import com.edn.olleego.R;
import com.edn.olleego.activity.diary.DiaryFoodActivity;
import com.edn.olleego.activity.login.LoginActivity;
import com.edn.olleego.adapter.main.MainMiddleViewPagerAdapter;
import com.edn.olleego.adapter.main.MainTopViewPagerAdapter;
import com.edn.olleego.custom.CircleProgressBar;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.DiarySleepAddDialog;
import com.edn.olleego.dialog.DiaryWaterAddDialog;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.fragment.diary.Diary_Fragment;
import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.server.DiaryAPI;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    float time;
    int walkings;
    int water_temp;
    float sleep_temp;

    SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
    Date date = new Date();

    ProgressBar progressBar;

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
    LoadingBarDialog loadingBarDialog;
    boolean replay;



    @BindView(R.id.main_diary_sleep)
    TextView sleep;
    @BindView(R.id.main_diary_walking)
    TextView walking;
    @BindView(R.id.main_diary_water)
    TextView water;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 5) {
            switch (data.getStringExtra("food_type")) {
                case "아침":
                    rootView.findViewById(R.id.main_diary_morning).setVisibility(View.VISIBLE);
                    break;
                case "점심":
                    rootView.findViewById(R.id.main_diary_lunch).setVisibility(View.VISIBLE);
                    break;
                case "저녁":
                    rootView.findViewById(R.id.main_diary_dinner).setVisibility(View.VISIBLE);
                    break;
                case "모임":
                    rootView.findViewById(R.id.main_diary_etc).setVisibility(View.VISIBLE);
                    break;
                case "간식":
                    rootView.findViewById(R.id.main_diary_snack).setVisibility(View.VISIBLE);
                    break;
            }
        }


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



        //progressBar = (ProgressBar)rootView.findViewById(R.id.main);

        //initSP();

        loadingBarDialog = new LoadingBarDialog(getContext());
        loadingBarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingBarDialog.show();


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

            viewPager2.setPageMargin(25);
            viewPager.removeAllViews();
            mainTopViewPagerAdapter = new MainTopViewPagerAdapter(inflater, olleego_SP, viewPager2, getContext(),loadingBarDialog );
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
            mainMiddleViewPagerAdapter = new MainMiddleViewPagerAdapter(inflater,false, getContext(),loadingBarDialog );
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

        String token = "ollego " + olleego_SP.getString("login_token", "");;
        DiaryAPI diaryAPI = retrofit_diary.create(DiaryAPI.class);

        final Call<DiaryModel> diaryPos = diaryAPI.listRepos(dateFormat.format(date), token);

        diaryPos.enqueue(new Callback<DiaryModel>() {
            @Override
            public void onResponse(Call<DiaryModel> call, Response<DiaryModel> response) {

                if(response.isSuccessful()) {




                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMinimumFractionDigits(1);


                    float hour_temp = response.body().getResult().getSleep();
                    int hour = (int)hour_temp;

                    float minutes_temp = response.body().getResult().getSleep() - (int)response.body().getResult().getSleep();
                    String minutes;
                    String temp =nf.format(minutes_temp);


                    if(temp.equals("0.1")) {
                        minutes = "10분";
                    } else if(temp.equals("0.2")) {
                        minutes = "20분";
                    } else if(temp.equals("0.3")) {
                        minutes = "30분";
                    } else if(temp.equals("0.4")) {
                        minutes = "40분";
                    } else if(temp.equals("0.5")) {
                        minutes = "50분";

                    } else {
                        minutes = "00분";
                    }

                    sleep_temp = response.body().getResult().getSleep();


                    sleep.setText(String.valueOf((int)response.body().getResult().getSleep()) + "시간 " + minutes);







                    if (response.body().getResult().getWalking() == 0) {

                        walkings = 0;
                    } else {
                        walkings = response.body().getResult().getWalking();
                    }

                    water_temp = response.body().getResult().getWater();

                    walking.setText(String.valueOf(walkings));
                    water.setText(String.valueOf(water_temp));


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
                                case "모임":
                                    rootView.findViewById(R.id.main_diary_etc).setVisibility(View.VISIBLE);
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

    @OnClick(R.id.main_diary_water_layout)
    void main_diary_water_layout() {
        final DiaryWaterAddDialog diaryWaterAddDialog = new DiaryWaterAddDialog(getContext(), olleego_SP.getString("login_token", ""), Integer.valueOf(olleego_SP.getString("user_id","")),dateFormat.format(date), water_temp, sleep_temp, walkings);
        diaryWaterAddDialog.show();

        diaryWaterAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String day_temp="";
                if(diaryWaterAddDialog.getType() == true) {
                    water_temp = diaryWaterAddDialog.getWater();

                    water.setText(String.valueOf(water_temp));
                    // 008 됌 수정하셈
                }
            }
        });
    }

    @OnClick(R.id.main_diary_sleep_layout)
    void main_diary_sleep_layout() {
    final DiarySleepAddDialog diarySleepAddDialog = new DiarySleepAddDialog(getContext(), olleego_SP.getString("login_token", ""), Integer.valueOf(olleego_SP.getString("user_id","")),dateFormat.format(date), water_temp, sleep_temp, walkings);
    diarySleepAddDialog.show();

    diarySleepAddDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if(diarySleepAddDialog.getType() == true) {
                sleep_temp = diarySleepAddDialog.getSleep();

                float hour_temp = sleep_temp;
                int hour = (int)hour_temp;
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMinimumFractionDigits(1);
                float minutes_temp = sleep_temp - (int)sleep_temp;
                String minutes;
                String temp =nf.format(minutes_temp);


                if(temp.equals("0.1")) {
                    minutes = "10분";
                } else if(temp.equals("0.2")) {
                    minutes = "20분";
                } else if(temp.equals("0.3")) {
                    minutes = "30분";
                } else if(temp.equals("0.4")) {
                    minutes = "40분";
                } else if(temp.equals("0.5")) {
                    minutes = "50분";

                } else {
                    minutes = "00분";
                }



                sleep.setText(String.valueOf((int)sleep_temp + "시간 " + minutes));
            }
        }
    });
    }

    @OnClick(R.id.main_diary_food_add_layout)
    void main_diary_food_add_layout() {
        Intent intent = new Intent(getActivity(), DiaryFoodActivity.class);
        intent.putExtra("user", olleego_SP.getString("user_id", ""));
        intent.putExtra("day", dateFormat.format(date));
        intent.putExtra("token", olleego_SP.getString("login_token", ""));
        intent.putExtra("type", "main");
        getActivity().startActivityForResult(intent, 0);
    }

    @OnClick(R.id.main_diary_go)
    void main_diary_go() {
        android.support.v4.app.FragmentTransaction transaction;
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, new Diary_Fragment(olleego_SP),"diary");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}