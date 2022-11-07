package com.example.plant01.usersetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plant01.R;
import com.example.plant01.store.navi_MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class usersetting_LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mFirebaseAuth;  //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseFirestore firestore;
    private EditText mEtEmail, mEtPwd;
    private Button mBtnRegister, mBtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_intoRegister);
        mBtnLogin = findViewById(R.id.btn_login);

        mBtnRegister.setOnClickListener(onClickListener);
        mBtnLogin.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_intoRegister:
                    Intent intent = new Intent(usersetting_LoginActivity.this, usersetting_RegisterActivity.class);
                    startActivity(intent);
                    break;

                case R.id.btn_login:
                    login();
                    break;
            }
        }

    };



    private void login()
    {
        String strEmail = mEtEmail.getText().toString();
        String strPwd = mEtPwd.getText().toString();

        if(strEmail.length() >0 && strPwd.length() >0){
            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                Toast.makeText(usersetting_LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(usersetting_LoginActivity.this, navi_MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(usersetting_LoginActivity.this, "로그인 실패",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(usersetting_LoginActivity.this, "이메일 또는 비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }


    }
}