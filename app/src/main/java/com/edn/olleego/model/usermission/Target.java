package com.edn.olleego.model.usermission;

/**
 * Created by Antonio on 2016-08-09.
 */
public class Target {

    int _id;
    String value;


    public void set_id(int _id){
        this._id=_id;

    }

    public int get_id() {
        return _id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
