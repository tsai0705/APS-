package com.example.aps_true.ui.query.show.tab.recyclerview;

public class SaleItem {
    private String serial;
    private String product_number;
    private String customer_number;
    private String specifications;
    private String quantity;
    private String unit;
    private String notshipped;
    private String predeliverydate;
    private String note;

    public SaleItem(String serial, String product_number, String customer_number, String specifications,
                    String quantity, String unit, String notshipped, String predeliverydate, String note){
        this.serial = serial;
        this.product_number = product_number;
        this.customer_number = customer_number;
        this.specifications = specifications;
        this.quantity = quantity;
        this.unit = unit;
        this.notshipped = notshipped;
        this.predeliverydate = predeliverydate;
        this.note = note;
    }

    public String getSerial() { return serial; }
    public String getProduct_number() { return product_number; }
    public String getCustomer_number() { return customer_number; }
    public String getSpecifications() { return specifications; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public String getNotshipped() { return notshipped; }

    public String getPredeliverydate() { return predeliverydate; }

    public String getNote() { return note; }
}
