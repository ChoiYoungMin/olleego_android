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





    public void DiaryWaterAdd(int user, String day, int waters) {

        this.user= user;
        this.day = day;
        water = waters;
    }

    public void DiaryWalkingAdd(int user, String day, float sleeps) {

        this.user= user;
        this.day = day;
        sleep = sleeps;
    }

    public void DiarySleepAdd(int user, String day, int walkings) {

        this.user= user;
        this.day = day;
        walking = walkings;
    }

    public void DiaryFoodAdd(int user, String day, List<String> foods) {

        this.user= user;
        this.day = day;
        food = foods;
    }
}
