package com.example.plant01.usersetting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;

public class usersetting_UserSetting extends AppCompatActivity {

    private ImageView btnCamera, btnGallery;
    private RoundedImageView profileImageView;
    private RelativeLayout loaderLayout;
    private RelativeLayout buttonBackgroundLayout;
    private Button update;
    private String profilePath;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    Toolbar toolbar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmap;
    private byte[] data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_usersetting);
        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        setTitle("회원정보");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        profileImageView = (RoundedImageView) findViewById(R.id.img_setting_user);
        update = (Button)findViewById(R.id.btn_setting);
        update.setOnClickListener(onClickListener);
        btnGallery = (ImageView) findViewById(R.id.btn_gallery);

        profileImageView.setOnClickListener(onClickListener);
        showuser();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_setting_user:
                    showcameraDialog();
                    break;
                case R.id.btn_camera:
                    opencamera();
                    break;
                case R.id.btn_setting:
                    update();
                    Toast.makeText(usersetting_UserSetting.this, "수정완료", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

   

    /*-----------프로필사진 누르면 뜨는 다이어로그 -----------------*/
    public void showcameraDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(usersetting_UserSetting.this, R.style.AlterDialogTheme);
        View view = LayoutInflater.from(usersetting_UserSetting.this).inflate(R.layout.home_dialog_camera
                , (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);
        AlertDialog alterDialog = builder.create();
        if (alterDialog.getWindow() != null) {
            alterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        btnCamera = (ImageView) view.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(onClickListener);
        alterDialog.show();

    }

    /*----------카메라 열기-----------------*/
    public void opencamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    /*----------------회원정보 가져오기 --------------------------*/
    public void showuser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
////        RoundedImageView settinguserprofile = findViewById(R.id.img_setting_user);
        RoundedImageView profileImage = findViewById(R.id.img_setting_user);
        TextView settinguseremail = findViewById(R.id.et_stemail);
        TextView settingusernick = findViewById(R.id.et_stnick);
        EditText settinguserpwd = findViewById(R.id.et_stpwd);
        EditText settingph = findViewById(R.id.et_stph);
        EditText settingpostal = findViewById(R.id.et_stpostcode);
        EditText settingadr = findViewById(R.id.et_stadr);
        EditText settingbirth = findViewById(R.id.et_stbirth);

        db.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                String userEmailtxt = (String) document.get("userEmail");
                String userImgtxt = (String) document.get("userImg");
                String userBirthtxt = (String) document.get("userBirth");
                String userNicktxt = (String) document.get("userNick");
                String userPhtxt = (String) document.get("userPh");
                String userpostaltxt = (String) document.get("userPostalCode");
                String userAdrtxt = (String) document.get("userAdr");

                Log.e("asdjijfidsjdlksd", userEmailtxt + userImgtxt);
//
                settinguseremail.setText(userEmailtxt);
                settingusernick.setText(userNicktxt);
                settingph.setText(userPhtxt);
                settingbirth.setText(userBirthtxt);
                if(userImgtxt != null){
                    Glide.with(getApplicationContext())
                            .load(Uri.parse(userImgtxt))
                            .into(profileImage);
                }
                if (userAdrtxt != null || userpostaltxt != null){

                    settingadr.setText(userAdrtxt);
                    settingpostal.setText(userpostaltxt);
                }

            }
        });
    }



    /*-------------카메라 사진 보여주기---------------------*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            profilePath = data.getStringExtra("profilePath");
            //파이어스토리지와 연결



            Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data1 = baos.toByteArray();

            profileImageView.setImageBitmap(bitmap);

            
            

        }
    }


    private void update() {
//        profileImageView.setImageBitmap(bitmap);
        FirebaseStorage storage = FirebaseStorage.getInstance();
//            profileImageView = (RoundedImageView) findViewById(R.id.img_setting_user);
        //현재 유저 받아오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //파이어스토어와 연결
        db = FirebaseFirestore.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child("userprofile/"+user.getUid()+"/profile.jpg");
        UploadTask uploadTask = mountainImagesRef.putBytes(data1);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("이미지주소", uri.toString());
                        db.collection("Users").document(user.getUid())
                                .update("userImg", uri.toString());
                    }
                });
            }
        });
    }

}