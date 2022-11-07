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

public class post_RealTime extends Fragment {
    private View view;
    private RecyclerView PostRecyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private post_PostItemAdapter PostItemAdapter;
    private ArrayList<post_PostItem> postItemArrayList;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_data, container, false);
//        view.findViewById(R.id.add_post).setOnClickListener(onClickListener);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        /*--------------------게시판 보여주는 리사이클러뷰와 클래스  --------------------------*/
        PostRecyclerView = view.findViewById(R.id.post_PostRecyclerView);
        PostRecyclerView.setHasFixedSize(true);
        PostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postItemArrayList = new ArrayList<post_PostItem>();
        PostItemAdapter = new post_PostItemAdapter(getContext(),postItemArrayList);
        PostRecyclerView.setAdapter(PostItemAdapter);
        showPost();

        /*------------보여지는 게시글이 끝나면 END라고 뜨게함 ---------*/
        PostRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Boolean isBottom = !PostRecyclerView.canScrollVertically(1);
                if (isBottom) {
                    Toast.makeText(getContext(), "END", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.add_post:
//                    myStartActivity(Post_write.class);
//                    break;
//            }
//        }
//    };

    /*-----------------어댑터와 데이터 연결----------------------*/
    public void showPost(){
        /*----------날짜내림차순---------*/
        query = firestore.collection("Post")
                .orderBy("postDate",Query.Direction.DESCENDING);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                postItemArrayList.clear();
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        post_PostItem postItem = doc.getDocument().toObject(post_PostItem.class);
                        postItemArrayList.add(postItem);
                        PostItemAdapter.notifyDataSetChanged();
//                        Log.e("포스트 ", value.getDocuments().toString());
                    } else {
                        PostItemAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

//    private void myStartActivity(Class c) {
//        Intent intent = new Intent(getActivity(), c);
//        startActivityForResult(intent, 0);
//    }

}

