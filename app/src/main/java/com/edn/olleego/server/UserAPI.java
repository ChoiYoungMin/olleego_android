package com.edn.olleego.server;

import com.edn.olleego.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

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