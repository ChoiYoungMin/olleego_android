package com.edn.olleego.activity.diary.chartformatter;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 2016-06-22.
 */
public class SleepYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public SleepYAxisValueFormatter() {
        mFormat = new DecimalFormat("########"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information





        return mFormat.format(value)+" 시간" ; // e.g. append a dollar-sign
    }
}