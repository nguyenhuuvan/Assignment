package com.example.dell.assignment.UI;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.assignment.Adapter.Chi_FragmentAdapter;
import com.example.dell.assignment.Adapter.Thu_FragmentAdapter;
import com.example.dell.assignment.R;

public class ChiActivity extends Fragment {
    private ViewPager viewPagerChi;
    private Chi_FragmentAdapter adapter;
    private TabLayout tabLayoutChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chi, container, false);
        viewPagerChi = view.findViewById(R.id.viewPagerChi);
        adapter = new Chi_FragmentAdapter(getActivity().getSupportFragmentManager());
        tabLayoutChi =view.findViewById(R.id.tablayoutChi);
        viewPagerChi.setAdapter(adapter);
        tabLayoutChi.setupWithViewPager(viewPagerChi);
        return view;
        //test
    }

}
