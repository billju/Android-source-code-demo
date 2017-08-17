package com.example.chuboy.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sendNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNotice = (Button)findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_notice:
                //震動的週期{靜止時長,震動時長,靜止時長,震動時長} 並且取得權限才能震動
                long[] vibrates = {0,1000,1000,1000};
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                PendingIntent BigViewPI = PendingIntent.getService(this,0,BigViewIntent,0);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello, how do you do today?")
                        .setVibrate(vibrates)
                        .setLights(Color.GREEN,1000,1000);
						.addAction(R.drawable.state_dismiss,"close",BigViewPI)
                        .addAction(R.drawable.state_check,"check",BigViewPI);                        
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.setContentIntent(pi);
                manager.notify(1, mBuilder.build());
                break;
            default:
                break;
        }
    }
}
