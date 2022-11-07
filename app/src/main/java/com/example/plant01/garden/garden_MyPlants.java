package com.example.plant01.garden;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class garden_MyPlants extends AppCompatActivity {

    TextView tvType, tvName, tvLocation, tvDate, tvWaterDate;
    ImageView ivPlants;
    Button btnBack2, btnUpdateWater, btnOnWater;
    String name, location, date, profile;
    String myplantid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference myplantsdata = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_my_plants);
        SharedPreferences sharedPref = getSharedPreferences("lemubitApp", Context.MODE_PRIVATE);
        String valueState = sharedPref.getString("lemubit", "Not Activity");

        btnBack2 = findViewById(R.id.btnBack2);
        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvDate = (TextView) findViewById(R.id.tvDate);
        ivPlants = (ImageView) findViewById(R.id.ivPlants);
        tvWaterDate= (TextView)findViewById(R.id.tvWaterDate);
        btnUpdateWater = (Button)findViewById(R.id.btnUpdateWater);
        btnOnWater = (Button)findViewById(R.id.btnOnWater);
        tvType = (TextView) findViewById(R.id.tvType);
        Intent intent = getIntent();
        myplantid = intent.getStringExtra("myplantid");
        Log.e("String myplant", myplantid);
        getmyplantinfo();
//        tvName.setText(intent.getStringExtra("myplantid"));
//        tvLocation.setText(intent.getStringExtra("location"));
//        tvDate.setText(intent.getStringExtra("date"));
//        ivPlants.setImageURI(Uri.parse(intent.getStringExtra("profile")));

        btnUpdateWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date timestamp = Timestamp.now().toDate();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String date = df.format(timestamp);
                db.collection("Myplants").document(myplantid).update("recentWater", date);
            }
        });

        btnOnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date timestamp = Timestamp.now().toDate();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String date = df.format(timestamp);
                db.collection("Myplants").document(myplantid).update("recentWater", date);
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/MyDevice/aksjcnejas/motorPower", "ON");
                myplantsdata.updateChildren(childUpdates);
////                myplantsdata.child("MyDevice").child("aksjcnejas").child("motorPower").updateChildren();
////                childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//                mDatabase.updateChildren(childUpdates);
            }
        });

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.myPlant, new MyGarden()).commit();
                btnBack2.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void getmyplantinfo(){
        db = FirebaseFirestore.getInstance();

        db.collection("Myplants").document(myplantid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException e) {
                tvType.setText(doc.get("type").toString());
                tvName.setText(doc.get("name").toString());
                tvLocation.setText(doc.get("location").toString());
                tvDate.setText(doc.get("date").toString());
                if(doc.get("recentWater") != null){
                    tvWaterDate.setText(doc.get("recentWater").toString());
                }
                if(doc.get("profileUri") != null){
                    Glide.with(getApplicationContext())
                            .load(Uri.parse(doc.get("profileUri").toString()))
                            .into(ivPlants);
//                    ivPlants.setImageURI(Uri.parse(doc.get("profileUri").toString()));
                }
            }
        });

//        db.collection("Myplants").document(myplantid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot doc = task.getResult();
//                Log.e("taskgetResult", task.getResult().toString());
//                tvName.setText(doc.get("name").toString());
//                tvLocation.setText(doc.get("location").toString());
//                tvDate.setText(doc.get("date").toString());
//                if(doc.get("recentWater") != null){
//                    tvWaterDate.setText(doc.get("recentWater").toString());
//                }
//                if(doc.get("profileUri").toString() != null){
//                    Glide.with(getApplicationContext())
//                            .load(Uri.parse(doc.get("profileUri").toString()))
//                            .into(ivPlants);
////                    ivPlants.setImageURI(Uri.parse(doc.get("profileUri").toString()));
//                }
//
//
//            }
//        });
    }



}