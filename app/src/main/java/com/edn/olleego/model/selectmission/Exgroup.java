package com.edn.olleego.model.selectmission;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-21.
 */
public class Exgroup {



    int _id;

    Date updated;

    Date created;

    String title;

    int time;

    int freq;

    int count;

    int exset;

    int repeat;

    int holding;

    int __v;

    List<ExList> ex_list;

    List<Integer> target;

    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
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
    public int getFreq() {
        return this.freq;
    }
    public void setFreq(int freq) {
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
    public int getHolding() {
        return this.holding;
    }
    public void setHolding(int holding) {
        this.holding = holding;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    public List<ExList> getEx_list() {
        return this.ex_list;
    }
    public void setEx_list(List<ExList> ex_list) {
        this.ex_list = ex_list;
    }
    public List<Integer> getTarget() {
        return this.target;
    }
    public void setTarget(List<Integer> target) {
        this.target = target;
    }
}
