package com.example.aps_true.viewpager.todayschedule;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.graphics.Color;
import androidx.viewpager2.widget.ViewPager2;

import com.example.aps_true.ui.query.show.QueryViewPagerAdapter;
import com.example.aps_true.viewpager.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aps_true.R;

public class TodayMainActivity extends AppCompatActivity {
    private TextView numberTextView,number2TextView,number3TextView,number4TextView,timeTextView
            ,sumTextView,timestartTextView,timeendTextView,groupTextView,statusTextView;
    private ImageButton backButton;
    private ViewPager2 viewPager;
    private TabLayout tab;
    private QueryViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todaymain);

        bindUI();
    }

    protected void bindUI(){

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
        if (choose == 0){

            numberTextView.setText("1MO1812040071");
            number2TextView.setText("1SO1811270009");
            number3TextView.setText("M1-ATN260011-1");
            number4TextView.setText("ATN260011 垃圾筒系統櫃門片0.8*613.7*236.3mm-沖床組(6折)");
            sumTextView.setText("生產數量：3");
            timeTextView.setText("預計上線：2018-12-05");
            timestartTextView.setText("計劃開始：15:30");
            timeendTextView.setText("計劃結束：15:45");
            groupTextView.setText("一群-沖床");
            statusTextView.setText("結案");
            statusTextView.setTextColor(Color.rgb(255, 0, 0));

        } else if (choose == 1) {

            numberTextView.setText("1MO1812040031");
            number2TextView.setText("1SO1811270009");
            number3TextView.setText("F260011ATN-2");
            number4TextView.setText("ATN260011  系統櫃(垃圾筒) -抽屜+垃圾筒固定片*4pcs");
            sumTextView.setText("生產數量：3");
            timeTextView.setText("預計上線：2018-12-06");
            timestartTextView.setText("計劃開始：08:00");
            timeendTextView.setText("計劃結束：08:05");
            groupTextView.setText("一群-點焊");
            statusTextView.setText("生效");
            statusTextView.setTextColor(Color.rgb(0, 128, 0));

        }else if (choose == 2) {

            numberTextView.setText("1MO1812040025");
            number2TextView.setText("1SO1811270009");
            number3TextView.setText("J1-EP340T-F260011ATN-2");
            number4TextView.setText("ATN260011  系統櫃(垃圾筒) -抽屜+垃圾筒固定片*4pcs");
            sumTextView.setText("生產數量：3");
            timeTextView.setText("預計上線：2018-12-07");
            timestartTextView.setText("計劃開始：09:30");
            timeendTextView.setText("計劃結束：09:50");
            groupTextView.setText("一群-塗裝");
            statusTextView.setText("生效");
            statusTextView.setTextColor(Color.rgb(0, 128, 0));

        }else if (choose == 3) {

            numberTextView.setText("1MO1812040005");
            number2TextView.setText("1SO1811270009");
            number3TextView.setText("ATN260011-06");
            number4TextView.setText("EP338T砂漆淺灰/EP340T砂漆灰  系統櫃組合--26”下箱垃圾桶櫃");
            sumTextView.setText("生產數量：3");
            timeTextView.setText("預計上線：2018-12-05");
            timestartTextView.setText("計劃開始：08:00");
            timeendTextView.setText("計劃結束：08:05");
            groupTextView.setText("一群-裝配");
            statusTextView.setText("生效");
            statusTextView.setTextColor(Color.rgb(0, 128, 0));

        }else{

            numberTextView.setText("1SO1811270009");
            number2TextView.setText(" ");
            number3TextView.setText("客戶名稱：(M1315) MATADOR  GmbH");
            number4TextView.setText("客戶訂單：6003028");
            sumTextView.setText(" ");
            timeTextView.setText("業務人員：(M3049) 嚴卉婷");
            timestartTextView.setText(" ");
            timeendTextView.setText(" ");
            groupTextView.setText(" ");
            statusTextView.setText("生效");
            statusTextView.setTextColor(Color.rgb(0, 128, 0));

        }
    }
}
