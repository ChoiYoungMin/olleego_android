package com.edn.olleego.model.report_weight;

/**
 * Created by Antonio on 2016-08-05.
 */
public class Result {


    int _id;

    int weight;
    String day;


    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getDay() {
        return this.day;
    }
    public void setDay(String day) {
        this.day = day;
    }

}
