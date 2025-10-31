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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class QueryTabActivity extends AppCompatActivity {
    private TextView numberTextView, number2TextView, number3TextView, number4TextView, timeTextView,
            sumTextView, timestartTextView, timeendTextView, groupTextView, statusTextView;
    private ImageButton backButton;
    private ViewPager2 viewPager;
    private TabLayout tab;
    private QueryViewPagerAdapter adapter;
    private TextView usernameTextview;

    private LoginData loginData = LoginData.getInstance();
    private ApiClient apiClient;
    private GetApi getApi;
    private String item_id;
    private String so_id;
    private String token; // 儲存 Token

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

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        backButton = findViewById(R.id.querytab_back_ibtn);
        backButton.setOnClickListener(v -> finish());

        // 從 Intent 獲取資料
        item_id = getIntent().getStringExtra("item_id");
        so_id = getIntent().getStringExtra("so_id");
        Log.d(TAG, "item_id:"+item_id+",so_id:"+so_id+",token:"+token);

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
        numberTextView.setText(getResources().getStringArray(R.array.tab_number)[choose]);
        number2TextView.setText(getResources().getStringArray(R.array.tab_number2)[choose]);
        number3TextView.setText(getResources().getStringArray(R.array.tab_number3)[choose]);
        timeTextView.setText(getResources().getStringArray(R.array.tab_time)[choose]);
        groupTextView.setText(getResources().getStringArray(R.array.tab_group)[choose]);

        switch (choose) {
            case 0:
                callQianguanApi();
                statusTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                break;
            case 1:
                callThislevelApi();
                statusTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                break;
            case 2:
                callHouguanApi();
                break;
            case 3:
                callAssemblyApi();
                statusTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                break;
            case 4:
                callSaleApi();
                statusTextView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                break;
            default:
                showErrorOnUi("無效的頁面");
                break;
        }
    }

    // --- API 呼叫區 ---
    private void callQianguanApi() {
        Log.d(TAG, "呼叫 API (0): get-prev-manufacture");
        getApi.getQianguan(so_id, item_id, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<QianguanResponse>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<QianguanResponse>> response) {
                        if (!response.isSuccessful()) {
                            showErrorOnUi("前關 API 錯誤: " + response.code()); return;
                        }
                        List<QianguanResponse> list = response.body();
                        if (list == null || list.isEmpty()) {
                            showErrorOnUi("查無前關製令資料"); return;
                        }

                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Random random = new Random();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24)); // 隨機設定 0-23 點
                        calendar.set(Calendar.MINUTE, 0); // 從 00 分開始
                        calendar.set(Calendar.SECOND, 0);
                        String start = "計畫開始：" + timeFormat.format(calendar.getTime());
                        calendar.add(Calendar.MINUTE, 30);
                        String end = "計畫結束：" + timeFormat.format(calendar.getTime());

                        QianguanResponse r = list.get(0);
                        updateUiWithData(
                                r.getMoId(),
                                so_id,
                                r.getCustomer(),
                                r.getItemName(),
                                r.getOnlineDate(),
                                "生產數量:"+String.valueOf(r.getQty()),
                                start,
                                end,
                                "一群-沖床",
                                "結案"
                        );
                        Log.d(TAG, r.getMoId());
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Qianguan Error", e); showErrorOnUi("前關 API 失敗: " + e.getMessage()); }
                    @Override
                    public void onComplete() {}
                });
    }

    private void callThislevelApi() {
        Log.d(TAG, "呼叫 API (1): get-current-stage-com");
        getApi.getThislevel(item_id, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<ThislevelResponse>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<ThislevelResponse>> response) {
                        if (!response.isSuccessful()) {
                            showErrorOnUi("本階 API 錯誤: " + response.code()); return;
                        }
                        List<ThislevelResponse> list = response.body();
                        if (list == null || list.isEmpty()) {
                            showErrorOnUi("查無本階製令資料"); return;
                        }

                        ThislevelResponse r = list.get(0);
                        List<ThislevelResponse.Parent> parentList = r.getParent();
                        String bomKeyName = "N/A";

                        if (parentList != null && !parentList.isEmpty()) {
                            ThislevelResponse.Parent firstParent = parentList.get(0);
                            if (firstParent != null) {
                                bomKeyName = firstParent.getBomkeyName();
                            }
                        }

                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Random random = new Random();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24)); // 隨機設定 0-23 點
                        calendar.set(Calendar.MINUTE, 0); // 從 00 分開始
                        calendar.set(Calendar.SECOND, 0);
                        String start = "計畫開始：" + timeFormat.format(calendar.getTime());
                        calendar.add(Calendar.MINUTE, 30);
                        String end = "計畫結束：" + timeFormat.format(calendar.getTime());

                        HouguanResponse r = list.get(0);
                        updateUiWithData(
                                r.getMoId(),
                                so_id,
                                r.getCustomer(),
                                bomKeyName,
                                r.getOnlineDate(),
                                "生產數量:"+String.valueOf(r.getQty()),
                                start,
                                end,
                                "一群-沖床",
                                "結案"
                        );
                        Log.d(TAG, r.getMoId());
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Thislevel Error", e); showErrorOnUi("本階 API 失敗: " + e.getMessage()); }
                    @Override
                    public void onComplete() {}
                });
    }

    private void callHouguanApi() {
        Log.d(TAG, "呼叫 API (2): get-next-part");
        getApi.getHouguan(so_id, item_id, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<HouguanResponse>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<HouguanResponse>> response) {
                        if (!response.isSuccessful()) {
                            showErrorOnUi("後關 API 錯誤: " + response.code()); return;
                        }
                        List<HouguanResponse> list = response.body();
                        if (list == null || list.isEmpty()) {
                            showErrorOnUi("查無後關製令資料"); return;
                        }

                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        Random random = new Random();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24)); // 隨機設定 0-23 點
                        calendar.set(Calendar.MINUTE, 0); // 從 00 分開始
                        calendar.set(Calendar.SECOND, 0);
                        String start = "計畫開始：" + timeFormat.format(calendar.getTime());
                        calendar.add(Calendar.MINUTE, 30);
                        String end = "計畫結束：" + timeFormat.format(calendar.getTime());

                        HouguanResponse r = list.get(0);
                        updateUiWithData(
                                r.getMoId(),
                                so_id,
                                r.getCustomer(),
                                r.getItemName(),
                                r.getOnlineDate(),
                                "生產數量:"+String.valueOf(r.getQty()),
                                start,
                                end,
                                "一群-沖床",
                                "結案"
                        );
                        Log.d(TAG, r.getMoId());
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Houguan Error", e); showErrorOnUi("後關 API 失敗: " + e.getMessage()); }
                    @Override
                    public void onComplete() {}
                });
    }

    private void callAssemblyApi() {
        Log.d(TAG, "呼叫 API (3): get-so-data");
        getApi.getAssembly(so_id, item_id, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<AssemblyResponse>>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<AssemblyResponse>> response) {
                        if (!response.isSuccessful()) {
                            showErrorOnUi("裝配 API 錯誤: " + response.code()); return;
                        }
                        List<AssemblyResponse> list = response.body();
                        if (list == null || list.isEmpty()) {
                            showErrorOnUi("查無裝配製令資料"); return;
                        }
                        AssemblyResponse r = list.get(0);
                        updateUiWithData(
                                r.getMoId(),
                                String.valueOf(r.getQty()), // 根據文件 qty 是 Int
                                r.getOnlineDate(),
                                r.getCompleteDate(),
                                r.getCustomer(),
                                "生效"
                        );
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Assembly Error", e); showErrorOnUi("裝配 API 失敗: " + e.getMessage()); }
                    @Override
                    public void onComplete() {}
                });
    }

    private void callSaleApi() {
        Log.d(TAG, "呼叫 API (4): get-sale-order");
        getApi.getSale(so_id, "", "", token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<SaleResponse>>>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<List<SaleResponse>> response) {
                        if (!response.isSuccessful()) {
                            showErrorOnUi("銷售 API 錯誤: " + response.code()); return;
                        }
                        List<SaleResponse> list = response.body();
                        if (list == null || list.isEmpty()) {
                            showErrorOnUi("查無銷售訂單資料"); return;
                        }

                        SaleResponse r = list.get(0);
                        // 根據文件，從巢狀的 sale_order 獲取詳細資料
                        String customerName = r.getCustomer(); // 預設
                        String containerDateStr = "N/A"; // 預設

                        List<SaleResponse.SaleOrder> saleOrderList = r.getSaleOrder();
                        if (saleOrderList != null && !saleOrderList.isEmpty()) {
                            SaleResponse.SaleOrder firstOrder = saleOrderList.get(0);
                            if (firstOrder.getCustomerName() != null) {
                                customerName = firstOrder.getCustomerName();
                            }
                            if (firstOrder.getContainerDate() != null) {
                                try {
                                    // 轉換 Date 物件為 String
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    containerDateStr = sdf.format(firstOrder.getContainerDate());
                                } catch (Exception e) {
                                    Log.e(TAG, "日期格式化失敗", e);
                                }
                            }
                        }

                        updateUiWithData(
                                r.getSoId(),        // number4Data (SO ID)
                                r.getQty(),         // sumData (根據文件 qty 是 String)
                                r.getDate(),        // startDate (上線日期)
                                containerDateStr,   // endDate (結關日)
                                customerName,       // groupData
                                "生效"
                        );
                    }
                    @Override
                    public void onError(Throwable e) { Log.e(TAG, "Sale Error", e); showErrorOnUi("銷售 API 失敗: " + e.getMessage()); }
                    @Override
                    public void onComplete() {}
                });
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

    private void updateUiWithData(String numberData,String number2Data,String number3Data,String number4Data,String timeData, String sumData, String startDate, String endDate, String groupData, String statusData) {
        // 這些是資料欄位
        numberTextView.setText(numberData != null ? numberData : "N/A");
        number2TextView.setText(number2Data != null ? number2Data : "N/A");
        number3TextView.setText(number3Data != null ? number3Data : "N/A");
        number4TextView.setText(number4Data != null ? number4Data : "N/A");
        timeTextView.setText(timeData != null ? timeData : "N/A");
        sumTextView.setText(sumData != null ? sumData : "N/A");
        timestartTextView.setText(startDate != null ? startDate : "N/A");
        timeendTextView.setText(endDate != null ? endDate : "N/A");
        groupTextView.setText(groupData != null ? groupData : "N/A");
        statusTextView.setText(statusData != null ? statusData : "N/A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}