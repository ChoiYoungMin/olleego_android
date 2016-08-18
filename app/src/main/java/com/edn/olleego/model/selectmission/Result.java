package com.edn.olleego.model.selectmission;


import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class Result {


    String mission_status;

    int user_count;

    int point_user;

    int point_avg;

    int _id;

    Date updated;

    Date created;

    String title;

    String title_img;

    String description1;

    String description2;

    MiHealth mi_health;

    MiLgSort mi_lg_sort;

    int mi_sm_sort;

    MiTarget mi_target;

    MiLevel mi_level;

    int mi_term;

    int __v;

    List<MiDay> mi_days;

    List<String> description_img;
    List<MiMdSort> mi_md_sort;



    public String getMission_status() {
        return this.mission_status;
    }
    public void setMission_status(String mission_status) {
        this.mission_status = mission_status;
    }
    public int getUser_count() {
        return this.user_count;
    }
    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }
    public int getPoint_user() {
        return this.point_user;
    }
    public void setPoint_user(int point_user) {
        this.point_user = point_user;
    }
    public int getPoint_avg() {
        return this.point_avg;
    }
    public void setPoint_avg(int point_avg) {
        this.point_avg = point_avg;
    }
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
    public String getTitle_img() {
        return this.title_img;
    }
    public void setTitle_img(String title_img) {
        this.title_img = title_img;
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
    public MiHealth getMi_health() {
        return this.mi_health;
    }
    public void setMi_health(MiHealth mi_health) {
        this.mi_health = mi_health;
    }
    public MiLgSort getMi_lg_sort() {
        return this.mi_lg_sort;
    }
    public void setMi_lg_sort(MiLgSort mi_lg_sort) {
        this.mi_lg_sort = mi_lg_sort;
    }
    public int getMi_sm_sort() {
        return this.mi_sm_sort;
    }
    public void setMi_sm_sort(int mi_sm_sort) {
        this.mi_sm_sort = mi_sm_sort;
    }
    public MiTarget getMi_target() {
        return this.mi_target;
    }
    public void setMi_target(MiTarget mi_target) {
        this.mi_target = mi_target;
    }
    public MiLevel getMi_level() {
        return this.mi_level;
    }
    public void setMi_level(MiLevel mi_level) {
        this.mi_level = mi_level;
    }
    public int getMi_term() {
        return this.mi_term;
    }
    public void setMi_term(int mi_term) {
        this.mi_term = mi_term;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    public List<MiDay> getMi_days() {
        return this.mi_days;
    }
    public void setMi_days(List<MiDay> mi_days) {
        this.mi_days = mi_days;
    }
    public List<String> getDescription_img() {
        return this.description_img;
    }
    public void setDescription_img(List<String> description_img) {
        this.description_img = description_img;
    }
    public List<MiMdSort> getMi_md_sort() {
        return this.mi_md_sort;
    }
    public void setMi_md_sort(List<MiMdSort> mi_md_sort) {
        this.mi_md_sort = mi_md_sort;
    }

}
