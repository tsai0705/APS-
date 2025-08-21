package com.example.aps_true.ui.query.show.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.aps_true.data.DataProvider;
import com.example.aps_true.data.QueryData;
import com.example.aps_true.ui.query.main.recyclerview.QueryAdapter;
import com.example.aps_true.ui.query.main.recyclerview.QueryItem;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.ui.query.show.tab.recyclerview.OrderItem;
import com.example.aps_true.viewpager.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aps_true.R;

import java.util.ArrayList;
import java.util.HashMap;

//本階
public class ThislevelFragment extends Fragment {
    public ThislevelFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton,leftButton;
    private RecyclerView thislevelRecyclerView;
    private OrderAdapter adapter;
    private QueryData queryData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thislevel, container, false);

        rightButton = view.findViewById(R.id.thislevel_right_ibtn);
        leftButton = view.findViewById(R.id.thislevel_left_ibtn);
        thislevelRecyclerView = view.findViewById(R.id.thislevel_recyclerview_rcv);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        thislevelRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        thislevelRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter(requireContext(), new ArrayList<>());
        thislevelRecyclerView.setAdapter(adapter);

//        ArrayList<OrderItem> datalist = new ArrayList<>();
//        QueryData queryData = QueryData.getInstance().getQueryData();
//        for (int i = 1; i < 8; i++) {
//            OrderItem item = new OrderItem(
//                    queryData.getSerial(),
//                    queryData.getMaterial(),
//                    queryData.getSpecifications(),
//                    queryData.getUnitdosage(),
//                    queryData.getRequiredamount(),
//                    queryData.getUnit(),
//                    queryData.getStorage(),
//                    queryData.getDescription()
//            );
//            datalist.add(item);
//        }

        ArrayList<OrderItem> datalist = new ArrayList<>();

        // 取得資料清單
        ArrayList<HashMap<String, String>> rawDataList = QueryData.getInstance().getQueryData();

        for (HashMap<String, String> map : rawDataList) {
            OrderItem item = new OrderItem(
                    map.get("serial"),
                    map.get("material"),
                    map.get("specifications"),
                    map.get("unitdosage"),
                    map.get("requiredamount"),
                    map.get("unit"),
                    map.get("storage"),
                    map.get("description")
            );
            datalist.add(item);
        }
        adapter.updateData(datalist);
        thislevelRecyclerView.setAdapter(adapter);

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
}

