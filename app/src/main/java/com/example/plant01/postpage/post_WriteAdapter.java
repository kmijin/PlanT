package com.example.plant01.postpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class post_WriteAdapter extends RecyclerView.Adapter<post_WriteAdapter.PostViewHolder> {
    private post_Info post_info;
    Context context;
    private FragmentActivity fragmentActivity;
    private List<post_WriteInfo> mList;
    //
    private FirebaseFirestore firestore;

    public post_WriteAdapter(post_Info post_info, List<post_WriteInfo> mList){
        this.post_info = post_info;
        this.mList = mList;
    }

    public post_WriteAdapter(FragmentActivity fragmentActivity, List<post_WriteInfo> list) {
        this.fragmentActivity = fragmentActivity;
        this.mList = list;
    }

//        static class PostViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        PostViewHolder(CardView v) {
//            super(v);
//            cardView = v;
//        }
//    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragmentActivity).inflate(R.layout.post_item_write, parent, false);
        return new PostViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.UserNameWrite.setText(mList.get(position).getTitle());
        holder.TitleWrite.setText(mList.get(position).getTitle());
        holder.ContentWrite.setText(mList.get(position).getcontents());
    }
    //작성한 부분 넣기
    public static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView TitleWrite, ContentWrite, UserNameWrite;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);


            TitleWrite = itemView.findViewById(R.id.post_TitleWrite);
            ContentWrite = itemView.findViewById(R.id.post_ContentWrite);
            UserNameWrite = itemView.findViewById(R.id.post_UserNameWrite);
        }

    }
}

//package com.example.plant01.postpage;
//
//import android.app.Activity;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.plant01.R;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Locale;
//
//public class PostWriteAdapter extends RecyclerView.Adapter<PostWriteAdapter.PostViewHolder> {
//private ArrayList<Writeinfo> mDataset;
//private Activity activity;
//
//static class PostViewHolder extends RecyclerView.ViewHolder {
//    CardView cardView;
//    PostViewHolder(CardView v) {
//        super(v);
//        cardView = v;
//    }
//}
//
//    public PostWriteAdapter(Activity activity, ArrayList<Writeinfo> myDataset) {
//        mDataset = myDataset;
//        this.activity = activity;
//    }
//
//    @NonNull
//    @Override
//    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_write, parent, false);
//        final PostViewHolder galleryViewHolder = new PostViewHolder(cardView);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
//        return galleryViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
//        CardView cardView = holder.cardView;
//        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
//        titleTextView.setText(mDataset.get(position).getTitle());
//
//        TextView createdAtTextView = cardView.findViewById(R.id.createdAtTextView);
//        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getDate()));
//
//        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ArrayList<String> contentList = mDataset.get(position).getContents();
//
//        if(contentsLayout.getChildCount() == 0) {
//            for (int i = 0; i < contentList.size(); i++) {
//                String contents = contentList.get(i);
//                if (Patterns.WEB_URL.matcher(contents).matches()) {
//                    ImageView imageView = new ImageView(activity);
//                    imageView.setLayoutParams(layoutParams);
//                    imageView.setAdjustViewBounds(true);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    contentsLayout.addView(imageView);
//                    Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
//                } else {
//                    TextView textView = new TextView(activity);
//                    textView.setLayoutParams(layoutParams);
//                    textView.setText(contents);
//                    contentsLayout.addView(textView);
//                }
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataset.size();
//    }
//}