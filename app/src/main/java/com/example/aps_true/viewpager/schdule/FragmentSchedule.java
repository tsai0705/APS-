package com.example.aps_true.viewpager.schdule;

import android.os.Bundle;
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

import java.util.ArrayList;

public class FragmentSchedule extends Fragment {
    private RecyclerView resultRecyclerView;
    private ScheduleAdapter adapter;
    private ApiClient apiClient;
    private GetApi getApi;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private TabData TabData = com.example.aps_true.data.TabData.getInstance();

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



        // loadData();
        return view;
    }

//    protected void loadData() {
//        ArrayList<ScheduleItem> datalist = new ArrayList<>();
//        String token = loginData.getToken();
//        getApi.getOrder("", token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        response -> {  // response 是 Response<List<OrderResponse>>
//                            List<OrderResponse> soList = response.body();  // ← 取得實際的 List
//
//                            if (soList != null && !soList.isEmpty()) {
//                                for (OrderResponse so : soList) {
//                                    TabData.getInstance().setSo(so.getSoId());
//                                }
//                            }
//                        }
//                );
//
//        String so_id = com.example.aps_true.data.TabData.getInstance().getSo();
//
//        getApi.getCustomer("", token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        response -> {  // response 是 Response<List<OrderResponse>>
//                            List<CustomerResponse> customerList = response.body();  // ← 取得實際的 List
//
//                            if (customerList != null && !customerList.isEmpty()) {
//                                for (CustomerResponse customer : customerList) {
//                                    TabData.getInstance().setCustomer(customer.getCustomer_name());
//                                }
//                            }
//                        }
//                );
//
//        adapter.updateData(datalist);
//    }

}