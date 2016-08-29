package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 7..
 */
public class AllianceCategoryModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {
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
