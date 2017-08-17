package com.example.chuboy.paperscissorsstone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ChuBoy on 2017/7/25.
 */

public class GameResultActivity extends Activity {
    private EditText mEdtCountSet, mEdtCountPlayerWin, mEdtCountComputerWin, mEdtCcuntDraw;
    private Button mBtnBackToGame;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        mEdtCountSet = (EditText)findViewById(R.id.edtCountSet);
        mEdtCountPlayerWin = (EditText)findViewById(R.id.edtCountPlayerWin);
        mEdtCountComputerWin = (EditText)findViewById(R.id.edtComputerWin);
        mEdtCcuntDraw = (EditText)findViewById(R.id.edtCountDraw);
        mBtnBackToGame = (Button)findViewById(R.id.btnBackToGame);
        mBtnBackToGame.setOnClickListener(btnBackToGameOnClick);
        showResult();
    }
    private void showResult(){
        //從bundle物件中取出資料
        Bundle bundle = getIntent().getExtras();
        mEdtCountSet.setText(Integer.toString(bundle.getInt("countset")));
        mEdtCountPlayerWin.setText(Integer.toString(bundle.getInt("playerwin")));
        mEdtCountComputerWin.setText(Integer.toString(bundle.getInt("comwin")));
        mEdtCcuntDraw.setText(Integer.toString(bundle.getInt("draw")));
    };
    private View.OnClickListener btnBackToGameOnClick = new View.OnClickListener(){
        public void onClick(View v){
            finish();
        }
    };
}
