package com.edn.olleego.model.Lifes;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-15.
 */
public class Result {

    int _id;

    Date updated;

    Date created;

    //LfgLgSort lfg_lg_sort;

    //LfgMdSort lfg_md_sort;

    //LfgSmSort lfg_sm_sort;

    String title;

    int __v;

    List<LfList> lf_list;


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
    public LfgLgSort getLfg_lg_sort() {
        return this.lfg_lg_sort
    }
    public LfgLgSort setLfg_lg_sort(LfgLgSort lfg_lg_sort) {
        this.lfg_lg_sort = lfg_lg_sort
    }
    @JsonProperty("lfg_md_sort")
    public LfgMdSort getLfg_md_sort() {
        return this.lfg_md_sort
    }
    public LfgMdSort setLfg_md_sort(LfgMdSort lfg_md_sort) {
        this.lfg_md_sort = lfg_md_sort
    }
    @JsonProperty("lfg_sm_sort")
    public LfgSmSort getLfg_sm_sort() {
        return this.lfg_sm_sort
    }
    public LfgSmSort setLfg_sm_sort(LfgSmSort lfg_sm_sort) {
        this.lfg_sm_sort = lfg_sm_sort
    }*/
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
    public List<LfList> getLf_list() {
        return this.lf_list;
    }
    public void setLf_list(List<LfList> lf_list) {
        this.lf_list = lf_list;
    }

}
