package com.edn.olleego.model.report;

import java.util.Date;

/**
 * Created by Antonio on 2016-08-03.
 */
public class Result {


    int _id;

    Date updated;

    int weight;

    double bmi;

    int muscle;

    int body_fat;

    int body_fat_per;

    double whr;

    int basal_metabolism;

    int center;

    int charge;

    User user;

    int hip;

    int height;

    int waist;

    int blood_sugar;

    int proper_weight;

    Date created;

    int health_temp;

    int __v;

    BloodPressure blood_pressure;

    String writer_type;
    WhrEval whr_eval;
    MsStatus ms_status;

    Mission1 mission1;
    Mission2 mission2;



    public Mission1 getMission1() {
        return this.mission1;
    }
    public void setMission1(Mission1 mission1) {
        this.mission1 = mission1;
    }
    public Mission2 getMission2() {
        return this.mission2;
    }
    public void setMission2(Mission2 mission2) {
        this.mission2 = mission2;
    }

    public MsStatus getMs_status() {
        return this.ms_status;
    }
    public void setMs_status(MsStatus ms_status) {
        this.ms_status = ms_status;
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
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public double getBmi() {
        return this.bmi;
    }
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
    public int getMuscle() {
        return this.muscle;
    }
    public void setMuscle(int muscle) {
        this.muscle = muscle;
    }
    public int getBody_fat() {
        return this.body_fat;
    }
    public void setBody_fat(int body_fat) {
        this.body_fat = body_fat;
    }
    public int getBody_fat_per() {
        return this.body_fat_per;
    }
    public void setBody_fat_per(int body_fat_per) {
        this.body_fat_per = body_fat_per;
    }
    public double getWhr() {
        return this.whr;
    }
    public void setWhr(double whr) {
        this.whr = whr;
    }
    public int getBasal_metabolism() {
        return this.basal_metabolism;
    }
    public void setBasal_metabolism(int basal_metabolism) {
        this.basal_metabolism = basal_metabolism;
    }
    public int getCenter() {
        return this.center;
    }
    public void setCenter(int center) {
        this.center = center;
    }
    public int getCharge() {
        return this.charge;
    }
    public void setCharge(int charge) {
        this.charge = charge;
    }
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getHip() {
        return this.hip;
    }
    public void setHip(int hip) {
        this.hip = hip;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWaist() {
        return this.waist;
    }
    public void setWaist(int waist) {
        this.waist = waist;
    }
    public int getBlood_sugar() {
        return this.blood_sugar;
    }
    public void setBlood_sugar(int blood_sugar) {
        this.blood_sugar = blood_sugar;
    }
    public int getProper_weight() {
        return this.proper_weight;
    }
    public void setProper_weight(int proper_weight) {
        this.proper_weight = proper_weight;
    }
    public Date getCreated() {
        return this.created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public int getHealth_temp() {
        return this.health_temp;
    }
    public void setHealth_temp(int health_temp) {
        this.health_temp = health_temp;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    public BloodPressure getBlood_pressure() {
        return this.blood_pressure;
    }
    public void setBlood_pressure(BloodPressure blood_pressure) {
        this.blood_pressure = blood_pressure;
    }
    public String getWriter_type() {
        return this.writer_type;
    }
    public void setWriter_type(String writer_type) {
        this.writer_type = writer_type;
    }
    public WhrEval getWhr_eval() {
        return this.whr_eval;
    }
    public void setWhr_eval(WhrEval whr_eval) {
        this.whr_eval = whr_eval;
    }

}
