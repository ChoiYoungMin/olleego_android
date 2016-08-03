package com.edn.olleego.server;

import com.edn.olleego.model.ReportModel;
import com.edn.olleego.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Antonio on 2016-08-03.
 */
public interface ReportAPI {

    @GET("/api/v2/users/health/report")
    Call<ReportModel> listRepos(@Header("Authorization") String authorization);
}
