package com.example.plant01.home;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.plant01.R;
import com.example.plant01.garden.garden_MyPlants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home_MyService extends Service {
    NotificationManager Notifi_M;
    home_ServiceThread thread, thread1;
    Notification Notifi;
    FirebaseDatabase db;
    String maxTemper, minTemper, soilmoisture;
    String myPlantID;
    DatabaseReference databaseReference;
    public static final String CHANNEL_1_ID ="channel1";
    public static final String CHANNEL_2_ID ="channel2";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    public void gedtaa(){
//
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotifivationChannels();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


/*-------------------------온습도 데이터 받아오고 기준에 해당되면 쓰레드 실행-------------------------------------*/
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("MyDevice");
        databaseReference.child("aksjcnejas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxTemper = dataSnapshot.child("temperMaxGap").getValue().toString();
                minTemper = dataSnapshot.child("temperMinGap").getValue().toString();
                myPlantID = (String) dataSnapshot.child("myPlantID").getValue();
                soilmoisture = dataSnapshot.child("soilMoisture").getValue().toString();
                if(Double.parseDouble(maxTemper) < 0.0 ){
                    myServiceHandler handler = new myServiceHandler();
                    thread = new home_ServiceThread(handler);
                    thread.start();
                }
                if(Double.parseDouble(soilmoisture) > 850 ){
                    myPlantHum handler1 = new myPlantHum();
                    thread1 = new home_ServiceThread(handler1);
                    thread1.start();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//                if(Double.parseDouble(soilmoisture) < 40){
//                    myPlantHum handler1 = new myPlantHum();
//                    thread = new home_ServiceThread(handler1);
//                    thread.start();
//                }
//
//                Log.e("Myservice", String.valueOf(maxTemper));
//                if(Double.parseDouble(maxTemper) < 0.0 ){
//                    myServiceHandler handler = new myServiceHandler();
//                    thread = new home_ServiceThread(handler);
//                    thread.start();
//                }
//                else if (Double.parseDouble(minTemper) < 0.0){
//
//                }
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






        return START_STICKY;
    } //서비스가 종료될 때 할 작업

    public void onDestroy() {
//        thread.stopForever();
        thread = null;

//
//        출처: https://twinw.tistory.com/50 [흰고래의꿈]
//        myServiceHandler handler = new myServiceHandler();
//        thread = new home_ServiceThread(handler);
//        thread.start();
    }

    public void start() {
        myServiceHandler handler = new myServiceHandler();
        thread = new home_ServiceThread(handler);
        thread.start();
    }

    public void stop() {
        myServiceHandler handler = new myServiceHandler();

        thread = new home_ServiceThread(handler);

    }

    @SuppressLint("HandlerLeak")
    public class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {



            NotificationCompat.Builder builder = new NotificationCompat.Builder(home_MyService.this, CHANNEL_1_ID);
            builder.setSmallIcon(R.drawable.plantnotice);
            builder.setContentTitle("🚨🚨🚨온도경고🚨🚨🚨");
            builder.setContentText("온도가 너무 높아요!🌡️🌡️🌡️🌡️ 확인해주세요");

            Intent intent = new Intent(home_MyService.this, garden_MyPlants.class);
            intent.putExtra("myplantid", myPlantID);
            //            startActivity(intent);
            Log.e("온도myplantid", myPlantID);
            PendingIntent pendingIntent = PendingIntent.getActivity(home_MyService.this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);


            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.plantnotice);
            builder.setLargeIcon(largeIcon);
            Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(home_MyService.this, RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(ringtoneUri);

            long[] vibrate = {0, 100, 200, 300};
            builder.setVibrate(vibrate);
            builder.setAutoCancel(true);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                manager.createNotificationChannel(new NotificationChannel(CHANNEL_1_ID, "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
//            }
            if(Double.parseDouble(maxTemper) < 0.0 ){
//                        myServiceHandler handler = new myServiceHandler();
                manager.notify(1, builder.build());
            }
            else if (Double.parseDouble(minTemper) < 0.0){

            }
//            db = FirebaseDatabase.getInstance();
//            databaseReference = db.getReference("MyDevice");
//            databaseReference.child("aksjcnejas").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    maxTemper = dataSnapshot.child("temperMaxGap").getValue().toString();
//                    minTemper = dataSnapshot.child("temperMinGap").getValue().toString();
//                    myPlantID = (String) dataSnapshot.child("myPlantID").getValue();
//                    soilmoisture = dataSnapshot.child("soilMoisture").getValue().toString();
//
////                    if(Double.parseDouble(soilmoisture) < 40){
////                        myPlantHum handler1 = new myPlantHum();
////                        thread = new home_ServiceThread(handler1);
////                        thread.start();
////                    }
//
//                    Log.e("Myservice", String.valueOf(maxTemper));
//
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//
//            });


//            thread.isTemRun = false;
            thread.stopForever();
//            onDestroy();
        }
    }
    @SuppressLint("HandlerLeak")
    public class myPlantHum extends Handler {


        @Override
        public void handleMessage(android.os.Message msg) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(home_MyService.this, CHANNEL_2_ID);
            builder.setSmallIcon(R.drawable.plantnotice);
            builder.setContentTitle("🚨🚨🚨습도경고🚨🚨🚨");
            builder.setContentText("습도토양습도가 너무 건조해요! 물을 주세요 💧💧💧💧");

            Intent intent = new Intent(home_MyService.this, garden_MyPlants.class);
            intent.putExtra("myplantid", myPlantID);
//            startActivity(intent);
            Log.e("습도myplantid", myPlantID);
            PendingIntent pendingIntent = PendingIntent.getActivity(home_MyService.this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.plantnotice);
            builder.setLargeIcon(largeIcon);
            Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(home_MyService.this, RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(ringtoneUri);

            long[] vibrate = {0, 100, 200, 300};
            builder.setVibrate(vibrate);
            builder.setAutoCancel(true);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                manager.createNotificationChannel(new NotificationChannel("1", "기본채널", NotificationManager.IMPORTANCE_DEFAULT));
//            }

            if(Double.parseDouble(soilmoisture) > 850 ){
//                        myServiceHandler handler = new myServiceHandler();
                manager.notify(2, builder.build());

            }
            else if (Double.parseDouble(minTemper) < 0.0){

            }

            thread1.stopForever();
//            onDestroy();
        }
    }


    public void createNotifivationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("This is Channel1");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Channel2", NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("This is Channel2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

}


//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        db = FirebaseDatabase.getInstance();
////        databaseReference = db.getReference("MyDevice");
////
////        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
////        databaseReference.child("aksjcnejas").child("temperMaxGap").addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                maxTemper = dataSnapshot.getValue().toString();
////                Log.e("value", String.valueOf(maxTemper));
////                if(Double.parseDouble(maxTemper) < 0.0 ){
////
////                }
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////
////        });
////        databaseReference.child("aksjcnejas").child("temperMinGap").addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                minTemper = dataSnapshot.getValue().toString();
//////                Log.e("value", value);
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////
////        });
//
//        myServiceHandler handler = new myServiceHandler();
//        thread = new home_ServiceThread(handler);
//        thread.start();
//        return START_STICKY;
//    }
//
//    //서비스가 종료될 때 할 작업
//
//    public void onDestroy() {
//        thread.stopForever();
//        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
//    }
//
//    class myServiceHandler extends Handler {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            Intent intent = new Intent(home_MyService.this, noticebar.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(home_MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//            Notifi = new Notification.Builder(getApplicationContext())
//                    .setContentTitle("Content Title")
//                    .setContentText("Content Text")
//                    .setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .setTicker("알림!!!")
//                    .setContentIntent(pendingIntent)
//                    .build();
//
//            //소리추가
//            Notifi.defaults = Notification.DEFAULT_SOUND;
//
//            //알림 소리를 한번만 내도록
//            Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
//
//            //확인하면 자동으로 알림이 제거 되도록
//            Notifi.flags = Notification.FLAG_AUTO_CANCEL;
//
//
//            Notifi_M.notify( 777 , Notifi);
//
//            //토스트 띄우기
//            Toast.makeText(home_MyService.this, "뜸?", Toast.LENGTH_LONG).show();
//        }
//    };
//}


