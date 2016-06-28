package com.edn.olleego.server;

import com.edn.olleego.server.request.Login;
import com.edn.olleego.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-06-15.
 */
public interface LoginAPI {

    @Headers("Content-Type: application/json")
    @POST("/api/v2/auth/signin")
    Call<LoginModel> listRepos(@Body Login login);

}

