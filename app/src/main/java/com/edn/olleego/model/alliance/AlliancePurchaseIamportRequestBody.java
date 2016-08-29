package com.edn.olleego.model.alliance;

/**
 * Created by pc on 2016-08-27.
 */
public class AlliancePurchaseIamportRequestBody {
    private int amount;
    private int purchases_id;
    private String merchant_uid;
    private String name;
    private String buyer_tel;
    private String buyer_name;
    private String buyer_email;
    private String pay_method;

    public int getPurchases_id() {
        return purchases_id;
    }

    public void setPurchases_id(int purchases_id) {
        this.purchases_id = purchases_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMerchant_uid() {
        return merchant_uid;
    }

    public void setMerchant_uid(String merchant_uid) {
        this.merchant_uid = merchant_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuyer_tel() {
        return buyer_tel;
    }

    public void setBuyer_tel(String buyer_tel) {
        this.buyer_tel = buyer_tel;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }
}
