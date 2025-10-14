package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("so_id")
    private String soId;

    // Getter
    public String getSoId() {
        return soId;
    }

    // Setter
    public void setSoId(String soId) {
        this.soId = soId;
    }
}