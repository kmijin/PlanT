package com.example.plant01.postpage;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class post_Best extends Fragment {
    private View view;
    private FirebaseFirestore db;
    private FloatingActionButton floatingActionButton;
    private View.OnClickListener cl;

    public post_Best(){}
    public static post_Best newInstance(){
        post_Best post_best = new post_Best();
        return post_best;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_best, container, true);
//        view.findViewById(R.id.add_post).setOnClickListener(onClickListener);

        return view;
    }
//    //작성페이지로 이동하는 버튼
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.add_post:
//                    //startActivity(new Intent(testPostData.this, Post_write.class));
//                    myStartActivity(Post_write.class);
//                    break;
//            }
//        }
//    };

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        /*----------베스트 게시판 보여줌 ------------*/
        showBoard();
    }
    /*-------------인기게시판 불러오는 곳------------------*/
    public void showBoard() {
        ImageView UserImg = getView().findViewById(R.id.post_UserImg);
        TextView PostExplain = getView().findViewById(R.id.post_PostExplain);
        TextView LikeNum = getView().findViewById(R.id.post_LikeNum);
        TextView UserNick = getView().findViewById(R.id.post_UserNick);

//        RoundedImageView questuserprofile = getView().findViewById(R.id.quest_userprofile);
//        TextView quest_content = getView().findViewById(R.id.quset_content);
//        TextView quest_count = getView().findViewById(R.id.quest_likecount);
//        TextView quest_usernick = getView().findViewById(R.id.quest_usernick);

        /*----------------자유게시판 불러오는 쿼리 -------------------------*/
        Query freepost = db.collection("Posts").whereEqualTo("board", "자유게시판").orderBy("likes", Query.Direction.DESCENDING).limit(1);

//        /*-------------------질문 게시판 불러오는 쿼리 ---------------------------*/
//        Query questpost = db.collection("Posts").whereEqualTo("board", "질문게시판").orderBy("likes", Query.Direction.DESCENDING).limit(1);

        /*-------------------자유게시판 중 베스트-----------------------------------*/
        freepost.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList list = (ArrayList) document.getData().get("likes");

                        //int를 String으로 바꾸는 방법
                        String likes = String.valueOf(list.size());
                        LikeNum.setText(likes);

                        //컨텐츠 내용 가져오기
                        String content = (String) document.get("postContent");
                        PostExplain.setText(content);

                        //postUserID로 userNick가져오기
                        String UserUID = (String) document.get("postUserUID");
                        db.collection("Users").document(UserUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();

                                    // 닉네임과 이미지
                                    String userNick = (String) doc.get("userNick");
                                    UserNick.setText(userNick);
                                    String userImg = (String) doc.get("userImg");
                                    if (userImg != null) {
                                        Glide.with(getContext())
                                                .load(Uri.parse(userImg))
                                                .into(UserImg);
                                    }

                                    Log.e(TAG, doc.getId() + " " + UserUID + userNick);
                                }
                            }
                        });
                        Log.e(TAG, "자유게시판" + document.getId() + " => " + document.getData() + " " + UserUID + " ");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

//        /*------------질문 게시판 중 베스트--------------------------*/
//
//        questpost.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        ArrayList list = (ArrayList) document.getData().get("likes");
//
//                        //int를 String으로 바꾸는 방법
//                        String likes = String.valueOf(list.size());
//                        quest_count.setText(likes);
//
//                        //컨텐츠 내용 가져오기
//                        String content = (String) document.get("postContent");
//                        quest_content.setText(content);
//
//                        //postUserID로 userNick가져오기
//                        String UserUID = (String) document.get("postUserUID");
//                        db.collection("Users").document(UserUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    DocumentSnapshot doc = task.getResult();
//                                    String userNick = (String) doc.get("userNick");
//                                    quest_usernick.setText(userNick);
//
//                                    String userImg = (String) doc.get("userImg");
//                                    if (userImg != null) {
//                                        Glide.with(getContext())
//                                                .load(Uri.parse(userImg))
//                                                .into(questuserprofile);
//                                    }
//                                    Log.d(TAG, doc.getId() + " " + UserUID + userNick);
//                                }
//                            }
//                        });
//                        Log.d(TAG, document.getId() + " => " + document.getData() + " " + UserUID + " ");
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
    }


        @Override
    public void onStop() {
        super.onStop();
    }

//    private void myStartActivity(Class c) {
//        Intent intent = new Intent(getActivity(), c);
//        startActivityForResult(intent, 0);
//    }
}
