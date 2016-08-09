package com.edn.olleego.activity.diary;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.edn.olleego.R;
import com.edn.olleego.adapter.calendar.DiaryChartCategoryAdapter;
import com.edn.olleego.adapter.mission.Mission_Category_PagerAdapter;

public class DiaryChartActivity extends AppCompatActivity {

    private DiaryChartCategoryAdapter diaryChartCategoryAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_chart);


        diaryChartCategoryAdapter = new DiaryChartCategoryAdapter(getSupportFragmentManager(), getSupportActionBar());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(diaryChartCategoryAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
