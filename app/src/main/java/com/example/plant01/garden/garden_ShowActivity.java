package com.example.plant01.garden;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class garden_ShowActivity extends AppCompatActivity {

    private RecyclerView main_recyclerView;
    private FirebaseFirestore db;
    private garden_MyAdapter adapter;
    private List<garden_Model> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_showactivity);

        main_recyclerView = findViewById((R.id.main_recyclerview));
        main_recyclerView.setHasFixedSize(true);
        main_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new garden_MyAdapter(this, list);
        main_recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new garden_TouchHelper(adapter));
        touchHelper.attachToRecyclerView(main_recyclerView);

        showData();
    }

    public void  showData(){

        db.collection("Myplants").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            garden_Model gardenModel = new garden_Model(snapshot.getString("id"),snapshot.getString("profileUri"),snapshot.getString("type"),snapshot.getString("name"),snapshot.getString("location"),snapshot.getString("date"));
                            list.add(gardenModel);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(garden_ShowActivity.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}