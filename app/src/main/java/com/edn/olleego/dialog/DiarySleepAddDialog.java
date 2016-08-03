package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.DiaryAddAPI;
import com.edn.olleego.server.request.DiaryAdd;
import com.google.android.gms.vision.text.Text;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Antonio on 2016-07-28.
 */
public class DiarySleepAddDialog extends Dialog{

    @BindView(R.id.water_hour_text)
    TextView water_hour_text;

    @BindView(R.id.water_minutes_text)
    TextView water_minutes_text;


    NumberFormat nf;

    Boolean type = false;

    int hour=0;
    float hour_temp;

    int minutes=0;
    float minutes_temp;

    float sleep= (float) 0.0;
    int water;
    String token;
    int user_id;
    String day;

    int walking;
    String temp;

    public DiarySleepAddDialog(Context context, String token, int user_id, String day, int nowWater, float sleep, int walking) {
        super(context);

        this.token = token;
        this.user_id = user_id;
        this.day = day+ " 00:00:00";
        this.water = nowWater;
        this.sleep = sleep;
        this.walking = walking;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_diary_sleep);
        ButterKnife.bind(this);

         nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(1);


        hour_temp = sleep;
        hour = (int)hour_temp;

        minutes_temp = sleep - hour;

        temp =nf.format(minutes_temp);


        if(temp.equals("0.1")) {
            minutes = 10;
        } else if(temp.equals("0.2")) {
            minutes = 20;
        } else if(temp.equals("0.3")) {
            minutes = 30;
        } else if(temp.equals("0.4")) {
            minutes = 40;
        } else if(temp.equals("0.5")) {
            minutes = 50;
        }





        water_hour_text.setText(String.valueOf(hour));
        water_minutes_text.setText(String.valueOf(minutes));

        /*
        float test = 6.1f;



        Log.e("asd", test2);

        Log.e("asd", String.valueOf(sleep - Integer.valueOf(test2)));
        */




    }

    @OnClick(R.id.water_hour_add)
    void water_hour_add_click() {
        hour++;
        sleep += 1.0f;
        water_hour_text.setText(String.valueOf((int)hour));



        Log.e("fs", nf.format(sleep));
    }

    @OnClick(R.id.water_hour_remove)
    void water_hour_remove_click() {
        hour--;
        sleep -= 1.0f;

        water_hour_text.setText(String.valueOf((int)hour));
        Log.e("fs", nf.format(sleep));


    }


    @OnClick(R.id.water_minutes_add)
    void water_minutes_add_click() {

        if(minutes == 50) {
            minutes = 0;
            hour ++;
            sleep = hour;
            water_minutes_text.setText(String.valueOf((int)minutes));
            water_hour_text.setText(String.valueOf((int)hour));
        } else {
            minutes += 10;
            sleep += 0.1f;
            water_minutes_text.setText(String.valueOf((int)minutes));
        }
        Log.e("fs", nf.format(sleep));





    }
    @OnClick(R.id.water_minutes_remove)
    void water_minutes_remove_click() {

        if(minutes != 0) {
            minutes -= 10;
            sleep -= 0.1f;

            water_minutes_text.setText(String.valueOf((int)minutes));
            Log.e("fs", nf.format(sleep));


        }


    }

    @OnClick(R.id.dialog_diary_sleep_add)
    void dialog_diary_water_add_click() {



        Retrofit retrofit_diary = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tokens = "olleego " + token;
        DiaryAddAPI diaryAddAPI = retrofit_diary.create(DiaryAddAPI.class);

        DiaryAdd diaryAdd = new DiaryAdd();

        diaryAdd.DiaryWaterAdd(user_id,day,water,Float.valueOf(nf.format(sleep)),walking);

        final Call<MissionsModel> diaryPos = diaryAddAPI.listRepos(tokens, "sleep",diaryAdd);
        Log.e("zz", String.valueOf(diaryPos.request()));

        diaryPos.enqueue(new Callback<MissionsModel>() {
            @Override
            public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(),"추가 완료", Toast.LENGTH_SHORT).show();
                    type = true;

                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<MissionsModel> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.dialog_diary_sleep_no)
    void exit() {
        type = false;
        dismiss();
    }

    public boolean getType() {
        return type;
    }
}
