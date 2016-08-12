package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-08-11.
 */
public class LoadingBarDialog extends Dialog {

    @BindView(R.id.loadingbar_title)
    TextView loadingbar_title;

    String type;

    public LoadingBarDialog(Context context) {
        super(context);
        type = "";
    }

    public LoadingBarDialog(Context context, String type) {
        super(context);
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loadingbar);
        ButterKnife.bind(this);


        if(type.equals("food_add")) {
            loadingbar_title.setText("데이터를 입력중입니다.");
        }
    }
}
