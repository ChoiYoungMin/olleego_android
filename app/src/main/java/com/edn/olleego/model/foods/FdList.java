package com.edn.olleego.model.foods;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class FdList  implements Serializable {

    int _id;

    Date updated;

    Date created;

    int fd_lg_sort;

    int fd_md_sort;

    int fd_sm_sort;

    int fd_level;

    int fd_time;

    int fd_div;

    String title;

    String description1;

    String description2;

    String tip;

    int __v;

    String title_img;

    List<String> description_img;


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
    public int getFd_lg_sort() {
        return this.fd_lg_sort;
    }
    public void setFd_lg_sort(int fd_lg_sort) {
        this.fd_lg_sort = fd_lg_sort;
    }
    public int getFd_md_sort() {
        return this.fd_md_sort;
    }
    public void setFd_md_sort(int fd_md_sort) {
        this.fd_md_sort = fd_md_sort;
    }
    public int getFd_sm_sort() {
        return this.fd_sm_sort;
    }
    public void setFd_sm_sort(int fd_sm_sort) {
        this.fd_sm_sort = fd_sm_sort;
    }
    public int getFd_level() {
        return this.fd_level;
    }
    public void setFd_level(int fd_level) {
        this.fd_level = fd_level;
    }
    public int getFd_time() {
        return this.fd_time;
    }
    public void setFd_time(int fd_time) {
        this.fd_time = fd_time;
    }
    public int getFd_div() {
        return this.fd_div;
    }
    public void setFd_div(int fd_div) {
        this.fd_div = fd_div;
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
    public String getTitle_img() {
        return this.title_img;
    }
    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }
    public List<String> getDescription_img() {
        return this.description_img;
    }
    public void setDescription_img(List<String> description_img) {
        this.description_img = description_img;
    }
}
