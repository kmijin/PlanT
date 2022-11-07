package com.example.plant01.store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class store_CategoryAdapter extends FragmentPagerAdapter {
    public store_CategoryAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //프레그먼트 교체를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return store_Main.newInstance();
            case 1 :
                return store_RecommendGoods.newInstance();
            case 2 :
                return store_RecommendGoods.newInstance();
            case 3 :
                return store_RecommendGoods.newInstance();
            case 4 :
                return store_RecommendGoods.newInstance();
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    // 상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "메인";
            case 1 :
                return "추천상품";
            case 2 :
                return "소형식물";
            case 3 :
                return "대형식물";
            case 4 :
                return "공기정화";

            default:
                return null;
        }
    }
}
