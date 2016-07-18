package com.edn.olleego.model;

import com.edn.olleego.model.mission.Result;

import java.util.List;

/**
 * Created by Antonio on 2016-07-14.
 */
public class MissionModel {

    boolean success;

    List<Result> result;

    boolean err;


    public boolean getSuccess() {
        return this.success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public List<Result> getResult() {
        return this.result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }
    public boolean getErr() {
        return this.err;
    }
    public void setErr(boolean err) {
        this.err = err;
    }

}
