package com.edn.olleego.server;

import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.model.UserMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by Antonio on 2016-07-19.
 */
public interface AllMissionAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/missions")
    Call<AllMissionModel> listRepos(@Header("Authorization") String authorization);
}
