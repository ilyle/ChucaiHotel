package com.chucai.hotel.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.fragment.BookComfineFragment;
import com.chucai.hotel.ac.fragment.CheckRoomFragment;
import com.chucai.hotel.ac.fragment.ComPayFragment;
import com.chucai.hotel.ac.fragment.PaySuccessFragment;
import com.chucai.hotel.ac.fragment.PhoneFragement;
import com.chucai.hotel.ac.widget.TopWeatherWidget;
import com.chucai.hotel.bean.BookMessage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderSearchActivity extends AppCompatActivity {

    PhoneFragement phoneFragement;
    CheckRoomFragment showCheckRoomFragement;
    BookComfineFragment bookComfineFragment;
    private TopWeatherWidget mTopWeatherLayout;
    private FrameLayout mContent;
    private TextView mSecTimeTv;
    private ImageView mBackBtn;
    ComPayFragment comPayFragment;
    PaySuccessFragment paySuccessFragment;


    public void showPaySuccess(){
        paySuccessFragment=new PaySuccessFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, paySuccessFragment).commit();
        startPageCountTime(10);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        initView();
        mTopWeatherLayout.setType(2);
        mTopWeatherLayout.setStep(1);




        showPhoneFragment();
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String phone = getIntent().getStringExtra("phone");
        if(!TextUtils.isEmpty(phone)){
           BookMessage.DataDTO  orderListDTOS = JSON.parseObject(getIntent().getStringExtra("data"),BookMessage.DataDTO.class);
            showBookComfineFragment(orderListDTOS);
        }


//        showCheckRoomFagement(null);
    }

    private void initView() {
        mTopWeatherLayout = (TopWeatherWidget) findViewById(R.id.top_weather_layout);
        mContent = (FrameLayout) findViewById(R.id.content);
        mTopWeatherLayout = (TopWeatherWidget) findViewById(R.id.top_weather_layout);
        mContent = (FrameLayout) findViewById(R.id.content);
        mSecTimeTv = (TextView) findViewById(R.id.sec_time_tv);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
    }

    public void showPhoneFragment() {
        phoneFragement = new PhoneFragement(2);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, phoneFragement).commit();
        mTopWeatherLayout.setStep(1);

    }

    public void showCheckRoomFagement(BookMessage.DataDTO dataDTOXXXX) {
        showCheckRoomFragement = new CheckRoomFragment(1);
        showCheckRoomFragement.setData(1,dataDTOXXXX);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, showCheckRoomFragement).commit();
        mTopWeatherLayout.setStep(2);

    }


//    public void showBookComfineFragment(DataDTOXXXX dataDTOXXXX){
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.content,bookComfineFragment).commit();
//        bookComfineFragment.setData(dataDTOXXXX);
//        topWeatherLayout.setStep(3);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mTopWeatherLayout.onResume();
        startPageCountTime(180);
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

    private void startPageCountTime(int t) {
        final int time[] = {
                t
        };
        if (pageCountTimer != null) {
            pageCountTimer.cancel();
        }
            pageCountTimer = new Timer();
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

    public void showBookComfineFragment(BookMessage.DataDTO orderListDTOS){
        bookComfineFragment = new BookComfineFragment();
        mTopWeatherLayout.setStep(3);
        bookComfineFragment.setOrderListDTOS(orderListDTOS);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, bookComfineFragment).commit();
    }

    public void showPayFragment(int type, BookMessage.DataDTO orderListDTO){
        comPayFragment=new ComPayFragment();
        comPayFragment.setData(type,orderListDTO);
        mTopWeatherLayout.setStep(4);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, comPayFragment).commit();
    }


}