package com.example.plant01.store;

import android.content.Intent;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class store_SearchResult extends AppCompatActivity {

    private ImageButton store_resultback;
    private RecyclerView recyclerView;
    private store_GoodsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private store_GoodsAdapter store_goodsAdapter;
    private List<store_Goods> list;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView nothing;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_searchresult);

        store_resultback = (ImageButton) findViewById(R.id.store_ResultBack);
        store_resultback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nothing = findViewById(R.id.store_Nothing);
        recyclerView = findViewById(R.id.store_ResultView); // 리사이클러뷰에 LinearLayoutManager 객체 지정
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new store_GoodsAdapter(this, list); // 리사이클러뷰에 Adapter 객체 지정
        recyclerView.setAdapter(adapter);



        Intent intent = getIntent();

        TextView result = (TextView) findViewById(R.id.store_ResultText);
        String search = intent.getStringExtra("contact_search");
        if (search != null){
            result.setText(search);
            if (search.equals("선인장")){
                showSun();
            } else if (search.equals("난")){
                showNan();
            } else if (search.equals("꽃")){
                showFlower();
            }else if (search.equals("옥잠화")){
                showRecomand1();
            }
            else {
                nothing.setText("검색결과가 없습니다."); // 등록된 검색어가 아니면 표시
            }
        }
    }

    private void  showSun(){ // 상품 종류가 선인장인 데이터 불러오기

        CollectionReference goodsRef = db.collection("StoreGoods"); // 상품 데이터 객체 생성

        db.collection("StoreGoods").whereEqualTo("goodsKind", "선인장").get() // 검색 조건. goodsKind가 선인장인 상품만 검색
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_SearchResult.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void  showNan(){ // 상품 종류가 난인 데이터 불러오기

        CollectionReference goodsRef = db.collection("StoreGoods"); // 상품 데이터 객체 생성

        db.collection("StoreGoods").whereEqualTo("goodsKind", "난").get() // 검색 조건. goodsKind가 난인 상품만 검색
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_SearchResult.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  showFlower(){ // 상품 종류가 꽃인 데이터 불러오기

        CollectionReference goodsRef = db.collection("StoreGoods"); // 상품 데이터 객체 생성

        db.collection("StoreGoods").whereEqualTo("goodsKind", "꽃").get() // 검색 조건. goodsKind가 꽃인 상품만 검색
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_SearchResult.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void  showRecomand1(){
        CollectionReference goodsRef = db.collection("StoreGoods"); // 상품 데이터 객체 생성

        db.collection("StoreGoods").whereEqualTo("goodsKind", "옥잠화").get() // 검색 조건. goodsKind가 옥잠화인 상품만 검색
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_SearchResult.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void  showRecomand2(){
        CollectionReference goodsRef = db.collection("StoreGoods"); // 상품 데이터 객체 생성

        db.collection("StoreGoods").whereEqualTo("goodsKind", "필로덴드론").get() // 검색 조건. goodsKind가 필로덴드론인 상품만 검색
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            store_Goods storeGoods = new store_Goods(snapshot.getString("goodsImg"),snapshot.getString("storeName"),snapshot.getString("goodsTitle"),snapshot.getString("goodsReview"),snapshot.getString("goodsPrice"));
                            list.add(storeGoods);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(store_SearchResult.this,"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }




}

