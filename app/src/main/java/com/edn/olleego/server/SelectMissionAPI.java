package com.edn.olleego.server;

import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.model.SelectMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-21.
 */
public interface SelectMissionAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/missions/{id}")
    Call<SelectMissionModel> listRepos(@Path("id") String su);
}
