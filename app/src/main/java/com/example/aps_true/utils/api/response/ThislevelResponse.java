package com.example.aps_true.utils.api.response;

import java.util.List;

public class ThislevelResponse {
    public class Records{
        public Integer id;
        public Integer top_id;
        public Integer down_id;
        public Integer row_no;
        public Integer row_id;
        public Integer unit_id;
        public String unit_qty;
        public Integer nuse_qty;
        public Integer base_qty;
        public String remark;
        public String item_id;
        public Integer level;
        public List<parent> parent;
    }

    public class parent{
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String techroutekey_id;
        public String fetch_type;
    }

}
