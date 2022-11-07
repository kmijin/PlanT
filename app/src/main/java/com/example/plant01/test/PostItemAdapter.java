package com.example.plant01.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostItemAdapter extends RecyclerView.Adapter<PostItemAdapter.PostViewHolder>{
    Context context;
    ArrayList<PostItem> postItemArrayList;
    private FirebaseFirestore firestore;

    public PostItemAdapter(Context context, ArrayList<PostItem> postItemArrayList){
        this.context = context;
        this.postItemArrayList = postItemArrayList;
    }
    @NonNull
    @Override
    public PostItemAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item,viewGroup,false);
        return new PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {

/*----------------PostItem에 있는것들 가져올 때 ---------------------*/
        /*--포스트이미지--*/
        if(postItemArrayList.get(position).getContentImg() !=  null){
            Glide.with(postViewHolder.postpic).load(postItemArrayList.get(position).getContentImg())
                    .into(postViewHolder.postpic);
            postViewHolder.postpic.setVisibility(View.VISIBLE);


        }
        /*--포스트내용--*/
        postViewHolder.postcontent.setText(postItemArrayList.get(position).getContent());


        /*--날짜가져오기--*/
        Date milliseconds = postItemArrayList.get(position).getPostDate();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = df.format(milliseconds);
        postViewHolder.postdate.setText(date);

/*------------------PostItem에 없는 닉네임과 이미지를 포스트의 userid로 가져오기 ------------------------*/
        String userid = postItemArrayList.get(position).getUserID();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String userNick = task.getResult().getString("userNick");
                String userprofile = task.getResult().getString("userImg");
                postViewHolder.setPostusernick(userNick);
                if(userprofile != null) {
                    postViewHolder.setUserprofile(userprofile);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return postItemArrayList.size();
    }

    //보여져야한는것들을 여기에 넣는다,,,
    public class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView userprofile, postpic, likepic, commentpic;
        TextView postusernick, postdate, postlikes, postcontent;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userprofile = itemView.findViewById(R.id.post_UserImg);
            postpic = itemView.findViewById(R.id.post_PostImage);
            postusernick = itemView.findViewById(R.id.post_UserNick);
            postdate = itemView.findViewById(R.id.post_PostDate);
            postcontent = itemView.findViewById(R.id.post_PostExplain);

        }


        /*------------PostItem에 없는것 가져와야할 때 setter 만들어주기--------------*/
        public void setUserprofile(String userprofiletxt) {
            Glide.with(context).load(userprofiletxt).into(userprofile);
        }

        public void setPostusernick(String postusernicktxt) {
            postusernick.setText(postusernicktxt);
        }
    }
}

