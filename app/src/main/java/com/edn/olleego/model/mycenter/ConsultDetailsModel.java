package com.edn.olleego.model.mycenter;

import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-21.
 */
public class ConsultDetailsModel {
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
        private int purchase;
        private int user;
        private String pt_expire;
        private int pt_total;
        private int pt_remain;
        private int __v;
        private String pt_status;
        private int consult_count;

        private Center center;
        private List<PtReserve> pt_reserve = new ArrayList<>();
        private PtTrainer pt_trainer;

        public int getConsult_count() {
            return consult_count;
        }

        public void setConsult_count(int consult_count) {
            this.consult_count = consult_count;
        }

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

        public int getPurchase() {
            return purchase;
        }

        public void setPurchase(int purchase) {
            this.purchase = purchase;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
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

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getPt_status() {
            return pt_status;
        }

        public void setPt_status(String pt_status) {
            this.pt_status = pt_status;
        }

        public Center getCenter() {
            return center;
        }

        public void setCenter(Center center) {
            this.center = center;
        }

        public List<PtReserve> getPt_reserve() {
            return pt_reserve;
        }

        public void setPt_reserve(List<PtReserve> pt_reserve) {
            this.pt_reserve = pt_reserve;
        }

        public PtTrainer getPt_trainer() {
            return pt_trainer;
        }

        public void setPt_trainer(PtTrainer pt_trainer) {
            this.pt_trainer = pt_trainer;
        }

        public class Center {
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

            public class CenterBinfo {
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public class PtReserve implements ParentListItem {
            private List<ConsultChildModel> childItemList;

            public void setChildItemList(List<ConsultChildModel> childItemList) {
                this.childItemList = childItemList;
            }

            @Override
            public List<?> getChildItemList() {
                return childItemList;
            }

            @Override
            public boolean isInitiallyExpanded() {
                return false;
            }

            private String reserve_start;
            private int index;
            private String _id;
            private String updated;
            private String created;
            private boolean consult_confirm;
            private String consult_text;

            private InBody in_body;


            public String getReserve_start() {
                return reserve_start;
            }

            public void setReserve_start(String reserve_start) {
                this.reserve_start = reserve_start;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
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

            public boolean isConsult_confirm() {
                return consult_confirm;
            }

            public void setConsult_confirm(boolean consult_confirm) {
                this.consult_confirm = consult_confirm;
            }

            public String getConsult_text() {
                return consult_text;
            }

            public void setConsult_text(String consult_text) {
                this.consult_text = consult_text;
            }

            public InBody getIn_body() {
                return in_body;
            }

            public void setIn_body(InBody in_body) {
                this.in_body = in_body;
            }

            public class InBody{
                private int _id;
                private float weight;
                private float bmi;
                private float muscle;
                private float body_fat;
                private float body_fat_per;
                private float whr;
                private float basal_metabolism;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public float getWeight() {
                    return weight;
                }

                public void setWeight(float weight) {
                    this.weight = weight;
                }

                public float getBmi() {
                    return bmi;
                }

                public void setBmi(float bmi) {
                    this.bmi = bmi;
                }

                public float getMuscle() {
                    return muscle;
                }

                public void setMuscle(float muscle) {
                    this.muscle = muscle;
                }

                public float getBody_fat() {
                    return body_fat;
                }

                public void setBody_fat(float body_fat) {
                    this.body_fat = body_fat;
                }

                public float getBody_fat_per() {
                    return body_fat_per;
                }

                public void setBody_fat_per(float body_fat_per) {
                    this.body_fat_per = body_fat_per;
                }

                public float getWhr() {
                    return whr;
                }

                public void setWhr(float whr) {
                    this.whr = whr;
                }

                public float getBasal_metabolism() {
                    return basal_metabolism;
                }

                public void setBasal_metabolism(float basal_metabolism) {
                    this.basal_metabolism = basal_metabolism;
                }
            }
        }

        public class PtTrainer {
            private int _id;
            private String name;
            private BasicInfo basic_info;

            public int get_id() {
                return _id;
            }

            public void set_id(int _id) {
                this._id = _id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BasicInfo getBasic_info() {
                return basic_info;
            }

            public void setBasic_info(BasicInfo basic_info) {
                this.basic_info = basic_info;
            }

            public class BasicInfo {
                private String tel;

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
                }
            }
        }
    }
}
