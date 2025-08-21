package com.example.aps_true.data;

import java.util.ArrayList;
import java.util.HashMap;

public class SaleData {
    private static SaleData instance; // 單例實例變數，用來保存唯一的 LoginData 物件
    private String serial;
    private String material;
    private String specifications;
    private String unitdosage;
    private String requiredamount;
    private String unit;
    private String storage;
    private String description;
    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    private SaleData() {}

    // 提供一個公共的靜態方法，用於獲取單例
    public static SaleData getInstance() {
        if (instance == null) {  //檢查 instance 是否已經被建立
            //synchronized 鎖住關鍵字
            synchronized (SaleData.class) { // 在多執行緒（多線程）環境下，確保同一時間只有一個執行緒能夠進入這個區塊，從而保證只會產生一個唯一的 LoginData 實例（instance）
                if (instance == null) {
                    instance = new SaleData(); //如果還沒建立（instance == null）則建立一個新的。
                }
            }
        }
        return instance;
    }



    // getter 和 setter 方法
    public String Serial() { return serial; }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUnitdosage() {
        return unitdosage;
    }

    public void setUnitdosage(String unitdosage) { this.unitdosage = unitdosage; }

    public String getRequiredamount() { return requiredamount; }

    public void setRequiredamount(String requiredamount) {
        this.requiredamount = requiredamount;
    }

    public String getUnit() { return unit; }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStorage() { return storage; }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<HashMap<String, String>> getData() {
        return data; //取得目前 LoginData 實例裡 data 這個成員變數的內容
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data; //設定目前 LoginData 實例裡 data 這個成員變數的內容
    }


}

