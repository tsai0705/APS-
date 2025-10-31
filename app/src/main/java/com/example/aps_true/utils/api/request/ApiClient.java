package com.example.aps_true.utils.api.request;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit 建立器
//Retrofit 的設定類別，主要負責建立 Retrofit 實例來呼叫氣象資料 API

public class ApiClient {

    // 建立 Retrofit 實例，設定 baseUrl 和 JSON 轉換器並用於呼叫氣象 API
    public Retrofit ApsApi() {
        // 配置 HttpLoggingInterceptor 用於除錯
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 配置 OkHttpClient，增加超時設置和重試機制
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // 連接超時
                .readTimeout(30, TimeUnit.SECONDS)     // 讀取超時
                .writeTimeout(30, TimeUnit.SECONDS)    // 寫入超時
                .retryOnConnectionFailure(true)        // 連接失敗時自動重試
                .addInterceptor(loggingInterceptor)    // 添加日誌攔截器
                .build();

        return new Retrofit.Builder()
                // 設定 API 的根網址 (base URL)，所有的接口請求會以此為基準
                //.baseUrl("http://10.0.2.2:9000/api/")
                .baseUrl("http://10.0.0.11:9000/api/")
                // 設定 OkHttpClient
                .client(okHttpClient)
                // 設定 JSON 解析器，將回傳的 JSON 自動轉成 Java 物件
                //如果沒加這行，API 回傳 JSON 時，Retrofit 會不知道怎麼解析它，導致出現錯誤或拿到空資料。
                //註冊資料解析方式（JSON ⇄ Java）(使用 Gson 來處理 JSON)
                .addConverterFactory(GsonConverterFactory.create())
                // 配合 RxJava3 進行非同步呼叫，提供 Observable 等功能
                //讓 Retrofit 的 API 方法能回傳 RxJava3 的 Observable、Flowable 等可觀察物件
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                // 建立 Retrofit 實例
                .build();
    }
}

