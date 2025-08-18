package com.example.aps_true.ui.main;

import java.util.ArrayList;
import java.util.HashMap;

public class TabData {
    private static TabData instance; // 單例實例變數，用來保存唯一的 LoginData 物件
    private String number;
    private String number2;
    private String number3;
    private String number4;
    private String sum;
    private String time;
    private String time_start;
    private String time_end;
    private String group;
    private String status;
    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    private TabData() {}

    // 提供一個公共的靜態方法，用於獲取單例
    public static TabData getInstance() {
        if (instance == null) {  //檢查 instance 是否已經被建立
            //synchronized 鎖住關鍵字
            synchronized (TabData.class) { // 在多執行緒（多線程）環境下，確保同一時間只有一個執行緒能夠進入這個區塊，從而保證只會產生一個唯一的 LoginData 實例（instance）
                if (instance == null) {
                    instance = new TabData(); //如果還沒建立（instance == null）則建立一個新的。
                }
            }
        }
        return instance;
    }



    // getter 和 setter 方法
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getNumber3() {
        return number3;
    }

    public void setNumber3(String number3) {
        this.number3 = number3;
    }

    public String getNumber4() {
        return number4;
    }

    public void setNumber4(String number4) {
        this.number4 = number4;
    }

    public String getSum() { return sum; }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_start() { return time_start; }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() { return time_end; }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getGroup() { return group; }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }


    public ArrayList<HashMap<String, String>> getData() {
        return data; //取得目前 LoginData 實例裡 data 這個成員變數的內容
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data; //設定目前 LoginData 實例裡 data 這個成員變數的內容
    }


}
