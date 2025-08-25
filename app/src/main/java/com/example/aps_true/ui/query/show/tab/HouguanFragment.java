package com.example.aps_true.ui.query.show.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.data.QueryData;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.aps_true.R;
import java.util.ArrayList;
import java.util.HashMap;

public class HouguanFragment extends Fragment {
    public HouguanFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton,leftButton;
    private RecyclerView houguanRecyclerView;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_houguan, container, false);

        rightButton = view.findViewById(R.id.houguan_right_ibtn);
        leftButton = view.findViewById(R.id.houguan_left_ibtn);
        houguanRecyclerView = view.findViewById(R.id.houguan_recyclerview_rcv);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        houguanRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        houguanRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter(requireContext(), new ArrayList<>());
        houguanRecyclerView.setAdapter(adapter);

        ArrayList<OrderItem> datalist = new ArrayList<>();

        String[] material = getResources().getStringArray(R.array.houguan_material);
        String[] specifications = getResources().getStringArray(R.array.houguan_specifications);
        String[] unitdosage = getResources().getStringArray(R.array.houguan_unitdosage);
        String[] requiredamount = getResources().getStringArray(R.array.houguan_requiredamount);
        String[] unit = getResources().getStringArray(R.array.houguan_unit);
        String[] storage = getResources().getStringArray(R.array.houguan_storage);
        String[] description = getResources().getStringArray(R.array.houguan_description);

        // 最小長度，避免陣列長度不一致
        int maxLength = Math.min(
                Math.min(material.length, specifications.length),
                Math.min(
                        Math.min(unitdosage.length, requiredamount.length),
                        Math.min(unit.length, Math.min(storage.length, description.length))
                )
        );

        // 依照 index 逐一建立 OrderItem
        for (int i = 1; i < maxLength; i++) {
            OrderItem item = new OrderItem(
                    String.valueOf(i),
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
        houguanRecyclerView.setAdapter(adapter);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(3);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(1);
            }
        });

        // 回傳 view
        return view;
    }
}


