package com.example.aps_true.ui.query.show.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aps_true.R;

public class QianguanFragment extends Fragment {
    public QianguanFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qianguan, container, false);

        rightButton = view.findViewById(R.id.qianguan_right_ibtn);

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ThislevelFragment.class);
                startActivity(intent);
            }
        });

        // 回傳 view
        return view;
    }
}