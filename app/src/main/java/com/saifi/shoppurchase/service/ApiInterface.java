package com.saifi.shoppurchase.service;

import com.saifi.shoppurchase.retrofitmodel.LoginModel;
import com.saifi.shoppurchase.retrofitmodel.StatusModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("purchase_login_new")
    Call<LoginModel> hitLogin(@Field("key")String key,@Field("mobile")String mobile,@Field("password") String password,
                              @Field("role")String role);

    @FormUrlEncoded
    @POST("check_active_user")
    Call<StatusModel> hitStatusApi(@Field("key")String key,@Field("user_id")String id);

}
