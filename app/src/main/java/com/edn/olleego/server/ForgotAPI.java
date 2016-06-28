package com.edn.olleego.server;

import android.text.Editable;

import com.edn.olleego.model.ForgotModel;
import com.edn.olleego.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-06-16.
 */
public interface ForgotAPI {


    @FormUrlEncoded
    @POST("/auth/forgot")
    Call<ForgotModel> listRepos(@Field("email") Editable email, @Field("mobile") Boolean mobile);

}
