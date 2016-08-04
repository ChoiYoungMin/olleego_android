package com.edn.olleego.model.report_life;

/**
 * Created by Antonio on 2016-08-04.
 */
public class Result {


    Report report;

    TotalCount total_count;


    public Report getReport() {
        return this.report;
    }
    public void setReport(Report report) {
        this.report = report;
    }
    public TotalCount getTotal_count() {
        return this.total_count;
    }
    public void setTotal_count(TotalCount total_count) {
        this.total_count = total_count;
    }

}
