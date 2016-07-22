package com.edn.olleego.server.request;

import java.util.Date;

/**
 * Created by Antonio on 2016-07-21.
 */
public class ChoiceMission {

    int user,mission, time;
    Date startAt, endAt, created;


    public ChoiceMission(int user, int mission, int time, Date startAt, Date endAt, Date created) {
        this.user = user;
        this.mission = mission;
        this.time = time;
        this.startAt = startAt;
        this.endAt = endAt;
        this.created = created;
    }

}
