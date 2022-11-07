package com.example.plant01.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plant01.R;


public class store_RecommendGoods extends Fragment {

    private View view;
    private ImageButton btn_recommend;

    public static store_RecommendGoods newInstance() {
        store_RecommendGoods storeRecommendgoods = new store_RecommendGoods();
        return storeRecommendgoods;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.store_recommendgoods, container, false);


        btn_recommend = view.findViewById(R.id.store_RecommendBtn);
        btn_recommend.setOnClickListener(new View.OnClickListener() { // 추천상품에서 #초보집사 아이콘을 눌러 해당 카테고리 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), store_RecommendList.class);
                startActivity(intent);
            }
        });
        return view;
    }
}