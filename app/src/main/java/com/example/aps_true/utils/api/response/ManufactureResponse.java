package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

// mo_id
public class ManufactureResponse {

    @SerializedName("mo_id")
    private String moId;

    // Getter
    public String getMoId() {
        return moId;
    }

    // Setter
    public void setMoId(String moId) {
        this.moId = moId;
    }
}