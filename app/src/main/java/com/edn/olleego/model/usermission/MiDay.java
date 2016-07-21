package com.edn.olleego.model.usermission;

import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MiDay {


    Life life;

    Food food;

    List<Exgroup> exgroup;

    int day;

    String _id;

    boolean rest;



    public Life getLife() {
        return this.life;
    }
    public void setLife(Life life) {
        this.life = life;
    }
    public Food getFood() {
        return this.food;
    }
    public void setFood(Food food) {
        this.food = food;
    }
    public List<Exgroup> getExgroup() {
        return this.exgroup;
    }
    public void setExgroup(List<Exgroup> exgroup) {
        this.exgroup = exgroup;
    }
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
    public boolean getRest() {
        return this.rest;
    }
    public void setRest(boolean rest) {
        this.rest = rest;
    }




}
