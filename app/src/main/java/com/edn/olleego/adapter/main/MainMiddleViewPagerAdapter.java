package com.edn.olleego.adapter.main;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;
import com.edn.olleego.model.LifesModel;

import retrofit2.Retrofit;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MainMiddleViewPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
     int mSize;
    int food;
    int food2;
    boolean food_back;
    int life;
    boolean life_back;
    Retrofit retrofit;



    View convertView ;

    private SharedPreferences olleego_SP;

    ExgroupsModel exgroupsModel;
    FoodsModel foodsModel;
    LifesModel lifesModel;

    Boolean type;

    public MainMiddleViewPagerAdapter(LayoutInflater inflater,ExgroupsModel exgroupsModel,FoodsModel foodsModel,LifesModel lifesModel, Boolean type) {
        this.inflater = inflater;
        this.exgroupsModel = exgroupsModel;
        this.foodsModel = foodsModel;
        this.lifesModel = lifesModel;
        convertView= null;
        mSize = 1 + this.foodsModel.getResult().getFd_list().size()+ this.lifesModel.getResult().getLf_list().size();
        this.type = type;
        //notifyDataSetChanged();

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
        if(type == false) {
            convertView = inflater.inflate(R.layout.item_main_middle_no, null);
            view.addView(convertView);
            mSize = 1;
            notifyDataSetChanged();
        } else {
            if(position < 1) {


                convertView = inflater.inflate(R.layout.item_main_middle_mission, null);

                TextView today_allcount = (TextView) convertView.findViewById(R.id.user_mission_today_count);
                TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_mission_now_position);
                TextView mission_title = (TextView) convertView.findViewById(R.id.user_mission_title);
                TextView mission_time = (TextView)convertView.findViewById(R.id.user_mission_time);
                TextView mission_time2 = (TextView)convertView.findViewById(R.id.user_mission_time2);

                mission_title.setText(exgroupsModel.getResult().getTitle());
                mission_time.setText(String.valueOf(exgroupsModel.getResult().getTime()) + "분");
                mission_time2.setText(String.valueOf(exgroupsModel.getResult().getEx_list().size()) + "개");

                today_allcount.setText(String.valueOf(mSize));
                today_nowcount.setText(String.valueOf(position+1));

                view.addView(convertView);


            } else if(position < 1+foodsModel.getResult().getFd_list().size()){


                convertView = inflater.inflate(R.layout.item_main_middle_food, null);

                TextView today_allcount = (TextView) convertView.findViewById(R.id.user_food_today_count);
                TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_food_now_position);
                TextView food_title = (TextView) convertView.findViewById(R.id.user_food_title);
                TextView food_content = (TextView) convertView.findViewById(R.id.user_food_context);

                today_allcount.setText(String.valueOf(mSize));
                today_nowcount.setText(String.valueOf(position + 1));


                food_title.setText(foodsModel.getResult().getTitle());
                food_content.setText(foodsModel.getResult().getFd_list().get(position - 1).getTitle());

                view.addView(convertView);

                //되돌아갈떄 계속 커짐
            } else if(position < 1+foodsModel.getResult().getFd_list().size()+ lifesModel.getResult().getLf_list().size()){

                convertView = inflater.inflate(R.layout.item_main_middle_life, null);

                TextView today_allcount = (TextView) convertView.findViewById(R.id.user_life_today_count);
                TextView today_nowcount = (TextView) convertView.findViewById(R.id.user_life_now_position);
                TextView life_title = (TextView) convertView.findViewById(R.id.user_life_title);
                TextView life_content = (TextView) convertView.findViewById(R.id.user_life_content);


                today_allcount.setText(String.valueOf(mSize));
                today_nowcount.setText(String.valueOf(position + 1));
                life_title.setText(lifesModel.getResult().getTitle());
                life_content.setText(lifesModel.getResult().getLf_list().get(position - (1+foodsModel.getResult().getFd_list().size())).getTitle());

                view.addView(convertView);



            }else {
                return convertView;
            }

        }






        /*if (position == 1) {
            convertView = inflater.inflate(R.layout.item_main_middle_life, null);
            view.addView(convertView);
        }else if (position == 2) {
            convertView = inflater.inflate(R.layout.item_main_middle_food, null);
            view.addView(convertView);
        }*/


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
