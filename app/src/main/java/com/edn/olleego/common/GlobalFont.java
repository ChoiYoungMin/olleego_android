package com.edn.olleego.common;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by Antonio on 2016-06-24.
 */
public class GlobalFont  extends Application {

    private static final String TAG = GlobalFont.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "KoPubBatangLight.ttf"))
                .addBold(Typekit.createFromAsset(this, "KoPubBatangBold.ttf"));

                /*

                .addNormal(Typekit.createFromAsset(this, "NotoSansCJKkr-Medium.otf"))
                .addCustom1(Typekit.createFromAsset(this, "NotoSansCJKkr-Regular.otf"))
                .addBold(Typekit.createFromAsset(this, "NotoSansCJKkr-Bold.otf"));

                 */

                //.addNormal(Typekit.createFromAsset(this, "NanumGothic.ttf"))
                //.addBold(Typekit.createFromAsset(this, "NanumGothicBold.ttf"));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(TypekitContextWrapper.wrap(base));
    }
}
