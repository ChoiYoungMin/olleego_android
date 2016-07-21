package com.edn.olleego.model.selectmission;

import java.util.List;

/**
 * Created by Antonio on 2016-07-21.
 */
public class MiDay {


    int day;

    String _id;

    List<Exgroup> exgroup;

    boolean rest;

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
    public List<Exgroup> getExgroup() {
        return this.exgroup;
    }
    public void setExgroup(List<Exgroup> exgroup) {
        this.exgroup = exgroup;
    }
    public boolean getRest() {
        return this.rest;
    }
    public void setRest(boolean rest) {
        this.rest = rest;
    }
}
