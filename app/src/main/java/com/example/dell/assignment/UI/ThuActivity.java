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
import android.widget.TextView;

import com.example.dell.assignment.Adapter.Thu_FragmentAdapter;
import com.example.dell.assignment.R;

public class ThuActivity extends Fragment {
   private ViewPager viewPagerThu;
   private Thu_FragmentAdapter adapter;
   private TabLayout tabLayoutThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_thu, container, false);
        viewPagerThu = view.findViewById(R.id.viewPagerThu);
        adapter = new Thu_FragmentAdapter(getActivity().getSupportFragmentManager());
        tabLayoutThu =view.findViewById(R.id.tablayoutThu);
        viewPagerThu.setAdapter(adapter);
        tabLayoutThu.setupWithViewPager(viewPagerThu);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
