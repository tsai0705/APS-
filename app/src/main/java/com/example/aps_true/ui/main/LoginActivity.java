package com.example.aps_true.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.aps_true.utils.api.response.AuthResponse;
import com.example.aps_true.utils.api.response.LoginResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

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

    }

    private void onSubmitButtonClick(View v) {
        Log.d("LoginActivity", "登入按鈕被點擊！");
        String account = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (account.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "請完整填寫", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("LoginActivity", "準備送出登入請求...");
        getApi.getToken(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<LoginResponse>>() {
                    @Override
                    public void onNext(Response<LoginResponse> loginResponse) {
                        Log.d("getToken", "onNext: 收到伺服器回應");
                        if(loginResponse.body()!=null){
                            loginData.setToken(loginResponse.body().getToken());
                            Log.d("getToken", "onNext: "+loginResponse.body().getToken());

                            getApi.getLoginData(loginData.getToken())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableObserver<AuthResponse>() {
                                        @Override
                                        public void onNext(AuthResponse response) {
                                            loginData.setName(response.getName());
                                            Log.d("getLoginData", "收到回應: " + response);

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            clear();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e("getLoginData", "錯誤", e);
                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.d("getLoginData", "請求完成");
                                        }
                                    });


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            clear();
                        }
                        else {
                            Log.e("getToken", "登入錯誤");
                            Toast.makeText(LoginActivity.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                            clear();
                        }

                    }

                    @Override
                    public void onError( Throwable e) {
                        Log.d("getToken", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getToken", "onComplete");
                    }
                });
    }

    private void clear() {
        accountEditText.setText("");
        passwordEditText.setText("");
    }
}