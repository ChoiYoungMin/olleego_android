package com.edn.olleego.model.report;

/**
 * Created by Antonio on 2016-08-03.
 */
public class MsStatus {

    boolean waist;

    boolean bp;

    boolean bs;

    int check;

    String data_e;
    String code;


    public boolean getWaist() {
        return this.waist;
    }
    public void setWaist(boolean waist) {
        this.waist = waist;
    }
    public boolean getBp() {
        return this.bp;
    }
    public void setBp(boolean bp) {
        this.bp = bp;
    }
    public boolean getBs() {
        return this.bs;
    }
    public void setBs(boolean bs) {
        this.bs = bs;
    }
    public int getCheck() {
        return this.check;
    }
    public void setCheck(int check) {
        this.check = check;
    }
    public String getData_e() {
        return this.data_e;
    }
    public void setData_e(String data_e) {
        this.data_e = data_e;
    }

    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
