package com.example.plant01.garden;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class garden_AddPlants extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mName , mLocation, mDate;
    private ProgressBar progressBar2;
    private ImageView mProfile;
    private Button mSaveBtn, mShowBtn;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Myplants");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;
    private FirebaseFirestore db;
    private String uName, uLocation, uDate , uType, uId, uProfileUri, listsize, storeuri;
    private Spinner spinner;

    String myplantid = UUID.randomUUID().toString();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference list = reference.child("Myplants/"+user.getUid()+"/");
        list.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<StorageReference> items = listResult.getItems();
                listsize = String.valueOf(items.size()+1);
                Log.e("listsize", listsize);
            }
        });

        mProfile = (ImageView) findViewById(R.id.iv_PlantsProfile);
        mName = (EditText) findViewById(R.id.edit_name);
        mLocation = (EditText) findViewById(R.id.edit_location);
        mDate = (EditText) findViewById(R.id.edit_date);
        mSaveBtn = (Button) findViewById(R.id.save_btn);
//        mShowBtn = (Button) findViewById(R.id.showall_btn);
        spinner = (Spinner) findViewById(R.id.spinner);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);
        db= FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //이미지 갤러리에서 가져오기
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        Bundle bundle = getIntent().getExtras();
//        uId = bundle.getString("uId");
        if (bundle != null){
            mSaveBtn.setText("Update");
            uName = bundle.getString("uName");
            uId = bundle.getString("uId");
            Log.e("uID", uId);
            uLocation = bundle.getString("uLocation");
            uDate = bundle.getString("uDate");
            uProfileUri = bundle.getString("uProfileUri");
            mName.setText(uName);
            mLocation.setText(uLocation);
            mDate.setText(uDate);
            uProfileUri = mProfile.toString();

        }else{
            mSaveBtn.setText("Save");// 업데이트할 데이터가 없을 경우
        }


//        mShowBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MyMainActivity.this, Context.class));
//            }
//        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//                Log.e("storeuri", storeuri);
//                if (imageUri != null){
//
//                }else{
//                    Toast.makeText(MyMainActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
                String name = mName.getText().toString();
                String location = mLocation.getText().toString();
                String date = mDate.getText().toString();
                String profileUri = storeuri;
                String userid = user.getUid();
                String type = spinner.getSelectedItem().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    String id  = uId;
                    updateToFireStore(type, name, location, date, profileUri);

                }else{

                    saveToFireStore(myplantid, type, name, location, date, profileUri, userid, null);

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            mProfile.setImageURI(imageUri);

        }
    }

    //수정 기능
    private void updateToFireStore( String type, String name, String location, String date, String profileUri){

        db.collection("Myplants").document(uId).update("name", name, "location",location, "date", date, "profileUri", profileUri)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadToFirebase(imageUri);
                        Toast.makeText(garden_AddPlants.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(garden_AddPlants.this, "Error : updating document", Toast.LENGTH_SHORT).show();
            }
        });
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            uploadToFirebase(imageUri);
//                            Toast.makeText(MyMainActivity.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(MyMainActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MyMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //저장
    private void saveToFireStore(String id , String type, String name , String location, String date, String profileUri, String userid, String recentDate){

        if (!name.isEmpty() && !location.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , myplantid);
            map.put("profileUri" , null);
            map.put("type" , type);
            map.put("name" , name);
            map.put("location" , location);
            map.put("date" , date);
            map.put("userID", userid);
            map.put("recentWater", null);

            db.collection("Myplants").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                if( imageUri != null){
                                    uploadToFirebasefirst(imageUri);
                                }else{
                                    Toast.makeText(garden_AddPlants.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                                    finish();

                                }


                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(garden_AddPlants.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });
        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }

    //이미지 업로드
    private void uploadToFirebase(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.e("listsize2", listsize);

        StorageReference fileRef = reference.child("Myplants/"+user.getUid()+"/file"+listsize+".jpg");
        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                            Log.e("이미지주소", uri.toString());
                            storeuri = uri.toString();
                            db.collection("Myplants").document(uId)
                                    .update("profileUri", uri.toString());
                         Toast.makeText(garden_AddPlants.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                         finish();
                        }

                    });
            }
        });

    }
    private void uploadToFirebasefirst(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.e("listsize2", listsize);

        StorageReference fileRef = reference.child("Myplants/"+user.getUid()+"/file"+listsize+".jpg");
        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("이미지주소", uri.toString());
                        storeuri = uri.toString();
                        db.collection("Myplants").document(myplantid)
                                .update("profileUri", uri.toString());
                        Toast.makeText(garden_AddPlants.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

//        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        Model model = new Model(uri.toString());
//                        String modelId = root.push().getKey();
//                        root.child(modelId).setValue(model);
//                        progressBar2.setVisibility(View.INVISIBLE);
//
//                        Toast.makeText(MyMainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar2.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar2.setVisibility(View.INVISIBLE);
//                Toast.makeText(MyMainActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private String getfileExtension(Uri mUri){
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(mUri));
//    }
//}
