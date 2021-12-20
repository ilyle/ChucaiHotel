package com.chucai.hotel.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.chucai.hotel.ac.widget.TopWeatherWidget;

import java.util.Timer;
import java.util.TimerTask;

public class CheckOutActvity extends AppCompatActivity {


    private TopWeatherWidget mTopWeatherLayout;
    private FrameLayout mContent;
    private TextView mSecTimeTv;
    private ImageView mBackBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        initView();
    }


    private void initView() {
        mTopWeatherLayout = (TopWeatherWidget) findViewById(R.id.top_weather_layout);
        mContent = (FrameLayout) findViewById(R.id.content);
        mSecTimeTv = (TextView) findViewById(R.id.sec_time_tv);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTopWeatherLayout.onResume();
        startPageCountTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTopWeatherLayout.onPause();
        if (pageCountTimer != null) {
            pageCountTimer.cancel();
        }
    }

    private Timer pageCountTimer;

    private void startPageCountTime() {
        final int time[] = {
                180
        };
        if (pageCountTimer != null) {
            pageCountTimer.cancel();
        }
        if (pageCountTimer == null) {
            pageCountTimer = new Timer();
        }
        pageCountTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSecTimeTv.setText(time[0] + "s");
                        time[0]--;
                        if (time[0] == 0) {
                            finish();
                        }

                    }
                });
            }
        }, 1000, 1000);


    }
}
