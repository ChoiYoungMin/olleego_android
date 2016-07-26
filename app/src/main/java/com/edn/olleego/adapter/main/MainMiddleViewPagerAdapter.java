package com.edn.olleego.adapter.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.activity.mission.exmission.EtcMissionActivity;
import com.edn.olleego.activity.mission.exmission.ExMissionActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.FoodsAPI;
import com.edn.olleego.server.LifesAPI;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MainMiddleViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
     int mSize;
    int food;
    int food2;
    boolean food_back;
    int life;
    boolean life_back;
    Retrofit retrofit;

    Context context;



    View convertView ;

    private SharedPreferences olleego_SP;

    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;

    Boolean type;

    Boolean rest;

    String token;
    int exgroup_id;
    int mission_today;
    int mission_id;
    int life_id;
    int food_id;

    String exgroup_complete;
    String food_complete;
    String life_complete;


    int foods = 0;
    int lifes =0;
    public MainMiddleViewPagerAdapter(LayoutInflater inflater,Boolean type, int i, SharedPreferences olleego_SP, Context context) {
        this.inflater = inflater;
        this.context= context;
        convertView = null;
        this.type = type;
        //notifyDataSetChanged();

        if(olleego_SP.getString("user_mission_today_rest", "").equals("false")) {

            today_mission(inflater, olleego_SP);
            rest = false;
        } else if (olleego_SP.getString("user_mission_today_rest", "").equals("true")){
            mSize = 1;
            Toast.makeText(context,"쉬는날 디자인 ㄴ",  Toast.LENGTH_SHORT).show();
            rest = true;
        }

        token = olleego_SP.getString("login_token", "");
        mission_id = olleego_SP.getInt("user_mission_id", 0);
        mission_today = Integer.parseInt(olleego_SP.getString("user_mission_today", ""));
        exgroup_id = olleego_SP.getInt("user_mission_today_exgroup", 0);
        food_id = olleego_SP.getInt("user_mission_today_food", 0);
        life_id = olleego_SP.getInt("user_mission_today_life", 0);


        exgroup_complete = olleego_SP.getString("user_mission_today_exgroup_complete", "");
        food_complete  = olleego_SP.getString("user_mission_today_food_complete", "");
        life_complete = olleego_SP.getString("user_mission_today_life_complete", "");
    }

    public MainMiddleViewPagerAdapter(LayoutInflater inflater, Boolean type, Context context){
        this.context = context;
        this.inflater = inflater;
        this.type = type;
        mSize = 1;
    }



    @Override
    public int getCount() {
        return mSize;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {



        if(type == false) {
            convertView = inflater.inflate(R.layout.item_main_middle_no, null);
            view.addView(convertView);
            mSize = 1;
        } else {
            if (rest == true) {
                convertView = inflater.inflate(R.layout.item_main_middle_rest, null);
                view.addView(convertView);
                mSize = 1;
            } else if (rest == false) {
                if(position < 1) {
                    convertView = inflater.inflate(R.layout.item_main_middle_mission, null);


                        convertView.findViewById(R.id.user_mission_completes).setVisibility(View.GONE);

                        TextView today_allcount = (TextView) convertView.findViewById(R.id.user_mission_today_count);
                        TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_mission_now_position);
                        TextView mission_title = (TextView) convertView.findViewById(R.id.user_mission_title);
                        TextView mission_time = (TextView) convertView.findViewById(R.id.user_mission_time);
                        TextView mission_time2 = (TextView) convertView.findViewById(R.id.user_mission_time2);

                        try {
                            mission_title.setText(exgroupsModel.getResult().getTitle());
                            mission_time.setText(String.valueOf(exgroupsModel.getResult().getTime()) + "분");
                            mission_time2.setText(String.valueOf(exgroupsModel.getResult().getEx_list().size()) + "개");
                        } catch (NullPointerException e ){
                            Log.e("zz", String.valueOf(e));
                        }
                        today_allcount.setText(String.valueOf(mSize));
                        today_nowcount.setText(String.valueOf(position + 1));

                    if(exgroup_complete.equals("true")){
                        convertView.findViewById(R.id.user_mission_completes).setVisibility(View.VISIBLE);

                        convertView.findViewById(R.id.main_middle_mission_replay).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ExMissionActivity.class);
                                for(int a=0; a< exgroupsModel.getResult().getEx_list().size(); a++) {

                                    intent.putExtra("ex"+a, (Serializable) exgroupsModel.getResult().getEx_list().get(a).getEx());
                                }
                                intent.putExtra("step", 0);
                                intent.putExtra("size", exgroupsModel.getResult().getEx_list().size());
                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                intent.putExtra("exgroup_id", exgroup_id);
                                intent.putExtra("exgroup_title", exgroupsModel.getResult().getTitle());
                                context.startActivity(intent);
                                ((AppCompatActivity)context).finish();


                            }
                        });

                    } else {


                        convertView.findViewById(R.id.user_mission_start).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ExMissionActivity.class);
                                for(int a=0; a< exgroupsModel.getResult().getEx_list().size(); a++) {

                                    intent.putExtra("ex"+a, (Serializable) exgroupsModel.getResult().getEx_list().get(a).getEx());
                                }
                                intent.putExtra("step", 0);
                                intent.putExtra("size", exgroupsModel.getResult().getEx_list().size());
                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                intent.putExtra("exgroup_id", exgroup_id);

                                context.startActivity(intent);
                                ((AppCompatActivity)context).finish();


                            }
                        });
                    }


                        view.addView(convertView);







                } else if(position < 1+foodsModel.getResult().getFd_list().size()){



                    convertView = inflater.inflate(R.layout.item_main_middle_food, null);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_food_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_food_now_position);
                    TextView food_title = (TextView) convertView.findViewById(R.id.user_food_title);
                    TextView food_content = (TextView) convertView.findViewById(R.id.user_food_context);

                    today_allcount.setText(String.valueOf(mSize));
                    today_nowcount.setText(String.valueOf(position + 1));

                    try {
                        food_title.setText(foodsModel.getResult().getTitle());
                        food_content.setText(foodsModel.getResult().getFd_list().get(position - 1).getTitle());
                    }catch (NullPointerException e ){
                        Log.e("zz", String.valueOf(e));
                    }

                    if(food_complete.equals("true")) {
                        convertView.findViewById(R.id.user_food_completes).setVisibility(View.VISIBLE);
                    }
                    else {
                        convertView.findViewById(R.id.user_food_completes).setVisibility(View.GONE);


                        convertView.findViewById(R.id.foodtestbtn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, EtcMissionActivity.class);

                                intent.putExtra("type", "food");
                                intent.putExtra("food", (Serializable) foodsModel.getResult().getFd_list().get(position - 1));
                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                intent.putExtra("food_id", food_id);

                                context.startActivity(intent);
                                ((AppCompatActivity)context).finish();
                            }
                        });
                    }


                    view.addView(convertView);

                    //되돌아갈떄 계속 커짐
                } else if(position < 1+foodsModel.getResult().getFd_list().size()+ lifesModel.getResult().getLf_list().size()){

                    convertView = inflater.inflate(R.layout.item_main_middle_life, null);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_life_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_life_now_position);
                    TextView life_title = (TextView) convertView.findViewById(R.id.user_life_title);
                    TextView life_content = (TextView) convertView.findViewById(R.id.user_life_content);


                    today_allcount.setText(String.valueOf(mSize));
                    today_nowcount.setText(String.valueOf(position + 1));
                    try {
                        life_title.setText(lifesModel.getResult().getTitle());
                        life_content.setText(lifesModel.getResult().getLf_list().get(position - (1 + foodsModel.getResult().getFd_list().size())).getTitle());
                    } catch (NullPointerException e ){
                        Log.e("zz", String.valueOf(e));
                    }
                    view.addView(convertView);

                    if(life_complete.equals("true")) {
                        convertView.findViewById(R.id.user_life_completes).setVisibility(View.VISIBLE);
                    } else {
                        convertView.findViewById(R.id.user_life_completes).setVisibility(View.GONE);

                        convertView.findViewById(R.id.main_middle_life).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, EtcMissionActivity.class);

                                intent.putExtra("type", "life");
                                intent.putExtra("life", (Serializable) lifesModel.getResult().getLf_list().get(position -(foodsModel.getResult().getFd_list().size()+ lifesModel.getResult().getLf_list().size()) ));
                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                intent.putExtra("life_id", life_id);

                                context.startActivity(intent);
                                ((AppCompatActivity)context).finish();
                            }
                        });
                    }



                }else {

                    return convertView;
                }
            }



        }






        /*if (position == 1) {
            convertView = inflater.inflate(R.layout.item_main_middle_life, null);
            view.addView(convertView);
        }else if (position == 2) {
            convertView = inflater.inflate(R.layout.item_main_middle_food, null);
            view.addView(convertView);
        }*/


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

    public void today_mission(final LayoutInflater inflater, SharedPreferences olleego_SP) {
            //rootView.findViewById(R.id.main_mission_yes).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.main_mission_no).setVisibility(View.GONE);
            //운동 조회
            this.olleego_SP = olleego_SP;

            Retrofit retrofit_home = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ExgroupsAPI exgroupsAPI = retrofit_home.create(ExgroupsAPI.class);
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

    public void today_foods( final LayoutInflater inflater) {
        Retrofit retrofit_foods = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodsAPI foodsAPI = retrofit_foods.create(FoodsAPI.class);
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
        Retrofit retrofit_lifes = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LifesAPI lifesAPI = retrofit_lifes.create(LifesAPI.class);
        final Call<LifesModel> lifePos = lifesAPI.listRepos(olleego_SP.getInt("user_mission_today_life", 0));

        lifePos.enqueue(new Callback<LifesModel>() {
            @Override
            public void onResponse(Call<LifesModel> call, Response<LifesModel> response) {
                lifesModel = response.body();
                init();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LifesModel> call, Throwable t) {

            }
        });

    }

    public void init() {
        mSize = 1 + this.foodsModel.getResult().getFd_list().size() + this.lifesModel.getResult().getLf_list().size();

    }


}
