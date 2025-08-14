package com.example.aps_true.viewpager.todayschedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aps_true.viewpager.todayschedule.tab.TD_AssemblyFragment;
import com.example.aps_true.viewpager.todayschedule.tab.TD_SaleFragment;
import com.example.aps_true.viewpager.todayschedule.tab.TD_HouguanFragment;
import com.example.aps_true.viewpager.todayschedule.tab.TD_ThislevelFragment;
import com.example.aps_true.viewpager.todayschedule.tab.TD_QianguanFragment;

public class TodayViewPagerAdapter extends FragmentStateAdapter {
    //初始化
    public TodayViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new TD_QianguanFragment();
        }
        else if (position == 1){
            return new TD_ThislevelFragment();
        }else if (position == 2){
            return new TD_HouguanFragment();
        }else if (position == 3){
            return new TD_AssemblyFragment();
        }
        else {
            return new TD_SaleFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}