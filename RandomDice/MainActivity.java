package com.example.chuboy.randomdice;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ImageView mImgRollingDice;
    private TextView mTxtDiceResult;
    private Button mBtnRollDice;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            int iRandom = (int)(Math.random()*6+1);
            String s = "點數 ";
            mTxtDiceResult.setText(s + iRandom);
            switch (iRandom){
                case 1:
                    mImgRollingDice.setImageResource(R.drawable.dice01);
                    break;
                case 2:
                    mImgRollingDice.setImageResource(R.drawable.dice02);
                    break;
                case 3:
                    mImgRollingDice.setImageResource(R.drawable.dice03);
                    break;
                case 4:
                    mImgRollingDice.setImageResource(R.drawable.dice04);
                    break;
                case 5:
                    mImgRollingDice.setImageResource(R.drawable.dice05);
                    break;
                case 6:
                    mImgRollingDice.setImageResource(R.drawable.dice06);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgRollingDice = (ImageView) findViewById(R.id.imgRollingDice);
        mTxtDiceResult = (TextView) findViewById(R.id.txtDiceResult);
        mBtnRollDice = (Button) findViewById(R.id.btnRollDice);

        mBtnRollDice.setOnClickListener(mBtnRollDiceOnClick);
    }
    private View.OnClickListener mBtnRollDiceOnClick = new View.OnClickListener(){
        public void onClick(View v){

            mTxtDiceResult.setText("我骰...");
            //mImgRollingDice.setBackgroundResource(R.drawable.anim_roll_dice);
            //final AnimationDrawable animationDraw = (AnimationDrawable) mImgRollingDice.getBackground();
            Resources res = getResources();
            final AnimationDrawable animationDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgRollingDice.setImageDrawable(animationDraw);
            animationDraw.start();
            final int sleeptime = (int)(Math.random()*3+1);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        //讓骰子跑5秒
                        Thread.sleep(1000*sleeptime);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    animationDraw.stop();
                    Log.d("ACTIVITY_TAG", "This is Debug.");
                    handler.sendMessage(handler.obtainMessage());
                }
            }).start();
        }
    };
}
