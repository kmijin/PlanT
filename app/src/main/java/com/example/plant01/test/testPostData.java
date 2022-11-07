package com.example.plant01.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.plant01.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class testPostData extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FloatingActionButton fab;
    private PostItemAdapter adapter;
    private ArrayList<PostItem> postItemArrayList;
    private Query query;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_data);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

//        fab = findViewById(R.id.add_post);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(testPostData.this, Post_write.class));
//            }
//        });


/*--------------------게시판 보여주는 리사이클러뷰와 클래스  --------------------------*/
        mRecyclerView = findViewById(R.id.post_PostRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(testPostData.this));
        postItemArrayList = new ArrayList<PostItem>();
        adapter = new PostItemAdapter(testPostData.this,postItemArrayList);
        mRecyclerView.setAdapter(adapter);
        showPost();

        /*------------보여지는 게시글이 끝나면 END라고 뜨게함 ---------*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Boolean isBottom = !mRecyclerView.canScrollVertically(1);
                if (isBottom) {
                    Toast.makeText(testPostData.this, "END", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    /*-----------------어댑터와 데이터 연결----------------------*/
    public void showPost(){
        /*----------날짜내림차순---------*/
        query = firestore.collection("Post").orderBy("postDate",Query.Direction.DESCENDING);
        query.addSnapshotListener(testPostData.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                postItemArrayList.clear();
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        PostItem postItem = doc.getDocument().toObject(PostItem.class);
                        postItemArrayList.add(postItem);
                        adapter.notifyDataSetChanged();
                        Log.e("포스트 ", value.getDocuments().toString());
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


}