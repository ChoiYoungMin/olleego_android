package com.edn.olleego.adapter.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edn.olleego.fragment.Mission.MissionCategoryFragment;
import com.edn.olleego.fragment.report.HealthyReportFragment;

/**
 * Created by Antonio on 2016-08-02.
 */
public class Report_PagerAdapter extends FragmentPagerAdapter {



    public Report_PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        HealthyReportFragment healthyReportFragment = new HealthyReportFragment();
        // getItem is called to h the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                //전체
                return healthyReportFragment;
            case 1:
                //다이어트
                return healthyReportFragment;
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
