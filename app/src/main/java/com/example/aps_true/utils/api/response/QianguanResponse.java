package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * 修正後的 POJO。
 * 欄位已從 'Records' 內部類別移至頂層。
 */
public class QianguanResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("mo_id")
    private String moId;

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("qty")
    private Integer qty;

    @SerializedName("techroutekey_id")
    private String techroutekeyId;

    @SerializedName("online_date")
    private String onlineDate;

    @SerializedName("complete_date")
    private String completeDate;

    @SerializedName("so_id")
    private String soId;

    @SerializedName("customer")
    private String customer;


    public Integer getId() { return id; }
    public String getMoId() { return moId; }
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public Integer getQty() { return qty; }
    public String getTechroutekeyId() { return techroutekeyId; }
    public String getOnlineDate() { return onlineDate; }
    public String getCompleteDate() { return completeDate; }
    public String getSoId() { return soId; }
    public String getCustomer() { return customer; }
}