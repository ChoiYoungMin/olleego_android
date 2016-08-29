package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 15..
 */
public class AllianceLocationModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private String _id;
        private String title;
        private List<Double> coordinates = new ArrayList<>();

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }
}
