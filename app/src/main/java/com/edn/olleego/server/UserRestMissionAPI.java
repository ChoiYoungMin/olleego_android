package com.edn.olleego.server;

import com.edn.olleego.model.UserMissionModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-08-11.
 */
public interface UserRestMissionAPI {

    @Headers("Content-Type: application/json")
    @PUT("/api/v2/histories/mission/{historyid}/day/{day}")
    Call<UserMissionModel> listRepos(@Header("Authorization") String authorization, @Path("historyid") int historyid, @Path("day") int day);
}
