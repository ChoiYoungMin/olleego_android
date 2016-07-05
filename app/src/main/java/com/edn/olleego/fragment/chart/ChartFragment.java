package com.edn.olleego.fragment.chart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.activity.chart.*;
import com.edn.olleego.activity.chart.MyYAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    @Nullable
    @BindView(R.id.chart)
    BarChart barChart;

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, rootView);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("맨몸");
        labels.add("머신");
        labels.add("덤벨");
        labels.add("바벨");

        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(17f, 0, "세트"));
        group1.add(new BarEntry(50f, 1, "세트"));
        group1.add(new BarEntry(1f, 2, "세트"));
        group1.add(new BarEntry(28f, 3, "세트"));

        ValueFormatter custom = new MyBarValueFormatter(getContext());

        BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));

        barDataSet1.setHighlightEnabled(false);
        barDataSet1.setValueFormatter(custom);
        barDataSet1.setDrawValues(true);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet1.setBarSpacePercent(50);

        BarData data = new BarData(labels, barDataSet1);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        data.setGroupSpace(0);

        barChart.setData(data);
        barChart.animateY(2000);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setDescription("");



        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(4, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(30f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());




        barChart.getAxisRight().setDrawLabels(false);

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        // Inflate the layout for this fragment
        return rootView;
    }

}
