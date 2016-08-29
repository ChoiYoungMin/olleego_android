package com.edn.olleego.model.alliance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kym on 2016. 8. 9..
 */
public class AlliancePTListModel {
    private List<Result> result = new ArrayList<>();

    public List<Result> getResult() {
        return result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result{
        private int _id;
        private String title;
        private int expiry;
        private int money;
        private int count;
        private int sale_money;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSale_money() {
            return sale_money;
        }

        public void setSale_money(int sale_money) {
            this.sale_money = sale_money;
        }
    }
}
