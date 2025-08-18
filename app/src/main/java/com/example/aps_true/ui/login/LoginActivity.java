package com.example.aps_true.ui.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aps_true.R;
import com.example.aps_true.ui.main.MainActivity; // 補上 import
import com.example.aps_true.ui.main.TabData;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText, passwordEditText;
    private Button submitButton;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private TabData tabData = TabData.getInstance(); // 呼叫類別的靜態方法產生單例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        bindUI();
        submitButton.setOnClickListener(this::onSubmitButtonClick);
    }

    private void bindUI(){
        accountEditText = findViewById(R.id.login_account_et);
        passwordEditText = findViewById(R.id.login_password_et);
        submitButton = findViewById(R.id.login_submit_btn);
        submitButton.setBackgroundTintList(null);

        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String password = passwordEditText.getText().toString();
                boolean isPassword = Pattern.matches("\\d{6,}", password);
                if (!isPassword && !password.isEmpty()) {
                    passwordEditText.setError("密碼需至少6碼數字");
                } else {
                    passwordEditText.setError(null);
                }
            }
        });

        if (loginData.getData().isEmpty()) {
            HashMap<String, String> admin = new HashMap<>();
            admin.put("account", "admin");
            admin.put("password", "123456");
            loginData.getData().add(admin);
        }
    }

    private void onSubmitButtonClick(View v) { // 加上 View 參數
        String account = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!account.isEmpty() && !password.isEmpty()){
            boolean found = false;
            for (HashMap<String, String> user : loginData.getData()) {
                if (user.get("account").equals(account) &&
                        user.get("password").equals(password)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                setData();
                clear();
            } else {
                clear();
                Toast.makeText(this, "帳號密碼錯誤", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "請完整填寫", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        accountEditText.setText("");
        passwordEditText.setText("");
    }

    private void setData(){
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

}