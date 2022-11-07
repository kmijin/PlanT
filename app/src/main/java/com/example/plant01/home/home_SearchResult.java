package com.example.plant01.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class home_SearchResult extends AppCompatActivity {
    private FirebaseFirestore db;
    RecyclerView recyclerView;
    ImageView level;
    TextView sun, water, like, plantshort, plantname, keyword0, keyword1, keyword2, keyword3;
    RoundedImageView plantImg;
    Toolbar toolbar;
    home_SearchAdapter adapter;
    private ArrayList<String> tipArrayList;

    public home_SearchResult() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        toolbar = findViewById(R.id.search_result_toolbar);
        sun = (TextView) findViewById(R.id.search_plantSun);
        water = (TextView) findViewById(R.id.search_plantWater);
        plantshort = (TextView) findViewById(R.id.search_plantShort);
        plantname = (TextView) findViewById(R.id.search_plantName);
        level = (ImageView) findViewById(R.id.search_plantLevel);
        keyword0 = (TextView)findViewById(R.id.search_plantKeyword1);
        keyword1 = (TextView)findViewById(R.id.search_plantKeyword2);
        keyword2 = (TextView)findViewById(R.id.search_plantKeyword3);
        keyword3 = (TextView)findViewById(R.id.search_plantKeyword4);

        setSupportActionBar(toolbar);
        setTitle("검색결과");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        plantImg = (RoundedImageView) findViewById(R.id.result_plant_img);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        tipArrayList = new ArrayList<String>();
        recivetip();


    }

    /*----------------검색결과 보여주기-------------------------------*/
    public void recivetip() {
        /*-------분석에서 결과 받아오기 ------*/
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.tipRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(home_SearchResult.this));
        Intent intent = getIntent(); /*데이터 수신*/
        String getplantname = intent.getExtras().getString("plantName");
        Query plantinfo = db.collection("Plants").whereEqualTo("plantName", getplantname);

        plantinfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                tipArrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {


                    ArrayList keyword = (ArrayList) document.getData().get("plantKeyword");
                    keyword0.setText(keyword.get(0).toString());
                    keyword1.setText(keyword.get(1).toString());
                    keyword2.setText(keyword.get(2).toString());
                    keyword3.setText(keyword.get(3).toString());

                    String plantsuntxt = (String) document.get("plantSun");
                    sun.setText(plantsuntxt);

                    String plantwatertxt = (String) document.get("plantWater");
                    water.setText(plantwatertxt);

                    String plantnametxt = (String) document.get("plantName");
                    plantname.setText(plantnametxt);

                    String plantshorttxt = (String) document.get("plantShort");
                    plantshort.setText(plantshorttxt);

                    String plantImgurl = (String) document.get("plantImg");
                    Glide.with(home_SearchResult.this)
                            .load(Uri.parse(plantImgurl))
                            .into(plantImg);

                    String levelurl = (String) document.get("plantLevel");
                    Glide.with(home_SearchResult.this)
                            .load(Uri.parse(levelurl))
                            .into(level);

                    ArrayList list = (ArrayList) document.getData().get("plantTip");
                    for (int i = 0; i < list.size(); i++) {
                        String string = list.get(i).toString();
                        tipArrayList.add(list.get(i).toString());
                    }
                    Log.e("TEST", tipArrayList.toString());
                    adapter = new home_SearchAdapter(tipArrayList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
/*-------------뒤로가기----------------*/
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
//    public String[] returnString(){
//        db = FirebaseFirestore.getInstance();
//        final String[] dbtips;
//        Intent intent = getIntent(); /*데이터 수신*/
//        String getplantname = intent.getExtras().getString("plantName");
//        Query plantinfo = db.collection("Plant").whereEqualTo("plantName", getplantname);
//
//        plantinfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public  void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        ArrayList list = (ArrayList) document.getData().get("plantTip");
//                         dbtipas = new String[list.size()];
////                        Convert to ArrayList
////                        List<String> testList = new ArrayList<>(Arrays.asList(t));
//                        for (int i = 0; i < list.size(); i++) {
////                            Log.e("TEST", "data["+i+"] > " + list.get(i).toString());
//                            dbtips[i] = list.get(i).toString();
//                        }
//
//                    }
//                }
//            }
//        });
//        return dbtips;
//    }


//        tiplistView = findViewById(R.id.tipListview);
//        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                return null;
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//            }
//
//            @Override
//            public int getItemCount() {
//                return 0;
//            }
//        }

//        tips = new String[]{};

//        Log.e("String[] ",Arrays.toString(tips));

//
////        for(int i=0; i< list.size(); i++){
////                            Log.e("TEST", "data["+i+"] > " + list.get(i).toString());
//////                            tips[i] = list.get(i).toString();
////                        }
//////        /*----Tips스트링 가져오기*/
//        ArrayAdapter<String> tipAdapter = new ArrayAdapter<>(this, R.layout.result_tip_item,R.id.tip,tips);
//        tiplistView.setAdapter(tipAdapter);


/*-------파이어스토어와 연결----*/


//        Query plantinfo = db.collection("Plant").whereEqualTo("plantName", getplantname);
//
//        plantinfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        ArrayList list = (ArrayList)document.getData().get("plantTip");
//                        tips = new String[list.size()];
//                        //Convert to ArrayList
////                        List<String> testList = new ArrayList<>(Arrays.asList(t));
//                        for(int i=0; i< list.size(); i++){
////                            Log.e("TEST", "data["+i+"] > " + list.get(i).toString());
//                            tips[i] = list.get(i).toString();
//                        }
//
//                    }
//                }
//            }
//        });
