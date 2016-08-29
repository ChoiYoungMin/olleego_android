package com.edn.olleego.model.alliance;

/**
 * Created by kym on 2016. 8. 15..
 */
public class AllianceMapItemModel {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        private int _id;
        private boolean sale_flag;
        private int sale_rate;
        private int sale_money;
        private float point_avg;
        private int review_count;
        private double distance;
        private CenterBinfo center_binfo;
        private PT pt;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public boolean isSale_flag() {
            return sale_flag;
        }

        public void setSale_flag(boolean sale_flag) {
            this.sale_flag = sale_flag;
        }

        public int getSale_rate() {
            return sale_rate;
        }

        public void setSale_rate(int sale_rate) {
            this.sale_rate = sale_rate;
        }

        public int getSale_money() {
            return sale_money;
        }

        public void setSale_money(int sale_money) {
            this.sale_money = sale_money;
        }

        public float getPoint_avg() {
            return point_avg;
        }

        public void setPoint_avg(float point_avg) {
            this.point_avg = point_avg;
        }

        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;
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

        public PT getPt() {
            return pt;
        }

        public void setPt(PT pt) {
            this.pt = pt;
        }

        public class CenterBinfo{
            private String name;
            private String place;
            private String locate;
            private String image;
            private Type type;

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

            public String getLocate() {
                return locate;
            }

            public void setLocate(String locate) {
                this.locate = locate;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Type getType() {
                return type;
            }

            public void setType(Type type) {
                this.type = type;
            }

            public class Type{
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
        public class PT{
            private String title;
            private int money;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }
        }
    }
}
