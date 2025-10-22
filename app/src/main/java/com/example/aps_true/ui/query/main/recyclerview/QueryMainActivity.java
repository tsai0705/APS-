package com.example.aps_true.ui.query.main.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.ManufactureResponse;
import com.example.aps_true.utils.api.response.SaleResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueryMainActivity extends AppCompatActivity {
    private ImageButton backImageButton;
    private RecyclerView resultRecyclerView;
    private TextView usernameTextview;
    private boolean moLoaded = false, orderLoaded = false, saleLoaded = false;
    private int SaleRequests = 0;
    private LoginData loginData = LoginData.getInstance();
    private TabData tabData = TabData.getInstance();
    private QueryAdapter adapter;
    private ApiClient apiClient;
    private GetApi getApi;

    // 存放資料
    private ArrayList<ManufactureResponse> moList = new ArrayList<>();
    private ArrayList<SaleResponse> saleList = new ArrayList<>();
    private ArrayList<String> soIdList = new ArrayList<>();
    private ArrayList<String> customerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querymain);

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        usernameTextview = findViewById(R.id.querymain_username_tv);
        usernameTextview.setText(loginData.getName());
        backImageButton = findViewById(R.id.querymain_back_ibtn);
        resultRecyclerView = findViewById(R.id.querymain_result_rv);

        // RecyclerView 設定
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new QueryAdapter(this, new ArrayList<>());
        resultRecyclerView.setAdapter(adapter);

        getData();

        backImageButton.setOnClickListener(v -> finish());
    }

    private void getData(){
        // 設置為未存取
        moLoaded = orderLoaded = saleLoaded = false;
        // 清除資料
        moList.clear();
        saleList.clear();
        soIdList.clear();
        customerList.clear();
        SaleRequests = 0;

        // mo
        String moid = "";
        getApi.getMo(moid, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ManufactureResponse>>() {
                    @Override
                    public void onNext(List<ManufactureResponse> responseList) {
                        moList.clear();
                        moList.addAll(responseList);

                        ArrayList<String> moIds = new ArrayList<>();
                        for (ManufactureResponse r : responseList) {
                            if (r.getMoId() != null) moIds.add(r.getMoId());
                        }
                        tabData.setMo(moIds);

                        Log.d("getMo", "mo size=" + moList.size());
                        // 設為已存取
                        moLoaded = true;
                        checkDataReady();
                    }

                    @Override
                    public void onError(Throwable e) {
                        moList.clear();
                        Log.e("getMo", "API 錯誤", e);
                        moLoaded = true;
                        checkDataReady();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getMo", "請求完成");
                    }
                });
    }

    private void getSaleData() {
        soIdList = tabData.getSo();
        customerList = tabData.getCustomer();
        String onlineDate = "";

        // 最大長度
        int maxQuery = Math.min(soIdList.size(), 20);
        SaleRequests = maxQuery;

        Log.d("getSaleData", "開始查詢 " + maxQuery + " 筆 sale（共 " + soIdList.size() + " 筆 order）");

        for (int i = 0; i < maxQuery; i++) {
            String soId = soIdList.get(i);
            String customer = customerList.get(i);

            getApi.getSale(soId, customer, onlineDate, loginData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<List<SaleResponse>>() {
                        @Override
                        public void onNext(List<SaleResponse> responseList) {
                            synchronized (saleList) {
                                saleList.addAll(responseList);
                            }

                            Log.d("getSale", "soId=" + soId + " 回傳 " + responseList.size() + " 筆，目前總共 " + saleList.size());
                            checkSaleRequestComplete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("getSale", "soId=" + soId + " API 錯誤", e);
                            checkSaleRequestComplete();
                        }

                        @Override
                        public void onComplete() {
                            Log.d("getSale", "soId=" + soId + " 請求完成");
                        }
                    });
        }
    }

    // 確認已抓取sale資料
    private void checkSaleRequestComplete() {
        SaleRequests--;
        Log.d("checkSaleRequest", "剩餘=" + SaleRequests + " 已收集 sale=" + saleList.size());

        if (SaleRequests <= 0) {
            saleLoaded = true;
            checkDataReady();
        }
    }

    // 確認已抓取全部資料
    private void checkDataReady() {
        Log.d("checkDataReady", "mo=" + moLoaded + " order=" + orderLoaded + " sale=" + saleLoaded);
        if (moLoaded && orderLoaded && saleLoaded) {
            loadData();
        }
    }

    protected void loadData() {
        ArrayList<QueryItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};
        String[] check = {"生效"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Log.d("loadData", "mo count=" + moList.size() + " sale count=" + saleList.size());

        if (saleList.isEmpty()) {
            Log.w("loadData", "saleList 為空，只顯示 mo");
            // 若沒有 sale 資料，只顯示 mo
            for (int i = 0; i < moList.size(); i++) {
                ManufactureResponse mo = moList.get(i);
                QueryItem item = new QueryItem(
                        i + 1,
                        mo.getMoId() != null ? mo.getMoId() : "",
                        "未指定",
                        "未指定",
                        "未指定",
                        "數量-",
                        "結關日：-",
                        "上線日：-",
                        process[0],
                        check[0]
                );
                datalist.add(item);
            }
        } else {
            Log.d("loadData", "有 sale 資料，開始處理");
            // 有 sale 資料，以 sale 為主顯示
            for (int i = 0; i < saleList.size(); i++) {
                SaleResponse sale = saleList.get(i);
                Log.d("loadData", "處理第 " + i + " 筆 sale");

                SaleResponse.Records rec = sale.getRecords();
                if (rec == null) {
                    Log.w("loadData", "第 " + i + " 筆 rec 為 null，跳過");
                    continue;
                }

                Log.d("loadData", "第 " + i + " 筆: soId=" + rec.getSoId() + " itemId=" + rec.getItemId());

                // 嘗試配對 mo（用 index 或其他邏輯）
                String moId = (i < moList.size()) ? moList.get(i).getMoId() : "未配對";

                String soId = rec.getSoId() != null ? rec.getSoId() : "";
                String itemId = rec.getItemId() != null ? rec.getItemId() : "";
                String customer = rec.getCustomer() != null ? rec.getCustomer() : "";
                String qty = rec.getQty() != null ? "數量" + rec.getQty() : "數量-";
                String date = rec.getDate() != null ? "上線日：" + rec.getDate() : "上線日：-";

                String containerDate = "結關日：-";
                List<SaleResponse.SaleOrder> soList = rec.getSaleOrder();
                if (soList != null && !soList.isEmpty()) {
                    Date cd = soList.get(0).getContainerDate();
                    if (cd != null) {
                        containerDate = "結關日：" + sdf.format(cd);
                    }
                }

                QueryItem item = new QueryItem(
                        i + 1,
                        moId,
                        soId,
                        itemId,
                        customer,
                        qty,
                        containerDate,
                        date,
                        process[0],
                        check[0]
                );
                datalist.add(item);
                Log.d("loadData", "第 " + i + " 筆加入成功，datalist size=" + datalist.size());
            }
        }

        Log.d("loadData", "最終 datalist size=" + datalist.size());

        if (datalist.isEmpty()) {
            Toast.makeText(this, "查無資料", Toast.LENGTH_SHORT).show();
        }

        adapter.updateData(datalist);
        Log.d("loadData", "adapter updated with " + datalist.size() + " items");
    }
}