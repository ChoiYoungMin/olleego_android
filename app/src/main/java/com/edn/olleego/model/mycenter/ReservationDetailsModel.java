package com.edn.olleego.model.mycenter;

import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-21.
 */
public class ReservationDetailsModel {
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
        private int reserve_count;
        private Center center;
        private List<PtReserve> pt_reserve = new ArrayList<>();
        private PtTrainer pt_trainer;

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

        public class PtReserve implements Parcelable {
            private int index;
            private String reserve_start;
            private String reserve_end;
            private String _id;
            private String updated;
            private String created;
            private String reserve_status;

            protected PtReserve(Parcel in) {
                index = in.readInt();
                reserve_start = in.readString();
                reserve_end = in.readString();
                _id = in.readString();
                updated = in.readString();
                created = in.readString();
                reserve_status = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(index);
                dest.writeString(reserve_start);
                dest.writeString(reserve_end);
                dest.writeString(_id);
                dest.writeString(updated);
                dest.writeString(created);
                dest.writeString(reserve_status);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public final Creator<PtReserve> CREATOR = new Creator<PtReserve>() {
                @Override
                public PtReserve createFromParcel(Parcel in) {
                    return new PtReserve(in);
                }

                @Override
                public PtReserve[] newArray(int size) {
                    return new PtReserve[size];
                }
            };

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

            public String getReserve_end() {
                return reserve_end;
            }

            public void setReserve_end(String reserve_end) {
                this.reserve_end = reserve_end;
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

            public String getReserve_status() {
                return reserve_status;
            }

            public void setReserve_status(String reserve_status) {
                this.reserve_status = reserve_status;
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
