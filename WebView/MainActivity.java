package com.example.chuboy.webview;

import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    /*
    //記得要加uses-permission
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;    //使用當前的WebView打開新網頁，不用跳轉到系統瀏覽器
            }
        });
        webView.loadUrl("https://www.google.com");
    }
    */
    public static final int SHOW_RESPONSE = 0;
    private Button sendRequest;
    private EditText inputUrl;
    private TextView responseText;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case SHOW_RESPONSE:
                    String response = (String)msg.obj;
                    //在此處可以進行UI操作
                    responseText.setText(response);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputUrl = (EditText)findViewById(R.id.url_input);
        sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithHttpURLConnection();
            }
        });
    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    //建立一段URL的連結
                    String url_input = inputUrl.getText().toString();
                    URL url = new URL(url_input);
                    connection = (HttpURLConnection) url.openConnection();
                    //希望從服務器那裡獲得數據
                    connection.setRequestMethod("GET");
                    //自訂連結讀取超時的毫秒數
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //對獲取到的輸入進行讀取
                    InputStream in =  connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //將內容傳給一個可以更改UI的執行緒
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = response.toString();
                    handler.sendMessage(message);
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
