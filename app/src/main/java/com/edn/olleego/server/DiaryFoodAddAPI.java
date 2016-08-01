package com.edn.olleego.server;

import com.edn.olleego.model.MissionsModel;
import com.edn.olleego.server.request.DiaryAdd;
import com.edn.olleego.server.request.DiaryFoodAdd;
import com.edn.olleego.server.request.Foods;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Antonio on 2016-08-01.
 */
public interface DiaryFoodAddAPI {
    @Multipart
    @POST("/api/v2/diaries")
    Call<MissionsModel> listRepos(@Header("Authorization") String authorization, @Query("type") String type, @Part("DiaryFoodAdd") DiaryFoodAdd diaryFoodAdd,  @Part MultipartBody.Part file);

}



