package com.edn.olleego.fragment.report;

import android.content.Context;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Antonio on 2016-06-22.
 */
public class MyBarValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;
    private Context context;

    int toggle;
    float totalVal;

    public MyBarValueFormatter(Context context){
        toggle = 0;
        totalVal = 0;
        this.context = context;
        mFormat = new DecimalFormat("###,###,###,##");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            return mFormat.format(value)+ " kg" ;

    }
}