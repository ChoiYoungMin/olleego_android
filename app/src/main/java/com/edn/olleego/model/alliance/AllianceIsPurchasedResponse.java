package com.edn.olleego.model.alliance;

/**
 * Created by kym on 2016. 8. 19..
 */
public class AllianceIsPurchasedResponse {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private boolean isPurchase;

        public boolean isPurchase() {
            return isPurchase;
        }

        public void setPurchase(boolean purchase) {
            isPurchase = purchase;
        }
    }
}
