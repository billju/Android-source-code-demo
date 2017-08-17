package com.example.chuboy.jsonlocation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int SHOW_LOCATION = 0;
    private TextView positionTextView;
    private LocationManager locationManager;
    private HttpURLConnection urlConnection;
    private String provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        positionTextView = (TextView)findViewById(R.id.Position_Text);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //獲取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            Toast.makeText(this, "Located by GPS ", Toast.LENGTH_SHORT).show();
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            Toast.makeText(this, "Located by Network", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Oops")
                    .setMessage("No location provider is available")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
            return;
        }
        //Permission Check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission is not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            showLocation(location); //自訂義setText
        }else{
            positionTextView.setText("location is null");
        }
        //刷新狀態(位置提供者, 每幾秒觸發一次, 每移動多少距離觸發一次, 呼叫函式)
        locationManager.requestLocationUpdates(provider, 5000 , 1, locationListener);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(locationManager != null){
            //APP結束時將監聽器關掉
            locationManager.removeUpdates(locationListener);
        }
    }
    //建立狀態改變時的監聽器
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location != null) {
                showLocation(location);
            }else{
                positionTextView.setText("location is null");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "Please turn on your network or GPS", Toast.LENGTH_SHORT).show();
        }
    };
    //final代表不會被重寫影響
    private void showLocation(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //使用urlConnection去取得GoogleApi Json格式的位置
                    URL url = new URL("http://maps.google.com/maps/api/geocode/json?latlng="
                            + location.getLatitude() + "," + location.getLongitude() +
                            "&language=zh-TW&sensor=true");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //定義一個讀取的物件並把資料讀到BufferedReader中
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    //再把資料轉進String當中，要用StringBuilder不然會讀錯
                    StringBuilder jsonStringBuilder = new StringBuilder();
                    String jsonString;
                    while((jsonString = reader.readLine()) != null){
                        jsonStringBuilder.append(jsonString);
                    }
                    reader.close();
                    Log.d("JsonSayHello",jsonStringBuilder.toString());
                    //使用JsonObject去剖析Json物件
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonStringBuilder));
                        JSONArray resultArray = jsonObject.getJSONArray("results");
                        if(resultArray.length() > 0 ){
                            JSONObject subObject = resultArray.getJSONObject(0);
                            String address = subObject.getString("formatted_address");
                            //把要更改UI的資訊丟到Message當中
                            Message message = new Message();
                            message.what = SHOW_LOCATION;
                            message.obj = address;
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {

                }

            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_LOCATION:
                    String currentPosition = (String) msg.obj;
                    positionTextView.setText(currentPosition);
                    break;
                default:
                    break;
            }
        }
    };

}
