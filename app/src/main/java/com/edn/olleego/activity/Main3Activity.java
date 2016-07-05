package com.edn.olleego.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.edn.olleego.R;
import com.edn.olleego.activity.diary.DiaryActivity;
import com.edn.olleego.activity.mission.MissionActivity;
import com.edn.olleego.fragment.Home_Fragment;
import com.edn.olleego.fragment.OlleegoGym_Fragment;
import com.edn.olleego.fragment.chart.ChartFragment;
import com.edn.olleego.fragment.diary.Diary_Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment GymFramgment;
    private Fragment MainFramgment;
    private Fragment DiaryFramgment;
    private Fragment ChartFragment;
    android.support.v4.app.FragmentTransaction transaction;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HOME");
        setSupportActionBar(toolbar);


        // 하단에 + 버튼임!!!


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Init_Framgment();


        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mysql, MainFramgment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void Init_Framgment() {
        GymFramgment = new OlleegoGym_Fragment();
        MainFramgment = new Home_Fragment();
        DiaryFramgment = new Diary_Fragment();
        ChartFragment = new ChartFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        transaction = getSupportFragmentManager().beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            toolbar.setTitle("HOME");
            transaction.replace(R.id.mysql, MainFramgment);
        }  else if (id == R.id.nav_gallery2) {
            toolbar.setTitle("건강차트");
            transaction.replace(R.id.mysql, ChartFragment);

        }else if (id == R.id.nav_gallery) {
            toolbar.setTitle("건강일기");
            transaction.replace(R.id.mysql, DiaryFramgment);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            transaction.replace(R.id.mysql, GymFramgment);

        } else if (id == R.id.nav_share) {

        }
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.mission)
    void mission_click() {
        Intent intent = new Intent(getApplicationContext(), MissionActivity.class);
        startActivity(intent);
    }
}
