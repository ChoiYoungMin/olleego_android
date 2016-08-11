package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.edn.olleego.R;

/**
 * Created by Antonio on 2016-08-11.
 */
public class LoadingBarDialog extends Dialog {
    public LoadingBarDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loadingbar);
    }
}
