package com.edn.olleego.common;

/**
 * Created by kym on 2016. 8. 18..
 */
public class OlleegoEvent {
    //리뷰 작성 및 수정 시 fragment refresh를 위해 상위 activity에 알림.
    public final static class WriteReviewEvent {
        private boolean success;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
