package com.edn.olleego.activity.alliance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.edn.olleego.R;

/**
 * Created by pc on 2016-08-27.
 *
 * 제휴센터 상세 > 하단 구매하기 > 상품 선택 > 구매 상세 페이지 > 결제하기 > 나이스페이 webview
 */
public class AllianceIamPortWebViewActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String APP_SCHEME = "iamporttest://";
    private WebView webView;
    private Context mContext;
    private NiceWebViewClient niceClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_iamport_web_view);
        mContext = this;
        webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        String html = intent.getStringExtra("html");
        niceClient = new NiceWebViewClient(this, webView);
        webView.setWebViewClient(niceClient);
        webView.setWebChromeClient(new WebViewChromeClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);

        Uri intentData = intent.getData();

        if (intentData == null) {
            webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if (url.startsWith(APP_SCHEME)) {
                String redirectURL = url.substring(APP_SCHEME.length() + 3);
                webView.loadUrl(redirectURL);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    	/* 실시간 계좌이체 인증 후 후속처리 루틴 */
        String resVal = data.getExtras().getString("bankpay_value");
        String resCode = data.getExtras().getString("bankpay_code");

        if("000".equals(resCode)){
            niceClient.bankPayPostProcess(resCode, resVal);
        }else if("091".equals(resCode)){//계좌이체 결제를 취소한 경우
            Log.e(TAG, "계좌이체 결제를 취소하였습니다.");
        }else if("060".equals(resCode)){
            Log.e(TAG, "타임아웃");
        }else if("050".equals(resCode)){
            Log.e(TAG, "전자서명 실패");
        }else if("040".equals(resCode)){
            Log.e(TAG, "OTP/보안카드 처리 실패");
        }else if("030".equals(resCode)){
            Log.e(TAG, "인증모듈 초기화 오류");
        }
    }

    class WebViewChromeClient extends WebChromeClient {
        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
