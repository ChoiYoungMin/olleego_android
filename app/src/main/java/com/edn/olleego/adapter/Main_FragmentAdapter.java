package com.edn.olleego.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edn.olleego.activity.diary.DiaryActivity;
import com.edn.olleego.fragment.Home_Fragment;
import com.edn.olleego.fragment.OlleegoGym_Fragment;
import com.edn.olleego.fragment.Policy_Fragment;

/**
 * Created by Antonio on 2016-06-23.
 */
public class Main_FragmentAdapter extends FragmentPagerAdapter {

    public Main_FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).


        switch (position) {

            case 0:
                Home_Fragment tab1 = new Home_Fragment();
                return tab1;
            case 1:
                OlleegoGym_Fragment tab2 = new OlleegoGym_Fragment();
                return tab2;
            default:
                tab1 = new Home_Fragment();
                return tab1;
        }

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
                return "홈";
            case 1:
                return "건강일기";

                case 2:
                    return "올리고짐";
                case 3:
                    return "나의Gym";

        }
        return null;
    }
}