package com.edn.olleego.model.exgroups;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class Ex  implements Serializable{


    int _id;

    Date updated;

    Date created;

    String title;

    String method;

    String warning;

    int tt_sort;

    int lg_sort;

    int md_sort;

    int dt_sort;

    int bd_sort;

    int lv_attr;

    int std_attr;

    boolean bp_etc;

    boolean bs_etc;

    String thum_gif;

    String thum_jpg;

    int __v;

    //List<Object> hashtags;

    List<Integer> sm_sort;

    String movie_url;
    String description;




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
    public String getMethod() {
        return this.method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getWarning() {
        return this.warning;
    }
    public void setWarning(String warning) {
        this.warning = warning;
    }
    public int getTt_sort() {
        return this.tt_sort;
    }
    public void setTt_sort(int tt_sort) {
        this.tt_sort = tt_sort;
    }
    public int getLg_sort() {
        return this.lg_sort;
    }
    public void setLg_sort(int lg_sort) {
        this.lg_sort = lg_sort;
    }
    public int getMd_sort() {
        return this.md_sort;
    }
    public void setMd_sort(int md_sort) {
        this.md_sort = md_sort;
    }
    public int getDt_sort() {
        return this.dt_sort;
    }
    public void setDt_sort(int dt_sort) {
        this.dt_sort = dt_sort;
    }
    public int getBd_sort() {
        return this.bd_sort;
    }
    public void setBd_sort(int bd_sort) {
        this.bd_sort = bd_sort;
    }
    public int getLv_attr() {
        return this.lv_attr;
    }
    public void setLv_attr(int lv_attr) {
        this.lv_attr = lv_attr;
    }
    public int getStd_attr() {
        return this.std_attr;
    }
    public void setStd_attr(int std_attr) {
        this.std_attr = std_attr;
    }
    public boolean getBp_etc() {
        return this.bp_etc;
    }
    public void setBp_etc(boolean bp_etc) {
        this.bp_etc = bp_etc;
    }
    public boolean getBs_etc() {
        return this.bs_etc;
    }
    public void setBs_etc(boolean bs_etc) {
        this.bs_etc = bs_etc;
    }
    public String getThum_gif() {
        return this.thum_gif;
    }
    public void setThum_gif(String thum_gif) {
        this.thum_gif = thum_gif;
    }
    public String getThum_jpg() {
        return this.thum_jpg;
    }
    public void setThum_jpg(String thum_jpg) {
        this.thum_jpg = thum_jpg;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    public List<Integer> getSm_sort() {
        return this.sm_sort;
    }
    public void setSm_sort(List<Integer> sm_sort) {
        this.sm_sort = sm_sort;
    }
    public String getMovie_url() {
        return this.movie_url;
    }
    public void setMovie_url(String movie_url) {
        this.movie_url = movie_url;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
