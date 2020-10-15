package com.saifi.shoppurchase.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saifi.shoppurchase.retrofitmodel.LoginModel;
import com.saifi.shoppurchase.retrofitmodel.ShopModel;
import com.saifi.shoppurchase.retrofitmodel.StatusModel;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseStatusModel;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseWareHouseModel;
import com.saifi.shoppurchase.retrofitmodel.managerReceived.ReceivedStatusModel;
import com.saifi.shoppurchase.retrofitmodel.managerReceived.SubmitToStockModel;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockFinalSubmitModel;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockMatch;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockStstusModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("purchase_login_new")
    Call<LoginModel> hitLogin(@Field("key") String key, @Field("mobile") String mobile, @Field("password") String password,
                              @Field("role") String role, @Field("user_role") String userRole);

    @FormUrlEncoded
    @POST("check_active_user")
    Call<StatusModel> hitStatusApi(@Field("key") String key, @Field("user_id") String id);

    @FormUrlEncoded
    @POST("manager_purchase_list")
    Call<PurchaseStatusModel> hitPurchaseListApi(@Field("key")String key,@Field("user_id")String userId, @Field("business_location_id")String buisnessId);

    @FormUrlEncoded
    @POST("manager_stock_list")
    Call<StockStstusModel> hitStockListApi(@Field("key")String key, @Field("user_id")String userId, @Field("business_location_id")String buisnessId);

    @FormUrlEncoded
    @POST("purchase_booking_new")
    Call<ShopModel> hitFinalShop(@Field("key") String key, @Field("order_no") String order, @Field("product_category") String product,
                                 @Field("storage") String gb, @Field("warrenty") String warranty,
                                 @Field("warrenty_month") String warrantyMonth, @Field("imei_no") String imei,
                                 @Field("purchase_amount") String amount, @Field("customer_name") String name,
                                 @Field("customer_mobile") String mobile, @Field("customer_aadhar") String aadhar,
                                 @Field("remark") String remark, @Field("app_price") String app_Price,
                                 @Field("brand_id") String brand, @Field("series_name") String series,
                                 @Field("model_id") String model, @Field("userid") String userId,
                                 @Field("condition") String condition, @Field("exchange") String exchange,
                                 @Field("purchase_cat_name") String cat_name, @Field("business_location_id") String buisnessID,
                                 @Field("barcode_scan") String barcode,@Field("cash") String cash,
                                 @Field("bank") String bank);

 @FormUrlEncoded
    @POST("purchase_booking_new")
    Call<ShopModel> hitFinalShopWithExchange(@Field("key") String key, @Field("order_no") String order, @Field("product_category") String product,
                                 @Field("storage") String gb, @Field("warrenty") String warranty,
                                 @Field("warrenty_month") String warrantyMonth, @Field("imei_no") String imei,
                                 @Field("purchase_amount") String amount, @Field("customer_name") String name,
                                 @Field("customer_mobile") String mobile, @Field("customer_aadhar") String aadhar,
                                 @Field("remark") String remark, @Field("app_price") String app_Price,
                                 @Field("brand_id") String brand, @Field("series_name") String series,
                                 @Field("model_id") String model, @Field("userid") String userId,
                                 @Field("condition") String condition, @Field("exchange") String exchange,
                                 @Field("purchase_cat_name") String cat_name, @Field("business_location_id") String buisnessID,
                                 @Field("barcode_scan") String barcode,@Field("cash") String cash,
                                 @Field("bank") String bank,@Field("exchange_price") String exchange_price);


    @Multipart
    @POST("shop_purchase_uplode_image")
    Call<JsonObject> imageAPi(@Part MultipartBody.Part[] imageArray1, @PartMap() Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST("manager_subto_warehouse")
    Call<PurchaseWareHouseModel> hitWareHouseApi(@Field("key") String key,@Field("phone_id")String phoneId,@Field("code") String code);

    @FormUrlEncoded
    @POST("manager_recive_today")
    Call<ReceivedStatusModel> hitReceivedListApi(@Field("key")String key, @Field("user_id")String userId, @Field("business_location_id")String buisnessId);

    @FormUrlEncoded
    @POST("warehouse_subto_manager")
    Call<SubmitToStockModel> hitWareHousetoManagerApi(@Field("key") String key, @Field("phone_id")String phoneId, @Field("code") String code);

    @FormUrlEncoded
    @POST("manager_stock_list")
    Call<StockStstusModel> hitAllStockApi(@Field("key") String key, @Field("page") String page, @Field("business_location_id") String business_location_id);

    @FormUrlEncoded
    @POST("manager_send_stock_details")
    Call<StockFinalSubmitModel> hitFinalStockApi(@Field("key") String key, @Field("user_id") String user_id, @Field("business_location_id") String business_location_id);

    @FormUrlEncoded
    @POST("manager_stock_list")
    Call<StockStstusModel> hitAllStockSearchApi(@Field("key") String key, @Field("page") String page,
                                                @Field("business_location_id") String business_location_id,@Field("search") String search);


    @FormUrlEncoded
    @POST("manager_match_stock")
    Call<StockMatch> hitMatchStock(@Field("key") String key, @Field("phone_id") String phoneId);
}
