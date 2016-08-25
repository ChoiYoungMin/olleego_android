package com.edn.olleego.activity.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaryFoodDetailActivity extends AppCompatActivity {

    @BindView(R.id.diray_food_deatil_img)
    ImageView diray_food_deatil_img;

    @BindView(R.id.diray_food_deatil_title)
    TextView diray_food_deatil_title;
    @BindView(R.id.diray_food_deatil_types)
    TextView diray_food_deatil_types;
    @BindView(R.id.diray_food_deatil_memo)
    TextView diray_food_deatil_memo;

    String img;
    String title;
    String types;
    String memo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_food_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("sort");
        types = intent.getStringExtra("food");
        memo = intent.getStringExtra("memo");

        Glide.with(this).load(img).into(diray_food_deatil_img);
        diray_food_deatil_title.setText(title);
        diray_food_deatil_types.setText(types);
        diray_food_deatil_memo.setText("ddddd\nddfdfdfd\ndbddgdgdg\ndgdgdg");


    }

    @OnClick(R.id.diary_food_detail_exit)
    void diary_food_detail_exit() {
        finish();
    }
}
