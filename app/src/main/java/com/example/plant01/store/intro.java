package com.example.plant01.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.plant01.R;
import com.example.plant01.usersetting.usersetting_LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class intro extends Activity {

    Handler handler = new Handler();
    FirebaseAuth firebaseAuth;
    Runnable r = new Runnable() {
        @Override
        public void run() {
// 4초뒤에 다음화면(PostMainActivity)으로 넘어가기 Handler 사용
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Intent intent = new Intent(getApplicationContext(), navi_MainActivity.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish(); // Activity 화면 제거
            }else {
                Intent intent = new Intent(getApplicationContext(), usersetting_LoginActivity.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish(); // Activity 화면 제거
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro); // xml과 java소스를 연결

    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 1500); // 1.5초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }
}