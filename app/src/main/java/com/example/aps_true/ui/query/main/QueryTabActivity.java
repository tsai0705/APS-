package com.example.aps_true.ui.query.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.AssemblyResponse;
import com.example.aps_true.utils.api.response.HouguanResponse;
import com.example.aps_true.utils.api.response.QianguanResponse;
import com.example.aps_true.utils.api.response.SaleResponse;
import com.example.aps_true.utils.api.response.ThislevelResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueryTabActivity extends AppCompatActivity {
    private TextView numberTextView, number2TextView, number3TextView, number4TextView, timeTextView,
            sumTextView, timestartTextView, timeendTextView, groupTextView, statusTextView;
    private ImageButton backButton;
    private ViewPager2 viewPager;
    private TabLayout tab;
    private QueryViewPagerAdapter adapter;
    private TextView usernameTextview;
    private ArrayList<QianguanResponse> qianguanList = new ArrayList<>();
    private ArrayList<ThislevelResponse> thislevelList = new ArrayList<>();
    private ArrayList<HouguanResponse> houguanList = new ArrayList<>();
    private ArrayList<AssemblyResponse> assemblyList = new ArrayList<>();
    private ArrayList<SaleResponse> saleList = new ArrayList<>();
    private LoginData loginData = LoginData.getInstance();
    private ApiClient apiClient;
    private GetApi getApi;
    private String item_id;
    private String so_id;
    private String token; // 儲存 Token

    private CompositeDisposable disposables = new CompositeDisposable();
    private int defaultTextColor;

    private static final String TAG = "QueryTabActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querytab);

        usernameTextview = findViewById(R.id.querytab_username_tv);
        usernameTextview.setText(loginData.getName());
        token = loginData.getToken(); // 獲取 Token

        bindUI();
    }

    protected void bindUI() {
        numberTextView = findViewById(R.id.querytab_number_tv);
        number2TextView = findViewById(R.id.querytab_number2_tv);
        number3TextView = findViewById(R.id.querytab_number3_tv);
        number4TextView = findViewById(R.id.querytab_number4_tv);
        sumTextView = findViewById(R.id.querytab_sum_tv);
        timeTextView = findViewById(R.id.querytab_time_tv);
        timestartTextView = findViewById(R.id.querytab_time_start_tv);
        timeendTextView = findViewById(R.id.querytab_time_end_tv);
        groupTextView = findViewById(R.id.querytab_group_tv);
        statusTextView = findViewById(R.id.querytab_status_tv);

        defaultTextColor = statusTextView.getCurrentTextColor();

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        backButton = findViewById(R.id.querytab_back_ibtn);
        backButton.setOnClickListener(v -> finish());

        // 從 Intent 獲取資料
        item_id = getIntent().getStringExtra("item_id");
        so_id = getIntent().getStringExtra("so_id");

        viewPager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);

        adapter = new QueryViewPagerAdapter(this, item_id, so_id);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0); // 預設選中 "本階製令" (1) 現改為0測試

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Choose(position);
            }
        });

        new TabLayoutMediator(tab, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("前關製令"); break;
                case 1: tab.setText("本階製令"); break;
                case 2: tab.setText("後關製令"); break;
                case 3: tab.setText("裝配製令"); break;
                case 4: tab.setText("銷售訂單"); break;
            }
        }).attach();

        // 載入 "本階製令" 的資料
        Choose(viewPager.getCurrentItem());
    }

    protected void Choose(Integer choose) {
        disposables.clear(); // 清除上一個 Tab 的 API 請求

        numberTextView.setText(getResources().getStringArray(R.array.tab_number)[choose]);
        number2TextView.setText(getResources().getStringArray(R.array.tab_number2)[choose]);
        number3TextView.setText(getResources().getStringArray(R.array.tab_number3)[choose]);
        timeTextView.setText(getResources().getStringArray(R.array.tab_time)[choose]);
        groupTextView.setText(getResources().getStringArray(R.array.tab_group)[choose]);

        switch (choose) {
            case 0:
                callQianguanApi();
                break;
            case 1:
                callThislevelApi();
                break;
            case 2:
                callHouguanApi();
                break;
            case 3:
                callAssemblyApi();
                break;
            case 4:
                callSaleApi();
                break;
            default:
                showErrorOnUi("無效的頁面");
                break;
        }
    }

    // --- API 呼叫區 ---

    private void callQianguanApi() {
        Log.d(TAG, "呼叫 API (0): get-prev-manufacture");
        disposables.add(
                getApi.getQianguan(so_id, item_id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<QianguanResponse>>() {
                            @Override
                            public void onNext(List<QianguanResponse> responseList) {
                                synchronized (qianguanList) {
                                    qianguanList.addAll(responseList);
                                }
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, "Qianguan Error", e); showErrorOnUi("前關 API 失敗: " + e.getMessage()); }
                            @Override
                            public void onComplete() {}
                        })
        );
    }

    private void callThislevelApi() {
        Log.d(TAG, "呼叫 API (1): get-current-stage-com");
        disposables.add(
                getApi.getThislevel(item_id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<ThislevelResponse>>() {
                            @Override
                            public void onNext(List<ThislevelResponse> responseList) {
                                synchronized (thislevelList) {
                                    thislevelList.addAll(responseList);
                                }
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, "Thislevel Error", e); showErrorOnUi("本階 API 失敗: " + e.getMessage()); }
                            @Override
                            public void onComplete() {}
                        })
        );
    }

    private void callHouguanApi() {
        Log.d(TAG, "呼叫 API (2): get-next-part");
        disposables.add(
                getApi.getHouguan(so_id, item_id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<HouguanResponse>>() {
                            @Override
                            public void onNext(List<HouguanResponse> responseList) {
                                synchronized (houguanList) {
                                    houguanList.addAll(responseList);
                                }
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, "Houguan Error", e); showErrorOnUi("後關 API 失敗: " + e.getMessage()); }
                            @Override
                            public void onComplete() {}
                        })
        );
    }

    private void callAssemblyApi() {
        Log.d(TAG, "呼叫 API (3): get-so-data");
        disposables.add(
                getApi.getAssembly(so_id, item_id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<AssemblyResponse>>() {
                            @Override
                            public void onNext(List<AssemblyResponse> responseList) {
                                synchronized (assemblyList) {
                                    assemblyList.addAll(responseList);
                                }
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, "Assembly Error", e); showErrorOnUi("裝配 API 失敗: " + e.getMessage()); }
                            @Override
                            public void onComplete() {}
                        })
        );
    }

    private void callSaleApi() {
        Log.d(TAG, "呼叫 API (4): get-sale-order");
        disposables.add(
                getApi.getSale(so_id, null, null, token) // 傳遞 null 給缺失的參數
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<SaleResponse>>() {
                            @Override
                            public void onNext(List<SaleResponse> responseList) {
                                synchronized (saleList) {
                                    saleList.addAll(responseList);
                                }
                            }
                            @Override
                            public void onError(Throwable e) { Log.e(TAG, "Sale Error", e); showErrorOnUi("銷售 API 失敗: " + e.getMessage()); }
                            @Override
                            public void onComplete() {}
                        })
        );
    }

    // --- UI 輔助方法 ---

    private void showErrorOnUi(String message) {
        sumTextView.setText("-");
        timestartTextView.setText("-");
        timeendTextView.setText("-");
        statusTextView.setText(message);
        statusTextView.setTextColor(Color.RED);
        number4TextView.setText("-");
    }

    private void updateUiWithData(String number4Data, String sumData, String startDate, String endDate, String groupData, String statusData) {
        // 這些是資料欄位
        number4TextView.setText(number4Data != null ? number4Data : "N/A");
        sumTextView.setText(sumData != null ? sumData : "N/A");
        timestartTextView.setText(startDate != null ? startDate : "N/A");
        timeendTextView.setText(endDate != null ? endDate : "N/A");
        groupTextView.setText(groupData != null ? groupData : "N/A");
        statusTextView.setText(statusData != null ? statusData : "N/A");

        // 狀態顏色
        if (statusData != null && statusData.equals("已完工")) {
            statusTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
        } else {
            statusTextView.setTextColor(defaultTextColor);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // 離開 Activity 時，清除所有 API 請求
    }
}