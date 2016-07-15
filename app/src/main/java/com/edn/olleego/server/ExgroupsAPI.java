package com.edn.olleego.server;

import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-15.
 */
public interface ExgroupsAPI {

    @GET("/api/v2/exgroups/{id}")
    Call<ExgroupsModel> listRepos(@Path("id") int user);
}
