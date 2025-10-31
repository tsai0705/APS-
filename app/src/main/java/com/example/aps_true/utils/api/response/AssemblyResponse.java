package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Based on GetApi (List<AssemblyResponse>), this class is the item in the list.
 * Fields from the original "Records" class have been moved to this top level.
 */
public class AssemblyResponse {

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

    @SerializedName("related_parent_part")
    private List<RelatedParentPart> relatedParentPart;

    // --- Getters and Setters ---

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
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

    public List<RelatedParentPart> getRelatedParentPart() {
        return relatedParentPart;
    }

    public void setRelatedParentPart(List<RelatedParentPart> relatedParentPart) {
        this.relatedParentPart = relatedParentPart;
    }

    /**
     * Nested class for related_parent_part
     */
    public static class RelatedParentPart {

        @SerializedName("id")
        private Integer id;

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

        @SerializedName("downstream_child")
        private List<DownstreamChild> downstreamChild;

        // --- Getters and Setters ---

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public List<DownstreamChild> getDownstreamChild() {
            return downstreamChild;
        }

        public void setDownstreamChild(List<DownstreamChild> downstreamChild) {
            this.downstreamChild = downstreamChild;
        }
    }

    /**
     * Nested class for downstream_child
     */
    public static class DownstreamChild {

        @SerializedName("id")
        private Integer id;

        @SerializedName("material_id")
        private String materialId;

        @SerializedName("unit_id")
        private String unitId;

        @SerializedName("unit_qty")
        private String unitQty;

        @SerializedName("nuse_qty")
        private String nuseQty;

        @SerializedName("base_qty")
        private String baseQty;

        @SerializedName("parent")
        private List<Parent> parent;

        // --- Getters and Setters ---

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitQty() {
            return unitQty;
        }

        public void setUnitQty(String unitQty) {
            this.unitQty = unitQty;
        }

        public String getNuseQty() {
            return nuseQty;
        }

        public void setNuseQty(String nuseQty) {
            this.nuseQty = nuseQty;
        }

        public String getBaseQty() {
            return baseQty;
        }

        public void setBaseQty(String baseQty) {
            this.baseQty = baseQty;
        }

        public List<Parent> getParent() {
            return parent;
        }

        public void setParent(List<Parent> parent) {
            this.parent = parent;
        }
    }

    /**
     * Nested class for parent
     */
    public static class Parent {

        @SerializedName("id")
        private Integer id;

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

        // --- Getters and Setters ---

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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
    }
}