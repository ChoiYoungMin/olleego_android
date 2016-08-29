package com.edn.olleego.model.alliance;

/**
 * Created by kym on 2016. 8. 26..
 */
public class AlliancePTticketModel {
    private int position;
    private int id;
    private int sale_rate;
    private String title;
    private int expiry;
    private int money;
    private int sale_money;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSale_rate() {
        return sale_rate;
    }

    public void setSale_rate(int sale_rate) {
        this.sale_rate = sale_rate;
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

    public int getSale_money() {
        return sale_money;
    }

    public void setSale_money(int sale_money) {
        this.sale_money = sale_money;
    }
}
