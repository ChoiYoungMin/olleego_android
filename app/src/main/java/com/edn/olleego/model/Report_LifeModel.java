package com.edn.olleego.model;

import com.edn.olleego.model.report_life.Result;

/**
 * Created by Antonio on 2016-08-04.
 */
public class Report_LifeModel {


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
