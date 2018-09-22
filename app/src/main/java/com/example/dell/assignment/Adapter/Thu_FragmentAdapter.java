package com.example.dell.assignment.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dell.assignment.UI.KhoanThu_Fragment;
import com.example.dell.assignment.UI.LoaiThu_Fragment;

public class Thu_FragmentAdapter extends FragmentPagerAdapter {

    public Thu_FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhoanThu_Fragment();
            case 1:
                return new LoaiThu_Fragment();
            default:
                return new KhoanThu_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Khoản Thu";
            case 1:
                return "Loại Thu";
            default:
                return "Khoản Thu";
        }
    }
}
