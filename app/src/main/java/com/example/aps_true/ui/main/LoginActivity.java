package com.example.aps_true.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText, passwordEditText;
    private Button submitButton;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private ApiClient apiClient;
    private GetApi getApi;
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

        // 建立 API 用戶端與接口
        apiClient = new ApiClient();
        //apiClient.ApsApi()：取得一個 Retrofit 實例。
        //.create(GetApi.class)：用這個 Retrofit 實例，建立 GetApi 接口的實作。
        getApi = apiClient.ApsApi().create(GetApi.class);


        getApi.getToken("account","password");
    }

    private void onSubmitButtonClick(View v) { // 加上 View 參數
        String account = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (account.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "請完整填寫", Toast.LENGTH_SHORT).show();
            return;
        }

        // 呼叫登入 API：傳入帳號與密碼
        getApi.getToken(account, password)
                // 指定在背景執行緒（IO 執行緒）執行 API 請求
                .subscribeOn(Schedulers.io())
                // 指定回到主執行緒（UI 執行緒）處理結果
                .observeOn(AndroidSchedulers.mainThread())
                // 訂閱（subscribe）API 回傳結果
                .subscribe(
                        response -> {
                            // 若伺服器回傳狀態碼為 0，表示登入成功
                            if (response.getStatus() == 0) {

                                // 儲存伺服器回傳的 Token 到單例 LoginData（方便之後呼叫其他 API 使用）
                                loginData.setToken(response.getToken());

                                // 顯示登入成功訊息
                                Toast.makeText(this, "登入成功！", Toast.LENGTH_SHORT).show();

                                // 登入成功後，跳轉到主畫面 (MainActivity)
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);

                                // 清空輸入欄位（帳號、密碼）
                                clear();

                            } else {
                                // 登入失敗（帳密錯誤）
                                Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            // 若 API 請求過程中發生例外（例如網路錯誤、伺服器無回應）
                            Toast.makeText(this, "伺服器連線失敗：" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                );

    }

    private void clear() {
        accountEditText.setText("");
        passwordEditText.setText("");
    }
}