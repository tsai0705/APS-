package com.example.aps_true.utils.api.response;

import java.util.List;

public class HouguanResponse {
    public class Records{
        public Integer id;
        public Integer unit_id;
        public Integer unit_qty;
        public Integer nuse_qty;
        public Integer base_qty;
        public List<related_top_parent> related_top_parent;
    }

    public class related_top_parent{
        public String id;
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String techroutekey_id;
        public String fetch_type;
        public List<manufactures>  manufactures;
    }

    public class manufactures{
        public Integer id;
        public String mo_id;
        public String item_id;
        public String item_name;
        public String qty;
        public String techroutekey_id;
        public String online_date;
        public String complete_date;
        public String so_id;
        public String customer;
    }
}
