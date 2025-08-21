package com.example.aps_true.ui.query.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;
import androidx.viewpager2.widget.ViewPager2;
import com.example.aps_true.data.TabData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aps_true.R;

import java.util.HashMap;

public class QueryTabActivity extends AppCompatActivity {
    private TextView numberTextView,number2TextView,number3TextView,number4TextView,timeTextView
                    ,sumTextView,timestartTextView,timeendTextView,groupTextView,statusTextView;
    private ImageButton backButton;
    private ViewPager2 viewPager;
    private TabLayout tab;
    private QueryViewPagerAdapter adapter;
    private TabData tabData = TabData.getInstance(); // 呼叫類別的靜態方法產生單例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querytab);
        bindUI();
    }

    protected void bindUI(){

        numberTextView = findViewById(R.id.querytab_number_tv);
        number2TextView = findViewById(R.id.querytab_number2_tv);
        number3TextView = findViewById(R.id.querytab_number3_tv);
        number4TextView = findViewById(R.id.querytab_number4_tv);
        sumTextView = findViewById(R.id.querytab_sum_tv);
        timeTextView = findViewById(R.id.querytab_time_tv);
        timestartTextView = findViewById(R.id.querytab_time_start_tv);
        timeendTextView = findViewById(R.id.querytab_time_end_tv);
        groupTextView = findViewById(R.id.querytab_group_tv);
        statusTextView = findViewById(R.id.querytab_status_tv);

        backButton = findViewById(R.id.querytab_back_ibtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);

        adapter = new QueryViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        // 添加頁面切換監聽器
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // 當頁面被選中時調用 Choose 方法
                Choose(position);
            }
        });

        // 使用 TabLayoutMediator 綁定 TabLayout 和 ViewPager2，只設置標題
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

        // 初始化顯示當前頁面的數據
        Choose(viewPager.getCurrentItem());
    }

    protected void Choose(Integer choose){
        if (!tabData.getData().isEmpty()) {
            HashMap<String, String> data = tabData.getData().get(choose);
            numberTextView.setText(data.get("number"));
            number2TextView.setText(data.get("number2"));
            number3TextView.setText(data.get("number3"));
            number4TextView.setText(data.get("number4"));
            sumTextView.setText(data.get("sum"));
            timeTextView.setText(data.get("time"));
            timestartTextView.setText(data.get("time_start"));
            timeendTextView.setText(data.get("time_end"));
            groupTextView.setText(data.get("group"));
            statusTextView.setText(data.get("status"));

            // 狀態顏色
            if (choose == 0) {
                statusTextView.setTextColor(Color.rgb(255, 0, 0));
            } else {
                statusTextView.setTextColor(Color.rgb(0, 128, 0));
            }
        }
    }
}
