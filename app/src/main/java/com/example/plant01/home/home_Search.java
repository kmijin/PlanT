package com.example.plant01.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.plant01.R;

public class home_Search extends AppCompatActivity {
    // 데이터를 넣은 리스트변수
    private AutoCompleteTextView search;
    private ImageButton store_searchback;
    Toolbar toolbar;
    ImageView home_searchcamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);

        toolbar = findViewById(R.id.home_SearchTololBar);

        setSupportActionBar(toolbar);
        setTitle("식물정보검색");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        home_searchcamera = (ImageView)findViewById(R.id.home_search_camera);

//        store_searchback = (ImageButton) findViewById(R.id.store_SearchBack);
//        store_searchback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        home_searchcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageFromGallery();
            }
        });


        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.home_SearchBar);


        search = findViewById(R.id.home_SearchBar);

        search.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(getApplicationContext(), home_SearchResult.class);
                    intent.putExtra("plantName", search.getText().toString());
                    startActivity(intent);//액티비티 띄우기
                    return true;
                }
                return false;
            }
        });
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


    /*--------------------------카메라 권한--------------------------------*/
    public void PickImageFromGallery() {
        Intent intent=new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12 && resultCode==RESULT_OK && data!=null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(home_Search.this, home_Search2.class);
            intent.putExtra("uri", bitmap);
            startActivity(intent);

        }
    }


}
