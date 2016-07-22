package com.edn.olleego.server;

import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.request.ChoiceMission;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-22.
 */
public interface ChoicePutMissionAPI {

    @Headers("Content-Type: application/json")
    @PUT("/api/v2/histories/mission/{id}")
    Call<MissionsModel> listRepos(@Header("Authorization") String authorization, @Body ChoiceMission newMission, @Path("id") int missionid);
}
