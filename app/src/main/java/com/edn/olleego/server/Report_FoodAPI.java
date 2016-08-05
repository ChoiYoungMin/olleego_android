package com.edn.olleego.server;

import com.edn.olleego.model.Report_FoodModel;
import com.edn.olleego.model.Report_LifeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-08-05.
 */
public interface Report_FoodAPI {
    @GET("/api/v2/diaries/report/food")
    Call<Report_FoodModel> listRepos(@Header("Authorization") String authorization);
}
