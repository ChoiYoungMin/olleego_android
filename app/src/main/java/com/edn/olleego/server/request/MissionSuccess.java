package com.edn.olleego.server.request;

/**
 * Created by Antonio on 2016-07-25.
 */
public class MissionSuccess {

    public int day;
    public int exgroup;
    public int life;
    public int food;

    public MissionSuccess(int day, int result, int type) {

        if(type == 1) {
            this.day = day;
            this.exgroup =result;
        }
        else if(type==2) {
            this.day = day;
            this.life = result;

        }
        else if(type ==3) {
            this.day = day;
            this.food= result;

        }
    }
}
