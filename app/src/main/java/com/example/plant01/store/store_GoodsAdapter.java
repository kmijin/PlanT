package com.example.plant01.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;

import java.util.List;

public class store_GoodsAdapter extends RecyclerView.Adapter<store_GoodsAdapter.store_viewholder> {

    private store_SearchResult activity;
    private List<store_Goods> goodsList;

    public store_GoodsAdapter(store_SearchResult activity, List<store_Goods> goodsList){
        this.activity = activity;
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public store_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.store_regoods, parent,false);
        return new store_viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull store_viewholder holder, int position) {
        Glide.with(holder.itemView)
                .load(goodsList.get(position).getGoodsImg())
                .into(holder.img0);
        holder.store0.setText(goodsList.get(position).getStoreName());
        holder.title0.setText(goodsList.get(position).getGoodsTitle());
        holder.review0.setText(goodsList.get(position).getGoodsReview());
        holder.price0.setText(goodsList.get(position).getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public static class store_viewholder extends RecyclerView.ViewHolder{

        ImageButton img0;
        TextView store0, title0, review0, price0;

        public store_viewholder(@NonNull View itemView) {
            super(itemView);

            img0 = itemView.findViewById(R.id.store_RecommendImg0);
            store0 = itemView.findViewById(R.id.store_RecommendStore0);
            title0 = itemView.findViewById(R.id.store_RecommendTitle0);
            review0 = itemView.findViewById(R.id.store_RecommendReview0);
            price0 = itemView.findViewById(R.id.store_RecommendPrice0);
        }
    }
}