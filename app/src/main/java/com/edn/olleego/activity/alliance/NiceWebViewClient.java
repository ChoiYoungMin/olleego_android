package com.edn.olleego.activity.alliance;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.util.EncodingUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pc on 2016-08-28.
 * <p/>
 * 제휴센터 상세 > 하단 구매하기 > 구매 페이지 상세 > 결제하기 > 나이스페이 WebView
 */
public class NiceWebViewClient extends WebViewClient {

    private Activity activity;
    private AlertDialog alertIsp, alertKFTC;
    private WebView target;
    private String BANK_TID = "";

    final int RESCODE = 1;
    final String ISP_LINK = "market://details?id=kvp.jjy.MispAndroid320";                // ISP 설치 링크
    final String NICE_URL = "https://web.nicepay.co.kr/smart/interfaceURL.jsp";            // NICEPAY SMART 요청 URL
    final String NICE_BANK_URL = "https://web.nicepay.co.kr/smart/bank/payTrans.jsp";    // 계좌이체 거래 요청 URL
    final String KFTC_LINK = "market://details?id=com.kftc.bankpay.android";            //	금융결제원 설치 링크

    private boolean isCompletedPayment; //결제 실패시 webview 종료 시점 구분 flag
    /**
     * boolean isCompletedPayment
     * 결제 실패시.. 나이스페이 화면의 확인 버튼 이후 보이는 iamport측 화면의 확인 버튼을 눌러 webview 종료
     * (실패시 shouldOverrideUrlLoading 메소드에 떨어지는 url 값이 같아 구분 값이 필요.)
     * 결제 성공시.. 나이스페이 확인 버튼 클릭시 url 파라미터값이 존재하지만,
     * iamport측 화면 확인 버튼 클릭시 url parameter에 null 값이 떨어지므로 종료시점 구분 가능
     */


    public NiceWebViewClient(Activity activity, WebView target) {
        this.activity = activity;
        this.target = target;

        createISPPopup();
        createKFTCPopup();
    }

    public void bankPayPostProcess(String bankpayCode, String bankpayValue) {
        String postData = "callbackparam2=" + BANK_TID + "&bankpay_code=" + bankpayCode + "&bankpay_value=" + bankpayValue;
        target.postUrl(NICE_BANK_URL, EncodingUtils.getBytes(postData, "euc-kr"));
    }

