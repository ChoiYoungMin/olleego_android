package com.edn.olleego.adapter.splash;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.edn.olleego.R;

/**
 * Created by Antonio on 2016-08-19.
 */
public class SplashAdapter extends PagerAdapter {

    LayoutInflater inflater;

    public SplashAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view=null;

        view= inflater.inflate(R.layout.item_splash, null);

        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.
        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_childimage);

        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
        //현재 position에 해당하는 이미지를 setting
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        if(position == 0)  img.setImageResource(R.drawable.and_intro1);
        else if(position == 1)  img.setImageResource(R.drawable.and_intro2);
        else if(position == 2)  img.setImageResource(R.drawable.and_intro3);
        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


}
