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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.activity.mission.exmission.EtcMissionActivity;
import com.edn.olleego.activity.mission.exmission.ExMissionActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.fragment.Mission.MissionCategoryFragment;
import com.edn.olleego.fragment.Mission.MissionCategoryMainFragment;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.model.UserMissionModel;
import com.edn.olleego.model.UserModel;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.FoodsAPI;
import com.edn.olleego.server.LifesAPI;
import com.edn.olleego.server.UserAPI;
import com.edn.olleego.server.UserRestMissionAPI;

import java.io.Serializable;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
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

    LoadingBarDialog loadingBarDialog;


    View convertView ;

    private SharedPreferences olleego_SP;

    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;

    boolean type;

    boolean rest;

    String token;
    int exgroup_id;
    int mission_today;
    int mission_id;
    int life_id;
    int food_id;

    String exgroup_complete;
    String food_complete;
    String life_complete;

    int taget_img;
    int taget2_img;


    int foods = 0;
    int lifes =0;
    public MainMiddleViewPagerAdapter(LayoutInflater inflater,boolean type, int i, SharedPreferences olleego_SP, Context context,LoadingBarDialog loadingBarDialog) {
        this.inflater = inflater;
        this.context= context;
        convertView = null;
        this.type = type;
        this.loadingBarDialog= loadingBarDialog;
        //notifyDataSetChanged();

        if(olleego_SP.getString("user_mission_today_rest", "").equals("false")) {

            today_mission(inflater, olleego_SP);
            rest = false;
        } else if (olleego_SP.getString("user_mission_today_rest", "").equals("true")){
            mSize = 1;
            Toast.makeText(context,"쉬는날 디자인 ㄴ",  Toast.LENGTH_SHORT).show();
            this.olleego_SP = olleego_SP;
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

    public MainMiddleViewPagerAdapter(LayoutInflater inflater, boolean type, Context context,LoadingBarDialog loadingBarDialog){
        this.context = context;
        this.inflater = inflater;
        this.type = type;
        mSize = 1;
        this.loadingBarDialog= loadingBarDialog;
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
            LinearLayout reduis_main_top = (LinearLayout)convertView.findViewById(R.id.reduis_main_top);
            reduis_main_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, new MissionCategoryMainFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            view.addView(convertView);
            loadingBarDialog.dismiss();
            mSize = 1;
        } else {
            if (rest == true) {
                loadingBarDialog.dismiss();

                convertView = inflater.inflate(R.layout.item_main_middle_rest, null);
                view.addView(convertView);
                mSize = 1;




            } else if (rest == false) {
                if(position < 1) {



                    convertView = inflater.inflate(R.layout.item_main_middle_food, null);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_food_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_food_now_position);
                    TextView food_title = (TextView) convertView.findViewById(R.id.user_food_title);
                    TextView food_content = (TextView) convertView.findViewById(R.id.user_food_context);

                    today_allcount.setText(String.valueOf(mSize));
                    today_nowcount.setText(String.valueOf(position+1));

                    try {
                        food_title.setText(foodsModel.getResult().getTitle());
                        food_content.setText(foodsModel.getResult().getFd_list().get(position).getTitle());
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("type", "food");
                                intent.putExtra("food", (Serializable) foodsModel.getResult().getFd_list().get(position));
                                intent.putExtra("food_id", foodsModel.getResult().get_id());
                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                ((MainActivity)context).startActivityForResult(intent, 2);
                            }
                        });
                    }


                    view.addView(convertView);
                } else if(position < 1+foodsModel.getResult().getFd_list().size()){
                    convertView = inflater.inflate(R.layout.item_main_middle_mission, null);


                    convertView.findViewById(R.id.user_mission_completes).setVisibility(View.GONE);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_mission_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_mission_now_position);
                    TextView mission_title = (TextView) convertView.findViewById(R.id.user_mission_title);
                    TextView mission_time = (TextView) convertView.findViewById(R.id.user_mission_time);
                    TextView mission_time2 = (TextView) convertView.findViewById(R.id.user_mission_time2);
                    ImageView mission_taget = (ImageView) convertView.findViewById(R.id.user_mission_taget);
                    ImageView mission_taget2 = (ImageView) convertView.findViewById(R.id.user_mission_taget2);
                    String taget;
                    String taget2;
                    try{

                        taget = exgroupsModel.getResult().getTarget().get(0).getValue();
                    } catch (NullPointerException e) {
                        taget="전신";
                    }
                    try{


                        taget2 = exgroupsModel.getResult().getTarget().get(1).getValue();
                    } catch (NullPointerException e) {
                        taget2=taget;
                    } catch (IndexOutOfBoundsException e) {
                        taget2=taget;
                    }



                    try {
                        mission_title.setText(exgroupsModel.getResult().getTitle());
                        mission_time.setText(String.valueOf(exgroupsModel.getResult().getTime()) + "분");
                        mission_time2.setText(String.valueOf(exgroupsModel.getResult().getEx_list().size()) + "개");
                        Glide.with(context).load(taget_chk(taget)).into(mission_taget);
                        Glide.with(context).load(taget_chk(taget2)).into(mission_taget2);


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
                                intent.putExtra("exgroup_id", exgroupsModel.getResult().get_id());
                                intent.putExtra("exgroup_title", exgroupsModel.getResult().getTitle());
                                intent.putExtra("mission_ok", olleego_SP.getString("user_mission_today_exgroup_complete",""));
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                ((MainActivity)context).startActivityForResult(intent, 1);


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
                                intent.putExtra("exgroup_id", exgroupsModel.getResult().get_id());
                                intent.putExtra("exgroup_title", exgroupsModel.getResult().getTitle());
                                intent.putExtra("mission_ok", olleego_SP.getString("user_mission_today_exgroup_complete",""));
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                ((MainActivity)context).startActivityForResult(intent, 1);
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("type", "life");
                                intent.putExtra("life", (Serializable) lifesModel.getResult().getLf_list().get(position -(foodsModel.getResult().getFd_list().size()+ lifesModel.getResult().getLf_list().size()) ));

                                intent.putExtra("token", token);
                                intent.putExtra("mission_id", mission_id);
                                intent.putExtra("mission_today", mission_today);
                                intent.putExtra("life_id", lifesModel.getResult().get_id());
                                ((MainActivity)context).startActivityForResult(intent, 3);
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

    private int taget_chk(String taget) {
        switch (taget){
            case "상체":
                return R.drawable.m_bd_up;
            case "하체":
                return R.drawable.m_bd_down;
            case "복근":
                return R.drawable.m_bd_stomach;
            case "힙업":
                return R.drawable.m_bd_hips;
            case "등":
                return R.drawable.m_bd_back;
            case "전신":
                return R.drawable.m_bd_all;

        }
        return 0;
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

            Retrofit retrofit_home = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .client(client)
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
                loadingBarDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LifesModel> call, Throwable t) {

            }
        });

    }

    public void init() {
        mSize = 1 + this.foodsModel.getResult().getFd_list().size() + this.lifesModel.getResult().getLf_list().size();

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
