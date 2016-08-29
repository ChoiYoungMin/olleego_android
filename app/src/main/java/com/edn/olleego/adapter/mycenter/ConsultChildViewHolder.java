package com.edn.olleego.adapter.mycenter;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.edn.olleego.R;
import com.edn.olleego.model.mycenter.ConsultChildModel;

/**
 * Created by pc on 2016-08-22.
 */
public class ConsultChildViewHolder extends ChildViewHolder {
    private TextView weight;
    private TextView muscle;
    private TextView body_fat;
    private TextView bmi;
    private TextView body_fat_per;
    private TextView whr;
    private TextView basal_metabolism;
    private TextView consult_text;

    public ConsultChildViewHolder(View itemView) {
        super(itemView);
        weight = (TextView) itemView.findViewById(R.id.weight);
        muscle = (TextView) itemView.findViewById(R.id.muscle);
        body_fat = (TextView) itemView.findViewById(R.id.body_fat);
        bmi = (TextView) itemView.findViewById(R.id.bmi);
        body_fat_per = (TextView) itemView.findViewById(R.id.body_fat_per);
        whr = (TextView) itemView.findViewById(R.id.whr);
        basal_metabolism = (TextView) itemView.findViewById(R.id.basal_metabolism);
        consult_text = (TextView) itemView.findViewById(R.id.consult_text);
    }

    public void bind(ConsultChildModel result) {
        weight.setText(result.getWeight() + "kg");
        muscle.setText(result.getMuscle() + "kg");
        body_fat.setText(result.getBody_fat() + "kg");
        bmi.setText(result.getBmi() + "");
        body_fat_per.setText(result.getBody_fat_per() + "%");
        whr.setText(result.getWhr() + "%");
        basal_metabolism.setText(result.getBasal_metabolism() + "kcal");
        consult_text.setText(result.getConsult_text());
    }
}
