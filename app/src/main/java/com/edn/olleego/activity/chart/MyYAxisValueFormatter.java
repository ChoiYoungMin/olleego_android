package com.edn.olleego.activity.chart;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 2016-06-22.
 */
public class MyYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter () {
        mFormat = new DecimalFormat("###,###,##"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        // write your logic here
        // access the YAxis object to get more information

        if(value < 10) {
            value = 0f;
        } else if (value < 20) {
            value = 10f;
        } else if (value < 30) {
            value = 20f;
        } else if (value < 40) {
            value = 30f;
        } else if (value < 50) {
            value = 40f;
        } else if (value < 60) {
            value = 50f;
        }


        return mFormat.format(value) + " 세트"; // e.g. append a dollar-sign
    }
}