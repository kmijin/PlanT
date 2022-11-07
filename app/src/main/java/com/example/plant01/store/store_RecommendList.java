package com.example.plant01.store;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class store_RecommendList extends AppCompatActivity {

    ImageButton store_back, img1, img2, img3, img4, img5;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private store_GoodsAdapter2 store_goodsAdapter2;
    private List <store_Goods> list;
    private ImageButton store_resultback;
    private store_GoodsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    TextView store1, title1, review1, price1;
    TextView store2, title2, review2, price2;
    TextView store3, title3, review3, price3;
    TextView store4, title4, review4, price4;
    TextView store5, title5, review5, price5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // 뒤로가기 버튼 누를 시 추천상품 페이지로 돌아감
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_recommend_list);

        store_back = (ImageButton) findViewById(R.id.store_Back);
        store_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.store_Recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        store_goodsAdapter2 = new store_GoodsAdapter2(this, list);
        recyclerView.setAdapter(store_goodsAdapter2);

        showData();

    }

    private void showData() {

        // 상품 데이터 객체 생성
        db.collection("StoreGoods").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        store_goodsAdapter2.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_RecommendList.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}







