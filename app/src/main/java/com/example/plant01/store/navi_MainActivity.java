package com.example.plant01.store;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;


import com.example.plant01.R;
import com.example.plant01.garden.garden_MyGarden;
import com.example.plant01.home.home_HomeFragment;

import com.example.plant01.postpage.post_MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class navi_MainActivity extends AppCompatActivity {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference();
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private post_MainActivity mainActivity;
    private home_HomeFragment home_HomeFragment;
    private store_Category store_category;
    private garden_MyGarden garden_MyGarden;
    private FragmentPagerAdapter fragmentPagerAdapter;


    Button bt1, bt2;
    EditText name, title, review, price, inp;
    TextView result1, result2, result3, result4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity_main);

        bottomNavigationView = findViewById(R.id.store_Navigation); // 네비게이션 아이콘을 누를시 해당 화면으로 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NaviHome:
                        setFrag(0);
                        break;
                    case R.id.NaviPlant:
                        setFrag(1);
                        break;
                    case R.id.NaviBoard:
                        setFrag(2);
                        break;
                    case R.id.NaviStore:
                        setFrag(3);
                        break;


                }
                return true;
            }
        });
        home_HomeFragment = new home_HomeFragment();
        mainActivity = new post_MainActivity();
        store_category = new store_Category();
        garden_MyGarden = new garden_MyGarden();
        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택

    }

    //프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.store_Frame, home_HomeFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.store_Frame, garden_MyGarden);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.store_Frame, mainActivity);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.store_Frame, store_category);
                ft.commit();
                break;

        }
    }
}