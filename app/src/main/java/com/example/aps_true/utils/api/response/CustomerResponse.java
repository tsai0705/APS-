package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

public class CustomerResponse {
    @SerializedName("customer_name")
    public String customer_name;

    public CustomerResponse() {
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
