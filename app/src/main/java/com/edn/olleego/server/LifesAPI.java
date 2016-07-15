package com.edn.olleego.server;

import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.LifesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-15.
 */
public interface LifesAPI {

    @GET("/api/v2/lifes/{id}")
    Call<LifesModel> listRepos(@Path("id") int user);
}
