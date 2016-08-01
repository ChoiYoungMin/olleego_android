package com.edn.olleego.model.exdetail;

import com.edn.olleego.model.diary.Food;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class Result {


    int _id;

    Date updated;

    Date created;

    String title;

    String method;

    String warning;

    int tt_sort;

    LgSort lg_sort;

    MdSort md_sort;

    DtSort dt_sort;

    BdSort bd_sort;

    LvAttr lv_attr;

    //int std_attr;

    boolean bp_etc;

    boolean bs_etc;

    int __v;

    //List<Object> hashtags;

    //List<Object> thum_gif;

    //List<Object> thum_jpg;

    List<SmSort> sm_sort;



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
    public LgSort getLg_sort() {
        return this.lg_sort;
    }
    public void setLg_sort(LgSort lg_sort) {
        this.lg_sort = lg_sort;
    }
    public MdSort getMd_sort() {
        return this.md_sort;
    }
    public void setMd_sort(MdSort md_sort) {
        this.md_sort = md_sort;
    }
    public DtSort getDt_sort() {
        return this.dt_sort;
    }
    public void setDt_sort(DtSort dt_sort) {
        this.dt_sort = dt_sort;
    }
    public BdSort getBd_sort() {
        return this.bd_sort;
    }
    public void setBd_sort(BdSort bd_sort) {
        this.bd_sort = bd_sort;
    }
    public LvAttr getLv_attr() {
        return this.lv_attr;
    }
    public void setLv_attr(LvAttr lv_attr) {
        this.lv_attr = lv_attr;
    }
    /*
    public int getStd_attr() {
        return this.std_attr;
    }
    public void setStd_attr(int std_attr) {
        this.std_attr = std_attr;
    }*/
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
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }

    /*
    public List<Object> getHashtags() {
        return this.hashtags;
    }

    public void setHashtags(List<Object> hashtags) {
        this.hashtags = hashtags;
    }
    public List<Object> getThum_gif() {
        return this.thum_gif;
    }
    public void setThum_gif(List<Object> thum_gif) {
        this.thum_gif = thum_gif;
    }
    public List<Object> getThum_jpg() {
        return this.thum_jpg;
    }
    public void setThum_jpg(List<Object> thum_jpg) {
        this.thum_jpg = thum_jpg;
    }
    */
    public List<SmSort> getSm_sort() {
        return this.sm_sort;
    }
    public void setSm_sort(List<SmSort> sm_sort) {
        this.sm_sort = sm_sort;
    }
}
