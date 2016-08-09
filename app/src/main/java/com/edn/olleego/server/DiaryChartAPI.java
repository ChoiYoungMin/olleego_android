package com.edn.olleego.server;

import com.edn.olleego.model.DiaryChartModel;
import com.edn.olleego.model.DiaryMonthModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-08-09.
 */
public interface DiaryChartAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/diaries/activity/{type}")
    Call<DiaryChartModel> listRepos(@Header("Authorization") String authorization, @Path("type") String type);

}
