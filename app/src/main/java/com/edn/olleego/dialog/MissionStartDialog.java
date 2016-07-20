package com.edn.olleego.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.edn.olleego.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Antonio on 2016-07-20.
 */
public class MissionStartDialog extends Dialog {


    public MissionStartDialog(Context context) {
        super(context);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_missionstart);
        ButterKnife.bind(this);

        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);


    }

    @OnClick(R.id.missionstart_mission_no)
    void mission_no() {
        Toast.makeText(getContext(), "취소잼!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @OnClick(R.id.missionstart_mission_start)
    void mission_stert() {
        Toast.makeText(getContext(), "클릭했어요~ ", Toast.LENGTH_SHORT).show();
        dismiss();
    }



}
