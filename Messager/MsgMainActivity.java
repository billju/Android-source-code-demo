package com.example.chu.messager;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MsgMainActivity extends AppCompatActivity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initMsgs();
        adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    //當有新消息時，刷新ListView中的顯示
                    adapter.notifyDataSetChanged();
                    //將ListView定位到最後一行
                    msgListView.setSelection(msgList.size());
                    //清空輸入框的內容
                    inputText.setText("");
                }
            }
        });
    }
    private void initMsgs(){
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECIEVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. How are you?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Dick. Nice to chat with you.", Msg.TYPE_RECIEVED);
        msgList.add(msg3);
    }
}