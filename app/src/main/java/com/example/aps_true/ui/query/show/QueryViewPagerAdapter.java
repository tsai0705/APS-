package com.example.aps_true.ui.query.show;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aps_true.ui.query.show.tab.AssemblyFragment;
import com.example.aps_true.ui.query.show.tab.HouguanFragment;
import com.example.aps_true.ui.query.show.tab.QianguanFragment;
import com.example.aps_true.ui.query.show.tab.SaleFragment;
import com.example.aps_true.ui.query.show.tab.ThislevelFragment;

public class QueryViewPagerAdapter extends FragmentStateAdapter {
    //初始化
    public QueryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new QianguanFragment();
        }
        else if (position == 1){
            return new ThislevelFragment();
        }else if (position == 2){
            return new HouguanFragment();
        }else if (position == 3){
            return new AssemblyFragment();
        }
        else {
            return new SaleFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}