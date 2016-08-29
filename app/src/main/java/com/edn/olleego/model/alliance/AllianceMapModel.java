package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 15..
 */
public class AllianceMapModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {
        private int _id;
        private double distance;
        private CenterBinfo center_binfo;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public CenterBinfo getCenter_binfo() {
            return center_binfo;
        }

        public void setCenter_binfo(CenterBinfo center_binfo) {
            this.center_binfo = center_binfo;
        }

        public class CenterBinfo {
            private Loc loc;
            private Type type;

            public Loc getLoc() {
                return loc;
            }

            public void setLoc(Loc loc) {
                this.loc = loc;
            }

            public Type getType() {
                return type;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public class Loc {
                private String type;
                private List<Double> coordinates = new ArrayList<>();

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<Double> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<Double> coordinates) {
                    this.coordinates = coordinates;
                }
            }

            public class Type {
                private int _id;
                private String value;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}

