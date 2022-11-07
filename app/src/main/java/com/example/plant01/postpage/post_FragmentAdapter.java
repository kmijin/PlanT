package com.example.plant01.postpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class post_FragmentAdapter extends FragmentStateAdapter {
//    public post_FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }

    //카테고리와 연결
    public post_FragmentAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }
    //카테고리 이동부분
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new post_RealTime();
            case 1:
                return new post_Best();
            case 2:
                return new post_Free();
            case 3:
                return new post_Info();
//            default:
//                return null;
        }

        return createFragment(0);
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
