package com.edn.olleego.model.mycenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-22.
 */
public class PurchaseListModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {
        private int _id;
        private int __v;
        private String updated;
        private String created;
        private String paid_at;
        private String paid_status;
        private String pt_status;
        private boolean inbody_public;
        private PT pt;
        private Payment payment;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
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

        public String getPaid_at() {
            return paid_at;
        }

        public void setPaid_at(String paid_at) {
            this.paid_at = paid_at;
        }

        public String getPaid_status() {
            return paid_status;
        }

        public void setPaid_status(String paid_status) {
            this.paid_status = paid_status;
        }

        public String getPt_status() {
            return pt_status;
        }

        public void setPt_status(String pt_status) {
            this.pt_status = pt_status;
        }

        public boolean isInbody_public() {
            return inbody_public;
        }

        public void setInbody_public(boolean inbody_public) {
            this.inbody_public = inbody_public;
        }

        public PT getPt() {
            return pt;
        }

        public void setPt(PT pt) {
            this.pt = pt;
        }

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

        public class PT {
            private int _id;
            private String title;
            private Center center;

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

            public Center getCenter() {
                return center;
            }

            public void setCenter(Center center) {
                this.center = center;
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
                    private String place;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getPlace() {
                        return place;
                    }

                    public void setPlace(String place) {
                        this.place = place;
                    }
                }
            }
        }

        public class Payment {
            private String _id;
            private IamPort iamport;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public IamPort getIamport() {
                return iamport;
            }

            public void setIamport(IamPort iamport) {
                this.iamport = iamport;
            }

            public class IamPort {
                private int amount;
                private String paid_at;

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }

                public String getPaid_at() {
                    return paid_at;
                }

                public void setPaid_at(String paid_at) {
                    this.paid_at = paid_at;
                }
            }
        }
    }
}
