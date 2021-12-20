package com.chucai.hotel.ac.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherLive;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.MainActivity_new;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.chucai.hotel.ac.MainActivity_new.dayTimeFormate;
import static com.chucai.hotel.ac.MainActivity_new.secTimeFormate;

public class TopWeatherWidget extends RelativeLayout {
    private ImageView mTianQiImage;
    private TextView mTianQiName;
    private TextView mWeekTv;
    private TextView mDateTv;
    private TextView mTimeTv;
    private TextView mNameTv;
    private TextView mSetp1Tv;
    private ImageView mD1Iv;
    private TextView mSetp2Tv;
    private ImageView mD2Iv;
    private TextView mSetp3Tv;
    private ImageView mD3Iv;
    private TextView mSetp4Tv;
    private ImageView mD4Iv;
    private TextView mSetp5Tv;
    private int type;
    private LinearLayout mRuzhuLayout;
    private LinearLayout mXuZhuLayout;
    private TextView mDSetp1Tv;
    private ImageView mDxIv;
    private TextView mDSetp2Tv;
    private ImageView mDx2Iv;
    private TextView mDSetp3Tv;
    private ImageView tianQiImage;
    private TextView tianQiName;
    private TextView weekTv;
    private TextView dateTv;
    private TextView timeTv;
    private TextView nameTv;
    private LinearLayout ruzhuLayout;
    private TextView setp1Tv;
    private ImageView d1Iv;
    private TextView setp2Tv;
    private ImageView d2Iv;
    private TextView setp3Tv;
    private ImageView d3Iv;
    private TextView setp4Tv;
    private ImageView d4Iv;
    private TextView setp5Tv;
    private LinearLayout xuZhuLayout;
    private TextView dSetp1Tv;
    private ImageView dxIv;
    private TextView dSetp2Tv;
    private ImageView dx2Iv;
    private TextView dSetp3Tv;
    private LinearLayout bookZhuLayout;
    private TextView bSetp1Tv;
    private ImageView bIv;
    private TextView bSetp2Tv;
    private ImageView bx2Iv;
    private TextView bSetp3Tv;
    private ImageView bx3Iv;
    private TextView bSetp4Tv;


    public void setType(int type) {
        this.type = type;
        if (type == 0) {
            mXuZhuLayout.setVisibility(View.VISIBLE);
            mRuzhuLayout.setVisibility(View.GONE);
            bookZhuLayout.setVisibility(View.GONE);
        } else if (type == 1) {
            mXuZhuLayout.setVisibility(View.GONE);
            mRuzhuLayout.setVisibility(View.VISIBLE);
            bookZhuLayout.setVisibility(View.GONE);
        } else if (type == 2) {
            mXuZhuLayout.setVisibility(View.GONE);
            mRuzhuLayout.setVisibility(View.GONE);
            bookZhuLayout.setVisibility(View.VISIBLE);
        }
    }

