package com.edn.olleego.server;

import com.edn.olleego.model.Report_FoodModel;
import com.edn.olleego.model.Report_WeightModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Antonio on 2016-08-05.
 */
public interface Report_WeightAPI {

    @GET("/api/v2/users/health/weight")
    Call<Report_WeightModel> listRepos(@Header("Authorization") String authorization);
}
