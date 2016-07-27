package com.edn.olleego.model.user;

import java.util.Date;
import java.util.List;

public class Result {

    int _id;
    String name;
    String email;
    Date birthday;
    String gender;
    String avatar;

    int point;

    int user_level;

    List<InBody> in_body;

    public int get_id() {
        return this._id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return this.birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean getActive_yn() {
        return this.active_yn;
    }
    public void setActive_yn(boolean active_yn) {
        this.active_yn = active_yn;
    }
    boolean active_yn;

    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getPoint() {
        return this.point;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    public int getUser_level() {
        return this.user_level;
    }
    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }



    public List<InBody> getIn_body() {
        return this.in_body;
    }
    public void setIn_body(List<InBody> in_body) {
        this.in_body = in_body;
    }
}
