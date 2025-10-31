package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Based on GetApi (List<HouguanResponse>), this class is the item in the list.
 * Fields from the original "Records" class have been moved to this top level.
 */
public class HouguanResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("unit_id")
    private Integer unitId;

    @SerializedName("unit_qty")
    private Integer unitQty;

    @SerializedName("nuse_qty")
    private Integer nuseQty;

    @SerializedName("base_qty")
    private Integer baseQty;

    @SerializedName("related_top_parent")
    private List<RelatedTopParent> relatedTopParent;

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Integer unitQty) {
        this.unitQty = unitQty;
    }

    public Integer getNuseQty() {
        return nuseQty;
    }

    public void setNuseQty(Integer nuseQty) {
        this.nuseQty = nuseQty;
    }

    public Integer getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(Integer baseQty) {
        this.baseQty = baseQty;
    }

    public List<RelatedTopParent> getRelatedTopParent() {
        return relatedTopParent;
    }

    public void setRelatedTopParent(List<RelatedTopParent> relatedTopParent) {
        this.relatedTopParent = relatedTopParent;
    }

    /**
     * Nested class for related_top_parent
     */
    public static class RelatedTopParent {

        @SerializedName("id")
        private String id;

        @SerializedName("material_id")
        private String materialId;

        @SerializedName("bomkey_name")
        private String bomkeyName;

        @SerializedName("unit_id")
        private String unitId;

        @SerializedName("techroutekey_id")
        private String techroutekeyId;

        @SerializedName("fetch_type")
        private String fetchType;

        @SerializedName("manufactures")
        private List<Manufactures> manufactures;

        // --- Getters and Setters ---

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getBomkeyName() {
            return bomkeyName;
        }

        public void setBomkeyName(String bomkeyName) {
            this.bomkeyName = bomkeyName;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getTechroutekeyId() {
            return techroutekeyId;
        }

        public void setTechroutekeyId(String techroutekeyId) {
            this.techroutekeyId = techroutekeyId;
        }

        public String getFetchType() {
            return fetchType;
        }

        public void setFetchType(String fetchType) {
            this.fetchType = fetchType;
        }

        public List<Manufactures> getManufactures() {
            return manufactures;
        }

        public void setManufactures(List<Manufactures> manufactures) {
            this.manufactures = manufactures;
        }
    }

    /**
     * Nested class for manufactures
     */
    public static class Manufactures {

        @SerializedName("id")
        private Integer id;

        @SerializedName("mo_id")
        private String moId;

        @SerializedName("item_id")
        private String itemId;

        @SerializedName("item_name")
        private String itemName;

        @SerializedName("qty")
        private String qty;

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

        // --- GetG and Setters ---

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMoId() {
            return moId;
        }

        public void setMoId(String moId) {
            this.moId = moId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getTechroutekeyId() {
            return techroutekeyId;
        }

        public void setTechroutekeyId(String techroutekeyId) {
            this.techroutekeyId = techroutekeyId;
        }

        public String getOnlineDate() {
            return onlineDate;
        }

        public void setOnlineDate(String onlineDate) {
            this.onlineDate = onlineDate;
        }

        public String getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(String completeDate) {
            this.completeDate = completeDate;
        }

        public String getSoId() {
            return soId;
        }

        public void setSoId(String soId) {
            this.soId = soId;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }
    }
}