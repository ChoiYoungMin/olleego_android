package com.edn.olleego.server;

import com.edn.olleego.model.ReportModel;
import com.edn.olleego.model.Report_LifeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-08-04.
 */
public interface Report_LifeAPI {


    @GET("/api/v2/diaries/report/{type}")
    Call<Report_LifeModel> listRepos(@Header("Authorization") String authorization, @Path("type") String type);

}
