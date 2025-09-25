package com.example.aps_true.utils.api.response;

import java.util.Date;
import java.util.List;

//資料類別（Data Model）
//用來對應API回傳的JSON結構，並提供方便取用資料的方法
//Retrofit 用 Gson 會自動將 JSON 轉成這個類別的物件。

public class SaleResponse {
    public Records records; //Api資料，records後面是{}，也就是class類別，所以要建立一個records的class
    public class Records{ //records底下的location，他後面是先[]才是{}，意思是location裡面的資料是用list[]包起來，再更裡面的資料是class{}類別
        public Integer id;
        public String date; // 上線日期
        public String type;
        public String so_id;
        public String item_id;
        public String item_name;
        public String qty;
        public String customer;  // 客戶名稱
        public List<sale_order> sale_order;//這裡代表在List裡面放入Location的物件，後面表示這個變數名稱為location，而Location是class，所以就是用List去包class，就可以完成上述的順序
        public List<parent_parts> parent_parts;
    }

    public class sale_order{
        public Integer id;
        public String so_id; // 訂單單號(SO)
        public String item;
        public String customer_name;
        public Integer qty;
        public Date container_date;
        public Date bill_date;
        public String org_id;
        public String current_state;
        public String customer_order;
        public String person_id;
        public String material_spec;
        public String sunit_id;
        public Integer untrans_qty;
        public String cu_remark;
    }

    public class parent_parts{
        public Integer id;
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String techroutekey_id;
        public Integer fetch_type;
        public List<downstream_child> downstream_child;
    }

    public class downstream_child{
        public Integer id;
        public Integer row_no;
        public Integer row_id;
        public String unit_id;
        public String unit_qty;
        public String nuse_qty;
        public String base_qty;
        public String remark;
        public String item_id;
        public List<parent> parent;
    }

    public class parent{
        public String id;
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String fetch_type;
    }

}
