package com.example.chuboy.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

/**
 * Created by ChuBoy on 2017/7/26.
 */
//此類別為用來查看當前執行緒
public class MyIntentService extends IntentService {
    public MyIntentService(){
        super("MyIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent){
        Log.d("MyIntentService", "Thead id is " + Thread.currentThread().getId());
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyIntentService","onDestroy executed");
    }
}
