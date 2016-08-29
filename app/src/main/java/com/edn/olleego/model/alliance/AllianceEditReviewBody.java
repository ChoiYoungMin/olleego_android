package com.edn.olleego.model.alliance;

/**
 * Created by kym on 2016. 8. 18..
 */
public class AllianceEditReviewBody {
    private int point;
    private String body;
    private int trainer;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTrainer() {
        return trainer;
    }

    public void setTrainer(int trainer) {
        this.trainer = trainer;
    }
}
