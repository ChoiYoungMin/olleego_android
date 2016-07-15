package com.edn.olleego.model.diary;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class Result {

    Date day;

    String water;

    //User user;

    Date updated;

    Date created;

    String walking;
    String sleep;
    List<Food> food;

    Date waterUpdatedAt;

    Date waterCreatedAt;


    public Date getDay() {
        return this.day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public String getSleep() {
        return this.sleep;
    }
    public void setSleep(String sleep) {
        this.water = sleep;
    }
    public String getWater() {
        return this.water;
    }
    public void setWater(String water) {
        this.water = water;
    }

    /*
    public User getUser() {
        return this.user
    }
    public User setUser(User user) {
        this.user = user
    }*/
    public Date getUpdated() {
        return this.updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    int __v;

    public Date getCreated() {
        return this.created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getWalking() {
        return this.walking;
    }
    public void setWalking(String walking) {
        this.walking = walking;
    }
    public List<Food> getFood() {
        return this.food;
    }
    public void setFood(List<Food> food) {
        this.food = food;
    }
    public Date getWaterUpdatedAt() {
        return this.waterUpdatedAt;
    }
    public void setWaterUpdatedAt(Date waterUpdatedAt) {
        this.waterUpdatedAt = waterUpdatedAt;
    }
    public Date getWaterCreatedAt() {
        return this.waterCreatedAt;
    }
    public void setWaterCreatedAt(Date waterCreatedAt) {
        this.waterCreatedAt = waterCreatedAt;
    }
}
