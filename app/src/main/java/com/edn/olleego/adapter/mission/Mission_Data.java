package com.edn.olleego.adapter.mission;

/**
 * Created by Antonio on 2016-06-28.
 */
public class Mission_Data {

    public String mImg;
    public String mission_type;
    public String mission_title;
    public int rating;
    public int rating_peple;
    public String mission_level;
    public String mission_time;

    public Mission_Data(String mImg, String mission_type, String mission_title, int rating, int rating_peple, String mission_level, String mission_time){
        this.mImg = mImg;
        this.mission_type = mission_type;
        this.mission_title = mission_title;
        this.rating = rating;
        this.rating_peple = rating_peple;
        this.mission_level = mission_level;
        this.mission_time = mission_time;
    }
}
