package com.example.plant01.garden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class garden_MyAdapter extends RecyclerView.Adapter<garden_MyAdapter.MyViewHolder> {
    Activity activity;
    List<garden_Model> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public garden_MyAdapter(Activity activity, List<garden_Model> mList){
        this.activity = activity;
        this.mList = mList;

    }

    public void updateData(int position){
        garden_Model item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        bundle.putString("uType" , item.getType());
        bundle.putString("uName" , item.getName());
        bundle.putString("uLocation" , item.getLocation());
        bundle.putString("uDate" , item.getDate());
        bundle.putString("uProfileUri" , item.getProfileUri());
        Intent intent = new Intent(activity , garden_AddPlants.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    //데이터 삭제
    public void deleteData(int position){
        garden_Model item = mList.get(position);
        db.collection("Myplants").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
//        activity.showData();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.garden_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(mList.get(position).getProfileUri() != null){
            Glide.with(activity).load(mList.get(position).getProfileUri()).into(holder.profile);
        }
        holder.name.setText(mList.get(position).getName());
//        holder.location.setText(mList.get(position).getLocation());
//        holder.date.setText(mList.get(position).getDate());


    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, location, date;
        ImageView profile;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.iv_PlantsProfile);
            name = itemView.findViewById(R.id.name_text);
//            location = itemView.findViewById(R.id.location_text);
//            date = itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int currentPos = getAdapterPosition();
                    garden_Model myplants = mList.get(currentPos);
                    String myplantsId = myplants.getId();
                    Intent intent = new Intent(activity, garden_MyPlants.class);
                    intent.putExtra("myplantid",myplantsId);
                    activity.startActivity(intent);
                    Log.e("myplantid", myplants.getId());

                }
            });
        }
    }
}
