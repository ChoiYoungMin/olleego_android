package com.edn.olleego.model;

import com.edn.olleego.model.exgroups.Result;

import java.io.Serializable;

/**
 * Created by Antonio on 2016-07-15.
 */
public class ExgroupsModel {

    boolean success;

    Result result;

    Boolean err;



    public boolean getSuccess() {
        return this.success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Result getResult() {
        return this.result;
    }
    public void setResult(Result result) {
        this.result = result;
    }
    public Boolean getErr() {
        return this.err;
    }
    public void setErr(Boolean err) {
        this.err = err;
    }
}
