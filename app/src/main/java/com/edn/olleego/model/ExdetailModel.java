package com.edn.olleego.model;


import com.edn.olleego.model.exdetail.Result;

/**
 * Created by Antonio on 2016-07-21.
 */
public class ExdetailModel {
    boolean success;

    Result result;

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
}
