package com.edn.olleego.model;

import com.edn.olleego.model.foods.Result;

/**
 * Created by Antonio on 2016-07-15.
 */
public class FoodsModel {

    boolean success;

    Result result;

    boolean err;



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
    public boolean getErr() {
        return this.err;
    }
    public void setErr(boolean err) {
        this.err = err;
    }
}
