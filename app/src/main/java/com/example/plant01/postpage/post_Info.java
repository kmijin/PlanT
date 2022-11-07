package com.example.plant01.postpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class post_Info extends Fragment {
    private View view;
    private RecyclerView InfoRecyclerView;
    private FirebaseFirestore db;
    private post_WriteAdapter WriteAdapter;
    private List<post_WriteInfo> list;
    private FloatingActionButton floatingActionButton;
    private View.OnClickListener cl;
//    private FirebaseUser firebaseUser;
//    private FirebaseFirestore firebaseFirestore;
//    private RecyclerView recyclerView;


    public static post_Info newInstance(){
        post_Info post_info = new post_Info();
        return post_info;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_info, container, false);
//        view.findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);

        InfoRecyclerView = view.findViewById((R.id.post_InfoRecyclerView));
        InfoRecyclerView.setHasFixedSize(true);
        InfoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        WriteAdapter = new post_WriteAdapter(getActivity(), list);
        InfoRecyclerView.setAdapter(WriteAdapter);
        showData();
//        db.collection("WritePosts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            ArrayList<Writeinfo> postList = new ArrayList<>();
//                            postList.clear();
//                            for(QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId()+ "=>" + document.getData());
//                                postList.add(new Writeinfo(document.getData().get("UserId").toString(),
//                                        document.getData().get("Title").toString(),
//                                        document.getData().get("Contents").toString()));
//                                adapter.notifyDataSetChanged();
//                            }
////                            RecyclerView recyclerView = view.findViewById(R.id.post_recyclerView);
////                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
////                            recyclerView.setLayoutManager(manager);
////                            recyclerView.setHasFixedSize(true);
////                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
////                            adapter = new PostWriteAdapter(getActivity(), postList);
////                            post_recyclerView.setAdapter(adapter);
//                        }else{
//                            Log.d(TAG, "Error", task.getException());
//                        }
//                    }
//                });

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        db.collection("WritePosts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            ArrayList<Writeinfo> postList = new ArrayList<>();
//                            for(QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId()+ "=>" + document.getData());
//                                postList.add(new Writeinfo(document.getData().get("UserId").toString(),
//                                        document.getData().get("Title").toString(),
//                                        document.getData().get("Contents").toString()));
//                            }
//                            RecyclerView recyclerView = view.findViewById(R.id.post_recyclerView);
//                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
//                            recyclerView.setLayoutManager(manager);
//                            recyclerView.setHasFixedSize(true);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//                            RecyclerView.Adapter madapter = new PostWriteAdapter(getActivity(), postList);
//                            recyclerView.setAdapter(madapter);
//                        }else{
//                            Log.d(TAG, "Error", task.getException());
//                        }
//                    }
//                });
//    }
    //정보게시판 게시글 보여줌
    private void showData(){

        db.collection("Post").whereEqualTo("board", "정보게시판").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){
                            post_WriteInfo model = new post_WriteInfo(snapshot.getString("userID"),snapshot.getString("title"),snapshot.getString("content"));
                            list.add(model);
                        }
                        WriteAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }




//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.floatingActionButton:
//                    myStartActivity(Post_write.class);
//                    break;
//            }
//        }
//    };


    @Override
    public void onStop() {
        super.onStop();
    }

//    private void myStartActivity(Class c) {
//        Intent intent = new Intent(getActivity(), c);
//        startActivityForResult(intent, 0);
//    }
}

/*
package com.example.plant01.postpage;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Post_info extends Fragment {
    private View view;
    private FloatingActionButton floatingActionButton;
    private View.OnClickListener cl;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    public static Post_info newInstance(){
        Post_info post_info = new Post_info();
        return post_info;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_info, container, false);
        view.findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //리사이클러뷰 초기화
        recyclerView = view.findViewById(R.id.post_recyclerView); //리사이클러뷰 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void onResume(){
        super.onResume();
        if(firebaseUser != null){
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
            collectionReference
                    .orderBy("date", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                ArrayList<Writeinfo> postList = new ArrayList<>();
                                for(QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId()+ "=>" + document.getData());
                                    postList.add(new Writeinfo(document.getData().get("title").toString(),
                                            (ArrayList<String>) document.getData().get("contents"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("date").getTime())
                                    ));
                                }

//                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
//                                recyclerView.setLayoutManager(manager);
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                RecyclerView.Adapter madapter = new PostWriteAdapter(getActivity(), postList);
                                recyclerView.setAdapter(madapter);
                            }else{
                                Log.d(TAG, "Error", task.getException());
                            }
                        }
                    });
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.floatingActionButton:
                    myStartActivity(Post_write.class);
                    break;
            }
        }
    };


    @Override
    public void onStop() {
        super.onStop();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivityForResult(intent, 0);
    }
}

 */