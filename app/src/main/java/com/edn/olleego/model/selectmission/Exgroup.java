package com.edn.olleego.model.selectmission;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-21.
 */
public class Exgroup {

    int _id;

    int time;
    Freq freq;


    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public Freq getFreq() {
        return this.freq;
    }
    public void setFreq(Freq freq) {
        this.freq = freq;
    }

}
