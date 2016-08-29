package com.edn.olleego.adapter.alliance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.edn.olleego.fragment.alliance.AllianceMapFragment;
import com.edn.olleego.model.alliance.AllianceCategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 제휴센터 Map 리스트 pagerAdapter (전체, 피트니스, 필라테스...)
 */
public class AllianceMapPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private List<AllianceCategoryModel> list = new ArrayList<>();
    private AllianceCategoryModel.Result result;
    private double lon;
    private double lat;

    public AllianceMapPagerAdapter(FragmentManager fm, int tabCount, List<AllianceCategoryModel> list, double lon, double lat) {
        super(fm);
        this.tabCount = tabCount;
        this.list = list;
        this.lon = lon;
        this.lat = lat;
    }

    public void setData(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        result = list.get(0).getResult().get(position);
        String value = "306"; //전체 탭
        int ids = result.get_id();
        if (ids != 0)
            value = String.valueOf(ids);
        String id = String.format("{\"center_binfo.type\":%s}", value);
        return new AllianceMapFragment().newInstance(value, id, lon, lat);
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}