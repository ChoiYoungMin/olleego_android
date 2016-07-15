package com.edn.olleego.model;

import com.edn.olleego.model.user.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class UserModel {

     String success;

    com.edn.olleego.model.user.Result result;

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public com.edn.olleego.model.user.Result getResult() {
        return result;
    }

    public void setResult(com.edn.olleego.model.user.Result result) {
        this.result = result;
    }


}

