package com.edn.olleego.model.mycenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-21.
 */
public class ReservationModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private int _id;
        private int pt_trainer;
        private String pt_expire;
        private int pt_total;
        private int pt_remain;
        private String pt_status;
        private int consult_count;
        private int reserve_count;
        private Center center;
        private List<PtConsult> pt_consult = new ArrayList<>();
        private List<PtReserve> pt_reserve = new ArrayList<>();

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public int getPt_trainer() {
            return pt_trainer;
        }

        public void setPt_trainer(int pt_trainer) {
            this.pt_trainer = pt_trainer;
        }

        public String getPt_expire() {
            return pt_expire;
        }

        public void setPt_expire(String pt_expire) {
            this.pt_expire = pt_expire;
        }

        public int getPt_total() {
            return pt_total;
        }

        public void setPt_total(int pt_total) {
            this.pt_total = pt_total;
        }

        public int getPt_remain() {
            return pt_remain;
        }

        public void setPt_remain(int pt_remain) {
            this.pt_remain = pt_remain;
        }

        public String getPt_status() {
            return pt_status;
        }

        public void setPt_status(String pt_status) {
            this.pt_status = pt_status;
        }

        public int getConsult_count() {
            return consult_count;
        }

        public void setConsult_count(int consult_count) {
            this.consult_count = consult_count;
        }

        public int getReserve_count() {
            return reserve_count;
        }

        public void setReserve_count(int reserve_count) {
            this.reserve_count = reserve_count;
        }

        public Center getCenter() {
            return center;
        }

        public void setCenter(Center center) {
            this.center = center;
        }

        public List<PtConsult> getPt_consult() {
            return pt_consult;
        }

        public void setPt_consult(List<PtConsult> pt_consult) {
            this.pt_consult = pt_consult;
        }

        public List<PtReserve> getPt_reserve() {
            return pt_reserve;
        }

        public void setPt_reserve(List<PtReserve> pt_reserve) {
            this.pt_reserve = pt_reserve;
        }

        public class Center{
            private int _id;
            private CenterBinfo center_binfo;

            public int get_id() {
                return _id;
            }

            public void set_id(int _id) {
                this._id = _id;
            }

            public CenterBinfo getCenter_binfo() {
                return center_binfo;
            }

            public void setCenter_binfo(CenterBinfo center_binfo) {
                this.center_binfo = center_binfo;
            }

            public class CenterBinfo{
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public class PtConsult{
            private int index;
            private String consult_text;
            private boolean consult_confirm;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getConsult_text() {
                return consult_text;
            }

            public void setConsult_text(String consult_text) {
                this.consult_text = consult_text;
            }

            public boolean isConsult_confirm() {
                return consult_confirm;
            }

            public void setConsult_confirm(boolean consult_confirm) {
                this.consult_confirm = consult_confirm;
            }
        }

        public class PtReserve{
            private int index;
            private String reserve_start;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getReserve_start() {
                return reserve_start;
            }

            public void setReserve_start(String reserve_start) {
                this.reserve_start = reserve_start;
            }
        }
    }
}
