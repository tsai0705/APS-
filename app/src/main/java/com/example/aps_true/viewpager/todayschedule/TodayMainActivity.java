package com.example.aps_true.viewpager.todayschedule;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TodayMainActivity extends AppCompatActivity {
    private TextView numberTextView,number2TextView,number3TextView,number4TextView,timeTextView
            ,sumTextView,timestartTextView,timeendTextView,groupTextView,statusTextView;
    private ImageButton backButton;
    private ViewPager2 viewPager;
    private TabLayout tab;
    private TodayViewPagerAdapter adapter;
    private TabData tabData = TabData.getInstance(); // 呼叫類別的靜態方法產生單例
    private TextView usernameTextview;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_todaymain);

        bindUI();
    }

    protected void bindUI(){
        //用來高效組合多行或多段字串，最後用 sb.toString() 取得完整內容
        StringBuilder sb = new StringBuilder();

        usernameTextview = findViewById(R.id.todaymain_username_tv);
        usernameTextview.setText(loginData.getName());

        numberTextView = findViewById(R.id.todaymain_number_tv);
        number2TextView = findViewById(R.id.todaymain_number2_tv);
        number3TextView = findViewById(R.id.todaymain_number3_tv);
        number4TextView = findViewById(R.id.todaymain_number4_tv);
        sumTextView = findViewById(R.id.todaymain_sum_tv);
        timeTextView = findViewById(R.id.todaymain_time_tv);
        timestartTextView = findViewById(R.id.todaymain_time_start_tv);
        timeendTextView = findViewById(R.id.todaymain_time_end_tv);
        groupTextView = findViewById(R.id.todaymain_group_tv);
        statusTextView = findViewById(R.id.todaymain_status_tv);

        backButton = findViewById(R.id.todaymain_back_ibtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);

        adapter = new TodayViewPagerAdapter(this);
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
        numberTextView.setText(getResources().getStringArray(R.array.tab_number)[choose]);
        number2TextView.setText(getResources().getStringArray(R.array.tab_number2)[choose]);
        number3TextView.setText(getResources().getStringArray(R.array.tab_number3)[choose]);
        number4TextView.setText(getResources().getStringArray(R.array.tab_number4)[choose]);
        sumTextView.setText(getResources().getStringArray(R.array.tab_sum)[choose]);
        timeTextView.setText(getResources().getStringArray(R.array.tab_time)[choose]);
        timestartTextView.setText(getResources().getStringArray(R.array.tab_timestart)[choose]);
        timeendTextView.setText(getResources().getStringArray(R.array.tab_timeend)[choose]);
        groupTextView.setText(getResources().getStringArray(R.array.tab_group)[choose]);
        statusTextView.setText(getResources().getStringArray(R.array.tab_status)[choose]);

        // 狀態顏色
        if (choose == 0) {
            statusTextView.setTextColor(Color.rgb(255, 0, 0));
        } else {
            statusTextView.setTextColor(Color.rgb(0, 128, 0));
        }
    }
}
