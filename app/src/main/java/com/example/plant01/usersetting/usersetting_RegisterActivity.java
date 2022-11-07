package com.example.plant01.usersetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class usersetting_RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mFirebaseAuth;  //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText mEtEmail,mEtPwd, mEtPh ,mEtPostcode ,mEtBirth, mEtNick, mEtAdr;
    private Button mBtnRegister;
    public  String UserGender;
    private RadioGroup mRgGender;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("PlanT");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        mBtnRegister = findViewById(R.id.btn_intoRegister);
        mBtnRegister.setOnClickListener(onClickListener);

        findViewById(R.id.btn_cancle).setOnClickListener(onClickListener);

//
//        mRgGender = (RadioGroup)findViewById(R.id.rg_gender);
//        int mEtGenderID = mRgGender.getCheckedRadioButtonId();
//        UserGender =((RadioButton)findViewById(mEtGenderID)).getText().toString();
//        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int i) {
//                RadioButton genderButton = (RadioButton) findViewById(i);
//                UserGender = genderButton.getText().toString();
//            }
//        });
        mEtNick = findViewById(R.id.et_nick);
        mEtPh =  findViewById(R.id.et_ph);
        mEtPostcode =  findViewById(R.id.et_postcode);
        mEtBirth = findViewById(R.id.et_birth);
        mEtAdr = findViewById(R.id.et_adr);



    }

    /*----------클릭이벤트-----------------------*/
    View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_intoRegister:
                    register();
                    break;
                case  R.id.btn_cancle:
                    Intent intent = new Intent(usersetting_RegisterActivity.this, usersetting_LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };



    private void register(){
        String strEmail = mEtEmail.getText().toString();
        String strPwd = mEtPwd.getText().toString();
        String strBirth = mEtBirth.getText().toString();
        String strNick = mEtNick.getText().toString();
        String strPost = mEtPostcode.getText().toString();
        String strPh = mEtPh.getText().toString();
        String strAdr = mEtAdr.getText().toString();
        Date strdt = Timestamp.now().toDate();

        /*-----성별 버튼을 String으로 가져오는 것------*/
        mRgGender = (RadioGroup)findViewById(R.id.rg_gender);
        int mEtGenderID = mRgGender.getCheckedRadioButtonId();
        UserGender =((RadioButton)findViewById(mEtGenderID)).getText().toString();
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                UserGender = genderButton.getText().toString();
            }
        });


        /*--------------------파이어베이스 회원가입 기능을 이용한 회원가입과, firestore에 정보저장------------------------------*/
        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(usersetting_RegisterActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    //현재 정보 저장
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();


                    usersetting_UserAccount account = new usersetting_UserAccount();
                    account.setIdToken(firebaseUser.getUid());
                    account.setUserEmail(firebaseUser.getEmail());
                    account.setUserPassword(strPwd);
                    account.setUserBirth(strBirth);
                    account.setUserPh(strPh);
                    account.setUserGender(UserGender);
                    account.setUserNick(strNick);
                    account.setUserPostalCode(strPost);
                    account.setJoinDate(strdt.toString());
                    account.setUserAdr(strAdr);


                    //setvalue 데이터베이스에 삽입하는 행위
                    mDatabaseRef.child("Users").child(firebaseUser.getUid()).setValue(account);
                    db.collection("Users").document(firebaseUser.getUid()).set(account);

                    Log.e("성공","성공");

                    Toast.makeText(usersetting_RegisterActivity.this,"회원가입 성공", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(usersetting_RegisterActivity.this,"회원가입실패", Toast.LENGTH_SHORT).show();
                    Log.e("실패","실패");

                }
            }
        });
    }


}