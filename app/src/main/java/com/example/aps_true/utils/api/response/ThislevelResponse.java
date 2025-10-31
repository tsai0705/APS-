package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * 根據 GetApi 介面 (List<ThislevelResponse>)，
 * 這個類別本身就是列表中的項目。
 * 原先在 "Records" 類別中的欄位已被移至這裡。
 */
public class ThislevelResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("top_id")
    private Integer topId;

    @SerializedName("down_id")
    private Integer downId;

    @SerializedName("row_no")
    private Integer rowNo;

    @SerializedName("row_id")
    private Integer rowId;

    @SerializedName("unit_id")
    private Integer unitId;

    @SerializedName("unit_qty")
    private String unitQty;

    @SerializedName("nuse_qty")
    private Integer nuseQty;

    @SerializedName("base_qty")
    private Integer baseQty;

    @SerializedName("remark")
    private String remark;

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("level")
    private Integer level;

    @SerializedName("parent")
    private List<Parent> parent; // 修正了類別名稱

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopId() {
        return topId;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    public Integer getDownId() {
        return downId;
    }

    public void setDownId(Integer downId) {
        this.downId = downId;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Parent> getParent() {
        return parent;
    }

    public void setParent(List<Parent> parent) {
        this.parent = parent;
    }

    /**
     * 巢狀的 Parent 類別
     * (原先的 'parent' 類別)
     */
    public static class Parent {

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