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
import retrofit2.Response;

public class QueryMainActivity extends AppCompatActivity {
    private ImageButton backImageButton;
    private RecyclerView resultRecyclerView;
    private TextView usernameTextview ,totalTextview;
    private boolean moLoaded = false, saleLoaded = false;
    private int SaleRequests = 0;
    private LoginData loginData = LoginData.getInstance();
    private TabData tabData = TabData.getInstance();
    private QueryAdapter adapter;
    private ApiClient apiClient;
    private GetApi getApi;
    private ArrayList<ManufactureResponse> moList = new ArrayList<>();
    private ArrayList<SaleResponse> saleList = new ArrayList<>();
    private ArrayList<String> soIdList = tabData.getSo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querymain);

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        totalTextview = findViewById(R.id.querymain_total_tv);
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
        moLoaded = saleLoaded = false;
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
        Log.d("getSo", "so size=" + soIdList.size());
        String customer = "";
        String onlineDate = "";

        // 最大長度
        int maxQuery = Math.min(soIdList.size(), 20);
        SaleRequests = maxQuery;

        Log.d("getSaleData", "開始查詢 " + maxQuery + " 筆 sale（共 " + soIdList.size() + " 筆 order）");

        for (int i = 0; i < maxQuery; i++) {
            String soId = soIdList.get(i);

            getApi.getSale(soId, customer, onlineDate, loginData.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<Response<List<SaleResponse>>>() {
                        @Override
                        public void onNext(Response<List<SaleResponse>> response) {
                            List<SaleResponse> list = response.body();
                            synchronized (saleList) {
                                saleList.addAll(list);
                            }

                            Log.d("getSale", "soId=" + soId + " 回傳 " + list.size() + " 筆，目前總共 " + saleList.size());
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
        Log.d("checkDataReady", "mo=" + moLoaded + " sale=" + saleLoaded + " SaleRequests=" + SaleRequests);

        // 當 MO 載入完成，且 Sale 尚未開始載入
        if (moLoaded && !saleLoaded && SaleRequests == 0) {
            Log.d("checkDataReady", "MO 已載入，開始載入 Sale 資料");
            getSaleData();
        }
        // 當兩者都載入完成
        else if (moLoaded && saleLoaded) {
            Log.d("checkDataReady", "所有資料已載入完成，開始處理");
            loadData();
        }
    }

    protected void loadData() {
        ArrayList<QueryItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};
        String[] check = {"生效"};
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Log.d("loadData", "mo count=" + moList.size() + " sale count=" + saleList.size());

        Log.d("loadData", "有 sale 資料，開始處理");
        for (int i = 0; i < saleList.size(); i++) {
            SaleResponse sale = saleList.get(i);
            Log.d("loadData", "處理第 " + i + " 筆 sale");

            if (sale == null) {
                Log.w("loadData", "第 " + i + " 筆 rec 為 null，跳過");
                continue;
            }

            Log.d("loadData", "第 " + i + " 筆: soId=" + sale.getSoId() + " itemId=" + sale.getItemId());

            String moId = (i < moList.size()) ? moList.get(i).getMoId() : "未配對";
            String soId = sale.getSoId() != null ? sale.getSoId() : "";
            String itemId = sale.getItemId() != null ? sale.getItemId() : "";
            String customer = "";
            String qty = "數量: -";
            String containerDate = "結關日：-";
            String date = sale.getDate() != null ? "上線日：" + sale.getDate() : "上線日：-";

            List<SaleResponse.SaleOrder> soList = sale.getSaleOrder();
            if (soList != null && !soList.isEmpty()) {
                SaleResponse.SaleOrder firstOrder = soList.get(0);
                if (firstOrder.getCustomerName() != null) {
                    customer = firstOrder.getCustomerName();
                    Log.d("loadData", "✅ 取得 customer: " + customer);
                } else {
                    Log.w("loadData", "⚠️ customer_name 為 null");
                }

                if (firstOrder.getQty() != null) {
                    qty = "數量: " + firstOrder.getQty();
                    Log.d("loadData", "✅ 取得 qty: " + qty);
                } else {
                    Log.w("loadData", "⚠️ qty 為 null");
                }
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
            Log.d("loadData", "第 " + i + " 筆，moid:" + moId + "，soid:" + soId + "，itemid:" + itemId +
                    "，customer:" + customer + "，qty:" + qty + "，container:" + containerDate + "，date:" + date);
        }


        Log.d("loadData", "最終 datalist size=" + datalist.size());
        totalTextview.setText("共"+datalist.size()+"筆");

        if (datalist.isEmpty()) {
            Toast.makeText(this, "查無資料", Toast.LENGTH_SHORT).show();
        }

        adapter.updateData(datalist);
        Log.d("loadData", "adapter updated with " + datalist.size() + " items");
    }
}