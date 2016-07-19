package com.edn.olleego.adapter.mission;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edn.olleego.fragment.Mission.MissionCategoryFragment;

/**
 * Created by Antonio on 2016-07-19.
 */
public class Mission_Category_PagerAdapter extends FragmentPagerAdapter {

    public Mission_Category_PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        MissionCategoryFragment missionFragment = new MissionCategoryFragment();
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                //전체
                return missionFragment.newInstance(position+1);
            case 1:
                //다이어트
                return missionFragment.newInstance(position+1);
            case 2:
                //필라테스
                return missionFragment.newInstance(position+1);
            case 3:
                //건강관리
                return missionFragment.newInstance(position+1);
        }
        return missionFragment.newInstance(position+1);



    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "전체";
            case 1:
                return "다이어트";
            case 2:
                return "필라테스";
            case 3:
                return "건강관리";
        }
        return null;
    }
}
