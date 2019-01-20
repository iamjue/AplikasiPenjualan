package com.iamjue.aplikasipenjualan.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iamjue.aplikasipenjualan.R;
import com.iamjue.aplikasipenjualan.fragment.FragmentDataCustomerView;
import com.iamjue.aplikasipenjualan.fragment.FragmentStokView;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE = 2;
    final Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super( fm );
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FragmentStokView.newInstance();
                break;
            case 1:
                fragment = FragmentDataCustomerView.newInstance();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString( R.string.tab_1 );
            case 1:
                return context.getResources().getString( R.string.tab_2 );
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE;

    }
}
