package com.edn.olleego.activity.diary;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.common.FileUtils;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.dialog.DiaryFoodImageDialog;
import com.edn.olleego.dialog.LoadingBarDialog;
import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.DiaryAddAPI;
import com.edn.olleego.server.DiaryFoodAddAPI;
import com.edn.olleego.server.request.DiaryAdd;
import com.edn.olleego.server.request.DiaryFoodAdd;
import com.edn.olleego.server.request.Foods;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class DiaryFoodActivity extends AppCompatActivity {

    @BindView(R.id.asdasd)
    ImageView test;

    @BindView(R.id.diary_food_memo)
    TextView diary_food_memo;

    @BindView(R.id.diary_food_Image_yes)
    LinearLayout diary_food_Image_yes;

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




    Uri Image_url;


    int types=0 ;

    int chk= 0;

    int con = 0;

    HashMap<Integer, String> meal = new HashMap<Integer, String>() ;


    ArrayList<Integer> meal_chk_int = new ArrayList<Integer>();
    ArrayList<String> meal_chk_String = new ArrayList<String>();

    String type_temp;
    String cons_temp;

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



    String user_id;
    String token;
    String day;
    String type;

    String img_src;
    String sort;
    String foods;
    String memo;
    String icon;

    List<String> foodlist;

    int typea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary_food);
        ButterKnife.bind(this);
        mealinit();
        Intent intent = getIntent();
        user_id= intent.getStringExtra("user");
        token = intent.getStringExtra("token");
        day = intent.getStringExtra("day") + " 00:00:00";;
        type = intent.getStringExtra("type");

        img_src = intent.getStringExtra("img_src");
        sort = intent.getStringExtra("sort");
        foods= intent.getStringExtra("foods");
        memo = intent.getStringExtra("memo");
        icon = intent.getStringExtra("icon");


        foodlist = (ArrayList<String>) intent.getSerializableExtra("foodlist");



        init_layout();
        meal_chk_int.clear();

        try{
            initdate();
            typea = 1;
        } catch (NullPointerException e) {
            typea = 2;

        }


    }

    public void initdate() {

        switch (sort) {
            case "아침":
                type_layout_chk(1);
                break;
            case "점심":
                type_layout_chk(2);
                break;
            case "저녁":
                type_layout_chk(3);
                break;
            case "간식":
                type_layout_chk(4);
                break;
            case "모임":
                type_layout_chk(5);
                break;

        }

        for(int i=0; i<foodlist.size(); i++){
            switch (foodlist.get(i)) {
                case "밥(흰밥)":
                    meal1_click();
                    break;
                case "밥(잡곡)":
                    meal2_click();
                    break;
                case "육류":
                    meal3_click();
                    break;
                case "생선류":
                    meal4_click();
                    break;
                case "채소류":
                    meal5_click();
                    break;
                case "과일류":
                    meal6_click();
                    break;
                case "빵류":
                    meal7_click();
                    break;
                case "국수류":
                    meal8_click();
                    break;
                case "유제품":
                    meal9_click();
                    break;
                case "술":
                    meal10_click();
                    break;
            }

        }

        switch (icon) {
            case "부족해":
                hungry_click();
                break;

            case "기분좋아":
                happy_click();
                break;

            case "배불러요":
                full_click();
                break;

            case "과식!":
                overeat_click();
                break;

        }


        try{
            diary_food_memo.setText(memo);

        }catch (NullPointerException e) {
            e.printStackTrace();
        }






        Glide.with(this).load(img_src).into(test);
        diary_food_Image.setVisibility(View.GONE);
        diary_food_Image_yes.setVisibility(View.VISIBLE);
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
            Image_url =  Crop.getOutput(result);
            diary_food_Image.setVisibility(View.GONE);
            diary_food_Image_yes.setVisibility(View.VISIBLE);




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
                diary_food_hungry_text.setTextColor(Color.parseColor("#ffffff"));
                diary_food_hungry_icon.setImageResource(R.drawable.hungry);
                con = 1;
                break;

            case 2:
                diary_food_happy_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_happy_text.setTextColor(Color.parseColor("#ffffff"));
                diary_food_happy_icon.setImageResource(R.drawable.happy);
                con =2;
                break;

            case 3:
                diary_food_full_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_full_text.setTextColor(Color.parseColor("#ffffff"));
                diary_food_full_icon.setImageResource(R.drawable.full);
                con = 3;
                break;

            case 4:
                diary_food_overeat_layout.setBackgroundResource(R.drawable.barder_food_type_on);
                diary_food_overeat_text.setTextColor(Color.parseColor("#ffffff"));
                diary_food_overeat_icon.setImageResource(R.drawable.overeat);
                con=4;
                break;



        }
    }

    public void mealinit() {
        meal.put(1, "밥(흰밥)");
        meal.put(2, "밥(잡곡)");
        meal.put(3, "육류");
        meal.put(4, "생선류");
        meal.put(5, "채소류");
        meal.put(6, "과일류");
        meal.put(7, "빵류");
        meal.put(8, "국수류");
        meal.put(9, "유제품");
        meal.put(10, "술");

    }

    @OnClick(R.id.diary_food_ok)
    void diary_food_ok() {


        if(diary_food_Image_yes.getVisibility() == View.GONE) {
            Toast.makeText(this, "이미지를 등록하세요.", Toast.LENGTH_SHORT).show();
        } else if (types == 0) {
            Toast.makeText(this, "식사종류를 선택하세요.", Toast.LENGTH_SHORT).show();
        } else if(meal_chk_int.size() == 0) {
            Toast.makeText(this, "식사를 선택하세요.", Toast.LENGTH_SHORT).show();

        }else if(con == 0) {
            Toast.makeText(this, "포만감을 선택하세요.", Toast.LENGTH_SHORT).show();
        }
        else {

            final LoadingBarDialog loadingBarDialog = new LoadingBarDialog(this, "food_add");
            loadingBarDialog.show();


            switch (types) {
                case 1:
                    type_temp = "아침";
                    break;
                case 2:
                    type_temp = "점심";
                    break;
                case 3:
                    type_temp = "저녁";
                    break;
                case 4:
                    type_temp = "간식";
                    break;
                case 5:
                    type_temp = "모임";
                    break;

            }



            switch (con) {
                case 1:
                    cons_temp = "부족해";
                    break;
                case 2:
                    cons_temp = "기분좋아";
                    break;
                case 3:
                    cons_temp = "배불러요";
                    break;
                case 4:
                    cons_temp = "과식!";
                    break;

            }

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                    .connectTimeout(15, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                    .writeTimeout(15, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                    .readTimeout(15, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                    .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                    .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                    .build();



            Retrofit retrofit_diary = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String tokens = "olleego " + token;
            DiaryFoodAddAPI diaryAddAPI = retrofit_diary.create(DiaryFoodAddAPI.class);

            for(int j=0;j<meal_chk_int.size(); j++) {
                meal_chk_String.add(meal.get(meal_chk_int.get(j)));
            }

            Call<MissionsModel> diaryPos = null;
            if(typea == 2) {

                File file = FileUtils.getFile(this, Image_url);

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/*"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", file.getName()+".png", requestFile);

                Foods foods = new Foods(type_temp, meal_chk_String, diary_food_memo.getText().toString(), cons_temp);


                DiaryFoodAdd diaryAdd = new DiaryFoodAdd(Integer.valueOf(user_id), day, foods);


                diaryPos = diaryAddAPI.listRepos(tokens, "food" ,diaryAdd, body);

            } else if(typea == 1) {
                Foods foods = new Foods(type_temp, meal_chk_String, diary_food_memo.getText().toString(), cons_temp);


                DiaryFoodAdd diaryAdd = new DiaryFoodAdd(Integer.valueOf(user_id), day, foods);

                diaryPos = diaryAddAPI.listRepos(tokens, "food" ,diaryAdd);
            }





            diaryPos.enqueue(new Callback<MissionsModel>() {
                @Override
                public void onResponse(Call<MissionsModel> call, Response<MissionsModel> response) {

                    if(response.isSuccessful()) {
                        loadingBarDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("food_type", type_temp);
                        intent.putExtra("type", type);
                        setResult(5, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<MissionsModel> call, Throwable t) {

                }
            });
        }



    }

    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder;
    }

}
