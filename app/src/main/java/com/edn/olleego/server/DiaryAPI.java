package com.edn.olleego.server;

import com.edn.olleego.model.DiaryModel;
import com.edn.olleego.model.ExgroupsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Antonio on 2016-07-15.
 */
public interface DiaryAPI {


    @Headers("Content-Type: application/json")
    @GET("/api/v2/diaries/day/{day}")
    Call<DiaryModel> listRepos(@Path("day") String user , @Header("Authorization") String authorization);
}
