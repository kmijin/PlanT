package com.example.plant01.postpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class post_Write extends AppCompatActivity {
    private EditText Title, Content;
    private Button AddImage, AddVideo, PostBtn;
    private ImageButton BackBtn;
    private FirebaseFirestore db;
    private Spinner Spinner;
    private String profilePath, uritxt, listsize;
    private FirebaseStorage storage;
    private Uri galleryUri;
    private byte[] data1;
    private ImageView UploadImg;
    String postID = UUID.randomUUID().toString();
    View.OnClickListener cl;
    private Object Post_write;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_write);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //파이어스토어와 연결
        StorageReference storageRef = storage.getReference();
        StorageReference list = storageRef.child("Myplants/"+user.getUid()+"/");
        list.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<StorageReference> items = listResult.getItems();
                listsize = String.valueOf(items.size()+1);
                Log.e("listsize", listsize);
            }
        });

        Title = (EditText) findViewById(R.id.post_Title);
        Content = (EditText) findViewById(R.id.post_Content);
        AddImage = (Button) findViewById(R.id.post_AddImage);
        AddVideo = (Button) findViewById(R.id.post_AddVideo);
        PostBtn = (Button) findViewById(R.id.post_PostBtn);
        BackBtn = (ImageButton) findViewById(R.id.post_BackBtn);
        Spinner = (Spinner) findViewById(R.id.post_Spinner);
        UploadImg = (ImageView) findViewById(R.id.post_UploadImg);

        db = FirebaseFirestore.getInstance();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        AddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setType("video/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent1);
            }
        });

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String title = Title.getText().toString();
                String contents = Content.getText().toString();
//                String userid
                String id = user.getUid();
                String board = Spinner.getSelectedItem().toString();
                Date timestamp = Timestamp.now().toDate();
                String contentimg = null;

                saveToFireStore(id, title, contents, board, timestamp, contentimg);
                onBackPressed();
            }

            private void saveToFireStore(String userid, String title, String contents, String board, Date timestamp, String contentimg) {

                if (!title.isEmpty() && !contents.isEmpty()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userID", userid);
                    map.put("title", title);
                    map.put("content", contents);
                    map.put("board", board);
                    map.put("postDate", timestamp);
                    map.put("contentImg", contentimg);

                    db.collection("Post").document(postID).set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(post_Write.this, "Post Data!!", Toast.LENGTH_SHORT).show();
                                        update();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(post_Write.this, "Failed !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            profilePath = data.getStringExtra("profilePath");
            //파이어스토리지와 연결
            UploadImg.setVisibility(View.VISIBLE);
            galleryUri = data.getData();
//            Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            data1 = baos.toByteArray();

            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();
                UploadImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
//        profileImageView.setImageBitmap(bitmap);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //파이어스토어와 연결
        db = FirebaseFirestore.getInstance();
        StorageReference storageRef = storage.getReference();
//        Log.e("timestamp",Timestamp.n;
        StorageReference mountainImagesRef = storageRef.child("PostImg/"+user.getUid()+"/file"+listsize+".jpg");
        if(galleryUri != null){
            UploadTask uploadTask = mountainImagesRef.putFile(galleryUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("이미지주소", uri.toString());
//                            storeuri = uri.toString();
                            db.collection("Post").document(postID)
                                    .update("contentImg", uri.toString());
                        }

                    });
                }
            });

        }


    }
}





//        cl = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.img:
//                        //이미지를 갤러리에서 가져옴
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                        intent.setType("image/*");
//                        startActivity(intent);
//                        break;
//                    case R.id.vod:
//                        //동영상을 갤러리에서 가져옴
//                        Intent intent1 = new Intent();
//                        intent1.setType("video/*");
//                        intent1.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivity(intent1);
//                        break;
//                    case R.id.postbtn:
//                        String title = Title.getText().toString();
//                        String contents = Contents.getText().toString();
//                        String id = UUID.randomUUID().toString();
//
//                        saveToFireStore(id, title, contents);
////                        finish();
//                        break;
////                    case R.id.post_back:
////                        finish();
////                        break;
//                }
//
//            }
//
//            private void saveToFireStore(String userid, String title, String contents) {
//
//                if (!title.isEmpty() && !contents.isEmpty()) {
//                    HashMap<String, Object> map = new HashMap<>();
//                    map.put("UserId", userid);
//                    map.put("Title", title);
//                    map.put("Contents", contents);
//
//                    db.collection("WritePosts").document(userid).set(map)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(Post_write.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Post_write.this, "Failed !!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        };
//        Image.setOnClickListener(cl);
//        Video.setOnClickListener(cl);
//        Post.setOnClickListener(cl);
//        back.setOnClickListener(cl);
//    }
//}



