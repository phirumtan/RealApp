package com.phirum.realapplication.webservice;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIUserInterface {

    /*@GET("/api/users?")
    Call<UserItem> doGetUserList(@Query("page") int page);*/

    @FormUrlEncoded
    @POST("/api/login")
    Call<Object> doLogin(@Field("email") String email, @Field("password") String pass);
}