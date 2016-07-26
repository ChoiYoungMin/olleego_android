package com.edn.olleego.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.mission.exmission.ExMissionActivity;

import butterknife.ButterKnife;

/**
 * Created by Antonio on 2016-07-25.
 */
public class MissionSuccessDialog extends Dialog {

    String titles;
    int type;
    public MissionSuccessDialog(Context context, String title, int type) {
        super(context);
        this.titles = title;
        this.type = type;
    }


    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_missionsuccess);


        title = (TextView) findViewById(R.id.dialog_mission_success_title);


        if(type == 1){
            title.setText("["+titles+ "]을\n 완료하였습니다.");
        } else if(type == 2) {
            title.setText("식습관 미션션을\n 완료하습니다.");
        } else if(type == 3) {
            title.setText("생활습관 미션을\n 완료하였습니다.");
        }








    }
}
