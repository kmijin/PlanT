package com.example.plant01.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant01.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class store_Main extends Fragment {
    private View view;
    private TextView more;
    private FirebaseFirestore db;
    private ImageButton store_resultback;
    private RecyclerView recyclerView;
    private store_GoodsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private store_GoodsAdapter store_goodsAdapter;
    private List<store_Goods> list;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private TextView nothing;

    TextView store1, title1, review1, price1;
    TextView store2, title2, review2, price2;
    TextView store3, title3, review3, price3;

    public static store_Main newInstance() {
        store_Main Store = new store_Main();
        return Store;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.store_main, container, false);
        return view;
    }
}

