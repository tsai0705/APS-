package com.example.aps_true.utils.api.response;

public class QianguanResponse {
    public SaleResponse.Records records; //Api資料，records後面是{}，也就是class類別，所以要建立一個records的class
    public class Records{
        public Integer id;
        public String mo_id;
        public String item_id;
        public String item_name;
        public String qty;
        public String techroutekey_id;
        public String online_date;
        public String complete_date;
        public String so_id;
        public String customer;  // 客戶名稱
    }

}
