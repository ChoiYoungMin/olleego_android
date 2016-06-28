package com.edn.olleego.server;

import com.edn.olleego.model.LoginModel;
import com.edn.olleego.model.SignupModel;
import com.edn.olleego.server.request.Login;
import com.edn.olleego.server.request.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-06-17.
 */
public interface SignupAPI {

    @Headers("Content-Type: application/json")
    @POST("/api/v2/auth/signup")
    Call<SignupModel> listRepos(@Body Signup signup);
}
