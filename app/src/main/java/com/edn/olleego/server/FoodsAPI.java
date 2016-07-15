package com.edn.olleego.server;

import com.edn.olleego.model.ExgroupsModel;
import com.edn.olleego.model.FoodsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-15.
 */
public interface FoodsAPI {

    @GET("/api/v2/foods/{id}")
    Call<FoodsModel> listRepos(@Path("id") int id);
}
