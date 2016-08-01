package com.edn.olleego.server.request;

import java.util.List;

/**
 * Created by Antonio on 2016-08-01.
 */
public class DiaryFoodAdd {

    int user;
    String day;
    Foods food;





    public DiaryFoodAdd(int user, String day, Foods foods) {

        this.user= user;
        this.day = day;
        food = foods;
    }

    public int getUser() {
        return user;
    }

    public String getDay() {
        return day;
    }

    public Foods getFood() {
        return food;
    }


}
