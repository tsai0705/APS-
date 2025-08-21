package com.example.aps_true.ui.query.show.tab.recyclerview;

public class OrderItem {
    private String serial;
    private String material;
    private String specifications;
    private String unitdosage;
    private String requiredamount;
    private String unit;
    private String storage;
    private String description;

    public OrderItem(String serial, String material,String specifications, String unitdosage, String requiredamount,
                     String unit, String storage, String description) {
        this.serial = serial;
        this.material = material;
        this.specifications = specifications;
        this.unitdosage = unitdosage;
        this.requiredamount = requiredamount;
        this.unit = unit;
        this.storage = storage;
        this.description = description;
    }

    public String getSerial() { return serial; }
    public String getMaterial() { return material; }
    public String getSpecifications() { return specifications; }
    public String getUnitdosage() { return unitdosage; }
    public String getRequiredamount() { return requiredamount; }
    public String getUnit() { return unit; }
    public String getStorage() { return storage; }
    public String getDescription() { return  description; }
}

