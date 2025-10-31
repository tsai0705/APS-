package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("batch")
    private Integer batch;

    @SerializedName("related_tech_route")
    private RelatedTechRoute relatedTechRoute;

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
    public Integer getBatch() { return batch; }
    public RelatedTechRoute getRelatedTechRoute() { return relatedTechRoute; }

    // 巢狀類別（對應 related_tech_route）
    public static class RelatedTechRoute {
        @SerializedName("id")
        private Integer id;

        @SerializedName("tech_routing_id")
        private String techRoutingId;

        @SerializedName("tech_routing_name")
        private String techRoutingName;

        public Integer getId() { return id; }
        public String getTechRoutingId() { return techRoutingId; }
        public String getTechRoutingName() { return techRoutingName; }
    }
}
