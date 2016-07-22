package com.edn.olleego.server;

import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.request.ChoiceMission;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-21.
 */
public interface ChoiceGetMissionAPI {

    @Headers("Content-Type: application/json")
    @POST("/api/v2/histories/mission/")
    Call<MissionsModel> listRepos(@Header("Authorization") String authorization, @Body ChoiceMission newMission);

}
