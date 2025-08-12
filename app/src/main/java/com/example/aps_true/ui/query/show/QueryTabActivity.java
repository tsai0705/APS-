package com.example.aps_true.ui.query.show;

import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import com.example.aps_true.viewpager.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aps_true.R;

public class QueryTabActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tab;
    private QueryViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querytab);

        bindUI();
    }

    protected void bindUI(){
        viewPager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);

        adapter = new QueryViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        // 使用 TabLayoutMediator 綁定 TabLayout 和 ViewPager2，並加上 contentDescription
        new TabLayoutMediator(tab, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("前關製令");
                        break;
                    case 1:
                        tab.setText("本階製令");
                        break;
                    case 2:
                        tab.setText("後關製令");
                        break;
                    case 3:
                        tab.setText("裝配製令");
                        break;
                    case 4:
                        tab.setText("銷售訂單");
                        break;
                }
            }
        }).attach();
    }
}