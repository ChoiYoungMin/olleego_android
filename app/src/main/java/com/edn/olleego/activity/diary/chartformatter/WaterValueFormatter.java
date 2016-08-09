package com.edn.olleego.activity.diary.chartformatter;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 2016-08-09.
 */
public class WaterValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;
    private Context context;

    int toggle;
    float totalVal;

    public WaterValueFormatter(Context context){
        toggle = 0;
        totalVal = 0;
        this.context = context;
        mFormat = new DecimalFormat("###########");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return mFormat.format(value) +" ml" ;

    }
}
