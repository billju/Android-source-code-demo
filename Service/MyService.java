package com.example.chuboy.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by ChuBoy on 2017/7/26.
 */

public class MyService extends Service {
    //Binder可以讓主程式介入服務中
    class DownloadBinder extends Binder {
        public void startDownload(){
            Toast.makeText(MyService.this, "startDownload", Toast.LENGTH_SHORT).show();
        }
        public int getProgress(){
            Toast.makeText(MyService.this, "showProgress", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    private DownloadBinder mBinder = new DownloadBinder();
    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    //服務的生命週期
    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("Hello, how do you do today?");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pi);
        startForeground(1,mBuilder.build());
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Toast.makeText(this, "Service onStartCommand", Toast.LENGTH_SHORT).show();
        //啟動另一個執行緒
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService", "executed at " + new Date().toString());
                // stopSelf();
            }
        }).start();
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //觸發時間為現在時間 + 毫秒
        long triggerAtTime = SystemClock.elapsedRealtime() + 5000;
        //將任務以Broadcast的方式傳給AlarmReceiver
        Intent i = new Intent(this,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        //4種類型ELAPSED_REALTIME、ELAPSED_REALTIME_WAKEUP、RTC、RTC_WAKEUP
        //觸發時間從開機開始算起、並且會喚醒CPU
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
    }
}
