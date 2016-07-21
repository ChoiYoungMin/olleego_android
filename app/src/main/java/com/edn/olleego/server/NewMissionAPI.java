package com.edn.olleego.server;

import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.model.SignupModel;
import com.edn.olleego.model.UserMissionModel;
import com.edn.olleego.server.request.NewMission;
import com.edn.olleego.server.request.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-07-21.
 */
public interface NewMissionAPI {

    @Headers("Content-Type: application/json")
    @POST("/api/v2/histories/mission")
    Call<MissionsModel> listRepos(@Header("Authorization") String authorization, @Body NewMission newMission);

}
