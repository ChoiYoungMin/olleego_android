package com.edn.olleego.model.video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 5..
 */
public class VideoDetailsModel {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        private int _id;
        private String updated;
        private String created;
        private String title;
        private String movie_url;
        private int tt_sort;
        private boolean bp_etc;
        private boolean bs_etc;
        private String description;
        private String method;
        private String warning;
        private String thum_gif;
        private String thum_jpg;
        private int __v;
        private LgSort lg_sort;
        private MdSort md_sort;
        private DtSort dt_sort;
        private BdSort bd_sort;
        private LvAttr lv_attr;
        private StdAttr std_attr;
        private List<SmSort> sm_sort = new ArrayList<SmSort>();
        private List<Object> hashtags = new ArrayList<Object>();

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMovie_url() {
            return movie_url;
        }

        public void setMovie_url(String movie_url) {
            this.movie_url = movie_url;
        }

        public int getTt_sort() {
            return tt_sort;
        }

        public void setTt_sort(int tt_sort) {
            this.tt_sort = tt_sort;
        }

        public boolean isBp_etc() {
            return bp_etc;
        }

        public void setBp_etc(boolean bp_etc) {
            this.bp_etc = bp_etc;
        }

        public boolean isBs_etc() {
            return bs_etc;
        }

        public void setBs_etc(boolean bs_etc) {
            this.bs_etc = bs_etc;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getWarning() {
            return warning;
        }

        public void setWarning(String warning) {
            this.warning = warning;
        }

        public String getThum_gif() {
            return thum_gif;
        }

        public void setThum_gif(String thum_gif) {
            this.thum_gif = thum_gif;
        }

        public String getThum_jpg() {
            return thum_jpg;
        }

        public void setThum_jpg(String thum_jpg) {
            this.thum_jpg = thum_jpg;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        public List<SmSort> getSm_sort() {
            return sm_sort;
        }

        public void setSm_sort(List<SmSort> sm_sort) {
            this.sm_sort = sm_sort;
        }

        public List<Object> getHashtags() {
            return hashtags;
        }

        public void setHashtags(List<Object> hashtags) {
            this.hashtags = hashtags;
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
