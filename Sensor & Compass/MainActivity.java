package com.example.chuboy.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private TextView lightLevel, compassDegree;
    private ImageView compassImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightLevel = (TextView)findViewById(R.id.light_level);
        compassDegree = (TextView)findViewById(R.id.compass_degree);
        compassImg = (ImageView)findViewById(R.id.compass_img);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //最後一項是精確度 SENSOR_DELAY_GAME是最高的
        sensorManager.registerListener(lightSensor_listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, accelSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(sensorManager != null){
            sensorManager.unregisterListener(lightSensor_listener);
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener lightSensor_listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //當數值改變時
             float value = event.values[0];
            lightLevel.setText("Current light level is "+value+"lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private SensorEventListener listener = new SensorEventListener() {
        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegree;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                //傳入值的時候要用clone()
                accelerometerValues = event.values.clone();
            }else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticValues = event.values.clone();
            }
            float[] R = new float[9];
            float[] values = new float[3];
            //[0]是Z軸[1]是X軸[2]是Y軸
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);
            //把矩陣變成方向再變成角度
            int rotateDegree = - (int)Math.toDegrees(values[0]);
            compassDegree.setText("Degree = " + rotateDegree);
            if(Math.abs(rotateDegree - lastRotateDegree) > 1){
                RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                compassImg.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
