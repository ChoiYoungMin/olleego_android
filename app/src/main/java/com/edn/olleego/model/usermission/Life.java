package com.edn.olleego.model.usermission;

import java.util.List;

/**
 * Created by Antonio on 2016-07-21.
 */
public class Life {

    int _id;

    String title;
    List<LfList> lf_list;




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
    public List<LfList> getLf_list() {
        return this.lf_list;
    }
    public void setLf_list(List<LfList> lf_list) {
        this.lf_list = lf_list;
    }
}
