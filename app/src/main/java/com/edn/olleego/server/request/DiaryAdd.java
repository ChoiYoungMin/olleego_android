package com.edn.olleego.server.request;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-27.
 */
public class DiaryAdd {

    int user;
    String day;
    int water;
    float sleep;
    int walking;
    List<String> food;





    public void DiaryWaterAdd(int user, String day, int water, float sleep, int walking) {
        this.user= user;
        this.day = day;
        this.water = water;
        this.sleep = sleep;
        this.walking = walking;
    }


    public void DiaryFoodAdd(int user, String day, List<String> foods) {

        this.user= user;
        this.day = day;
        food = foods;
    }
}
