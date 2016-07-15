package com.edn.olleego.model.foods;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class Result {


    int _id;

    Date updated;

    Date created;

    //FdgLgSort fdg_lg_sort;

    //FdgMdSort fdg_md_sort;

    //FdgSmSort fdg_sm_sort;

    String title;

    int __v;

    List<FdList> fd_list;


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

    /*
    public FdgLgSort getFdg_lg_sort() {
        return this.fdg_lg_sort;
    }
    public void setFdg_lg_sort(FdgLgSort fdg_lg_sort) {
        this.fdg_lg_sort = fdg_lg_sort;
    }
    public FdgMdSort getFdg_md_sort() {
        return this.fdg_md_sort;
    }
    public void setFdg_md_sort(FdgMdSort fdg_md_sort) {
        this.fdg_md_sort = fdg_md_sort;
    }
    public FdgSmSort getFdg_sm_sort() {
        return this.fdg_sm_sort;
    }
    public void setFdg_sm_sort(FdgSmSort fdg_sm_sort) {
        this.fdg_sm_sort = fdg_sm_sort;
    }
    */
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    public List<FdList> getFd_list() {
        return this.fd_list;
    }
    public void setFd_list(List<FdList> fd_list) {
        this.fd_list = fd_list;
    }



}
