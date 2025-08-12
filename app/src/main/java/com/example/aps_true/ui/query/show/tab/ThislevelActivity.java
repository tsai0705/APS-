package com.example.aps_true.ui.query.show.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aps_true.R;

//本階
public class ThislevelActivity extends Fragment {
    public ThislevelActivity() {
        // 必須的空建構子
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thislevel, container, false);

        // 回傳 view
        return view;
    }
}

