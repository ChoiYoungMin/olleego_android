package com.edn.olleego.server;

import android.text.Editable;

import com.edn.olleego.model.CalendarModel;
import com.edn.olleego.model.ForgotModel;
import com.edn.olleego.model.LoginModel;
import com.edn.olleego.server.request.Calender;
import com.edn.olleego.server.request.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Antonio on 2016-06-20.
 */
public interface CalenderAPI {

    @FormUrlEncoded
    @POST("/olleego/wGetDiaryCalendarData")
    Call<List<CalendarModel>> listRepos(@Field("email") String email, @Field("date") String date);


}
