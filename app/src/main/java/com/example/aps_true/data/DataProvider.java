package com.example.aps_true.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataProvider {
    private TabData tabData = TabData.getInstance(); // 呼叫類別的靜態方法產生單例
    private QueryData queryData = QueryData.getInstance(); // 呼叫類別的靜態方法產生單例
    private SaleData saleData = SaleData.getInstance(); // 呼叫類別的靜態方法產生單例

    public static void initializeAllData() {
        setTabData();
        setQueryData();
        setSaleData();
    }

    public static void setTabData() {
        TabData tabData = TabData.getInstance();

        HashMap<String, String> qianguan = new HashMap<>();
        qianguan.put("number", "1MO1812040071");
        qianguan.put("number2", "1SO1811270009");
        qianguan.put("number3", "M1-ATN260011-1");
        qianguan.put("number4", "ATN260011 垃圾筒系統櫃門片0.8*613.7*236.3mm-沖床組(6折)");
        qianguan.put("sum", "生產數量：3");
        qianguan.put("time", "預計上線：2018-12-05");
        qianguan.put("time_start", "計劃開始：15:30");
        qianguan.put("time_end", "計劃結束：15:45");
        qianguan.put("group", "一群-沖床");
        qianguan.put("status", "結案");
        tabData.getData().add(qianguan);

        HashMap<String, String> thislevel = new HashMap<>();
        thislevel.put("number", "1MO1812040031");
        thislevel.put("number2", "1SO1811270009");
        thislevel.put("number3", "F260011ATN-2");
        thislevel.put("number4", "ATN260011  系統櫃(垃圾筒) -抽屜+垃圾筒固定片*4pcs");
        thislevel.put("sum", "生產數量：3");
        thislevel.put("time", "預計上線：2018-12-06");
        thislevel.put("time_start", "計劃開始：08:00");
        thislevel.put("time_end", "計劃結束：08:05");
        thislevel.put("group", "一群-點焊");
        thislevel.put("status", "生效");
        tabData.getData().add(thislevel);

        HashMap<String, String> houguan = new HashMap<>();
        houguan.put("number", "1MO1812040025");
        houguan.put("number2", "1SO1811270009");
        houguan.put("number3", "J1-EP340T-F260011ATN-2");
        houguan.put("number4", "ATN260011  系統櫃(垃圾筒) -抽屜+垃圾筒固定片*4pcs");
        houguan.put("sum", "生產數量：3");
        houguan.put("time", "預計上線：2018-12-07");
        houguan.put("time_start", "計劃開始：09:30");
        houguan.put("time_end", "計劃結束：09:50");
        houguan.put("group", "一群-塗裝");
        houguan.put("status", "生效");
        tabData.getData().add(houguan);

        HashMap<String, String> assembly = new HashMap<>();
        assembly.put("number", "1MO1812040005");
        assembly.put("number2", "1SO1811270009");
        assembly.put("number3", "ATN260011-06");
        assembly.put("number4", "EP338T砂漆淺灰/EP340T砂漆灰  系統櫃組合--26”下箱垃圾桶櫃");
        assembly.put("sum", "生產數量：3");
        assembly.put("time", "預計上線：2018-12-05");
        assembly.put("time_start", "計劃開始：08:00");
        assembly.put("time_end", "計劃結束：08:05");
        assembly.put("group", "一群-裝配");
        assembly.put("status", "生效");
        tabData.getData().add(assembly);

        HashMap<String, String> sale = new HashMap<>();
        sale.put("number", "1SO1811270009");
        sale.put("number2", " ");
        sale.put("number3", "客戶名稱：(M1315) MATADOR  GmbH");
        sale.put("number4", "客戶訂單：6003028");
        sale.put("sum", " ");
        sale.put("time", "業務人員：(M3049) 嚴卉婷");
        sale.put("time_start", " ");
        sale.put("time_end", " ");
        sale.put("group", " ");
        sale.put("status", "生效");
        tabData.getData().add(sale);
    }

    public static void setQueryData() {
        QueryData queryData = QueryData.getInstance();

        //前關
        HashMap<String, String> qianguan = new HashMap<>();
        qianguan.put("serial", "1");
        qianguan.put("material", "L25-ATN260011-1");
        qianguan.put("specifications", "ATN260011 垃圾筒系統櫃門片0.8*613.7*236.3mm(M1-ATN260011-1)-雷射課");
        qianguan.put("unitdosage", "1.00");
        qianguan.put("requiredamount", "3.00");
        qianguan.put("unit", "PCS");
        qianguan.put("storage", null);
        qianguan.put("description", null);
        queryData.getQueryData().add(qianguan);

        //本階
        HashMap<String, String> thislevel = new HashMap<>();
        List<String> serials = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            serials.add(String.valueOf(i));
        }

        List<String> material = new ArrayList<>(
                Arrays.asList("M1-ATN260011-22", "F260011ATN-21", "M1-F260011-1",
                        "M1-F260011-3A", "M1-F260011-3B", "25-ATA-A-54111-8")
        );

        List<String> specifications = new ArrayList<>(
                Arrays.asList(
                        "ATN260011塑膠垃圾筒抽屜-抽頭 0.8*658.4*617.1MM；沖床組",
                        "ATN260011垃圾筒系統櫃 超大抽身 0.8雙鋼珠 0.8*558*866 mm 世誠",
                        "ATN260011垃圾筒系統櫃抽屜固定片 0.8*140*163.5mm；沖床組（1折）",
                        "ATN260011/ATN340011 垃圾筒抽屜三角形固定片 1.0*200*18.2MM（左）；沖床組（1折）",
                        "ATN260011/ATN340011 垃圾筒抽屜三角形固定片 1.0*200*18.2MM（右）；沖床組（1折）",
                        "ATA-A-54111 抽屜L補強片/料加工 大邊")
        );

        List<String> unitdosage = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            if(i==3){
                unitdosage.add("4.00");
            }else if(i==6){
                unitdosage.add("2.00");
            }else {
                unitdosage.add("1.00");
            }
        }

        List<String> requiredamount = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            if (i == 3) {
                requiredamount.add("12.00");
            } else if (i == 6) {
                requiredamount.add("6.00");
            } else {
                requiredamount.add("3.00");
            }
        }

        List<String> description = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            if(i==2){
                description.add("（400長）橫圓須補強 0.8*555*866");
            }else {
                description.add(" ");
            }
        }

        thislevel.put("serial", String.join(",", serials));
        thislevel.put("material", String.join(",", material));
        thislevel.put("specifications", String.join(",", specifications));
        thislevel.put("unitdosage", String.join(",", unitdosage));
        thislevel.put("requiredamount", String.join(",", requiredamount));
        thislevel.put("unit", "PCS");
        thislevel.put("storage", null);
        thislevel.put("description", String.join(",", description));
        queryData.getQueryData().add(thislevel);


        //後關
        HashMap<String, String> houguan = new HashMap<>();
        List<String> hou_serials = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            hou_serials.add(String.valueOf(i));
        }

        List<String> hou_material = new ArrayList<>(
                Arrays.asList("JA31035", "F260011ATN-2")
        );

        List<String> hou_specifications = new ArrayList<>(
                Arrays.asList("砂漆灰色塗料EP340T VIGOR(國際色碼:RAL 7016)", "ATN260011 系統櫃(垃圾筒)-抽屜+垃圾筒固定片*4pcs")
        );

        List<String> hou_unitdosage = new ArrayList<>();
        hou_unitdosage.add("0.32");
        hou_unitdosage.add("1.00");


        List<String> hou_requiredamount = new ArrayList<>();
        hou_requiredamount.add("0.96");
        hou_requiredamount.add("3.00");

        List<String> hou_unit = new ArrayList<>();
        hou_unit.add("公斤");
        hou_unit.add("PCS");

        List<String> hou_storage = new ArrayList<>();
        hou_storage.add("常");
        hou_storage.add(" ");

        houguan.put("serial", String.join(",", hou_serials));
        houguan.put("material",String.join(",", hou_material));
        houguan.put("specifications", String.join(",", hou_specifications));
        houguan.put("unitdosage", String.join(",", hou_unitdosage));
        houguan.put("requiredamount", String.join(",", hou_requiredamount));
        houguan.put("unit", String.join(",", hou_unit));
        houguan.put("storage", String.join(",", hou_storage));
        houguan.put("description", "H8-218A/T RAL7016 出貨時需附值證色板");
        queryData.getQueryData().add(houguan);

        //裝配
        HashMap<String, String> assembly = new HashMap<>();
        assembly.put("serial", "1");
        assembly.put("material", "JA31035");
        assembly.put("specifications", "砂漆灰色塗料EP340T VIGOR(國際色碼:RAL 7016)");
        assembly.put("unitdosage", "EP338T砂漆淺灰/EP340T砂漆灰  系統櫃組合--26”下箱垃圾桶櫃");
        assembly.put("requiredamount", "生產數量：3");
        assembly.put("unit", "預計上線：2018-12-05");
        assembly.put("storage", "計劃開始：08:00");
        assembly.put("description", "計劃結束：08:05");
        queryData.getQueryData().add(assembly);
    }

    public static void setSaleData(){

    }
}
