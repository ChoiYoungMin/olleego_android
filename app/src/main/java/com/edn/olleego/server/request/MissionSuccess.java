package com.edn.olleego.server.request;

/**
 * Created by Antonio on 2016-07-25.
 */
public class MissionSuccess {

    public int day;

    public int exgroup;
    public int exgroup_id;

    public int life;
    public int life_id;

    public int food;
    public int food_id;


    public MissionSuccess(int day, int result, int result2, String type) {

        if(type.equals("exgroup")) {
            this.day = day;
            this.exgroup =result;
            this.exgroup_id =result2;
        }
        else if(type.equals("life")) {
            this.day = day;
            this.life = result;
            this.life_id =result2;

        }
        else if(type.equals("food")) {
            this.day = day;
            this.food= result;
            this.food_id =result2;

        }
    }
}
