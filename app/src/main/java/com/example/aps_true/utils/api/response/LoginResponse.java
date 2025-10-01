package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

// 登入 API 回應
public class LoginResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("token")
    private String token;

    // Constructor
    public LoginResponse() {
    }

    // Getters
    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    // Setters (選用)
    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 判斷是否登入成功
     * @return status == 0 表示成功
     */
    public boolean isSuccess() {
        return status == 0;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                ", token='" + token + '\'' +
                '}';
    }
}