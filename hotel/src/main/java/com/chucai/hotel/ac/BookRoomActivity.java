package com.chucai.hotel.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.chucai.hotel.ac.fragment.BookRoomFragment;
import com.chucai.hotel.ac.fragment.CheckRoomFragment;
import com.chucai.hotel.ac.fragment.ComPayFragment;
import com.chucai.hotel.ac.fragment.PaySuccessFragment;
import com.chucai.hotel.ac.fragment.PhoneFragement;
import com.chucai.hotel.ac.widget.TopWeatherWidget;
import com.chucai.hotel.bean.BookMessage;

import java.util.Timer;
import java.util.TimerTask;

public class BookRoomActivity extends AppCompatActivity {

    private TopWeatherWidget mTopWeatherLayout;
    private FrameLayout mContent;
    PhoneFragement phoneFragement;

    BookRoomFragment bookRoomFragment;
    CheckRoomFragment showCheckRoomFragement;
    private TextView mSecTimeTv;
    private ImageView mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        initView();
        mTopWeatherLayout.setType(1);
        payFragment = new ComPayFragment();
        showCheckRoomFragement = new CheckRoomFragment(0);
        this.showBookRoomFragment("15857769206");
        //showPhoneFragment();
                //showCheckRoomFagement();
        //showBookRoomFragment();

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String phone = getIntent().getStringExtra("phone");
        if(!TextUtils.isEmpty(phone)){
            showBookRoomFragment(phone);
        }


    }

    public void showBookRoomFragment(String phone) {
        bookRoomFragment = new BookRoomFragment(0);
        bookRoomFragment.setData(phone);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, bookRoomFragment).commit();
        mTopWeatherLayout.setStep(2);

    }


    public void showCheckRoomFagement( BookMessage.DataDTO orderListDTO) {
        showCheckRoomFragement.setData(0,orderListDTO);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, showCheckRoomFragement).commit();
        mTopWeatherLayout.setStep(3);

    }

    public void showPhoneFragment() {
        phoneFragement = new PhoneFragement(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, phoneFragement).commit();
        mTopWeatherLayout.setStep(1);

    }
    ComPayFragment payFragment;

    public void showPayFragment(int type, BookMessage.DataDTO orderListDTO) {
        payFragment=new ComPayFragment();
        mTopWeatherLayout.setStep(4);
        payFragment.setData(type,orderListDTO);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, payFragment).commit();

    }
       PaySuccessFragment paySuccessFragment;


    public void showPaySuccess(){
        paySuccessFragment=new PaySuccessFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, paySuccessFragment).commit();
        startPageCountTime(10);
        mTopWeatherLayout.setStep(5);
    }

    private void initView() {
        mTopWeatherLayout = (TopWeatherWidget) getWindow().getDecorView().findViewById(R.id.top_weather_layout);
        mContent = (FrameLayout) getWindow().getDecorView().findViewById(R.id.content);
        mSecTimeTv = (TextView) findViewById(R.id.sec_time_tv);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
    }

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
        if(pageCountTimer!=null){
            pageCountTimer.cancel();
        }
    }

    private Timer pageCountTimer;
    private void startPageCountTime(long t){
      final   int time[]={
              (int) t
      };
        if(pageCountTimer!=null){
            pageCountTimer.cancel();
        }
        pageCountTimer=new Timer();
        pageCountTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSecTimeTv.setText(time[0]+"s");
                            time[0]--;
                            if(time[0]==0){
                                finish();
                            }

                        }
                    });
            }
        },1000,1000);
    }




}