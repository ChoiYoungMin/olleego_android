package com.edn.olleego.model.diarymonth;

import java.util.Date;

/**
 * Created by Antonio on 2016-07-26.
 */
public class Food {


    Satiety satiety;

    String memo;

    Food food;

    Sort sort;

    String _id;

    Date updatedAt;
    Date createdAt;



    public Satiety getSatiety() {
        return this.satiety;
    }
    public void setSatiety(Satiety satiety) {
        this.satiety = satiety;
    }
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public Food getFood() {
        return this.food;
    }
    public void setFood(Food food) {
        this.food = food;
    }
    public Sort getSort() {
        return this.sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
