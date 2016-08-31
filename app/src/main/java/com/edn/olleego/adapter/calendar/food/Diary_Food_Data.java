package com.edn.olleego.adapter.calendar.food;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-27.
 */
public class Diary_Food_Data {

    String mImg;
    String mSort;
    String mFoods;
    String mMemo;
    Date mDay;
    String mIcon;

    ArrayList<String> foodlist= new ArrayList<String>();

    public Diary_Food_Data(String mImg, String mSort, String mFoods, String mMemo, String mIcon, Date day, List<String> foodlist) {
        this.mImg = mImg;
        this.mSort = mSort;
        this.mFoods = mFoods;
        this.mMemo = mMemo;
        this.mIcon = mIcon;
        this.mDay  = day;

        for(int i =0; i<foodlist.size(); i++ ){
            this.foodlist.add(foodlist.get(i));
        }
    }
}
