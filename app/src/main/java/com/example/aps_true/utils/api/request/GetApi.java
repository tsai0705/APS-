package com.example.aps_true.utils.api.request;

import com.example.aps_true.utils.api.response.SaleResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//API 接口
//定義Retrofit 取得資料的方式（API介面）
//定義每個 API 呼叫的路徑、方法(GET、POST等)與回傳資料型別
//Retrofit 會依照這個接口自動生成網路請求

public interface GetApi {
    // 透過GET請求呼叫API端點
    //接在baseUrl後面的

    // 登入
    @POST("api/auth/login")
    @FormUrlEncoded
    Observable<SaleResponse> getlogin(
            @Field("account") String account,
            @Field("password") String password
    );

    // 登出
    @POST("api/auth/logout")
    @FormUrlEncoded
    Observable<SaleResponse> getlogout(
            @Field("Token") String Token
    );

    // 前關
    @GET("get-prev-manufacture")
    Observable<SaleResponse> getQianGuan(
            @Query("so_id") String so_id,   // 訂單單號(SO)
            @Query("item_id") String item_id,   // 零件代號
            @Query("Token") String Token
    );

    // 本階
    @GET("get-current-stage-com")
    Observable<SaleResponse> getThisLevel(
            @Query("item_id") String item_id,   // 零件代號
            @Query("Token") String Token
    );

    // 後關
    @GET("get-next-part")
    Observable<SaleResponse> getHouGuan(
            @Query("so_id") String so_id,   // 訂單單號(SO)
            @Query("id") String id,   // 零件代號
            @Query("Token") String Token
    );

    // 裝配
    @GET("get-so-data")
    Observable<SaleResponse> getAssembly(
            @Query("sale_order") String sale_order,   // 訂單單號(SO)
            @Query("item") String item,   // 零件代號
            @Query("Token") String Token
    );

    @GET("get-sale-order")
    Observable<SaleResponse> getSaleOrder(
            @Query("sale_order") String sale_order,   // 訂單單號(SO)
            @Query("customer") String customer,   // 客戶名稱
            @Query("online_date") String online_date,   // 上線日期
            @Query("Token") String Token
    );
}