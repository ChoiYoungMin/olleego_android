package com.edn.olleego.model.mycenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016-08-22.
 */
public class RecommendedFitnessModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private int sale_money;
        private int _id;
        private int money;
        private String title;
        private Center center;

        public int getSale_money() {
            return sale_money;
        }

        public void setSale_money(int sale_money) {
            this.sale_money = sale_money;
        }

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
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
                private String locate;

                public String getLocate() {
                    return locate;
                }

                public void setLocate(String locate) {
                    this.locate = locate;
                }
            }
        }
    }

}
