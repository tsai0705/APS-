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

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText, passwordEditText;
    private Button submitButton;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData

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

        check();

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

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        accountEditText.addTextChangedListener(watcher);
        passwordEditText.addTextChangedListener(watcher);

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
                clear();
            } else {
                clear();
                Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "請填寫完整", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        accountEditText.setText("");
        passwordEditText.setText("");
    }

    private void check() {
        String account = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isAllFilled = !account.isEmpty() && !password.isEmpty();
        boolean isPassword = Pattern.matches("\\d{6,}", password);

        submitButton.setEnabled(isAllFilled && isPassword);
    }
}