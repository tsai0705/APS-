package com.example.aps_true.viewpager.todayschedule.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.R;
import com.example.aps_true.ui.query.show.tab.recyclerview.SaleAdapter;
import com.example.aps_true.ui.query.show.tab.recyclerview.SaleItem;

import java.util.ArrayList;

//銷售
public class TD_SaleFragment extends Fragment {
    public TD_SaleFragment() {
        // 必須的空建構子
    }

    private ImageButton leftButton;
    private RecyclerView saleRecyclerView;
    private SaleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale, container, false);

        leftButton = view.findViewById(R.id.sale_left_ibtn);
        saleRecyclerView = view.findViewById(R.id.sale_recyclerview_rcv);

        // RecyclerView 設定
        // Fragment 不是 Context 的子類，所以用requireContext()，不用.this
        saleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        saleRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new SaleAdapter(requireContext(), new ArrayList<>());

        ArrayList<SaleItem> datalist = new ArrayList<>();

        String[] product_number = getResources().getStringArray(R.array.sale_product_number);
        String[] customer_number = getResources().getStringArray(R.array.sale_customer_number);
        String[] specifications = getResources().getStringArray(R.array.sale_specifications);
        String[] quantity = getResources().getStringArray(R.array.sale_quantity);
        String[] unit = getResources().getStringArray(R.array.sale_unit);
        String[] notshipped = getResources().getStringArray(R.array.sale_notshipped);
        String[] predeliverydate = getResources().getStringArray(R.array.sale_predeliverydate);
        String[] note = getResources().getStringArray(R.array.sale_note);

        // 最小長度，避免陣列長度不一致
        int maxLength = Math.min(product_number.length,
                Math.min(customer_number.length,
                        Math.min(specifications.length,
                                Math.min(notshipped.length,
                                        Math.min(quantity.length,
                                                Math.min(unit.length,
                                                        Math.min(note.length, predeliverydate.length)
                                                )
                                        )
                                )
                        )
                )
        );

        for (int i = 0; i < maxLength; i++) {
            SaleItem item = new SaleItem(
                    String.valueOf(i+1),
                    product_number[i],
                    customer_number[i],
                    specifications[i],
                    quantity[i],
                    unit[i],
                    notshipped[i],
                    predeliverydate[i],
                    note[i]
            );
            datalist.add(item);
        }

        adapter.updateData(datalist);
        saleRecyclerView.setAdapter(adapter);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewpager);
                viewPager.setCurrentItem(3);
            }
        });

        // 回傳 view
        return view;
    }
}
