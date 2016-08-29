package com.edn.olleego.adapter.alliance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.edn.olleego.fragment.alliance.AllianceImageSliderFragment;

import java.util.List;

/**
 * Created by kym on 2016. 8. 7..
 *
 * 제휴센터 상세 페이지 상단 이미지 슬라이더 pagerAdapter
 */
public class AllianceImagePagerAdapter extends FragmentStatePagerAdapter {
    List<String> imageList;
    public AllianceImagePagerAdapter(FragmentManager fm, List<String> imageList) {
        super(fm);
        this.imageList = imageList;
    }

    @Override
    public Fragment getItem(int position) {
        return new AllianceImageSliderFragment().newInstance(imageList.get(position));
    }

    @Override
    public int getCount() {
        int count = 0;
        if(imageList != null && imageList.size() > 0)
            count = imageList.size();
        return count;
    }
}