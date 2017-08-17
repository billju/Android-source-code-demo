package com.example.chu.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinlLayRoot;
    private TextView mTxtDemo;
    private Button mBtnDrop, mBtnTransparent, mBtnRotate, mBtnScale, mBtnShift, mBtnChangeColor;
    private float y, yEnd;
    private boolean mIsFallingFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinlLayRoot = (LinearLayout) findViewById(R.id.linLayRoot);
        mTxtDemo = (TextView) findViewById(R.id.txtDemo);
        mBtnDrop = (Button) findViewById(R.id.btnDrop);
        mBtnTransparent = (Button) findViewById(R.id.btnTransparent);
        mBtnRotate = (Button) findViewById(R.id.btnRotate);

        mBtnScale = (Button) findViewById(R.id.btnScale);
        mBtnShift = (Button) findViewById(R.id.btnShift);
        mBtnChangeColor = (Button) findViewById(R.id.btnChangeColor);

        mBtnDrop.setOnClickListener(btnDropOnClick);
        mBtnTransparent.setOnClickListener(btnTransparentOnClick);
        mBtnRotate.setOnClickListener(btnRotateOnClick);
        mBtnScale.setOnClickListener(btnScaleOnClick);
        mBtnShift.setOnClickListener(btnShiftOnClick);
        mBtnChangeColor.setOnClickListener(btnChangeColorOnClick);
    }
    private View.OnClickListener btnDropOnClick = new View.OnClickListener(){
        public void onClick(View v){
            if(mIsFallingFirst){
                y = mTxtDemo.getY();
                yEnd = mTxtDemo.getHeight() - mTxtDemo.getHeight();
                mIsFallingFirst = false;
            }
            ObjectAnimator animTxtFalling = ObjectAnimator.ofFloat(mTxtDemo, "y", yEnd, y);
            animTxtFalling.setDuration(2000);
            animTxtFalling.setRepeatCount(ObjectAnimator.INFINITE);
            animTxtFalling.setInterpolator(new BounceInterpolator());
            animTxtFalling.start();
        }
    };
    private View.OnClickListener btnTransparentOnClick = new View.OnClickListener(){
        public void onClick(View v){
            ObjectAnimator animTxtAlpha = ObjectAnimator.ofFloat(mTxtDemo, "alpha", 1, 0);
            animTxtAlpha.setDuration(2000);
            animTxtAlpha.setRepeatCount(1);
            animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
            animTxtAlpha.setInterpolator(new LinearInterpolator());
            animTxtAlpha.start();
        }
    };
    private View.OnClickListener btnRotateOnClick = new View.OnClickListener(){
        public void onClick(View v){
            ObjectAnimator animTxtRotate = ObjectAnimator.ofFloat(mTxtDemo, "rotation", 0, 360);
            animTxtRotate.setDuration(3000);
            animTxtRotate.setRepeatCount(1);
            animTxtRotate.setRepeatMode(ObjectAnimator.REVERSE);
            animTxtRotate.setInterpolator(new AccelerateDecelerateInterpolator());
            animTxtRotate.start();
        }
    };
    private View.OnClickListener btnScaleOnClick = new View.OnClickListener(){
        public void onClick(View v){
            ValueAnimator animTxtScale = ValueAnimator.ofInt(0, 35);
            animTxtScale.setDuration(4000);
            animTxtScale.setRepeatCount(1);
            animTxtScale.setRepeatMode(ObjectAnimator.REVERSE);
            animTxtScale.setInterpolator(new LinearInterpolator());
            animTxtScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int val = (Integer)animation.getAnimatedValue();
                    mTxtDemo.setTextSize(TypedValue.COMPLEX_UNIT_SP,15+val);
                }
            });
            animTxtScale.start();
        }
    };
    private View.OnClickListener btnShiftOnClick = new View.OnClickListener(){
      public void onClick(View v){
          float x, xEnd1, xEnd2;
          x = mTxtDemo.getX();
          xEnd1=0;
          xEnd2=mLinlLayRoot.getWidth()-mTxtDemo.getWidth();
          ObjectAnimator animTxtMove1 = ObjectAnimator.ofFloat(mTxtDemo, "x", x, xEnd1);
          animTxtMove1.setDuration(2000);
          animTxtMove1.setInterpolator(new AccelerateDecelerateInterpolator());

          ObjectAnimator animTxtMove2 = ObjectAnimator.ofFloat(mTxtDemo, "x", xEnd1, xEnd2);
          animTxtMove2.setDuration(3000);
          animTxtMove2.setInterpolator(new AccelerateDecelerateInterpolator());

          ObjectAnimator animTxtMove3 = ObjectAnimator.ofFloat(mTxtDemo, "x", xEnd2, x);
          animTxtMove3.setDuration(2000);
          animTxtMove3.setInterpolator(new AccelerateDecelerateInterpolator());

          AnimatorSet animTxtMove = new AnimatorSet();
          animTxtMove.playSequentially(animTxtMove1, animTxtMove2, animTxtMove3);
          animTxtMove.start();
      }
    };
    private View.OnClickListener btnChangeColorOnClick = new View.OnClickListener(){
      public void onClick(View v){
          int iBackColorRedVal, iBackColorReadEnd;
          final int iBackColor = ((ColorDrawable)(mLinlLayRoot.getBackground())).getColor();
          iBackColorRedVal = (iBackColor & 0x00FF0000 >> 16);
          if(iBackColorRedVal > 127)
              iBackColorReadEnd = 0;
          else
              iBackColorReadEnd = 255;
          ValueAnimator animScreenBackColor = ValueAnimator.ofInt(iBackColorRedVal, iBackColorReadEnd);
          animScreenBackColor.setDuration(3000);
          animScreenBackColor.setInterpolator(new LinearInterpolator());
          animScreenBackColor.start();
          animScreenBackColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              @Override
              public void onAnimationUpdate(ValueAnimator animation) {
                  int val = (Integer)animation.getAnimatedValue();
                  mLinlLayRoot.setBackgroundColor(iBackColor & 0xFF00FFFF | val << 16);
              }
          });
      }
    };
}
