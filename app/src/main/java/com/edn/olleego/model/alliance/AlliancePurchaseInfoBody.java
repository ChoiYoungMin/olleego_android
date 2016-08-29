package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-27.
 */
public class AlliancePurchaseInfoBody {
    private int center;
    private int pt;
    private boolean inbody_public;
    private Hope hope = new Hope();

    public Hope getHope() {
        return hope;
    }

    public void setHope(Hope hope) {
        this.hope = hope;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public boolean isInbody_public() {
        return inbody_public;
    }

    public void setInbody_public(boolean inbody_public) {
        this.inbody_public = inbody_public;
    }

    public class Hope{
        private int trainer;
        private String start;
        private String end;
        private List<String> day = new ArrayList<>();

        public int getTrainer() {
            return trainer;
        }

        public void setTrainer(int trainer) {
            this.trainer = trainer;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public List<String> getDay() {
            return day;
        }

        public void setDay(List<String> day) {
            this.day = day;
        }
    }
}
