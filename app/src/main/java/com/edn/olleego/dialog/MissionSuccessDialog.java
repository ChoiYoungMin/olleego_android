package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.edn.olleego.R;
import com.edn.olleego.activity.mission.exmission.ExMissionActivity;

import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-07-25.
 */
public class MissionSuccessDialog extends Dialog {
    public MissionSuccessDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_missionsuccess);






    }
}
