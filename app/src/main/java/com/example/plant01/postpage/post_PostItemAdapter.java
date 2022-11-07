package com.example.plant01.postpage;

import android.content.Context;
import android.util.Log;
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

public class post_PostItemAdapter extends RecyclerView.Adapter<post_PostItemAdapter.PostViewHolder>{
    Context context;
    ArrayList<post_PostItem> postItemArrayList;
    private FirebaseFirestore firestore;

    public post_PostItemAdapter(Context context, ArrayList<post_PostItem> postItemArrayList){
        this.context = context;
        this.postItemArrayList = postItemArrayList;
    }
    @NonNull
    @Override
    public post_PostItemAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item,viewGroup,false);
        return new PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {

/*----------------PostItem에 있는것들 가져올 때 ---------------------*/
        /*--포스트이미지--*/
        if(postItemArrayList.get(position).getContentImg() != null){
            Glide.with(postViewHolder.PostImage).load(postItemArrayList.get(position).getContentImg())
                    .into(postViewHolder.PostImage);
            postViewHolder.PostImage.setVisibility(View.VISIBLE);

        }
        /*--포스트내용--*/
        postViewHolder.PostExplain.setText(postItemArrayList.get(position).getContent());


        /*--날짜가져오기--*/
        Date milliseconds = postItemArrayList.get(position).getPostDate();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = df.format(milliseconds);
        postViewHolder.PostDate.setText(date);

/*------------------PostItem에 없는 닉네임과 이미지를 포스트의 userid로 가져오기 ------------------------*/
        String userid = postItemArrayList.get(position).getUserID();
//        Log.e("userid", userid);
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Users").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String userNick = task.getResult().getString("userNick");
                String userprofile = task.getResult().getString("userImg");
                postViewHolder.setPostusernick(userNick);
                if(userprofile != null){
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

        ImageView UserImg, PostImage, likepic, commentpic;
        TextView UserNick, PostDate, postlikes, PostExplain;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            UserImg = itemView.findViewById(R.id.post_UserImg);
            PostImage = itemView.findViewById(R.id.post_PostImage);
            UserNick = itemView.findViewById(R.id.post_UserNick);
            PostDate = itemView.findViewById(R.id.post_PostDate);
            PostExplain = itemView.findViewById(R.id.post_PostExplain);

        }


        /*------------PostItem에 없는것 가져와야할 때 setter 만들어주기--------------*/
        public void setUserprofile(String userprofiletxt) {
            Glide.with(context).load(userprofiletxt).into(UserImg);
        }

        public void setPostusernick(String postusernicktxt) {
            UserNick.setText(postusernicktxt);
        }
    }
}

