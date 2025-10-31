package com.example.aps_true.ui.query.show.tab;

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
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.example.aps_true.ui.query.main.QueryTabActivity;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderItem;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;

import java.util.ArrayList;

//本階
public class ThislevelFragment extends Fragment {
    public ThislevelFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton,leftButton;
    private RecyclerView thislevelRecyclerView;
    private OrderAdapter adapter;
    private QueryTabActivity activity = (QueryTabActivity) getActivity();
    private String item_id;
    private ApiClient apiClient;
    private GetApi getApi;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private TabData tabData = TabData.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thislevel, container, false);

        rightButton = view.findViewById(R.id.thislevel_right_ibtn);
        leftButton = view.findViewById(R.id.thislevel_left_ibtn);
        thislevelRecyclerView = view.findViewById(R.id.thislevel_recyclerview_rcv);

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        thislevelRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        thislevelRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter(requireContext(), new ArrayList<>());
        thislevelRecyclerView.setAdapter(adapter);


        item_id = activity.getDataForFragment();

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(2);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(0);
            }
        });

        // 回傳 view
        return view;
    }

//    public void getData(){
//        getApi.getThislevel(item_id, loginData.getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<List<ThislevelFragment>>() {
//                    @Override
//                    public void onNext(List<ThislevelFragment> responseList) {
//                        for (ThislevelFragment thislevel : responseList) {
//                            String thislevelName = thislevel.getId();
//                        }
//                        Log.d("loadInitialData", "已載入 " + allCustomerNamesList.size() + " 筆客戶到主列表");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("loadInitialData", "載入客戶主列表失敗", e);
//                    }
//                    @Override
//                    public void onComplete() {}
//                });
//    }

    public void setData(){
        ArrayList<OrderItem> datalist = new ArrayList<>();

        String[] material = getResources().getStringArray(R.array.thislevel_material);
        String[] specifications = getResources().getStringArray(R.array.thislevel_specifications);
        String[] unitdosage = getResources().getStringArray(R.array.thislevel_unitdosage);
        String[] requiredamount = getResources().getStringArray(R.array.thislevel_requiredamount);
        String[] unit = getResources().getStringArray(R.array.thislevel_unit);
        String[] storage = getResources().getStringArray(R.array.thislevel_storage);
        String[] description = getResources().getStringArray(R.array.thislevel_description);

        // 最小長度，避免陣列長度不一致
        int maxLength = Math.min(
                Math.min(material.length, specifications.length),
                Math.min(
                        Math.min(unitdosage.length, requiredamount.length),
                        Math.min(unit.length, Math.min(storage.length, description.length))
                )
        );

        // 依照 index 逐一建立 OrderItem
        for (int i = 0; i < maxLength; i++) {
            OrderItem item = new OrderItem(
                    String.valueOf(i+1),
                    material[i],
                    specifications[i],
                    unitdosage[i],
                    requiredamount[i],
                    unit[i],
                    storage[i],
                    description[i]
            );
            datalist.add(item);
        }

        adapter.updateData(datalist);
        thislevelRecyclerView.setAdapter(adapter);
    }

}

