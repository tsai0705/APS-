package com.example.aps_true.utils.api.response;

import java.util.List;

public class AssemblyResponse {
    public class Records{
        public Integer id;
        public String mo_id;
        public String item_id;
        public String item_name;
        public Integer qty;
        public String techroutekey_id;
        public String online_date;
        public String complete_date;
        public String so_id;
        public String customer;
        public List<related_parent_part> related_parent_part;
    }

    public class related_parent_part{
        public Integer id;
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String techroutekey_id;
        public String fetch_type;
        public List<downstream_child> downstream_child;
    }

    public class downstream_child{
        public Integer id;
        public String material_id;
        public String unit_id;
        public String unit_qty;
        public String nuse_qty;
        public String base_qty;
        public List<parent> parent;
    }

    public class parent{
        public Integer id;
        public String material_id;
        public String bomkey_name;
        public String unit_id;
        public String techroutekey_id;
        public String fetch_type;
    }
}
