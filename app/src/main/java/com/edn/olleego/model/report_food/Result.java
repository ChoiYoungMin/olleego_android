package com.edn.olleego.model.report_food;

import java.util.List;

/**
 * Created by Antonio on 2016-08-05.
 */
public class Result {

    List<Report> report;

    TotalCount total_count;


    public List<Report> getReport() {
        return this.report;
    }
    public void setReport(List<Report> report) {
        this.report = report;
    }
    public TotalCount getTotal_count() {
        return this.total_count;
    }
    public void setTotal_count(TotalCount total_count) {
        this.total_count = total_count;
    }
}
