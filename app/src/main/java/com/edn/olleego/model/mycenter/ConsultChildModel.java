package com.edn.olleego.model.mycenter;

/**
 * Created by pc on 2016-08-22.
 */
public class ConsultChildModel{
    private String consult_text;
    private int _id;
    private float weight;
    private float bmi;
    private float muscle;
    private float body_fat;
    private float body_fat_per;
    private float whr;
    private float basal_metabolism;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getMuscle() {
        return muscle;
    }

    public void setMuscle(float muscle) {
        this.muscle = muscle;
    }

    public float getBody_fat() {
        return body_fat;
    }

    public void setBody_fat(float body_fat) {
        this.body_fat = body_fat;
    }

    public float getBody_fat_per() {
        return body_fat_per;
    }

    public void setBody_fat_per(float body_fat_per) {
        this.body_fat_per = body_fat_per;
    }

    public float getWhr() {
        return whr;
    }

    public void setWhr(float whr) {
        this.whr = whr;
    }

    public float getBasal_metabolism() {
        return basal_metabolism;
    }

    public void setBasal_metabolism(float basal_metabolism) {
        this.basal_metabolism = basal_metabolism;
    }

    public String getConsult_text() {
        return consult_text;
    }

    public void setConsult_text(String consult_text) {
        this.consult_text = consult_text;
    }
}
