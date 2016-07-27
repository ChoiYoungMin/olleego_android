package com.edn.olleego.model.user;

import java.util.Date;

/**
 * Created by Antonio on 2016-07-14.
 */
public class InBody {

    int health_temp;

    float bmi;

    int bloodsugar;

    int weight;

    int height;

    String _id;

    Date updated;

    Date created;

    Bloodpressure bloodpressure;

    int waist;




    public int getHealth_temp() {
        return this.health_temp;
    }
    public void setHealth_temp(int health_temp) {
        this.health_temp = health_temp;
    }
    public double getBmi() {
        return this.bmi;
    }
    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
    public int getBloodsugar() {
        return this.bloodsugar;
    }
    public void setBloodsugar(int bloodsugar) {
        this.bloodsugar = bloodsugar;
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
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
    public Bloodpressure getBloodpressure() {
        return this.bloodpressure;
    }
    public void setBloodpressure(Bloodpressure bloodpressure) {
        this.bloodpressure = bloodpressure;
    }
    public int getWaist() {
        return this.waist;
    }
    public void setWaist(int waist) {
        this.waist = waist;
    }


}

