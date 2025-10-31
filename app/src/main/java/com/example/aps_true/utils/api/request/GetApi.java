package com.example.aps_true.utils.api.request;

import com.example.aps_true.utils.api.response.AssemblyResponse;
import com.example.aps_true.utils.api.response.AuthResponse;
import com.example.aps_true.utils.api.response.CustomerResponse;
import com.example.aps_true.utils.api.response.HouguanResponse;
import com.example.aps_true.utils.api.response.LoginResponse;
import com.example.aps_true.utils.api.response.ManufactureResponse;
import com.example.aps_true.utils.api.response.OrderResponse;
import com.example.aps_true.utils.api.response.QianguanResponse;
import com.example.aps_true.utils.api.response.SaleResponse;
import com.example.aps_true.utils.api.response.ThislevelResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//API 接口
//定義Retrofit 取得資料的方式（API介面）
//定義每個 API 呼叫的路徑、方法(GET、POST等)與回傳資料型別
//Retrofit 會依照這個接口自動生成網路請求

public interface GetApi {
    @POST("auth/login")
    Observable<Response<LoginResponse>> getToken(
            @Query("account") String account,
            @Query("password") String password
    );

    // 登入人員資訊
    @GET("auth/")
    Observable<AuthResponse> getLoginData(
            @Query("token") String token
    );

    // 查詢客戶名稱
    @GET("app-search-customer")
    Observable<List<CustomerResponse>> getCustomer(
            @Query("customer_name") String customer_name,
            @Query("token") String token
    );

    // 查詢訂單單號
    @GET("app-search-so")
    Observable<List<OrderResponse>> getOrder(
            @Query("so_id") String so_id,
            @Query("token") String token
    );

    // 查詢製令單號
    @GET("app-search-mo")
    Observable<List<ManufactureResponse>> getMo(
            @Query("mo_id") String mo_id,
            @Query("token") String token
    );

    // 前關
    @GET("get-prev-manufacture")
    Observable<List<QianguanResponse>> getQianguan(
            @Query("customer") String customer,
            @Query("sale_order") String sale_order,
            @Query("token") String token
    );

    // 本階
    @GET("get-current-stage-com")
    Observable<List<ThislevelResponse>> getThislevel(
            @Query("item_id") String item_id,
            @Query("token") String token
    );

    // 後關*
    @GET("get-next-part")
    Observable<List<HouguanResponse>> getHouguan(
            @Query("sale_order") String sale_order,
            @Query("id") String id,
            @Query("token") String token
    );

    // 裝配*
    @GET("get-so-data")
    Observable<List<AssemblyResponse>> getAssembly(
            @Query("sale_order") String sale_order,
            @Query("item") String item,
            @Query("token") String token
    );

    // 訂單單號
    @GET("get-sale-order")
    Observable<List<SaleResponse>> getSale(
            @Query("sale_order") String so_id,
            @Query("customer") String customer,
            @Query("online_date") String online_date,
            @Query("token") String token
    );
}