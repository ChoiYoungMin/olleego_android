package com.edn.olleego.model.Lifes;

import java.util.Date;

/**
 * Created by Antonio on 2016-07-15.
 */
public class LfList {


    int _id;

    Date updated;

    Date created;

    String title;

    int lf_lg_sort;

    int lf_md_sort;

    int lf_sm_sort;

    int lf_level;

    int lf_time;

    int lf_div;

    String description1;

    String description2;

    String tip;

    int __v;

    //Image2 image2;

    //Image1 image1;


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
    public int getLf_lg_sort() {
        return this.lf_lg_sort;
    }
    public void setLf_lg_sort(int lf_lg_sort) {
        this.lf_lg_sort = lf_lg_sort;
    }
    public int getLf_md_sort() {
        return this.lf_md_sort;
    }
    public void setLf_md_sort(int lf_md_sort) {
        this.lf_md_sort = lf_md_sort;
    }
    public int getLf_sm_sort() {
        return this.lf_sm_sort;
    }
    public void setLf_sm_sort(int lf_sm_sort) {
        this.lf_sm_sort = lf_sm_sort;
    }
    public int getLf_level() {
        return this.lf_level;
    }
    public void setLf_level(int lf_level) {
        this.lf_level = lf_level;
    }
    public int getLf_time() {
        return this.lf_time;
    }
    public void setLf_time(int lf_time) {
        this.lf_time = lf_time;
    }
    public int getLf_div() {
        return this.lf_div;
    }
    public void setLf_div(int lf_div) {
        this.lf_div = lf_div;
    }
    public String getDescription1() {
        return this.description1;
    }
    public void setDescription1(String description1) {
        this.description1 = description1;
    }
    public String getDescription2() {
        return this.description2;
    }
    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    public String getTip() {
        return this.tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    /*
    public Image2 getImage2() {
        return this.image2
    }
    public Image2 setImage2(Image2 image2) {
        this.image2 = image2
    }
    public Image1 getImage1() {
        return this.image1
    }
    public Image1 setImage1(Image1 image1) {
        this.image1 = image1
    } */
}
