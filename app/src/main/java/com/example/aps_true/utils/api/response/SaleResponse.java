package com.example.aps_true.utils.api.response;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class SaleResponse {
        @SerializedName("id")
        private Integer id;

        @SerializedName("date")
        private String date; // 上線日期

        @SerializedName("so_id")
        private String soId;

        @SerializedName("item_id")
        private String itemId;

        @SerializedName("qty")
        private String qty;

        @SerializedName("customer")
        private String customer; // 客戶名稱

        @SerializedName("sale_order")
        private List<SaleOrder> saleOrder;

        @SerializedName("parent_parts")
        private List<ParentParts> parentParts;

        // Getters and Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getSoId() { return soId; }
        public void setSoId(String soId) { this.soId = soId; }

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
        public String getQty() { return qty; }
        public void setQty(String qty) { this.qty = qty; }

        public String getCustomer() { return customer; }
        public void setCustomer(String customer) { this.customer = customer; }

        public List<SaleOrder> getSaleOrder() { return saleOrder; }
        public void setSaleOrder(List<SaleOrder> saleOrder) { this.saleOrder = saleOrder; }

        public List<ParentParts> getParentParts() { return parentParts; }
        public void setParentParts(List<ParentParts> parentParts) { this.parentParts = parentParts; }

    public static class SaleOrder {
        @SerializedName("id")
        private Integer id;

        @SerializedName("so_id")
        private String soId; // 訂單單號(SO)

        @SerializedName("item")
        private String item;

        @SerializedName("customer_name")
        private String customerName;

        @SerializedName("qty")
        private Integer qty;

        @SerializedName("container_date")
        private Date containerDate;

        @SerializedName("customer_order")
        private String customerOrder;

        // Getters and Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getSoId() { return soId; }
        public void setSoId(String soId) { this.soId = soId; }

        public String getItem() { return item; }
        public void setItem(String item) { this.item = item; }

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public Integer getQty() { return qty; }
        public void setQty(Integer qty) { this.qty = qty; }

        public Date getContainerDate() { return containerDate; }
        public void setContainerDate(Date containerDate) { this.containerDate = containerDate; }
        public String getCustomerOrder() { return customerOrder; }
        public void setCustomerOrder(String customerOrder) { this.customerOrder = customerOrder; }
    }

    public static class ParentParts {
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
        private Integer fetchType;

        @SerializedName("downstream_child")
        private List<DownstreamChild> downstreamChild;

        // Getters and Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getMaterialId() { return materialId; }
        public void setMaterialId(String materialId) { this.materialId = materialId; }

        public String getBomkeyName() { return bomkeyName; }
        public void setBomkeyName(String bomkeyName) { this.bomkeyName = bomkeyName; }

        public String getUnitId() { return unitId; }
        public void setUnitId(String unitId) { this.unitId = unitId; }

        public String getTechroutekeyId() { return techroutekeyId; }
        public void setTechroutekeyId(String techroutekeyId) { this.techroutekeyId = techroutekeyId; }

        public Integer getFetchType() { return fetchType; }
        public void setFetchType(Integer fetchType) { this.fetchType = fetchType; }

        public List<DownstreamChild> getDownstreamChild() { return downstreamChild; }
        public void setDownstreamChild(List<DownstreamChild> downstreamChild) { this.downstreamChild = downstreamChild; }
    }

    public static class DownstreamChild {
        @SerializedName("id")
        private Integer id;

        @SerializedName("row_no")
        private Integer rowNo;

        @SerializedName("row_id")
        private Integer rowId;

        @SerializedName("unit_id")
        private String unitId;

        @SerializedName("unit_qty")
        private String unitQty;

        @SerializedName("nuse_qty")
        private String nuseQty;

        @SerializedName("base_qty")
        private String baseQty;

        @SerializedName("remark")
        private String remark;

        @SerializedName("item_id")
        private String itemId;

        @SerializedName("parent")
        private Parent parent;

        // Getters and Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public Integer getRowNo() { return rowNo; }
        public void setRowNo(Integer rowNo) { this.rowNo = rowNo; }

        public Integer getRowId() { return rowId; }
        public void setRowId(Integer rowId) { this.rowId = rowId; }

        public String getUnitId() { return unitId; }
        public void setUnitId(String unitId) { this.unitId = unitId; }

        public String getUnitQty() { return unitQty; }
        public void setUnitQty(String unitQty) { this.unitQty = unitQty; }

        public String getNuseQty() { return nuseQty; }
        public void setNuseQty(String nuseQty) { this.nuseQty = nuseQty; }

        public String getBaseQty() { return baseQty; }
        public void setBaseQty(String baseQty) { this.baseQty = baseQty; }

        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }

        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }

        public Parent getParent() { return parent; }
        public void setParent(Parent parent) { this.parent = parent; }
    }

    public static class Parent {
        @SerializedName("id")
        private String id;

        @SerializedName("material_id")
        private String materialId;

        @SerializedName("bomkey_name")
        private String bomkeyName;

        @SerializedName("unit_id")
        private String unitId;

        @SerializedName("fetch_type")
        private String fetchType;

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getMaterialId() { return materialId; }
        public void setMaterialId(String materialId) { this.materialId = materialId; }

        public String getBomkeyName() { return bomkeyName; }
        public void setBomkeyName(String bomkeyName) { this.bomkeyName = bomkeyName; }

        public String getUnitId() { return unitId; }
        public void setUnitId(String unitId) { this.unitId = unitId; }

        public String getFetchType() { return fetchType; }
        public void setFetchType(String fetchType) { this.fetchType = fetchType; }
    }
}