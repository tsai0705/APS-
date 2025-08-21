package com.example.aps_true.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import com.example.aps_true.R;
import com.example.aps_true.ui.query.main.QueryActivity;
import com.example.aps_true.ui.query.main.SettingActivity;

// 生產排程
public class FragmentProduction extends Fragment {
    public FragmentProduction() {
        // 必須的空建構子
    }

    private Button queryButton,settingButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 膨脹 fragment 的佈局檔，並存成 view
        View view = inflater.inflate(R.layout.activity_production, container, false);

        queryButton = view.findViewById(R.id.production_Query_btn);
        settingButton = view.findViewById(R.id.production_setting_btn);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QueryActivity.class);
                startActivity(intent);
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        // 回傳 view
        return view;
    }
}