package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Antonio on 2016-08-05.
 */
public class Report_HealthyDialog extends Dialog {

    @BindView(R.id.dialog_report_dis)
    RelativeLayout dialog_report_dis;


    public Report_HealthyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_report_healthy);
        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @OnClick(R.id.dialog_report_dis)
    void dialog_report_dis() {
        dismiss();
    }
}
