package com.example.plant01.store;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.plant01.R;
import com.google.android.material.tabs.TabLayout;


public class store_Category extends Fragment {

    int i=0;
    ViewPager viewPager;
    private store_Main category1;
    private store_RecommendGoods category2;
    private ImageButton btn_search;
    public store_Category(){
    }
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.store_activity_sub, container, false);

        // 뷰페이저 세팅
        ViewPager viewPager = view.findViewById(R.id.store_ViewPager);
        fragmentPagerAdapter = new store_CategoryAdapter(getChildFragmentManager());

        TabLayout tabLayout = view.findViewById(R.id.store_Tabla);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btn_search = view.findViewById(R.id.store_Search);
        btn_search.setOnClickListener(new View.OnClickListener() { // 검색 버튼 눌러서 검색페이지 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), store_Search.class);
                startActivity(intent);
            }
        });
        return view;
    }
}