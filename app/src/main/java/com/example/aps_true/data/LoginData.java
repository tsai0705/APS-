package com.example.aps_true.data;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginData {
    private static LoginData instance; // 單例實例變數，用來保存唯一的 LoginData 物件
    private String account; // 用戶名
    private String password; // 密碼
    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    private LoginData() {}  // 私有建構子，外部不能用 new LoginData() 新建物件，強制只能透過 getInstance() 取得唯一實例

    // 提供一個公共的靜態方法，用於獲取單例
    public static LoginData getInstance() {
        if (instance == null) {  //檢查 instance 是否已經被建立
            //synchronized 鎖住關鍵字
            synchronized (LoginData.class) { // 在多執行緒（多線程）環境下，確保同一時間只有一個執行緒能夠進入這個區塊，從而保證只會產生一個唯一的 LoginData 實例（instance）
                if (instance == null) {
                    instance = new LoginData(); //如果還沒建立（instance == null）則建立一個新的。
                }
            }
        }
        return instance;
    }



    // getter 和 setter 方法
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<HashMap<String, String>> getData() {
        return data; //取得目前 LoginData 實例裡 data 這個成員變數的內容
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.data = data; //設定目前 LoginData 實例裡 data 這個成員變數的內容
    }


}
