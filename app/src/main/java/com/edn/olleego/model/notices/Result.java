package com.edn.olleego.model.notices;

import java.util.Date;

/**
 * Created by Antonio on 2016-08-18.
 */
public class Result {

    int _id;

    Date updated;

    Date created;

    int user;

    String title;

    String body;
    int __v;


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
    public int getUser() {
        return this.user;
    }
    public void setUser(int user) {
        this.user = user;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
}
