package com.edn.olleego.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceCenterActivity;
import com.edn.olleego.activity.alliance.AllianceMapActivity;
import com.edn.olleego.activity.login.EmailActivity;
import com.edn.olleego.activity.mycenter.MyCenterActivity;
import com.edn.olleego.activity.notice.NoticeActivity;
import com.edn.olleego.activity.video.VideoCategoryActivity;
import com.edn.olleego.common.BackPressCloseHandler;
import com.edn.olleego.fragment.Home_Fragment;
import com.edn.olleego.fragment.Mission.MissionCategoryMainFragment;
import com.edn.olleego.fragment.diary.DiaryFoodDetailFragment;
import com.edn.olleego.fragment.setting.SettingFragment;
import com.edn.olleego.fragment.OlleegoGym_Fragment;
import com.edn.olleego.fragment.diary.Diary_Fragment;
import com.edn.olleego.fragment.report.ReportFragment;
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
    SettingFragment missionCustomFragment;
    ReportFragment reportFragment;





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
        nav_view2.setItemIconTintList(null);

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


        nav_view.getMenu().getItem(0).setChecked(true);


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
        reportFragment = new ReportFragment();
        missionCategoryFragment = new MissionCategoryMainFragment();
        missionCustomFragment = new SettingFragment();
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
            nav_view.getMenu().getItem(0).setChecked(true);
            super.onBackPressed();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
        View v = nav_view2.getHeaderView(0);
        RelativeLayout layout_menu = (RelativeLayout) v.findViewById(R.id.layout_menu);
        RelativeLayout layout_login = (RelativeLayout) v.findViewById(R.id.layout_login);
        if(login_chk == 0) {

            findViewById(R.id.login_no_left).setVisibility(View.GONE);
            findViewById(R.id.login_ok_left).setVisibility(View.GONE);

            if (olleego_SP.getString("login_chk", "").equals("true")) {
                findViewById(R.id.login_ok_left).setVisibility(View.VISIBLE);

                TextView left_header_name = (TextView)findViewById(R.id.left_header_name);
                TextView left_header_email = (TextView)findViewById(R.id.left_header_email);
                ImageView left_header_img= (ImageView)findViewById(R.id.left_header_img);
                layout_login.setVisibility(View.GONE);
                layout_menu.setVisibility(View.VISIBLE);
                left_header_name.setText(olleego_SP.getString("login_name",""));
                left_header_email.setText(olleego_SP.getString("login_email",""));
                Glide.with(this).load(olleego_SP.getString("login_img", "")).into(left_header_img);
            }


            // 비로그인 상태
            else {
                findViewById(R.id.login_no_left).setVisibility(View.VISIBLE);

                layout_menu.setVisibility(View.GONE);
                layout_login.setVisibility(View.VISIBLE);
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

        if (id == R.id.right1) { //My센터
            Intent intent = new Intent(this, MyCenterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.right2) { //제휴센터 리스트
            Intent intent = new Intent(this, AllianceCenterActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.right3) { //제휴센터 지도뷰
            Intent intent = new Intent(this, AllianceMapActivity.class);
            startActivity(intent);
            return true;
        }


        if(id == R.id.left_menu_home) {
            transaction.replace(R.id.content_main, mainFragment);
        }
        else if (id == R.id.left_menu_chart) {
            transaction.replace(R.id.content_main, reportFragment);
        }else if (id == R.id.left_menu_diary) {
            transaction.replace(R.id.content_main, diaryFragment, "diary");
        } else if (id == R.id.left_menu_mission) {
            transaction.replace(R.id.content_main, missionCategoryFragment);
        } else if (id == R.id.left_menu_video) {
            Intent intent = new Intent(this, VideoCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.left_menu_setting) {
            transaction.replace(R.id.content_main, missionCustomFragment);
        } else if (id == R.id.left_menu_notice) {
            Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
            startActivity(intent);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 5 ) {
            final android.support.v4.app.Fragment fragment =  getSupportFragmentManager().findFragmentByTag(data.getStringExtra("type"));
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if(resultCode == 1 ) {
            SharedPreferences.Editor editor = olleego_SP.edit();
            //editor.remove("login_chk");
            editor.putString("user_mission_today_exgroup_complete", "true");
            editor.commit();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, mainFragment,"main");
            transaction.addToBackStack(null);
            transaction.commit();

            final android.support.v4.app.Fragment fragment =  getSupportFragmentManager().findFragmentByTag("main");
            fragment.onActivityResult(requestCode, resultCode, data);

        }
        else if(resultCode == 2 ) {
            SharedPreferences.Editor editor = olleego_SP.edit();
            //editor.remove("login_chk");
            editor.putString("user_mission_today_food_complete", "true");
            editor.commit();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, mainFragment,"main");
            transaction.addToBackStack(null);
            transaction.commit();

            final android.support.v4.app.Fragment fragment =  getSupportFragmentManager().findFragmentByTag("main");
            fragment.onActivityResult(requestCode, resultCode, data);

        } else if(resultCode == 3 ) {
            SharedPreferences.Editor editor = olleego_SP.edit();
            //editor.remove("login_chk");
            editor.putString("user_mission_today_life_complete", "true");
            editor.commit();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, mainFragment,"main");
            transaction.addToBackStack(null);
            transaction.commit();
            final android.support.v4.app.Fragment fragment =  getSupportFragmentManager().findFragmentByTag("main");
            fragment.onActivityResult(requestCode, resultCode, data);

        }


    }

    public void food_edit() {
        DiaryFoodDetailFragment diaryFoodDetailFragment = new DiaryFoodDetailFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_alllayout, diaryFoodDetailFragment,"food");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    // 외주 추가 소스

    @OnClick(R.id.layout_bottom)
    public void onClickRightMenuBottomButton(View v){ //우측메뉴 건강관리홈 버튼
        //drawer_layout.closeDrawer(GravityCompat.END);
    }


}
