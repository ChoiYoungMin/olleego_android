package com.edn.olleego.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.login.EmailActivity;
import com.edn.olleego.common.BackPressCloseHandler;
import com.edn.olleego.fragment.Home_Fragment;
import com.edn.olleego.fragment.Mission.MissionCategoryFragment;
import com.edn.olleego.fragment.Mission.MissionCategoryMainFragment;
import com.edn.olleego.fragment.Mission.MissionCustomFragment;
import com.edn.olleego.fragment.OlleegoGym_Fragment;
import com.edn.olleego.fragment.chart.ChartFragment;
import com.edn.olleego.fragment.diary.Diary_Fragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    OlleegoGym_Fragment gymFragment;
    Home_Fragment mainFragment;
    Diary_Fragment diaryFragment;
    MissionCategoryMainFragment missionCategoryFragment;
    MissionCustomFragment missionCustomFragment;
    ChartFragment chartFragment;





    android.support.v4.app.FragmentTransaction transaction;

    Fragment fragment;



    private BackPressCloseHandler backPressCloseHandler;


    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.nav_view2)
    NavigationView nav_view2;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.drawer_toolbar)
    Toolbar drawer_toolbar;


    int login_chk;
    SharedPreferences olleego_SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        nav_view.setItemIconTintList(null);

        backPressCloseHandler = new BackPressCloseHandler(this);
        drawer_layout.setFitsSystemWindows(true);

        nav_view.setNavigationItemSelectedListener(this);
        nav_view2.setNavigationItemSelectedListener(this);
        login_chk = 0;

        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        Init_Framgment();





        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, mainFragment,"main");
        transaction.addToBackStack(null);
        transaction.commit();


        setCustomActionbar();





    }


    @OnClick(R.id.toolbar_left_menu)
    void toolbar_left_menu_click() {
        drawer_layout.openDrawer(GravityCompat.START);
    }



    @OnClick(R.id.toolbar_right_menu)
    void toolbar_right_menu_click() {
        drawer_layout.openDrawer(GravityCompat.END);
    }



    private void setCustomActionbar() {
        drawer_toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(drawer_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }





    public void Init_Framgment() {
        gymFragment = new OlleegoGym_Fragment();
        mainFragment = new Home_Fragment();
        diaryFragment = new Diary_Fragment(olleego_SP);
        chartFragment = new ChartFragment();
        missionCategoryFragment = new MissionCategoryMainFragment();
        missionCustomFragment = new MissionCustomFragment();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);

        }
        else if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
        }


        final FragmentManager fm=this.getSupportFragmentManager();
        final android.support.v4.app.Fragment fragment=fm.findFragmentByTag("main");

        if(fragment != null && fragment.isVisible()){
            backPressCloseHandler.onBackPressed();
        }
        else{

            /*
            toolbar_home2.setVisibility(View.GONE);
            toolbar_home.setVisibility(View.VISIBLE);
            toolbar_home22.setVisibility(View.VISIBLE);
            */

            super.onBackPressed();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);

        if(login_chk == 0) {

            findViewById(R.id.login_no_left).setVisibility(View.GONE);
            findViewById(R.id.login_no_right).setVisibility(View.GONE);
            findViewById(R.id.login_ok_right).setVisibility(View.GONE);
            findViewById(R.id.login_ok_left).setVisibility(View.GONE);

            if (olleego_SP.getString("login_chk", "").equals("true")) {
                findViewById(R.id.login_ok_left).setVisibility(View.VISIBLE);
                findViewById(R.id.login_ok_right).setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "로그인중", Toast.LENGTH_SHORT).show();

            }


            // 비로그인 상태
            else {
                findViewById(R.id.login_no_left).setVisibility(View.VISIBLE);
                findViewById(R.id.login_no_right).setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "비로그인중", Toast.LENGTH_SHORT).show();


            }
        } else {

            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {




        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.slide_in_left, 0);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // 오른쪽 메뉴

        if(id == R.id.right1) {
            //transaction.replace(R.id.content_main, getFragmentManager());
        } else if(id == R.id.right2) {
            Toast.makeText(this, "제휴 센터 지도", Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.right3) {
            Toast.makeText(this, "My 센터", Toast.LENGTH_SHORT).show();
            return true;
        }


        if(id == R.id.left_menu_home) {
            transaction.replace(R.id.content_main, mainFragment);
        }
        else if (id == R.id.left_menu_chart) {
            transaction.replace(R.id.content_main, chartFragment);
        }else if (id == R.id.left_menu_diary) {
            transaction.replace(R.id.content_main, diaryFragment);
        } else if (id == R.id.left_menu_mission) {
            transaction.replace(R.id.content_main, missionCategoryFragment);
        } else if (id == R.id.left_menu_video) {
            Intent intent = new Intent(getApplicationContext(), EmailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.left_menu_setting) {
            transaction.replace(R.id.content_main, missionCustomFragment);
        } else if (id == R.id.left_menu_notice) {
            SharedPreferences.Editor editor = olleego_SP.edit();
            //editor.remove("login_chk");
            editor.clear();
            editor.commit();
            finish();
            return true;
        }

        transaction.addToBackStack(null);
        transaction.commit();

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);

        }

        else if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
        }

        return true;
    }




}
