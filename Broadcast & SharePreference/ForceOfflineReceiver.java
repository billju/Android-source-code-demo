package com.example.chuboy.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by ChuBoy on 2017/7/24.
 */

public class ForceOfflineReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent){
        /*
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage("You are forced to be offline. Please try to login again.");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();      //銷毀所有活動
                Intent intent = new Intent(context,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);      //重新啟動LoginActivity
            }
        });
        //設置AlertDialog的類型
        AlertDialog alertDialog = dialogBuilder.create();
        //此視窗需要到manifests中註冊權限
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
        */
        Toast.makeText(context, "You are forced to be offline. Please try to login again.", Toast.LENGTH_LONG).show();
        ActivityCollector.finishAll();      //銷毀所有活動
        intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    //更換頁面的模式
        context.startActivity(intent);      //重新啟動LoginActivity
    }
}
