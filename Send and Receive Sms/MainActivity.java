package com.example.chuboy.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//非常重要!!!
//要到手機的APP Info裡去允許這個軟體使用SMS，否則會閃退
public class MainActivity extends AppCompatActivity {
    private TextView sender, content;
    private IntentFilter receiverFilter;
    private MessageReceiver messageReceiver;
    private EditText to, msgInput;
    private Button send;
    /*
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sender = (TextView)findViewById(R.id.sender);
        content = (TextView)findViewById(R.id.content);
        to = (EditText)findViewById(R.id.to);
        msgInput = (EditText)findViewById(R.id.msg_input);

        receiverFilter = new IntentFilter();
        receiverFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        //調整優先權以攔截訊息
        receiverFilter.setPriority(100);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver,receiverFilter);

        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防閃退機制
                if(!to.getText().toString().equals("")&&!msgInput.getText().toString().equals("")){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(to.getText().toString(),null,msgInput.getText().toString(),null,null);
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }
    class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            //攔截訊息
            abortBroadcast();
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages;
            if(Build.VERSION.SDK_INT>=19){
                //新版一行解決
                messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);}
            else {
                //舊版超激麻煩
                //使用pdu密鑰來取出SMS pdus數組，其中每一個pdu都代表一個短信消息
                Object[] pdus = (Object[]) bundle.get("pdus");
                //轉換成SmsMessager形式
                messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
            }
            String address = messages[0].getOriginatingAddress();   //獲取發送方號碼
            String fullMessage = "";
            for(SmsMessage message : messages){
                //getMessageBody()獲取簡訊的內容
                fullMessage += message.getMessageBody();
            }
            sender.setText(address);
            content.setText(fullMessage);
        }
    }

}
