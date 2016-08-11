package com.edn.olleego.adapter.mission.missionlist;

import com.edn.olleego.model.usermission.Exgroup2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2016-08-09.
 */
public class MissionListData {

    String mission_list_day;
    String mission_list_title_1;
    String mission_list_title_2;
    String mission_list_title_3;

    int life_list;
    int food_list;
    Exgroup2 ex_list;

    public MissionListData(String mission_list_day, String mission_list_title_1, String mission_list_title_2, String mission_list_title_3, int life_list, int food_list, Exgroup2 ex_list) {
        this.mission_list_day = mission_list_day;
        this.mission_list_title_1 = mission_list_title_1;
        this.mission_list_title_2 = mission_list_title_2;
        this.mission_list_title_3 = mission_list_title_3;

        this.life_list = life_list;
        this.food_list = food_list;
        this.ex_list= ex_list;
    }

}
