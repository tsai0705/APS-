package com.example.aps_true.ui.query.show;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aps_true.ui.query.show.tab.AssemblyActivity;
import com.example.aps_true.ui.query.show.tab.HouguanActivity;
import com.example.aps_true.ui.query.show.tab.QianguanActivity;
import com.example.aps_true.ui.query.show.tab.SaleActivity;
import com.example.aps_true.ui.query.show.tab.ThislevelActivity;

public class QueryViewPagerAdapter extends FragmentStateAdapter {
    //初始化
    public QueryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new QianguanActivity();
        }
        else if (position == 1){
            return new ThislevelActivity();
        }else if (position == 2){
            return new HouguanActivity();
        }else if (position == 3){
            return new AssemblyActivity();
        }
        else {
            return new SaleActivity();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}