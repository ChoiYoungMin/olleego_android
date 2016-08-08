package com.edn.olleego.server;

import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.model.ReportInputModel;
import com.edn.olleego.server.request.MissionSuccess;
import com.edn.olleego.server.request.ReportInput;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-08-08.
 */
public interface ReportInputAPI {

    @Headers("Content-Type: application/json")
    @POST("/api/v2/users/{id}/body")
    Call<ReportInputModel> listRepos(@Header("Authorization") String authorization, @Path("id") String id, @Body ReportInput reportInput);


}
