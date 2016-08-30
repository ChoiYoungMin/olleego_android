package com.edn.olleego.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by Antonio on 2016-06-24.
 */
public class GlobalFont  extends Application {

    private static GlobalFont mInstance;
    private static final String TAG = GlobalFont.class.getName();
    //private final String WEB_HOST_URL = "http://1.255.51.120:5000";
    private final String WEB_HOST_URL = "http://192.168.0.7:4000";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansCJKkr-Medium.woff"))
                .addBold(Typekit.createFromAsset(this, "NotoSansCJKkr-Bold.woff"));
                /*

                //.addCustom1(Typekit.createFromAsset(this, "NotoSansCJKkr-Regular.ttf"))

                .addNormal(Typekit.createFromAsset(this, "KoPubBatangLight.ttf"))
                .addBold(Typekit.createFromAsset(this, "KoPubBatangBold.ttf"));

                 */

                //.addNormal(Typekit.createFromAsset(this, "NanumGothic.ttf"))
                //.addBold(Typekit.createFromAsset(this, "NanumGothicBold.ttf"));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(TypekitContextWrapper.wrap(base));
        MultiDex.install(this);
    }

    public static GlobalFont getInstance() {
        return mInstance;
    }
    public String getWebHostUrl() {
        return WEB_HOST_URL;
    }
}
