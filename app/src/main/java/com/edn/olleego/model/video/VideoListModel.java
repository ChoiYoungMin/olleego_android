package com.edn.olleego.model.video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 3..
 */
public class VideoListModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {
        private int _id;
        private String title;
        private String thum_jpg;
        private LgSort lg_sort;
        private MdSort md_sort;
        private DtSort dt_sort;
        private BdSort bd_sort;
        private LvAttr lv_attr;
        private StdAttr std_attr;
        private List<SmSort> sm_sort = new ArrayList<>();

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThum_jpg() {
            return thum_jpg;
        }

        public void setThum_jpg(String thum_jpg) {
            this.thum_jpg = thum_jpg;
        }

        public LgSort getLg_sort() {
            return lg_sort;
        }

        public void setLg_sort(LgSort lg_sort) {
            this.lg_sort = lg_sort;
        }

        public MdSort getMd_sort() {
            return md_sort;
        }

        public void setMd_sort(MdSort md_sort) {
            this.md_sort = md_sort;
        }

        public DtSort getDt_sort() {
            return dt_sort;
        }

        public void setDt_sort(DtSort dt_sort) {
            this.dt_sort = dt_sort;
        }

        public BdSort getBd_sort() {
            return bd_sort;
        }

        public void setBd_sort(BdSort bd_sort) {
            this.bd_sort = bd_sort;
        }

        public LvAttr getLv_attr() {
            return lv_attr;
        }

        public void setLv_attr(LvAttr lv_attr) {
            this.lv_attr = lv_attr;
        }

        public StdAttr getStd_attr() {
            return std_attr;
        }

        public void setStd_attr(StdAttr std_attr) {
            this.std_attr = std_attr;
        }

        public List<SmSort> getSmSort() {
            return sm_sort;
        }

        public void setSmSort(List<SmSort> sm_sort) {
            this.sm_sort = sm_sort;
        }
    }

    public class LgSort{
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
    public class MdSort{
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
    public class DtSort{
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
    public class BdSort{
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
    public class LvAttr{
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
    public class StdAttr{
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
    public class SmSort{
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
