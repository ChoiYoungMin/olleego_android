package com.edn.olleego.model.mission;

import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MiDay2 {
    int day;


    String _id;

    boolean day_complete;


    List<Object> life;


    List<Object> food;


    List<Object> exgroup;


    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean getDay_complete() {
        return this.day_complete;
    }
    public void setDay_complete(boolean day_complete) {
        this.day_complete = day_complete;
    }
    public List<Object> getLife() {
        return this.life;
    }
    public void setLife(List<Object> life) {
        this.life = life;
    }
    public List<Object> getFood() {
        return this.food;
    }
    public void setFood(List<Object> food) {
        this.food = food;
    }
    public List<Object> getExgroup() {
        return this.exgroup;
    }
    public void setExgroup(List<Object> exgroup) {
        this.exgroup = exgroup;
    }
}
