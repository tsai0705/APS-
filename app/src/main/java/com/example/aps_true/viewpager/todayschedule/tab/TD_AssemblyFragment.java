package com.example.aps_true.viewpager.todayschedule.tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.R;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.SaleResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
public class TD_AssemblyFragment extends Fragment {
    public TD_AssemblyFragment() {
        // 必須的空建構子
    }
    private String token;
    private ImageButton rightButton,leftButton;
    private RecyclerView assemblyRecyclerView;
    private OrderAdapter adapter;
    private ApiClient apiClient;
    private GetApi getApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_assembly, container, false);

        // 建立 API 用戶端與接口
        apiClient = new ApiClient();
        //apiClient.ApsApi()：取得一個 Retrofit 實例。
        //.create(GetApi.class)：用這個 Retrofit 實例，建立 GetApi 接口的實作。
        getApi = apiClient.ApsApi().create(GetApi.class);

        rightButton = view.findViewById(R.id.assembly_right_ibtn);
        leftButton = view.findViewById(R.id.assembly_left_ibtn);
        assemblyRecyclerView = view.findViewById(R.id.assembly_recyclerview_rcv);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        assemblyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        assemblyRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter(requireContext(), new ArrayList<>());
        assemblyRecyclerView.setAdapter(adapter);

        // 從 SharedPreferences 取得 Token
        token = getTokenFromPreferences();
        // 呼叫 API（範例參數）
        String saleOrder = "SO12345";
        String item = "PART001";
//        getAssemblyData(saleOrder, item, token);
//        假資料
//        ArrayList<OrderItem> datalist = new ArrayList<>();
//
//        String[] material = getResources().getStringArray(R.array.assembly_material);
//        String[] specifications = getResources().getStringArray(R.array.assembly_specifications);
//        String[] unitdosage = getResources().getStringArray(R.array.assembly_unitdosage);
//        String[] requiredamount = getResources().getStringArray(R.array.assembly_requiredamount);
//        String[] unit = getResources().getStringArray(R.array.assembly_unit);
//        String[] storage = getResources().getStringArray(R.array.assembly_storage);
//        String[] description = getResources().getStringArray(R.array.assembly_description);
//
//        // 最小長度，避免陣列長度不一致
//        int maxLength = Math.min(
//                Math.min(material.length, specifications.length),
//                Math.min(
//                        Math.min(unitdosage.length, requiredamount.length),
//                        Math.min(unit.length, Math.min(storage.length, description.length))
//                )
//        );
//
//        // 依照 index 逐一建立 OrderItem
//        for (int i = 0; i < maxLength; i++) {
//            OrderItem item = new OrderItem(
//                    String.valueOf(i+1),
//                    material[i],
//                    specifications[i],
//                    unitdosage[i],
//                    requiredamount[i],
//                    unit[i],
//                    storage[i],
//                    description[i]
//            );
//            datalist.add(item);
//        }
//
//        adapter.updateData(datalist);
//        assemblyRecyclerView.setAdapter(adapter);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(4);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2);
            }
        });

        // 回傳 view
        return view;
    }

//    protected void getAssemblyData(String saleOrder, String item, String token) {
//        Disposable disposable = getApi.getAssembly(saleOrder, item, token) // 發起 API 請求
//                .subscribeOn(Schedulers.io()) // 在 IO 線程執行網路請求
//                .observeOn(AndroidSchedulers.mainThread())  // 在主線程處理結果
//                .subscribe(new DisposableObserver<Response<List<AssemblyResponse>>>() { // 訂閱並處理回應
//
//                    @Override
//                    public void onNext(@NonNull Response<List<AssemblyResponse>> response) {
//                        // 檢查 HTTP 回應是否成功
//                        if (response.isSuccessful() && response.body() != null) {
//                            List<AssemblyResponse> assemblyList = response.body();
//
//                            // 更新 UI
//                            updateUI(assemblyList);
//                            Toast.makeText(requireContext(), "資料載入成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(requireContext(),
//                                    "取得資料失敗：HTTP " + response.code(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        String errorMessage;
//
//                        if (e instanceof IOException) {
//                            errorMessage = "網路連線失敗，請檢查網路設定";
//                        } else if (e instanceof HttpException) {
//                            int code = ((HttpException) e).code();
//                            if (code == 401) {
//                                errorMessage = "登入逾時，請重新登入";
//                                // 導向登入頁面
//                            } else {
//                                errorMessage = "伺服器錯誤：" + code;
//                            }
//                        } else {
//                            errorMessage = "發生錯誤：" + e.getMessage();
//                        }
//
//                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
//                        Log.e("API_ERROR", "錯誤詳情：", e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("API", "裝配資料讀取完成");
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//    }

    private void updateUI(SaleResponse response) {
        // 根據您的 SaleResponse 結構更新 UI
        // 例如：textView.setText(response.getData());
    }

    private String getTokenFromPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return prefs.getString("Token", "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
