package com.edn.olleego.model.usermission;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-21.
 */
public class Exgroup {


    int _id;

    String title;

    int time;

    Freq freq;

    int count;

    int exset;

    int repeat;
    List<ExList> ex_list;
    List<Target> target;

    public void setTarget(List<Target> target) {
        this.target= target;
    }
    public List<Target> getTarget() {
        return target;
    }

    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public Freq getFreq() {
        return this.freq;
    }
    public void setFreq(Freq freq) {
        this.freq = freq;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getExset() {
        return this.exset;
    }
    public void setExset(int exset) {
        this.exset = exset;
    }
    public int getRepeat() {
        return this.repeat;
    }
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    public List<ExList> getEx_list() {
        return this.ex_list;
    }
    public void setEx_list(List<ExList> ex_list) {
        this.ex_list = ex_list;
    }
}
