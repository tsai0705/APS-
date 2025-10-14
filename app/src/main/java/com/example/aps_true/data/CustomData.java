package com.example.aps_true.data;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomData {
    private static CustomData instance; // 單例實例變數，用來保存唯一的 LoginData 物件
    private String so_id;
    private String mo_id;
    private String customer_name;
    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    private CustomData() {}

    // 提供一個公共的靜態方法，用於獲取單例
    public static CustomData getInstance() {
        if (instance == null) {  //檢查 instance 是否已經被建立
            //synchronized 鎖住關鍵字
            synchronized (CustomData.class) { // 在多執行緒（多線程）環境下，確保同一時間只有一個執行緒能夠進入這個區塊，從而保證只會產生一個唯一的 LoginData 實例（instance）
                if (instance == null) {
                    instance = new CustomData(); //如果還沒建立（instance == null）則建立一個新的。
                }
            }
        }
        return instance;
    }



    // getter 和 setter 方法

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getMo_id() {
        return mo_id;
    }

    public void setMo_id(String mo_id) {
        this.mo_id = mo_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
