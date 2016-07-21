package com.edn.olleego.server;

import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.model.UserMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-19.
 */
public interface MissionAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/missions/{id}")
    Call<AllMissionModel> listRepos(@Header("Authorization") String authorization, @Path("id") String su);
}
