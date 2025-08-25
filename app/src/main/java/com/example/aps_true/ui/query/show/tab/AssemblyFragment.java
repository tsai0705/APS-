package com.example.aps_true.ui.query.show.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.aps_true.data.DataProvider;
import com.example.aps_true.data.QueryData;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter;
import com.example.aps_true.ui.query.show.tab.recyclerview.OrderItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.aps_true.R;
import java.util.ArrayList;
import java.util.HashMap;

//裝配
public class AssemblyFragment extends Fragment {
    public AssemblyFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton,leftButton;
    private RecyclerView assemblyRecyclerView;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_assembly, container, false);

        rightButton = view.findViewById(R.id.assembly_right_ibtn);
        leftButton = view.findViewById(R.id.assembly_left_ibtn);
        assemblyRecyclerView = view.findViewById(R.id.assembly_recyclerview_rcv);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        assemblyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        assemblyRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new OrderAdapter(requireContext(), new ArrayList<>());
        assemblyRecyclerView.setAdapter(adapter);

        ArrayList<OrderItem> datalist = new ArrayList<>();

        // 取得資料清單
        QueryData.getInstance().clearQueryData();
        DataProvider.setAssemblyData();
        ArrayList<HashMap<String, String>> rawDataList = QueryData.getInstance().getQueryData();

        for (HashMap<String, String> map : rawDataList) {
            // 把每個欄位拆分成陣列
            // 如果map.get() 不為 null，根據,分割，如果為null,new String[]{""};
            String[] serials = map.get("serial") != null ? map.get("serial").split(",") : new String[]{""};
            String[] materials = map.get("material") != null ? map.get("material").split(",") : new String[]{""};
            String[] specs = map.get("specifications") != null ? map.get("specifications").split(",") : new String[]{""};
            String[] unitdosages = map.get("unitdosage") != null ? map.get("unitdosage").split(",") : new String[]{""};
            String[] requireds = map.get("requiredamount") != null ? map.get("requiredamount").split(",") : new String[]{""};
            String[] units = map.get("unit") != null ? map.get("unit").split(",") : new String[]{""};
            String[] storages = map.get("storage") != null ? map.get("storage").split(",") : new String[]{""};
            String[] descriptions = map.get("description") != null ? map.get("description").split(",") : new String[]{""};

            // 找出最大的長度，一個一個比較
            int maxLength = Math.max(
                    serials.length, Math.max(materials.length,
                            Math.max(specs.length, Math.max(unitdosages.length,
                                    Math.max(requireds.length, Math.max(units.length,
                                            Math.max(storages.length, descriptions.length)))))));

            // 依照 index 逐一建立 OrderItem
            for (int i = 0; i < maxLength; i++) {
                OrderItem item = new OrderItem(
                        i < serials.length ? serials[i].trim() : "",
                        i < materials.length ? materials[i].trim() : "",
                        i < specs.length ? specs[i].trim() : "",
                        i < unitdosages.length ? unitdosages[i].trim() : "",
                        i < requireds.length ? requireds[i].trim() : "",
                        i < units.length ? units[i].trim() : "",
                        i < storages.length ? storages[i].trim() : "",
                        i < descriptions.length ? descriptions[i].trim() : ""
                );
                datalist.add(item);
            }
        }

        adapter.updateData(datalist);
        assemblyRecyclerView.setAdapter(adapter);

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
}
