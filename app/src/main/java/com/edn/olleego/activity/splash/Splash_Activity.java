package com.edn.olleego.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.adapter.splash.SplashAdapter;
import com.edn.olleego.service.TrackerService;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash_Activity extends AppCompatActivity {

    @BindView(R.id.splash_viewpager)
    ViewPager splash_viewpager;
    private TimerTask mTask;
    private Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Intent intent = new Intent(getApplicationContext(),TrackerService.class);
        startService(intent);

        SplashAdapter splashAdapter = new SplashAdapter(getLayoutInflater());

        splash_viewpager.setAdapter(splashAdapter);
        splash_viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });

        mTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();

                handler.sendMessage(msg);

            }
        };

        mTimer = new Timer();

      mTimer.schedule(mTask, 2000, 2000);
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    final Handler handler = new Handler()

    {

        public void handleMessage(Message msg)

        {
            int position = splash_viewpager.getCurrentItem();

            if(position+1 == 3) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            splash_viewpager.setCurrentItem(position+1, true);

        }

    };

}
