package com.edn.olleego.server;

import com.edn.olleego.model.NoticesModel;
import com.edn.olleego.model.Report_FoodModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Antonio on 2016-08-18.
 */
public interface NoticesAPI {
    @GET("/api/v2/notices")
    Call<NoticesModel> listRepos(@Header("Authorization") String authorization);
}
