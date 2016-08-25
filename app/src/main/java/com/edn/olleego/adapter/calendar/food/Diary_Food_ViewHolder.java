package com.edn.olleego.adapter.calendar.food;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-07-27.
 */
public class Diary_Food_ViewHolder {

    @BindView(R.id.item_diary_food_img)
    ImageView diary_food_img;

    @BindView(R.id.item_diary_food_sort)
    TextView diary_food_sort;

    @BindView(R.id.item_diary_food_foods)
    TextView diary_food_foods;

    @BindView(R.id.item_diary_food_memo)
    TextView diary_food_memo;

    @BindView(R.id.item_diary_food_icon)
    ImageView diary_food_icon;

    @BindView(R.id.diary_food_detail)
    RelativeLayout diary_food_detail;

    @BindView(R.id.diary_food_edit)
    LinearLayout diary_food_edit;


    public Diary_Food_ViewHolder(View view){
            ButterKnife.bind(this, view);
    }

}
