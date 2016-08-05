package com.edn.olleego.adapter.report;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.edn.olleego.fragment.Mission.MissionCategoryFragment;
import com.edn.olleego.fragment.report.HealthyReportFragment;
import com.edn.olleego.fragment.report.LifeStyleReportFragment;

/**
 * Created by Antonio on 2016-08-02.
 */
public class Report_PagerAdapter extends FragmentPagerAdapter {

    ViewPager viewPager;
    Context context;

    public Report_PagerAdapter(FragmentManager fm, ViewPager viewPager, Context context) {
        super(fm);
        this.viewPager = viewPager;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        HealthyReportFragment healthyReportFragment = new HealthyReportFragment(context, viewPager);
        LifeStyleReportFragment lifeStyleReportFragment = new LifeStyleReportFragment(context, viewPager);
        // getItem is called to h the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                //전체
                return healthyReportFragment;
            case 1:
                //다이어트
                return lifeStyleReportFragment;
        }
        return healthyReportFragment;




    }



    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "건강리포트";
            case 1:
                return "Life Style";
        }
        return null;
    }
}
