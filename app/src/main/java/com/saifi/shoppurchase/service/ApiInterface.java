package com.saifi.shoppurchase.service;

import com.saifi.shoppurchase.retrofitmodel.LoginModel;
import com.saifi.shoppurchase.retrofitmodel.ShopModel;
import com.saifi.shoppurchase.retrofitmodel.StatusModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("purchase_login_new")
    Call<LoginModel> hitLogin(@Field("key") String key, @Field("mobile") String mobile, @Field("password") String password,
                              @Field("role") String role);

    @FormUrlEncoded
    @POST("check_active_user")
    Call<StatusModel> hitStatusApi(@Field("key") String key, @Field("user_id") String id);

    @FormUrlEncoded
    @POST("purchase_booking_new")
    Call<ShopModel> hitFinalShop(@Field("key") String key, @Field("order_no") String order, @Field("product_category") String product,
                                 @Field("storage") String gb, @Field("warrenty") String warranty,
                                 @Field("warrenty_month") String warrantyMonth, @Field("imei_no") String imei,
                                 @Field("purchase_amount") String amount, @Field("customer_name") String name,
                                 @Field("customer_mobile") String mobile, @Field("customer_aadhar") String aadhar,
                                 @Field("remark") String remark, @Field("app_price") String app_Price,
                                 @Field("brand_name") String brand, @Field("series_name") String series,
                                 @Field("model_name") String model, @Field("userid") String userId,
                                 @Field("condition") String condition, @Field("exchange") String exchange);
}
