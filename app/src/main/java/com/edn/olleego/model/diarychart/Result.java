package com.edn.olleego.model.diarychart;

import java.util.Date;

/**
 * Created by Antonio on 2016-08-09.
 */
public class Result {


    int _id;

    Date day;
    int walking;
    float sleep;
    int water;


    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public Date getDay() {
        return this.day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public float getSleep() {
        return this.sleep;
    }
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getWalking() {
        return this.walking;
    }
    public void setWalking(int walking) {
        this.walking = walking;
    }

    public int getWater() {
        return this.water;
    }
    public void setWater(int water) {
        this.water = water;
    }



}
