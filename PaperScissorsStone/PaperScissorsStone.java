package com.example.chuboy.paperscissorsstone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class paperscissorsstone extends AppCompatActivity {
    private Button mBtnBrowse, mBtnEditImg, mBtnViewImg, mBtnShowResult;
    private ImageButton mImgBtnScissors, mImgBtnStone, mImgBtnPaper;
    private ImageView mImgViewComPlay;
    private TextView mTxtResult;
    //統計遊戲局數輸贏的變數
    private int miCountSet=0, miCountPlayerWin=0, miCountComputerWin=0, miCcuntDraw=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtResult = (TextView) findViewById(R.id.txtResult);
        mImgBtnPaper = (ImageButton) findViewById(R.id.imgBtnPaper);
        mImgBtnScissors = (ImageButton) findViewById(R.id.imgBtnScissors);
        mImgBtnStone = (ImageButton) findViewById(R.id.imgBtnStone);
        mImgViewComPlay = (ImageButton) findViewById(R.id.imgViewComPlay);
        mBtnShowResult = (Button)findViewById(R.id.btnShowResult);

        mImgBtnPaper.setOnClickListener(mImgBtnPaperOnClick);
        mImgBtnScissors.setOnClickListener(mImgBtnScissorsOnClick);
        mImgBtnStone.setOnClickListener(mImgBtnStoneOnClick);
        mBtnShowResult.setOnClickListener(btnShowResultOnClick);
    }

    private View.OnClickListener mImgBtnPaperOnClick = new View.OnClickListener(){
        public void onClick(View v){
            miCountSet++;
            int iComplay=(int)(Math.random()*3+1);
            if(iComplay==1){
                mImgViewComPlay.setImageResource(R.drawable.paper);
                mTxtResult.setText("判定輸贏: 平手");
                miCcuntDraw++;
            }
            else if(iComplay==2){
                mImgViewComPlay.setImageResource(R.drawable.scissors);
                mTxtResult.setText("判定輸贏: 還太嫩了");
                miCountComputerWin++;
            }
            else{
                mImgViewComPlay.setImageResource(R.drawable.stone);
                mTxtResult.setText("判定輸贏: 淫了");
                miCountPlayerWin++;
            }
        }
    };
    private View.OnClickListener mImgBtnScissorsOnClick = new View.OnClickListener(){
        public void onClick(View v){
            miCountSet++;
            int iComplay=(int)(Math.random()*3+1);
            switch(iComplay){
                case 1:
                    mImgViewComPlay.setImageResource(R.drawable.paper);
                    mTxtResult.setText("好像很厲害!?讓你一次");
                    miCountPlayerWin++;
                    break;
                case 2:
                    mImgViewComPlay.setImageResource(R.drawable.scissors);
                    mTxtResult.setText("你以為你淫了嗎");
                    miCcuntDraw++;
                    break;
                case 3:
                    mImgViewComPlay.setImageResource(R.drawable.stone);
                    mTxtResult.setText("你是弱~雞嗎");
                    miCountComputerWin++;
                    break;
                default:
                    break;
            }
        }
    };
    private View.OnClickListener mImgBtnStoneOnClick = new View.OnClickListener(){
        public void onClick(View v) {
            miCountSet++;
            int iComplay=(int)(Math.random()*3+1);
            switch(iComplay){
                case 1:
                    mImgViewComPlay.setImageResource(R.drawable.paper);
                    mTxtResult.setText("GG比你大啦");
                    miCountComputerWin++;
                    break;
                case 2:
                    mImgViewComPlay.setImageResource(R.drawable.scissors);
                    mTxtResult.setText("啊不就好棒棒");
                    miCountPlayerWin++;
                    break;
                case 3:
                    mImgViewComPlay.setImageResource(R.drawable.stone);
                    mTxtResult.setText("靠北喔");
                    miCcuntDraw++;
                    break;
                default:
                    break;
            }
        }
    };
    private View.OnClickListener btnShowResultOnClick = new View.OnClickListener(){
        public void onClick(View v){
            Intent it = new Intent().setClass(MainActivity.this, GameResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("countset", miCountSet);
            bundle.putInt("playerwin", miCountPlayerWin);
            bundle.putInt("comwin", miCountComputerWin);
            bundle.putInt("draw", miCcuntDraw);
            it.putExtras(bundle);
            startActivity(it);
        }
    };
}