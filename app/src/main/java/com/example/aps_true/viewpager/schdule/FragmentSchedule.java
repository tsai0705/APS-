package com.example.aps_true.viewpager.schdule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.ManufactureResponse;
import com.example.aps_true.utils.api.response.OrderResponse;
import com.example.aps_true.utils.api.response.SaleResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentSchedule extends Fragment {
    private RecyclerView resultRecyclerView;
    private ScheduleAdapter adapter;
    private boolean moLoaded = false, saleLoaded = false;
    private int SaleRequests = 0;
    private LoginData loginData = LoginData.getInstance();
    private TabData tabData = TabData.getInstance();
    private ApiClient apiClient;
    private GetApi getApi;
    private ArrayList<String> soList = new ArrayList<>();
    private ArrayList<ManufactureResponse> moList = new ArrayList<>();
    private ArrayList<SaleResponse> saleList = new ArrayList<>();
    private ArrayList<String> soIdList = tabData.getSo();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule, container, false);

        resultRecyclerView = view.findViewById(R.id.schedule_result_rv);

        // 建立 API 用戶端與接口
        apiClient = new ApiClient();
        //apiClient.ApsApi()：取得一個 Retrofit 實例。
        //.create(GetApi.class)：用這個 Retrofit 實例，建立 GetApi 接口的實作。
        getApi = apiClient.ApsApi().create(GetApi.class);

        // RecyclerView 設定
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ScheduleAdapter(getContext(), new ArrayList<>());
        resultRecyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    private void getData(){

        getApi.getOrder("", loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<OrderResponse>>() {
                    @Override
                    public void onNext(List<OrderResponse> responseList) {
                        ArrayList<String> newSoIdList = new ArrayList<>();

                        for (OrderResponse order : responseList) {
                            if (order != null && order.getSoId() != null) {
                                newSoIdList.add(order.getSoId());
                            }
                        }

                        tabData.setSo(newSoIdList);
                        soIdList = newSoIdList;
                        Log.d("loadInitialData", "已載入 " + soList.size() + " 筆訂單到主列表");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("loadInitialData", "載入訂單主列表失敗", e);
                    }
                    @Override
                    public void onComplete() {}
                });

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
        ArrayList<ScheduleItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};

        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24)); // 隨機設定 0-23 點
        calendar.set(Calendar.MINUTE, 0); // 從 00 分開始
        calendar.set(Calendar.SECOND, 0);

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
            String date = "計畫開始：" + timeFormat.format(calendar.getTime());

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

            ScheduleItem item = new ScheduleItem(
                    i + 1,
                    moId,
                    soId,
                    itemId,
                    customer,
                    qty,
                    containerDate,
                    date,
                    process[0]
            );
            datalist.add(item);
            Log.d("loadData", "第 " + i + " 筆，moid:" + moId + "，soid:" + soId + "，itemid:" + itemId +
                    "，customer:" + customer + "，qty:" + qty + "，container:" + containerDate + "，date:" + date);
            calendar.add(Calendar.MINUTE, 30);
        }

        Log.d("loadData", "最終 datalist size=" + datalist.size());
        adapter.updateData(datalist);
        Log.d("loadData", "adapter updated with " + datalist.size() + " items");
    }

}