package com.edn.olleego.server;

import com.edn.olleego.model.ExdetailModel;
import com.edn.olleego.model.SelectMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-21.
 */
public interface ExdetailAPI {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/exdetails/{id}")
    Call<ExdetailModel> listRepos(@Path("id") int su);
}
