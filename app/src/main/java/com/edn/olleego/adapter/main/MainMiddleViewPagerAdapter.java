package com.edn.olleego.adapter.main;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;
import com.edn.olleego.model.MissionModel;
import com.edn.olleego.server.ExgroupsAPI;
import com.edn.olleego.server.FoodsAPI;
import com.edn.olleego.server.LifesAPI;
import com.edn.olleego.server.MissionAPI;
import com.google.android.gms.vision.text.Text;

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



    View convertView ;

    private SharedPreferences olleego_SP;

    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;

    public MainMiddleViewPagerAdapter(LayoutInflater inflater,ExgroupsModel exgroupsModel,FoodsModel foodsModel,LifesModel lifesModel) {
        this.inflater = inflater;
        this.exgroupsModel = exgroupsModel;
        this.foodsModel = foodsModel;
        this.lifesModel = lifesModel;
        convertView= null;
        mSize = this.exgroupsModel.getResult().getEx_list().size() + this.foodsModel.getResult().getFd_list().size()+ this.lifesModel.getResult().getLf_list().size();
        //notifyDataSetChanged();

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
    public Object instantiateItem(ViewGroup view, int position) {



            if(position < exgroupsModel.getResult().getEx_list().size()) {


                    convertView = inflater.inflate(R.layout.item_main_middle_mission, null);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_mission_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_mission_now_position);
                    TextView mission_title = (TextView) convertView.findViewById(R.id.user_mission_title);
                    TextView mission_time = (TextView)convertView.findViewById(R.id.user_mission_time);
                    TextView mission_time2 = (TextView)convertView.findViewById(R.id.user_mission_time2);

                    mission_title.setText(exgroupsModel.getResult().getTitle());
                    mission_time.setText(String.valueOf(exgroupsModel.getResult().getTime()) + "분");
                    mission_time2.setText(String.valueOf(exgroupsModel.getResult().getEx_list().size()) + "개");

                    today_allcount.setText(String.valueOf(mSize));
                    today_nowcount.setText(String.valueOf(position+1));

                    view.addView(convertView);


            } else if(position < exgroupsModel.getResult().getEx_list().size()+foodsModel.getResult().getFd_list().size()){


                convertView = inflater.inflate(R.layout.item_main_middle_food, null);

                TextView today_allcount = (TextView) convertView.findViewById(R.id.user_food_today_count);
                TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_food_now_position);
                TextView food_title = (TextView) convertView.findViewById(R.id.user_food_title);
                TextView food_content = (TextView) convertView.findViewById(R.id.user_food_context);

                today_allcount.setText(String.valueOf(mSize));
                today_nowcount.setText(String.valueOf(position + 1));


                food_title.setText(foodsModel.getResult().getFd_list().get(position - (exgroupsModel.getResult().getEx_list().size())).getTitle());
                food_content.setText(foodsModel.getResult().getFd_list().get(position - (exgroupsModel.getResult().getEx_list().size())).getDescription1());

                view.addView(convertView);

                //되돌아갈떄 계속 커짐
            } else if(position < exgroupsModel.getResult().getEx_list().size()+foodsModel.getResult().getFd_list().size()+ lifesModel.getResult().getLf_list().size()){

                    convertView = inflater.inflate(R.layout.item_main_middle_life, null);

                    TextView today_allcount = (TextView) convertView.findViewById(R.id.user_life_today_count);
                    TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_life_now_position);
                    TextView life_title = (TextView) convertView.findViewById(R.id.user_life_title);
                    TextView life_content = (TextView) convertView.findViewById(R.id.user_life_content);


                    today_allcount.setText(String.valueOf(mSize));
                    today_nowcount.setText(String.valueOf(position + 1));
                    life_title.setText(lifesModel.getResult().getLf_list().get(position - (exgroupsModel.getResult().getEx_list().size()+foodsModel.getResult().getFd_list().size())).getTitle());
                    life_content.setText(lifesModel.getResult().getLf_list().get(position - (exgroupsModel.getResult().getEx_list().size()+foodsModel.getResult().getFd_list().size())).getDescription1());

                    view.addView(convertView);



            }else {
                return convertView;
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



}
