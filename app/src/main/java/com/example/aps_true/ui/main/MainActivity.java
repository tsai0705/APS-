package com.example.aps_true.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.viewpager.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tab;
    private TextView name;
    private FragmentViewPagerAdapter adapter;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bindUI();
    }

    protected void bindUI(){
        viewPager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);
        name = findViewById(R.id.main_username_tv);

        adapter = new FragmentViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        name.setText(loginData.getName());

        // 使用 TabLayoutMediator 綁定 TabLayout 和 ViewPager2，並加上 contentDescription
        new TabLayoutMediator(tab, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("生產排程");
                        tab.setContentDescription("切換到生產排程分頁");
                        break;
                    case 1:
                        tab.setText("當日進度表");
                        tab.setContentDescription("切換到當日進度表分頁");
                        break;
                    case 2:
                        tab.setText("訊息通知");
                        tab.setContentDescription("切換到訊息通知分頁");
                        break;
                }
            }
        }).attach();
    }
}