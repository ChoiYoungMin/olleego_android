package com.edn.olleego.adapter.calendar;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edn.olleego.fragment.Mission.MissionCategoryFragment;
import com.edn.olleego.fragment.diary.DiaryChartFragment;

/**
 * Created by Antonio on 2016-08-09.
 */
public class DiaryChartCategoryAdapter extends FragmentPagerAdapter {
    android.support.v7.app.ActionBar actionBar;


    public DiaryChartCategoryAdapter(FragmentManager fm, android.support.v7.app.ActionBar supportActionBar) {
        super(fm);
        this.actionBar = supportActionBar;
    }

    @Override
    public Fragment getItem(int position) {

        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                //전체
                return new DiaryChartFragment(1, actionBar);
            case 1:
                //다이어트
                return new DiaryChartFragment(2, actionBar);
            case 2:
                //필라테스
                return new DiaryChartFragment(3, actionBar);
        }
        return null;



    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "활동량";
            case 1:
                return "수면량";
            case 2:
                return "수분섭취량";
        }
        return null;
    }
}
