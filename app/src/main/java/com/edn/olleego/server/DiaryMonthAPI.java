package com.edn.olleego.server;

import com.edn.olleego.model.DiaryMonthModel;
import com.edn.olleego.model.UserMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-26.
 */
public interface DiaryMonthAPI {

    @Headers("Content-Type: application/json")
    @GET("/api/v2/diaries/month/{day}")
    Call<DiaryMonthModel> listRepos(@Header("Authorization") String authorization, @Path("day") String day);

}
