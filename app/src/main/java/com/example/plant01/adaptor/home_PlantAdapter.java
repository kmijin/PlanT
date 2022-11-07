package com.example.plant01.adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.example.plant01.home.Plants;
import com.example.plant01.store.store_SearchResult;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class home_PlantAdapter extends RecyclerView.Adapter<home_PlantAdapter.PlantViewHolder> {

    Context context;
    ArrayList<Plants> plantsArrayList;

    public home_PlantAdapter(Context context, ArrayList<Plants> plantsArrayList) {
        this.context = context;
        this.plantsArrayList = plantsArrayList;
    }



    @NonNull
    @Override
    public home_PlantAdapter.PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.home_item, parent,false);

        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_PlantAdapter.PlantViewHolder holder, int position) {


        holder.plantName.setText(plantsArrayList.get(position).getPlantName());
        Glide.with(holder.plantImg)
                .load(plantsArrayList.get(position).getPlantImg())
                .into(holder.plantImg);

    }

    @Override
    public int getItemCount() {
        return plantsArrayList.size();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView plantImg;
        TextView plantName;
        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImg = itemView.findViewById(R.id.iv_plantImg);
            plantName = itemView.findViewById(R.id.tv_plantName);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int currentPos = getAdapterPosition();
                    Plants plants = plantsArrayList.get(currentPos);
                    String plantname = plants.getPlantName();
                    Intent intent = new Intent(context, store_SearchResult.class);
                    intent.putExtra("contact_search",plantname);
                    context.startActivity(intent);
                    Log.e("plantname", plants.getPlantName());

                }
            });

        }
    }
}
