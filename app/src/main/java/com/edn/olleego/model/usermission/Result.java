package com.edn.olleego.model.usermission;

import com.kakao.usermgmt.response.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class Result {

    int _id;
    Date updated;

    Date created;

    int user;

    Mission mission;

    Date startAt;

    Date endAt;

    List<MiDay2> mi_days;

    boolean total_complete;

    int current_day;

    boolean active_yn;

    int time;




    public int get_time() {
        return this.time;

    }

    public void setTime(int time) {
        this.time = time;
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
    public Mission getMission() {
        return this.mission;
    }
    public void setMission(Mission mission) {
        this.mission = mission;
    }
    public Date getStartAt() {
        return this.startAt;
    }
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }
    public Date getEndAt() {
        return this.endAt;
    }
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
    public int get__v() {
        return this.__v;
    }
    public void set__v(int __v) {
        this.__v = __v;
    }
    int __v;

    public List<MiDay2> getMi_days() {
        return this.mi_days;
    }
    public void setMi_days(List<MiDay2> mi_days) {
        this.mi_days = mi_days;
    }
    public boolean getTotal_complete() {
        return this.total_complete;
    }
    public void setTotal_complete(boolean total_complete) {
        this.total_complete = total_complete;
    }
    public int getCurrent_day() {
        return this.current_day;
    }
    public void setCurrent_day(int current_day) {
        this.current_day = current_day;
    }
    public boolean getActive_yn() {
        return this.active_yn;
    }
    public void setActive_yn(boolean active_yn) {
        this.active_yn = active_yn;
    }

}
