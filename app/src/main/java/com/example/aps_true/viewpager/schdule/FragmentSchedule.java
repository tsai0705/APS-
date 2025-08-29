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

import java.util.ArrayList;

public class FragmentSchedule extends Fragment {
    private RecyclerView resultRecyclerView;
    private ScheduleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule, container, false);

        resultRecyclerView = view.findViewById(R.id.schedule_result_rv);

        // RecyclerView 設定
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ScheduleAdapter(getContext(), new ArrayList<>());
        resultRecyclerView.setAdapter(adapter);

        loadData();
        return view;
    }

    protected void loadData() {
        ArrayList<ScheduleItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};
        String[] order_number = {"1MO1812040031", "1MO1812040030", "1MO1812040027", "1MO1812030024", "1MO1812030012", "1MO1812030010", "1MO1812040028", "1MO1812030023"};
        String[] source = {"1SO1811270009", "1SO1811270009", "1SO1811270010", "1SOA1811300002", "1SOA1811300001", "1SOA1811300001", "1SO1811270008", "1SOA1811300002"};
        String[] number = {"F260011ATN-2", "F260011ATN-1", "F260001-1", "F10318M-3A", "F10217M-5A", "F10217M-1", "F260001ATN-1A", "F10318M-2A"};
        String[] number3 = {"數量：3", "數量：3", "數量：30", "數量：10", "數量：10", "數量：10", "數量：6", "數量：70"};
        String[] number4 = {"結關日：2018-12-08"};
        String[] time = {"計劃開始：08:00","計劃開始：08:05","計劃開始：08:10","計劃開始：08:45","計劃開始：09:20","計劃開始：09:50","計劃開始：10:30","計劃開始：10:40"};
        for (int serial = 1; serial < 9; serial++) {
            String number2;
            if (serial == 1 || serial == 2 || serial == 3 || serial == 7) {
                number2 = "MATADOR";
            } else {
                number2 = "祥雲工具股份有限公司";
            }
            ScheduleItem item = new ScheduleItem(serial, order_number[serial - 1], source[serial - 1], number[serial - 1], number2, number3[serial - 1],
                    number4[0], time[serial - 1], process[0]);
            datalist.add(item);
        }
        adapter.updateData(datalist);
    }
}