package com.edn.olleego.activity.diary.chartformatter;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 2016-08-09.
 */
public class WalkingYAxisValueFormatter implements YAxisValueFormatter {
    private DecimalFormat mFormat;

    public WalkingYAxisValueFormatter() {
        mFormat = new DecimalFormat("########"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information





        return mFormat.format(value) ; // e.g. append a dollar-sign
    }
}
