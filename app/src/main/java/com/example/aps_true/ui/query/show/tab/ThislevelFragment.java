package com.example.aps_true.ui.query.show.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.viewpager2.widget.ViewPager2;
import com.example.aps_true.viewpager.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aps_true.R;

//本階
public class ThislevelFragment extends Fragment {
    public ThislevelFragment() {
        // 必須的空建構子
    }

    private ImageButton rightButton,leftButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thislevel, container, false);

        rightButton = view.findViewById(R.id.thislevel_right_ibtn);
        leftButton = view.findViewById(R.id.thislevel_left_ibtn);

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

