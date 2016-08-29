package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-27.
 */
public class AlliancePurchaseInfoResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private int _id;
        private int amount;
        private boolean inbody_public;
        private String merchant_uid;
        private PT pt;
        private Hope hope;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public boolean isInbody_public() {
            return inbody_public;
        }

        public void setInbody_public(boolean inbody_public) {
            this.inbody_public = inbody_public;
        }

        public String getMerchant_uid() {
            return merchant_uid;
        }

        public void setMerchant_uid(String merchant_uid) {
            this.merchant_uid = merchant_uid;
        }

        public PT getPt() {
            return pt;
        }

        public void setPt(PT pt) {
            this.pt = pt;
        }

        public Hope getHope() {
            return hope;
        }

        public void setHope(Hope hope) {
            this.hope = hope;
        }

        public class PT{
            private int _id;
            private int expiry;
            private int money;
            private String title;
            private Center center;

            public int get_id() {
                return _id;
            }

            public void set_id(int _id) {
                this._id = _id;
            }

            public int getExpiry() {
                return expiry;
            }

            public void setExpiry(int expiry) {
                this.expiry = expiry;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
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

            public class Center{
                private int _id;
                private int sale_rate;
                private CenterBinfo center_binfo;

                public int get_id() {
                    return _id;
                }

                public void set_id(int _id) {
                    this._id = _id;
                }

                public int getSale_rate() {
                    return sale_rate;
                }

                public void setSale_rate(int sale_rate) {
                    this.sale_rate = sale_rate;
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
        }

        public class Hope{
            private String start;
            private String end;
            private List<String> day = new ArrayList<>();
            private Trainer trainer;

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

            public Trainer getTrainer() {
                return trainer;
            }

            public void setTrainer(Trainer trainer) {
                this.trainer = trainer;
            }

            public class Trainer{
                private int _id;
                private String name;
                private String gender;
                private String avatar;

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

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }
        }
    }
}
