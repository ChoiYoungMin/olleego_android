package com.edn.olleego.model.usermission;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class Mission {


    int _id;

    String title;

    MiLgSort mi_lg_sort;

    int mi_term;
    List<MiDay> mi_days;


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
    public MiLgSort getMi_lg_sort() {
        return this.mi_lg_sort;
    }
    public void setMi_lg_sort(MiLgSort mi_lg_sort) {
        this.mi_lg_sort = mi_lg_sort;
    }
    public int getMi_term() {
        return this.mi_term;
    }
    public void setMi_term(int mi_term) {
        this.mi_term = mi_term;
    }
    public List<MiDay> getMi_days() {
        return this.mi_days;
    }
    public void setMi_days(List<MiDay> mi_days) {
        this.mi_days = mi_days;
    }
}
