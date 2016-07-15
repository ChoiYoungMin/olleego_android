package com.edn.olleego.adapter.main;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.edn.olleego.R;

import retrofit2.Retrofit;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MainMiddleViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private int mSize;

    Retrofit retrofit;


    public MainMiddleViewPagerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        mSize = 3;
    }

    public MainMiddleViewPagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View convertView = null;
        if (position == 0) {


            convertView = inflater.inflate(R.layout.item_main_middle_mission, null);



            view.addView(convertView);

        } else if (position == 1) {
            convertView = inflater.inflate(R.layout.item_main_middle_life, null);
            view.addView(convertView);
        }else if (position == 2) {
            convertView = inflater.inflate(R.layout.item_main_middle_food, null);
            view.addView(convertView);
        }

        return convertView;
    }

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }
}
