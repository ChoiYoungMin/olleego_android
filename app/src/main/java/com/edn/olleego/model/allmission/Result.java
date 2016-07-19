package com.edn.olleego.model.allmission;

import com.edn.olleego.model.usermission.MiDay2;
import com.edn.olleego.model.usermission.Mission;
import com.kakao.usermgmt.response.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class Result {


    int _id;

    Date updated;

    Date created;

    String title;

    String description1;

    String description2;

    MiHelth mi_helth;

    MiLgSort mi_lg_sort;

    MiTarget mi_target;

    MiLevel mi_level;

    String title_img;

    int mi_term;

    int __v;

    List<MiDay> mi_days;

    List<Object> description_img;

    List<MiMdSort> mi_md_sort;









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
    public MiHelth getMi_helth() {
        return this.mi_helth;
    }
    public void setMi_helth(MiHelth mi_helth) {
        this.mi_helth = mi_helth;
    }
    public MiLgSort getMi_lg_sort() {
        return this.mi_lg_sort;
    }
    public void setMi_lg_sort(MiLgSort mi_lg_sort) {
        this.mi_lg_sort = mi_lg_sort;
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
    public List<Object> getDescription_img() {
        return this.description_img;
    }
    public void setDescription_img(List<Object> description_img) {
        this.description_img = description_img;
    }
    public List<MiMdSort> getMi_md_sort() {
        return this.mi_md_sort;
    }
    public void setMi_md_sort(List<MiMdSort> mi_md_sort) {
        this.mi_md_sort = mi_md_sort;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }
    public String getTitle_img() {
        return this.title_img;
    }
}
