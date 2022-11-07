package com.example.plant01.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;

import java.util.ArrayList;

public class home_SearchAdapter extends RecyclerView.Adapter<home_SearchAdapter.TipViewHolder> {


    ArrayList<String> tipArrayList = null;

    public home_SearchAdapter(ArrayList<String> tipArrayList) {
        this.tipArrayList = tipArrayList;
    }



    @NonNull
    @Override
    public home_SearchAdapter.TipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.result_tip_item,viewGroup, false) ;
        home_SearchAdapter.TipViewHolder vh = new home_SearchAdapter.TipViewHolder(view) ;


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull home_SearchAdapter.TipViewHolder tipViewHolder, int position) {
        String text = tipArrayList.get(position) ;

//        for(int i=0; i< tipArrayList.size(); i++){
//            String[]  words = {"햇빛", "흙","물주기","온도","비료","해충","독성"};
//            String substr2 = text.substring(0, 4);
//            if (substr2 == words[i]){
//                return words[i];
//            }
//
//        }

        SpannableString spannableString = new SpannableString(text);
        String word ="햇빛";
        int start = 0;
        int end = 9;
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#2b5d5b")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);


        tipViewHolder.tip.setText(spannableString) ;
//        tipViewHolder.tip.setText(tipArrayList.get(position).getTip());
    }

    @Override
    public int getItemCount() {
        return tipArrayList.size();
    }

    public class TipViewHolder extends RecyclerView.ViewHolder {
        TextView tip;
        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            tip = itemView.findViewById(R.id.tip);
        }
    }
//
//    private class Tip {
//        String tip;
//
//        public Tip(String tip) {
//            this.tip = tip;
//        }
//
//        public String getTip() {
//            return tip;
//        }
//
//        public void setTip(String tip) {
//            this.tip = tip;
//        }
//    }
}
