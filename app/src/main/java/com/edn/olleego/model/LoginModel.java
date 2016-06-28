package com.edn.olleego.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Antonio on 2016-06-15.
 */
public class LoginModel {
    // 로그인 성공시 나오는 결과값
    Boolean success;
    String token;

    // 로그인 실패시 나오는 추가 결과값 +
    Boolean email;
    Boolean pw;
    String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    ///////////////////////////////////////////////

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getPw() {
        return pw;
    }

    public void setPw(Boolean pw) {
        this.pw = pw;
    }

}
