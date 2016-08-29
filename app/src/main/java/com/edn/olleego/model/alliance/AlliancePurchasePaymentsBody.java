package com.edn.olleego.model.alliance;

/**
 * Created by pc on 2016-08-27.
 */
public class AlliancePurchasePaymentsBody {
    /*  {
          {
      "merchant_uid":"olleego_34_1470818660693", //iamport 에서 넘어오는 결과값
      "imp_uid":"imp_940695909545", //iamport 에서 넘어오는 결과값
      "purchases_id":34 //구매 id 값
  }
      }*/
    private int purchases_id;
    private String merchant_uid;
    private String imp_uid;

    public int getPurchases_id() {
        return purchases_id;
    }

    public void setPurchases_id(int purchases_id) {
        this.purchases_id = purchases_id;
    }

    public String getMerchant_uid() {
        return merchant_uid;
    }

    public void setMerchant_uid(String merchant_uid) {
        this.merchant_uid = merchant_uid;
    }

    public String getImp_uid() {
        return imp_uid;
    }

    public void setImp_uid(String imp_uid) {
        this.imp_uid = imp_uid;
    }
}
