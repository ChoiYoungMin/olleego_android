package com.edn.olleego.model;

import com.edn.olleego.model.report_food.Result;

/**
 * Created by Antonio on 2016-08-05.
 */
public class Report_FoodModel {


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
