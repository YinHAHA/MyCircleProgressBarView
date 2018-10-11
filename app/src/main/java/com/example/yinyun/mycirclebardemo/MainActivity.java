package com.example.yinyun.mycirclebardemo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private CircleProgressBarView mCircleProgressBarView;
    private TextView mProgressText;

    private CircleProgressBarView mCircleProgressColorView;
    private TextView mProgressColorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCircleProgressBarView();
        setCircleProgressColorView();

    }

    private void setCircleProgressBarView() {
        mCircleProgressBarView = findViewById(R.id.circle_view);
        mProgressText = findViewById(R.id.text_progress);
        mCircleProgressBarView.setTextView(mProgressText);
        mCircleProgressBarView.setOnAnimationListener(new CircleProgressBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
//                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                String s = (int)(interpolatedTime * updateNum / maxNum * 100) + "%";
                return s;
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {

            }
        });

        startRefresh();
    }

    private void setCircleProgressColorView() {
        mCircleProgressColorView = findViewById(R.id.circle_view_color);
        mProgressColorText = findViewById(R.id.text_progress_color);
        mCircleProgressColorView.setTextView(mProgressColorText);
        mCircleProgressColorView.setOnAnimationListener(new CircleProgressBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
//                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                String s = (int)(interpolatedTime * updateNum / maxNum * 100) + "%";
                return s;
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil(Color.YELLOW,Color.RED);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
        mCircleProgressColorView.setProgressNum(100,3000);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mCircleProgressBarView.setProgressNum(msg.arg1,0);
        }
    };

    private void startRefresh() {

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 1; i < 101; i++) {
                        SystemClock.sleep(1000);

                        Message message = new Message();
                        message.arg1 = i;
                        mHandler.sendMessage(message);
                    }

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
