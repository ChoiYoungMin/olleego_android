package com.edn.olleego.server.request;

/**
 * Created by Antonio on 2016-06-17.
 */
public class Signup {

    String name,email,birthday,gender,password;

    String phone;

    public Signup(String name, String email, String birthday, String gender, String password) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
        this.phone = "null";
    }
}
