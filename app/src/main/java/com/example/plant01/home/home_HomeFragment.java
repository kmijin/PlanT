package com.example.plant01.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plant01.R;
import com.example.plant01.adaptor.home_SliderAdapter;
import com.example.plant01.adaptor.home_PlantAdapter;
import com.example.plant01.garden.garden_MyPlants;
import com.example.plant01.usersetting.usersetting_UserSetting;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class home_HomeFragment extends Fragment {
    private static final String TAG = "home_HomeFragment";

    private SliderView sliderView;
    private int[] images = {R.drawable.home_ad1,
            R.drawable.home_ad2,};
    private Uri searchimg;

    private RecyclerView recyclerView;
    private ArrayList<Plants> plantsArrayList;
    private home_PlantAdapter plantAdapter;
    private TextView managerplantname, managerplantdate;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaserf;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db;
    private View.OnClickListener cl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_main_fragment, container, false);

    }


    //  private FirebaseDatabase database;
//    private DatabaseReference databaseReference;



    @Override
    public void onStart() {
        super.onStart();


        sliderView = getView().findViewById(R.id.main_slider);

        home_SliderAdapter homeSliderAdapter = new home_SliderAdapter(images);

        sliderView.setSliderAdapter(homeSliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();




        /*----------------추천상품 부분 -------------------------------*/

        recyclerView = getView().findViewById(R.id.homerecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        plantsArrayList = new ArrayList<Plants>();
        plantAdapter = new home_PlantAdapter(getContext(), plantsArrayList);
        recyclerView.setAdapter(plantAdapter);
        db = FirebaseFirestore.getInstance();
        showRecomendPlant();
        /*----------베스트 게시판 보여줌 ------------*/
        showBoard();



        /*------------------드로워부분-------------------*/

        final DrawerLayout drawerLayout1 = (DrawerLayout) getView().findViewById(R.id.drawerLayout);
        NavigationView navigationView = getView().findViewById(R.id.nv_homedrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_profile:
                        Intent intent1 = new Intent(getActivity(), usersetting_UserSetting.class);
                        startActivity(intent1);
                        break;
                    case R.id.btn_bell:
                        Intent intent2 = new Intent(getActivity(), home_Bell.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        mangerplant();



        /*--------- 클릭 이벤트 -------------*/
        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imagemenu: //햄버거 버튼
                        drawerLayout1.openDrawer(GravityCompat.START);
                        showUserProfile();
                        break;
                    case R.id.home_btn_search:
                        Intent intent = new Intent(getActivity(), home_Search.class);
                        startActivity(intent);
//                        PickImageFromGallery();

                        break;
                }

            }
        };
        getView().findViewById(R.id.imagemenu).setOnClickListener(cl);
        getView().findViewById(R.id.home_btn_search).setOnClickListener(cl);

    }

    /*--------------------------카메라 권한--------------------------------*/
    public void PickImageFromGallery() {
        Intent intent=new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12 && resultCode==RESULT_OK && data!=null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(getActivity(), home_Search2.class);
            intent.putExtra("uri", bitmap);
            startActivity(intent);

        }
    }



    /*------------------추천상품 보여주는 부분----------------------------------*/
    public void showRecomendPlant() {
        db.collection("Plants").orderBy("likes", Query.Direction.DESCENDING).limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                plantsArrayList.clear();
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        plantsArrayList.add(dc.getDocument().toObject(Plants.class));
                    }

                    plantAdapter.notifyDataSetChanged();

                }
            }
        });
    }


    /*-----------------드로워 유저 프로필 보여주는 부분분---------------------------*/
    public void showUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //부분업데이트?어떻게
        CircleImageView userprofile = getView().findViewById(R.id.iv_userProfile);
        TextView usernick = getView().findViewById(R.id.tv_userNick);
        db.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    //해당 필드의 값 받아오기
                    String userImg = (String) doc.get("userImg");
                    String userNick = (String) doc.get("userNick");
                    usernick.setText(userNick);
                    if( userImg != null){
                        Glide.with(getActivity())
                                .load(Uri.parse(userImg))
                                .into(userprofile);

                    }

                    Log.e("드로워", userImg + "   " +userNick);
                }
            }

        });
    }

    /*--------------- 식물관리 --------------------*/

    public void mangerplant() {
        managerplantname = (TextView) getView().findViewById(R.id.tv_manager_plantname);
        managerplantdate = (TextView) getView().findViewById(R.id.tv_manager_plantdate);
        ImageView managerplantimg = (ImageView) getView().findViewById(R.id.home_mangerImg);
        CardView manager = (CardView) getView().findViewById(R.id.myplant_manage_card);
        db.collection("Myplants").document("ce9cf6da-94a6-4af5-88ab-1b222440d650").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                String myplantnicktxt = (String) doc.get("name");
                Log.e("myplantnicktxt", myplantnicktxt);
                Log.e("문서아이디", doc.getId());
                String myplantimgtxt = (String) doc.get("profileUri");
                String myplantplantname = (String) doc.get("type");
                String myplantrecentWater = (String) doc.get("recentWater");

                manager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), garden_MyPlants.class);
                        intent.putExtra("myplantid", doc.getId());
                        startActivity(intent);
                    }
                });

                /*-----------------내 식물 물주기 남은 날짜 가져오기-----------------------------------*/
                db.collection("Plants").whereEqualTo("plantName", myplantplantname)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()){
                            long plantWaterCycletxt = (long) document.get("plantWaterC");
                            int plantWaterCycle = Integer.valueOf(String.valueOf(plantWaterCycletxt));
                            Log.e("plant", String.valueOf(plantWaterCycletxt));

                            Calendar cal = Calendar.getInstance();
                            Calendar today = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                            Date date = null;
                            try {
                                date = sdf.parse(myplantrecentWater );
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            cal.setTime(date);
                            cal.add(Calendar.DATE, + plantWaterCycle);
                            long chai = cal.getTimeInMillis();
                            long now_day = today.getTimeInMillis();
                            long d_day = (chai - now_day) / (60*60*24*1000);


                            managerplantdate.setText(String.valueOf(d_day));
                            managerplantname.setText(myplantnicktxt);
                            if (getActivity() == null) {
                                return;
                            }
                            Glide.with(getActivity())
                                    .load(Uri.parse(myplantimgtxt))
                                    .into(managerplantimg);
                        }


                    }
                });

            }
        });
    }



    /*-------------인기게시판 불러오는 곳------------------*/

    public void showBoard(){
        RoundedImageView freeuserprofile = getView().findViewById(R.id.free_userpfile);
        TextView free_content = getView().findViewById(R.id.free_content);
        TextView free_count = getView().findViewById(R.id.free_likecount);
        TextView free_usernick = getView().findViewById(R.id.free_usernic);

        RoundedImageView questuserprofile = getView().findViewById(R.id.quest_userprofile);
        TextView quest_content = getView().findViewById(R.id.quset_content);
        TextView quest_count = getView().findViewById(R.id.quest_likecount);
        TextView quest_usernick = getView().findViewById(R.id.quest_usernick);


        /*----------------자유게시판 불러오는 쿼리 -------------------------*/

        Query freeboard = db.collection("Posts").whereEqualTo("board","자유게시판").orderBy("likes", Query.Direction.DESCENDING).limit(1);

        /*-------------------질문 게시판 불러오는 쿼리 ---------------------------*/
        Query questboard = db.collection("Posts").whereEqualTo("board","질문게시판").orderBy("likes", Query.Direction.DESCENDING).limit(1);

        //postUserUID 가져오는 방법?
        //postLike의 documentID가져옴.

/*-------------------자유게시판 중 베스트-----------------------------------*/
       freeboard.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       ArrayList list = (ArrayList)document.getData().get("likes");


                       //int를 String으로 바꾸는 방법
                       String  likes = String.valueOf(list.size());
                       free_count.setText(likes);
                       
                       //컨텐츠 내용 가져오기
                       String content = (String)document.get("postContent");
                       free_content.setText(content);

                       //postUserID로 userNick가져오기
                       String UserUID = (String) document.get("postUserUID");
                       db.collection("Users").document(UserUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful()) {
                                   DocumentSnapshot doc = task.getResult();

                                   // 닉네임과 이미지
                                   String userNick = (String) doc.get("userNick");
                                   free_usernick.setText(userNick);
                                   String userImg = (String) doc.get("userImg");
                                   if(userImg != null){
                                       Glide.with(getActivity())
                                               .load(Uri.parse(userImg))
                                               .into(freeuserprofile);
                                   }

                                   Log.e(TAG, doc.getId() + " " + UserUID + userNick);
                               }
                           }
                       });

                       Log.e(TAG, "자유게시판"+document.getId() + " => " + document.getData()+ " " + UserUID + " "  );
                   }
               } else {
                   Log.d(TAG, "Error getting documents: ", task.getException());
               }
           }
       });

/*------------질문 게시판 중 베스트--------------------------*/

        questboard.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ArrayList list = (ArrayList)document.getData().get("likes");


                        //int를 String으로 바꾸는 방법
                        String  likes = String.valueOf(list.size());
                        quest_count.setText(likes);

                        //컨텐츠 내용 가져오기
                        String content = (String)document.get("postContent");
                        quest_content.setText(content);

                        //postUserID로 userNick가져오기
                        String UserUID = (String) document.get("postUserUID");
                        db.collection("Users").document(UserUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    String userNick = (String) doc.get("userNick");
                                    quest_usernick.setText(userNick);

                                    String userImg = (String) doc.get("userImg");
                                    if(userImg != null){
                                        Glide.with(getActivity())
                                                .load(Uri.parse(userImg))
                                                .into(questuserprofile);
                                    }
                                    Log.d(TAG, doc.getId() + " " + UserUID + userNick);
                                }
                            }
                        });
                        Log.d(TAG, document.getId() + " => " + document.getData()+ " " + UserUID + " "  );
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });


    }
}








