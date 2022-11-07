package com.example.plant01.postpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.plant01.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Stack;

public class post_MainActivity extends Fragment {

    public post_MainActivity() {
    }

    TabLayout TabLayout;
    ViewPager2 MainViewPage;
    post_FragmentAdapter FragmentAdapter;
    private View.OnClickListener cl;
    public static Stack<Fragment> fragmentStack;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.post_main_fragment, container, false);
        view.findViewById(R.id.post_AddButton).setOnClickListener(onClickListener);

        return view;

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.post_AddButton:
                    Intent intent = new Intent(getActivity(), post_Write.class);
//                        myStartActivity(Post_write.class);
                    startActivity(intent);
                    break;
            }
        }
    };

        @Override
    public void onResume() {
        super.onResume();
            TabLayout = getView().findViewById(R.id.post_TabLayout);
            //게시글 담을 페이지
            MainViewPage = getView().findViewById(R.id.post_MainViewPage);
            //FragmentAdapter에서 카테고리 선택하여 페이지 이동
            FragmentManager fm = getFragmentManager();
            FragmentAdapter = new post_FragmentAdapter(getChildFragmentManager(), getLifecycle());
            MainViewPage.setSaveEnabled(false);
//            fm.beginTransaction().addToBackStack(null).commit();
            MainViewPage.setAdapter(FragmentAdapter);
            //카테고리 선택

            TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    MainViewPage.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            //카테고리 선택할 때마다 페이지 이동
            MainViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    TabLayout.selectTab(TabLayout.getTabAt(position));
                }
            });
            //드로워
            final DrawerLayout drawerLayout = (DrawerLayout) getView().findViewById(R.id.post_PostLayout);
            NavigationView navigationView = getView().findViewById(R.id.post_DrawerPage);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.btn_notice:
                            Intent intent1 = new Intent(getActivity(), post_DrawerNotice.class);
                            startActivity(intent1);
                            break;
//                        case R.id.btn_post:
//                            Intent intent2 = new Intent(getActivity(), home_Bell.class);
//                            startActivity(intent2);
//                            break;
                    }
                    return true;
                }
            });
            //햄버거 버튼 이미지 클릭시 작동
            cl = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.post_More: //햄버거 버튼
                            drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            };
            getView().findViewById(R.id.post_More).setOnClickListener(cl);
        }
}

