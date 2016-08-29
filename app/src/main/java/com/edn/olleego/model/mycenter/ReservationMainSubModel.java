package com.edn.olleego.model.mycenter;

/**
 * Created by pc on 2016-08-22.
 */
public class ReservationMainSubModel {
    private int index;
    private int reserveId;
    private String content;
    private boolean flag; //예약, 상담 구분자

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public boolean isFlag() {
        return flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
