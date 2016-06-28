package com.edn.olleego.server.request;

/**
 * Created by Antonio on 2016-06-16.
 */
public class Login {

    public String email;
    public String password;

    public Login(String email, String password) {
        this.email= email;
        this.password = password;
    }
}
