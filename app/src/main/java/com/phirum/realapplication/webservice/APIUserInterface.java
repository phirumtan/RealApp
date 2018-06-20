package com.phirum.realapplication.webservice;

import com.phirum.realapplication.pojo.RegisterItem;
import com.phirum.realapplication.pojo.UserItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIUserInterface {

    @GET("/api/users?")
    Call<UserItem> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/login")
    Call<Object> doLogin(@Field("email") String email, @Field("password") String pass);

    @POST("/api/login")
    Call<Object> doLogin(@Body RegisterItem item);
}