    private void createISPPopup() {
        this.alertIsp = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("알림")
                .setMessage("모바일 ISP어플리케이션이 설치되어있지 않습니다. \n설치를 눌러 진행해주십시요.\n취소를 누르면 결제가 취소됩니다.")
                .setPositiveButton("설치", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ISP
                        NiceWebViewClient.this.target.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp");
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NiceWebViewClient.this.activity, "(-1)결제가 취소되었습니다..", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }

    private void createKFTCPopup() {
        this.alertKFTC = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("알림")
                .setMessage("계좌이체 결제를 하기 위해서는 BANKPAY 앱이 필요합니다.\n설치 페이지로  진행하시겠습니까?")
                .setPositiveButton("설치", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // KFTC
                        //NiceWebViewClient.this.target.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(KFTC_LINK));
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NiceWebViewClient.this.activity, "(-1)결제가 취소되었습니다..", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("shouldOverride", "url : " + url);

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent;

            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException ex) {
                return false;
            }

            if (url.startsWith("kftc-bankpay")) {
                if (isPackageInstalled("com.kftc.bankpay.android")) {
                    try {
                        String reqParam = makeBankPayData(url);

                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.kftc.bankpay.android", "com.kftc.bankpay.android.activity.MainActivity"));
                        intent.putExtra("requestInfo", reqParam);
                        activity.startActivityForResult(intent, RESCODE);

                        return true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    alertKFTC.show();
                    return true;
                }
            }


            Uri uri = Uri.parse(intent.getDataString());
            intent = new Intent(Intent.ACTION_VIEW, uri);

            try {
                activity.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                if (url.startsWith("ispmobile://")) {
                    alertIsp.show();
                    return true;
                } else if (url.startsWith("kftc-bankpay")) {
                    alertKFTC.show();
                    return true;
                } else if (url.startsWith("intent://")) {//intent 형태의 스키마 처리
                    try {
                        Intent excepIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        String packageNm = excepIntent.getPackage();

                        excepIntent = new Intent(Intent.ACTION_VIEW);
                        excepIntent.setData(Uri.parse("market://search?q=pname:" + packageNm));
                        activity.startActivity(excepIntent);

                        return true;
                    } catch (URISyntaxException e1) {
                    }
                } else if (url != null && (url.contains("vguard")
                        || url.contains("droidxantivirus")
                        || url.contains("lottesmartpay")
                        || url.contains("smshinhancardusim://")
                        || url.contains("shinhan-sr-ansimclick")
                        || url.contains("v3mobile")
                        || url.endsWith(".apk")
                        || url.contains("smartwall://")
                        || url.contains("appfree://")
                        || url.contains("market://")
                        || url.contains("ansimclick://")
                        || url.contains("ansimclickscard")
                        || url.contains("ansim://")
                        || url.contains("mpocket")
                        || url.contains("mvaccine")
                        || url.contains("market.android.com")
                        || url.contains("http://m.ahnlab.com/kr/site/download"))) {

                    try {
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        } catch (URISyntaxException ex) {
                            Log.i("NICE", "Bad URI " + url + ":" + ex.getMessage());
                            return false;
                        }

                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        activity.startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException ee) {
                        Log.i("NICE", "Activity Exception " + e.getMessage());
                        e.printStackTrace();
                        return false;
                    } catch (Exception ee) {
                        Log.i("NICE", "Exception " + e.getMessage());
                        return false;
                    }
                }
            }
        } else {
            Uri uri = Uri.parse(url);
            Log.d("webClient", "getHost : " + uri.getHost());
            Log.d("webClient", "getQuery : " + uri.getQuery());
            Log.d("webClient", "isSuccess : " + uri.getQueryParameter("success"));
            Log.d("webClient", "imp_uid : " + uri.getQueryParameter("imp_uid"));

            //https://service.iamport.kr/payments/fail?success=false
            //https://service.iamport.kr/payments/success?success=true&imp_uid=imp_907066032486

            if (uri != null) {
                if (uri.getHost().equals("service.iamport.kr")) {
                    if (uri.getQuery() != null) {
                        boolean flag = false;
                        Intent intent = new Intent();
                        if (uri.getQueryParameter("success").equals("true")) {
                            flag = true;
                            intent.putExtra("imp_uid", uri.getQueryParameter("imp_uid"));
                        }
                        intent.putExtra("success", flag);
                        activity.setResult(Activity.RESULT_OK, intent);
                        if (isCompletedPayment)
                            activity.finish();
                        isCompletedPayment = true;
                    } else {
                        //iamport 성공화면에서 확인버튼 클릭시 query 값이 null
                        activity.finish();
                    }
                }
            }
        }

        return false;
    }

    private String makeBankPayData(String url) throws URISyntaxException {
        BANK_TID = "";
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");

        StringBuilder ret_data = new StringBuilder();
        List<String> keys = Arrays.asList(new String[]{"firm_name", "amount", "serial_no", "approve_no", "receipt_yn", "user_key", "callbackparam2", ""});

        String k, v;
        for (NameValuePair param : params) {
            k = param.getName();
            v = param.getValue();

            if (keys.contains(k)) {
                if ("user_key".equals(k)) {
                    BANK_TID = v;
                }
                ret_data.append("&").append(k).append("=").append(v);
            }
        }

        ret_data.append("&callbackparam1=" + "nothing");
        ret_data.append("&callbackparam3=" + "nothing");

        return ret_data.toString();
    }

    private boolean isPackageInstalled(String pkgName) {
        try {
            activity.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