    static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public TopWeatherWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.top_weather_layout, null);
        initView(view);
        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, lps);
    }


    private void initView(View view) {
        mTianQiImage = (ImageView) view.findViewById(R.id.tianQi_image);
        mTianQiName = (TextView) view.findViewById(R.id.tian_qi_name);
        mWeekTv = (TextView) view.findViewById(R.id.week_tv);
        mDateTv = (TextView) view.findViewById(R.id.date_tv);
        mTimeTv = (TextView) view.findViewById(R.id.time_tv);
        mNameTv = (TextView) view.findViewById(R.id.name_tv);
        mSetp1Tv = (TextView) view.findViewById(R.id.setp_1_tv);
        mD1Iv = (ImageView) view.findViewById(R.id.d1_iv);
        mSetp2Tv = (TextView) view.findViewById(R.id.setp_2_tv);
        mD2Iv = (ImageView) view.findViewById(R.id.d2_iv);
        mSetp3Tv = (TextView) view.findViewById(R.id.setp_3_tv);
        mD3Iv = (ImageView) view.findViewById(R.id.d3_iv);
        mSetp4Tv = (TextView) view.findViewById(R.id.setp_4_tv);
        mD4Iv = (ImageView) view.findViewById(R.id.d4_iv);
        mSetp5Tv = (TextView) view.findViewById(R.id.setp_5_tv);


        mRuzhuLayout = (LinearLayout) view.findViewById(R.id.ruzhu_layout);
        mXuZhuLayout = (LinearLayout) view.findViewById(R.id.xu_zhu_layout);
        mDSetp1Tv = (TextView) view.findViewById(R.id.d_setp_1_tv);
        mDxIv = (ImageView) view.findViewById(R.id.dx_iv);
        mDSetp2Tv = (TextView) view.findViewById(R.id.d_setp_2_tv);
        mDx2Iv = (ImageView) view.findViewById(R.id.dx2_iv);
        mDSetp3Tv = (TextView) view.findViewById(R.id.d_setp_3_tv);
        setStep(1);

        tianQiImage = (ImageView) view. findViewById(R.id.tianQi_image);
        tianQiName = (TextView) view. findViewById(R.id.tian_qi_name);
        weekTv = (TextView)  view.findViewById(R.id.week_tv);
        dateTv = (TextView) view. findViewById(R.id.date_tv);
        timeTv = (TextView)  view.findViewById(R.id.time_tv);
        nameTv = (TextView)  view.findViewById(R.id.name_tv);
        ruzhuLayout = (LinearLayout)  view.findViewById(R.id.ruzhu_layout);
        setp1Tv = (TextView)  view.findViewById(R.id.setp_1_tv);
        d1Iv = (ImageView)  view.findViewById(R.id.d1_iv);
        setp2Tv = (TextView)  view.findViewById(R.id.setp_2_tv);
        d2Iv = (ImageView)  view.findViewById(R.id.d2_iv);
        setp3Tv = (TextView)  view.findViewById(R.id.setp_3_tv);
        d3Iv = (ImageView) view. findViewById(R.id.d3_iv);
        setp4Tv = (TextView)  view.findViewById(R.id.setp_4_tv);
        d4Iv = (ImageView)  view.findViewById(R.id.d4_iv);
        setp5Tv = (TextView) view. findViewById(R.id.setp_5_tv);
        xuZhuLayout = (LinearLayout)  view.findViewById(R.id.xu_zhu_layout);
        dSetp1Tv = (TextView)  view.findViewById(R.id.d_setp_1_tv);
        dxIv = (ImageView)  view.findViewById(R.id.dx_iv);
        dSetp2Tv = (TextView) view. findViewById(R.id.d_setp_2_tv);
        dx2Iv = (ImageView) view. findViewById(R.id.dx2_iv);
        dSetp3Tv = (TextView) view. findViewById(R.id.d_setp_3_tv);
        bookZhuLayout = (LinearLayout) view. findViewById(R.id.book_zhu_layout);
        bSetp1Tv = (TextView)  view.findViewById(R.id.b_setp_1_tv);
        bIv = (ImageView) view. findViewById(R.id.b_iv);
        bSetp2Tv = (TextView)  view.findViewById(R.id.b_setp_2_tv);
        bx2Iv = (ImageView)  view.findViewById(R.id.bx2_iv);
        bSetp3Tv = (TextView) view. findViewById(R.id.b_setp_3_tv);
        bx3Iv = (ImageView)  view.findViewById(R.id.bx3_iv);
        bSetp4Tv = (TextView)  view.findViewById(R.id.b_setp_4_tv);
        showPageWeatherInfo();

    }

    private void showPageWeatherInfo() {
        LocalWeatherLive slocalWeatherLive = MainActivity_new.getSlocalWeatherLive();
        if(slocalWeatherLive!=null) {
            slocalWeatherLive.getTemperature();
            Calendar cal = Calendar.getInstance();
            int i = cal.get(Calendar.HOUR_OF_DAY);

            String name = "weather/day/undefined" + slocalWeatherLive.getWeather() + ".png";
            if (i > 7 && i < 19) {
                name = "weather/day/" + slocalWeatherLive.getWeather() + ".png";
            } else {
                name = "weather/night/" + slocalWeatherLive.getWeather() + ".png";
            }
            try {
                InputStream open = getResources().getAssets().open(name);
                tianQiImage.setImageBitmap(BitmapFactory.decodeStream(open));
                tianQiName.setText(slocalWeatherLive.getTemperature() + "℃  " + slocalWeatherLive.getWeather());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void onResume() {

        startPageTime();
    }


    public void onPause() {
        if (timeTimer != null) {
            timeTimer.cancel();
        }
    }

    private Timer timeTimer;

    public void startPageTime() {
        if (timeTimer != null) {
            timeTimer.cancel();
        }


        timeTimer = new Timer();
        timeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        mWeekTv.setText(weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                        Date d = new Date();
                        mDateTv.setText(dayTimeFormate.format(d));
                        mTimeTv.setText(secTimeFormate.format(d));


                    }
                });

            }
        }, 0, 1000);

    }


    public void setStep(int step) {
        if (type == 1) {
            if (step == 1) {
                mSetp1Tv.setAlpha(1f);
                mD1Iv.setAlpha(0.5f);
                mSetp2Tv.setAlpha(0.5f);
                mD2Iv.setAlpha(0.5f);
                mSetp3Tv.setAlpha(0.5f);
                mD3Iv.setAlpha(0.5f);
                mSetp4Tv.setAlpha(0.5f);
                mD4Iv.setAlpha(0.5f);
                mSetp5Tv.setAlpha(0.5f);
            } else if (step == 2) {
                mSetp1Tv.setAlpha(1f);
                mD1Iv.setAlpha(1f);
                mSetp2Tv.setAlpha(1f);
                mD2Iv.setAlpha(0.5f);
                mSetp3Tv.setAlpha(0.5f);
                mD3Iv.setAlpha(0.5f);
                mSetp4Tv.setAlpha(0.5f);
                mD4Iv.setAlpha(0.5f);
                mSetp5Tv.setAlpha(0.5f);
            } else if (step == 3) {
                mSetp1Tv.setAlpha(1f);
                mD1Iv.setAlpha(1f);
                mSetp2Tv.setAlpha(1f);
                mD2Iv.setAlpha(1f);
                mSetp3Tv.setAlpha(1f);
                mD3Iv.setAlpha(0.5f);
                mSetp4Tv.setAlpha(0.5f);
                mD4Iv.setAlpha(0.5f);
                mSetp5Tv.setAlpha(0.5f);
            } else if (step == 4) {
                mSetp1Tv.setAlpha(1f);
                mD1Iv.setAlpha(1f);
                mSetp2Tv.setAlpha(1f);
                mD2Iv.setAlpha(1f);
                mSetp3Tv.setAlpha(1f);
                mD3Iv.setAlpha(01f);
                mSetp4Tv.setAlpha(1f);
                mD4Iv.setAlpha(0.5f);
                mSetp5Tv.setAlpha(0.5f);
            } else if (step == 5) {
                mSetp1Tv.setAlpha(1f);
                mD1Iv.setAlpha(1f);
                mSetp2Tv.setAlpha(1f);
                mD2Iv.setAlpha(1f);
                mSetp3Tv.setAlpha(1f);
                mD3Iv.setAlpha(1f);
                mSetp4Tv.setAlpha(1f);
                mD4Iv.setAlpha(1f);
                mSetp5Tv.setAlpha(1f);
            }
        } else if (type == 0) {
            if (step == 1) {
                mDSetp1Tv.setAlpha(1f);
                mDSetp2Tv.setAlpha(0.5f);
                mDSetp3Tv.setAlpha(0.5f);
                mDx2Iv.setAlpha(0.5f);
                mDxIv.setAlpha(0.5f);
            } else if (step == 2) {
                mDSetp1Tv.setAlpha(1f);
                mDSetp2Tv.setAlpha(1f);
                mDSetp3Tv.setAlpha(0.5f);
                mDx2Iv.setAlpha(0.5f);
                mDxIv.setAlpha(1f);
            } else if (step == 3) {
                mDSetp1Tv.setAlpha(1f);
                mDSetp2Tv.setAlpha(1f);
                mDSetp3Tv.setAlpha(1f);
                mDx2Iv.setAlpha(1f);
                mDxIv.setAlpha(1f);
            }

        } else if (type == 2) {
            if (step == 1) {
                bSetp1Tv.setAlpha(1f);
                bSetp2Tv.setAlpha(0.5f);
                bSetp3Tv.setAlpha(0.5f);
                bSetp4Tv.setAlpha(0.5f);
                bIv.setAlpha(0.5f);
                bx2Iv.setAlpha(0.5f);
                bx3Iv.setAlpha(0.5f);
            } else if (step == 2) {
                bSetp1Tv.setAlpha(1f);
                bSetp2Tv.setAlpha(1f);
                bSetp3Tv.setAlpha(0.5f);
                bSetp4Tv.setAlpha(0.5f);
                bIv.setAlpha(1f);
                bx2Iv.setAlpha(0.5f);
                bx3Iv.setAlpha(0.5f);
            } else if (step == 3) {
                bSetp1Tv.setAlpha(1f);
                bSetp2Tv.setAlpha(1f);
                bSetp3Tv.setAlpha(1f);
                bSetp4Tv.setAlpha(0.5f);
                bIv.setAlpha(1f);
                bx2Iv.setAlpha(1f);
                bx3Iv.setAlpha(0.5f);
            } else if (type == 4) {
                bSetp1Tv.setAlpha(1f);
                bSetp2Tv.setAlpha(1f);
                bSetp3Tv.setAlpha(1f);
                bSetp4Tv.setAlpha(1f);
                bIv.setAlpha(1f);
                bx2Iv.setAlpha(1f);
                bx3Iv.setAlpha(1f);
            }

        }

    }
}
