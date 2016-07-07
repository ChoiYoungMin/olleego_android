package com.edn.olleego.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.diary.DiaryActivity;
import com.edn.olleego.activity.mission.MissionActivity;
import com.edn.olleego.common.BackPressCloseHandler;
import com.edn.olleego.fragment.Home_Fragment;
import com.edn.olleego.fragment.Mission.MissionDetailFragment;
import com.edn.olleego.fragment.OlleegoGym_Fragment;
import com.edn.olleego.fragment.chart.ChartFragment;
import com.edn.olleego.fragment.diary.Diary_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

     OlleegoGym_Fragment GymFramgment;
     Home_Fragment MainFramgment;
     Diary_Fragment DiaryFramgment;
     com.edn.olleego.fragment.chart.ChartFragment ChartFragment;
    android.support.v4.app.FragmentTransaction transaction;

    Fragment fragment;

    boolean move;

    private BackPressCloseHandler backPressCloseHandler;


    @BindView(R.id.toolbar_home)
    ImageView toolbar_home;
    @BindView(R.id.toolbar_home2)
    ImageView toolbar_home2;
    @BindView(R.id.toolbar_home22)
    ImageView toolbar_home22;

    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.nav_view2)
    NavigationView nav_view2;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.drawer_toolbar)
    Toolbar drawer_toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        backPressCloseHandler = new BackPressCloseHandler(this);
        move = false;



        drawer_layout.setFitsSystemWindows(true);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view2.setNavigationItemSelectedListener(this);


        Init_Framgment();

        transaction = getSupportFragmentManager().beginTransaction();


        transaction.add(R.id.content_main, MainFramgment);
        transaction.addToBackStack(null);
        transaction.commit();

        setCustomActionbar();


    }

    @OnClick(R.id.toolbar_home)
    void toolbar_home_click() {
        drawer_layout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.toolbar_home2)
    void toolbar_home2_click() {
        toolbar_home2.setVisibility(View.GONE);
        toolbar_home.setVisibility(View.VISIBLE);

        onBackPressed();
        move = false;
    }

    @OnClick(R.id.toolbar_home22)
    void toolbar_home22_click() {

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
        GymFramgment = new OlleegoGym_Fragment();
        MainFramgment = new Home_Fragment();
        DiaryFramgment = new Diary_Fragment();
        ChartFragment = new ChartFragment();
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);

        }
        else if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
        }

        else if(move == false) {
            backPressCloseHandler.onBackPressed();
        } else {
            toolbar_home2.setVisibility(View.GONE);
            toolbar_home.setVisibility(View.VISIBLE);
            toolbar_home22.setVisibility(View.VISIBLE);

            super.onBackPressed();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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
        actionbar_back(id);


        //nav_view.getMenu().setGroupCheckable(R.id.test, false, true);

        if (id == R.id.nav_gallery2) {
            transaction.replace(R.id.content_main, ChartFragment);
        }else if (id == R.id.nav_gallery) {
            transaction.replace(R.id.content_main, DiaryFramgment);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            transaction.replace(R.id.content_main, GymFramgment);
        } else if (id == R.id.nav_share) {

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



    public void actionbar_back(int id) {



        Log.e("dd", String.valueOf(id));

        if (id == R.id.nav_camera) {

            toolbar_home2.setVisibility(View.GONE);
            toolbar_home.setVisibility(View.VISIBLE);
            toolbar_home22.setVisibility(View.VISIBLE);
            move = false;

        } else {

            toolbar_home2.setVisibility(View.VISIBLE);
            toolbar_home.setVisibility(View.GONE);
            toolbar_home22.setVisibility(View.GONE);

            move = true;
        }
    }


}
