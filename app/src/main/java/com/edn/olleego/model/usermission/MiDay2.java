package com.edn.olleego.model.usermission;

import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MiDay2 {

    int day;

    String _id;

    Life2 life;

    Food2 food;

    Exgroup2 exgroup;

    boolean day_complete;

    boolean lf_complete;

    boolean fd_complete;
    boolean ex_complete;



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
    public Life2  getLife() {
        return this.life;
    }
    public void  setLife(Life2 life) {
        this.life = life;
    }
    public Food2  getFood() {
        return this.food;
    }
    public void  setFood(Food2 food) {
        this.food = food;
    }
    public Exgroup2 getExgroup() {
        return this.exgroup;
    }
    public void setExgroup(Exgroup2 exgroup) {
        this.exgroup = exgroup;
    }
    public boolean getDay_complete() {
        return this.day_complete;
    }
    public void setDay_complete(boolean day_complete) {
        this.day_complete = day_complete;
    }
    public boolean getLf_complete() {
        return this.lf_complete;
    }
    public void setLf_complete(boolean lf_complete) {
        this.lf_complete = lf_complete;
    }
    public boolean getFd_complete() {
        return this.fd_complete;
    }
    public void setFd_complete(boolean fd_complete) {
        this.fd_complete = fd_complete;
    }
    public boolean getEx_complete() {
        return this.ex_complete;
    }
    public void setEx_complete(boolean ex_complete) {
        this.ex_complete = ex_complete;
    }

}
