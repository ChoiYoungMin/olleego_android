package com.edn.olleego.model.diarymonth;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-26.
 */
public class Result {


    String _id;

    Date day;

    int user;


    Date updated;

    Date created;

    int sleep;
    int walking;
    int water;

    List<Food> food;

    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public Date getDay() {
        return this.day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public int getUser() {
        return this.user;
    }
    public void setUser(int user) {
        this.user = user;
    }
    public int getWalking() {
        return this.walking;
    }
    public void setWalking(int walking) {
        this.walking = walking;
    }
    public Date getUpdated() {
        return this.updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public Date getCreated() {
        return this.created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public int getSleep() {
        return this.sleep;
    }
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
    public int getWater() {
        return this.water;
    }
    public void setWater(int water) {
        this.water = water;
    }
    public List<Food> getFood() {
        return this.food;
    }
    public void setFood(List<Food> food) {
        this.food = food;
    }
}
