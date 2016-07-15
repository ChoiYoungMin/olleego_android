package com.edn.olleego.server;

import com.edn.olleego.model.SignupModel;
import com.edn.olleego.model.user.UserModel;
import com.edn.olleego.server.request.Signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-07-14.
 */
public interface UserAPI {

    @GET("/api/v2/users/me")
    Call<UserModel> listRepos(@Header("Authorization") String authorization);
}

/*
   .header("User-Agent", "Your-App-Name")
 */