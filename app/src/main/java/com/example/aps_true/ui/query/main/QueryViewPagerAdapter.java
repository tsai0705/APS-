package com.example.aps_true.ui.query.main;

import android.os.Bundle;

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
    private String itemId;
    private String soId;

    //初始化
    public QueryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String itemId, String soId) {
        super(fragmentActivity);
        this.itemId = itemId;
        this.soId = soId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle args = new Bundle();
        args.putString("ITEM_ID", itemId);
        args.putString("SO_ID", soId);

        Fragment fragment;

        if (position == 0){
            fragment = new QianguanFragment();
        }
        else if (position == 1){
            fragment = new ThislevelFragment();
        }else if (position == 2){
            fragment = new HouguanFragment();
        }else if (position == 3){
            fragment = new AssemblyFragment();
        }
        else {
            fragment = new SaleFragment();
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}