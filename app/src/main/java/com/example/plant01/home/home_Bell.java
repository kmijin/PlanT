package com.example.plant01.home;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.example.plant01.R;

public class home_Bell extends AppCompatActivity {

    Toolbar toolbar;
    private NotificationManagerCompat notificationManager;
    String maxTemper , minTemper, myPlantID, soilmoisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_drawel_bell);
        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        setTitle("알림설정");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Switch noticetem = findViewById(R.id.noticeTem);

        notificationManager = NotificationManagerCompat.from(this);

        noticetem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(home_Bell.this, home_MyService.class);
                    startService(intent);

                }else {
                    Intent intent = new Intent(home_Bell.this, home_MyService.class);
                    stopService(intent);


                }
            }
        });
    }

//    public  void  sendOnChannel1(){
//        Intent intent = new Intent(this, MyPlants.class);
//        intent.putExtra("myplantid", myPlantID);
//        PendingIntent cotentIntent = PendingIntent.getActivity(this,0,intent,0);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.intro)
//                .setContentTitle("온도경고")
//                .setContentText("온도가 너무 높아요! 확인해 주세요!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//        notificationManager.notify(1,notification);
//    }
//
//    public  void  sendOnChannel2() {
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.intro)
//                .setContentTitle("습도경고")
//                .setContentText("습도가 너무 높아요! 확인해 주세요!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//        notificationManager.notify(2, notification);
//    }
//
//    public void  getTemHum(){
////        db = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MyDevice");
//        databaseReference.child("aksjcnejas").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                maxTemper = dataSnapshot.child("temperMaxGap").getValue().toString();
//                minTemper = dataSnapshot.child("temperMinGap").getValue().toString();
//                myPlantID = (String) dataSnapshot.child("myPlantID").getValue();
//                soilmoisture = dataSnapshot.child("soilMoisture").getValue().toString();
//
////                    if(Double.parseDouble(soilmoisture) < 40){
////                        myPlantHum handler1 = new myPlantHum();
////                        thread = new home_ServiceThread(handler1);
////                        thread.start();
////                    }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }


}
