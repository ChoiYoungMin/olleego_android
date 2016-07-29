package com.edn.olleego.activity.diary;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.dialog.DiaryFoodImageDialog;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaryFoodActivity extends AppCompatActivity {

    @BindView(R.id.asdasd)
    ImageView test;


    @BindView(R.id.diary_food_Image)
    LinearLayout diary_food_Image;
    @BindView(R.id.diary_food_morning_layout)
    LinearLayout diary_food_morning_layout;
    @BindView(R.id.diary_food_lunch_layout)
    LinearLayout diary_food_lunch_layout;
    @BindView(R.id.diary_food_dinner_layout)
    LinearLayout diary_food_dinner_layout;
    @BindView(R.id.diary_food_etc_layout)
    LinearLayout diary_food_etc_layout;
    @BindView(R.id.diary_food_etc2_layout)
    LinearLayout diary_food_etc2_layout;
    @BindView(R.id.diary_food_morning_text)
    TextView diary_food_morning_text;
    @BindView(R.id.diary_food_lunch_text)
    TextView diary_food_lunch_text;
    @BindView(R.id.diary_food_dinner_text)
    TextView diary_food_dinner_text;
    @BindView(R.id.diary_food_etc_text)
    TextView diary_food_etc_text;
    @BindView(R.id.diary_food_etc2_text)
    TextView diary_food_etc2_text;

    ///////////////////////////////////////////////////////////////////

    @BindView(R.id.diary_food_meal1_layout)
    LinearLayout diary_food_meal1_layout;
    @BindView(R.id.diary_food_meal2_layout)
    LinearLayout diary_food_meal2_layout;
    @BindView(R.id.diary_food_meal3_layout)
    LinearLayout diary_food_meal3_layout;
    @BindView(R.id.diary_food_meal4_layout)
    LinearLayout diary_food_meal4_layout;
    @BindView(R.id.diary_food_meal5_layout)
    LinearLayout diary_food_meal5_layout;
    @BindView(R.id.diary_food_meal6_layout)
    LinearLayout diary_food_meal6_layout;
    @BindView(R.id.diary_food_meal7_layout)
    LinearLayout diary_food_meal7_layout;
    @BindView(R.id.diary_food_meal8_layout)
    LinearLayout diary_food_meal8_layout;
    @BindView(R.id.diary_food_meal9_layout)
    LinearLayout diary_food_meal9_layout;
    @BindView(R.id.diary_food_meal10_layout)
    LinearLayout diary_food_meal10_layout;

    @BindView(R.id.diary_food_meal1_icon)
    ImageView diary_food_meal1_icon;
    @BindView(R.id.diary_food_meal2_icon)
    ImageView diary_food_meal2_icon;
    @BindView(R.id.diary_food_meal3_icon)
    ImageView diary_food_meal3_icon;
    @BindView(R.id.diary_food_meal4_icon)
    ImageView diary_food_meal4_icon;
    @BindView(R.id.diary_food_meal5_icon)
    ImageView diary_food_meal5_icon;
    @BindView(R.id.diary_food_meal6_icon)
    ImageView diary_food_meal6_icon;
    @BindView(R.id.diary_food_meal7_icon)
    ImageView diary_food_meal7_icon;
    @BindView(R.id.diary_food_meal8_icon)
    ImageView diary_food_meal8_icon;
    @BindView(R.id.diary_food_meal9_icon)
    ImageView diary_food_meal9_icon;
    @BindView(R.id.diary_food_meal10_icon)
    ImageView diary_food_meal10_icon;


    @BindView(R.id.diary_food_meal1_text)
    TextView diary_food_meal1_text;
    @BindView(R.id.diary_food_meal2_text)
    TextView diary_food_meal2_text;
    @BindView(R.id.diary_food_meal3_text)
    TextView diary_food_meal3_text;
    @BindView(R.id.diary_food_meal4_text)
    TextView diary_food_meal4_text;
    @BindView(R.id.diary_food_meal5_text)
    TextView diary_food_meal5_text;
    @BindView(R.id.diary_food_meal6_text)
    TextView diary_food_meal6_text;
    @BindView(R.id.diary_food_meal7_text)
    TextView diary_food_meal7_text;
    @BindView(R.id.diary_food_meal8_text)
    TextView diary_food_meal8_text;
    @BindView(R.id.diary_food_meal9_text)
    TextView diary_food_meal9_text;
    @BindView(R.id.diary_food_meal10_text)
    TextView diary_food_meal10_text;


    @BindView(R.id.diary_food_hungry_layout)
    LinearLayout diary_food_hungry_layout;
    @BindView(R.id.diary_food_happy_layout)
    LinearLayout diary_food_happy_layout;
    @BindView(R.id.diary_food_full_layout)
    LinearLayout diary_food_full_layout;
    @BindView(R.id.diary_food_overeat_layout)
    LinearLayout diary_food_overeat_layout;

    @BindView(R.id.diary_food_hungry_text)
    TextView diary_food_hungry_text;
    @BindView(R.id.diary_food_happy_text)
    TextView diary_food_happy_text;
    @BindView(R.id.diary_food_full_text)
    TextView diary_food_full_text;
    @BindView(R.id.diary_food_overeat_text)
    TextView diary_food_overeat_text;

    @BindView(R.id.diary_food_hungry_icon)
    ImageView diary_food_hungry_icon;
    @BindView(R.id.diary_food_happy_icon)
    ImageView diary_food_happy_icon;
    @BindView(R.id.diary_food_full_icon)
    ImageView diary_food_full_icon;
    @BindView(R.id.diary_food_overeat_icon)
    ImageView diary_food_overeat_icon;







    int types ;

    int chk= 0;



    ArrayList<Integer> meal_chk_int = new ArrayList<Integer>();

    boolean meal1;
    boolean meal2;
    boolean meal3;
    boolean meal4;
    boolean meal5;
    boolean meal6;
    boolean meal7;
    boolean meal8;
    boolean meal9;
    boolean meal10;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary_food);
        ButterKnife.bind(this);
        init_layout();
        meal_chk_int.clear();


    }

    @OnClick(R.id.diary_food_Image)
    void food_image_click() {
        DiaryFoodImageDialog diaryFoodImageDialog = new DiaryFoodImageDialog(this, this);
        diaryFoodImageDialog.show();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK || data.getData() != null) {

                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        } catch (NullPointerException e ) {
            if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {

                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            test.setImageURI(Crop.getOutput(result));
            diary_food_Image.setVisibility(View.GONE);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void type_layout_chk(int type) {

        switch (type) {
            case 1:
                diary_food_morning_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_morning_text.setTextColor(Color.parseColor("#ffffff"));
                types = 1;
                break;

            case 2:
                diary_food_lunch_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_lunch_text.setTextColor(Color.parseColor("#ffffff"));
                types = 2;
                break;

            case 3:
                diary_food_dinner_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_dinner_text.setTextColor(Color.parseColor("#ffffff"));
                types = 3;
                break;

            case 4:
                diary_food_etc_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_etc_text.setTextColor(Color.parseColor("#ffffff"));
                types = 4;
                break;

            case 5:
                diary_food_etc2_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_etc2_text.setTextColor(Color.parseColor("#ffffff"));
                types = 5;
                break;

        }
    }



    @OnClick(R.id.diary_food_morning_layout)
    void morning_click() {
        init_layout();
        type_layout_chk(1);

    }

    @OnClick(R.id.diary_food_lunch_layout)
    void lunch_click() {
        init_layout();
        type_layout_chk(2);
    }

    @OnClick(R.id.diary_food_dinner_layout)
    void dinner_click() {
        init_layout();
        type_layout_chk(3);
    }

    @OnClick(R.id.diary_food_etc_layout)
    void etc_click() {
        init_layout();
        type_layout_chk(4);
    }

    @OnClick(R.id.diary_food_etc2_layout)
    void etc2_click() {
        init_layout();
        type_layout_chk(5);
    }



    @OnClick(R.id.diary_food_meal1_layout)
    void meal1_click() {
        if(chk == 3 && meal1 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal1 == true) {
                meal1=false;
                diary_food_meal1_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal1_icon.setImageResource(R.drawable.meal_1_unchecked);
                diary_food_meal1_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 1) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal1_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal1_icon.setImageResource(R.drawable.meal_1);
                diary_food_meal1_text.setTextColor(Color.parseColor("#ffffff"));
                meal1 = true;
                meal_chk_int.add(1);
                chk++;
            }

        }

    }

    @OnClick(R.id.diary_food_meal2_layout)
    void meal2_click() {
        if(chk == 3 && meal2 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal2 == true) {
                meal2=false;
                diary_food_meal2_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal2_icon.setImageResource(R.drawable.meal_2_unchecked);
                diary_food_meal2_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 2) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal2_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal2_icon.setImageResource(R.drawable.meal_2);
                diary_food_meal2_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(2);
                chk++;
                meal2 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal3_layout)
    void meal3_click() {
        if(chk == 3 && meal3 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal3 == true) {
                meal3=false;
                diary_food_meal3_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal3_icon.setImageResource(R.drawable.meal_3_unchecked);
                diary_food_meal3_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 3) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal3_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal3_icon.setImageResource(R.drawable.meal_3);
                diary_food_meal3_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(3);
                chk++;
                meal3 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal4_layout)
    void meal4_click() {
        if(chk == 3&& meal4 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal4 == true) {
                meal4=false;
                diary_food_meal4_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal4_icon.setImageResource(R.drawable.meal_4_unchecked);
                diary_food_meal4_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 4) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal4_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal4_icon.setImageResource(R.drawable.meal_4);
                diary_food_meal4_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(4);
                chk++;
                meal4 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal5_layout)
    void meal5_click() {
        if(chk == 3 && meal5 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal5 == true) {
                meal5=false;
                diary_food_meal5_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal5_icon.setImageResource(R.drawable.meal_5_unchecked);
                diary_food_meal5_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 5) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal5_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal5_icon.setImageResource(R.drawable.meal_5);
                diary_food_meal5_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(5);
                chk++;
                meal5 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal6_layout)
    void meal6_click() {
        if(chk == 3 && meal6 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal6 == true) {
                meal6=false;
                diary_food_meal6_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal6_icon.setImageResource(R.drawable.meal_6_unchecked);
                diary_food_meal6_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 6) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal6_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal6_icon.setImageResource(R.drawable.meal_6);
                diary_food_meal6_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(6);
                chk++;
                meal6 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal7_layout)
    void meal7_click() {
        if(chk == 3 && meal7 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal7 == true) {
                meal7=false;
                diary_food_meal7_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal7_icon.setImageResource(R.drawable.meal_7_unchecked);
                diary_food_meal7_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 7) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal7_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal7_icon.setImageResource(R.drawable.meal_7);
                diary_food_meal7_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(7);
                chk++;
                meal7 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal8_layout)
    void meal8_click() {
        if(chk == 3&& meal8 == false ) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal8 == true) {
                meal8=false;
                diary_food_meal8_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal8_icon.setImageResource(R.drawable.meal_8_unchecked);
                diary_food_meal8_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 8) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal8_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal8_icon.setImageResource(R.drawable.meal_8);
                diary_food_meal8_text.setTextColor(Color.parseColor("#ffffff"));
                meal_chk_int.add(8);
                chk++;
                meal8 = true;
            }
        }

    }

    @OnClick(R.id.diary_food_meal9_layout)
    void meal9_click() {
        if(chk == 3 && meal9 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal9 == true) {
                meal9=false;
                diary_food_meal9_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal9_icon.setImageResource(R.drawable.meal_9_unchecked);
                diary_food_meal9_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 9) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal9_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal9_icon.setImageResource(R.drawable.meal_9);
                diary_food_meal9_text.setTextColor(Color.parseColor("#ffffff"));
                meal9 = true;
                meal_chk_int.add(9);
                chk++;
            }

        }

    }

    @OnClick(R.id.diary_food_meal10_layout)
    void meal10_click() {
        for(int j=0;j<meal_chk_int.size(); j++) {
            Log.e("dasd", String.valueOf(meal_chk_int.get(j)));
        }

        if(chk == 3 && meal10 == false) {
            Toast.makeText(this,"3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
        } else {
            if(meal10 == true) {
                meal10=false;
                diary_food_meal10_layout.setBackgroundResource(R.drawable.barder_food_type_off);
                diary_food_meal10_icon.setImageResource(R.drawable.meal_10_unchecked);
                diary_food_meal10_text.setTextColor(Color.parseColor("#cbcbc9"));
                chk--;
                for(int j=0;j<meal_chk_int.size(); j++) {
                    if(meal_chk_int.get(j) == 10) {
                        meal_chk_int.remove(j);
                    }
                }
            } else {
                diary_food_meal10_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_meal10_icon.setImageResource(R.drawable.meal_10);
                diary_food_meal10_text.setTextColor(Color.parseColor("#ffffff"));
                meal10 = true;
                meal_chk_int.add(10);
                chk++;
            }


        }

    }







    public void init_layout() {
        diary_food_morning_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_morning_text.setTextColor(Color.parseColor("#cbcbc9"));

        diary_food_lunch_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_lunch_text.setTextColor(Color.parseColor("#cbcbc9"));

        diary_food_dinner_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_dinner_text.setTextColor(Color.parseColor("#cbcbc9"));

        diary_food_etc_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_etc_text.setTextColor(Color.parseColor("#cbcbc9"));

        diary_food_etc2_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_etc2_text.setTextColor(Color.parseColor("#cbcbc9"));
    }

    @OnClick(R.id.diary_food_hungry_layout)
    void hungry_click() {
        init_layout2();
        con_layout_chk(1);
    }

    @OnClick(R.id.diary_food_happy_layout)
    void happy_click() {
        init_layout2();

        con_layout_chk(2);

    }

    @OnClick(R.id.diary_food_full_layout)
    void full_click() {
        init_layout2();

        con_layout_chk(3);

    }

    @OnClick(R.id.diary_food_overeat_layout)
    void overeat_click() {
        init_layout2();

        con_layout_chk(4);

    }

    public void init_layout2() {
        diary_food_hungry_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_hungry_text.setTextColor(Color.parseColor("#cbcbc9"));
        diary_food_hungry_icon.setImageResource(R.drawable.hungry_unchecked);

        diary_food_happy_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_happy_text.setTextColor(Color.parseColor("#cbcbc9"));
        diary_food_happy_icon.setImageResource(R.drawable.happy_unchecked);

        diary_food_full_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_full_text.setTextColor(Color.parseColor("#cbcbc9"));
        diary_food_full_icon.setImageResource(R.drawable.full_unchecked);

        diary_food_overeat_layout.setBackgroundResource(R.drawable.barder_food_type_off);
        diary_food_overeat_text.setTextColor(Color.parseColor("#cbcbc9"));
        diary_food_overeat_icon.setImageResource(R.drawable.overeat_unchecked);


    }


    public void con_layout_chk(int type) {

        switch (type) {
            case 1:
                diary_food_hungry_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_hungry_text.setTextColor(Color.parseColor("#cbcbc9"));
                diary_food_hungry_icon.setImageResource(R.drawable.hungry);
                break;

            case 2:
                diary_food_happy_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_happy_text.setTextColor(Color.parseColor("#cbcbc9"));
                diary_food_happy_icon.setImageResource(R.drawable.happy);
                break;

            case 3:
                diary_food_full_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_full_text.setTextColor(Color.parseColor("#cbcbc9"));
                diary_food_full_icon.setImageResource(R.drawable.full);
                break;

            case 4:
                diary_food_overeat_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_overeat_text.setTextColor(Color.parseColor("#cbcbc9"));
                diary_food_overeat_icon.setImageResource(R.drawable.overeat);
                break;



        }
    }

}
