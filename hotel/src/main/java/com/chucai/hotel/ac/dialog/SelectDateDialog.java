package com.chucai.hotel.ac.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.chucai.hotel.ac.widget.newcal.CalendarList;
import com.chucai.hotel.ac.widget.newcal.DateBean;
import com.xtf.xtflib.util.ScreenUtils;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectDateDialog extends Dialog {


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ConstraintLayout mContent;
    private TextView mDayStartTime;
    private TextView mStartDesc;
    private TextView mZhusuTime;
    private TextView mDayEndTime;
    private TextView mEndDesc;
    private TextView mPrice;
    private TextView mFinishBtn;
    private CalendarList mCl;
    OnFinishClick  onFinishClick;
    Date startDate;
    Date endDate;

    public void setOnFinishClick(OnFinishClick onFinishClick) {
        this.onFinishClick = onFinishClick;
    }

    public SelectDateDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_select_date);
        initView();


        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width =(int) ((ScreenUtils.getScreenWidth(getContext())*0.9f));
        attributes.height= (int) ((ScreenUtils.getScreenHeight(getContext())*0.9f));
        attributes.gravity= Gravity.CENTER;
        getWindow().setAttributes(attributes);

        mContent.setBackground(ShapeUtil.getSoldRadiusBg(Color.WHITE, (int) getContext().getResources().getDimension(R.dimen.dp_10)));
        mFinishBtn.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"), new float[]{
                0f, 0f, 0f, 0f, getContext().getResources().getDimension(R.dimen.dp_10), getContext().getResources().getDimension(R.dimen.dp_10),0f,0f,
        }));
        mContent.setBackground(ShapeUtil.getSoldRadiusBg(Color.WHITE,(int)ScreenUtils.dip2px(getContext(),10)));
        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onFinishClick!=null){

                    onFinishClick.onDateSelec(startDate,endDate);
                }

                dismiss();
            }
        });
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd日");
        mZhusuTime.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"),(int)ScreenUtils.dip2px(getContext(),R.dimen.dp_5)));
//        mDayTv.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"),getContext().getResources().getDimension(R.dimen.dp_10)));
        mCl.setOnDateSelected(new CalendarList.OnDateSelected() {

            @Override
            public void selected(String startDate, String endDate, Date s, Date d) {
        SelectDateDialog.this.startDate=s;
        SelectDateDialog.this.endDate=d;

                mDayStartTime.setText(simpleDateFormat.format(s));
                mDayEndTime.setText(simpleDateFormat.format(d));
                mZhusuTime.setText(    (int)TimeUtil.getDayInterval(s.getTime(),d.getTime())+"晚");

            }

            @Override
            public void onStartSelect(String startDate, Date s) {
                mDayStartTime.setText(simpleDateFormat.format(s));
            }

            @Override
            public void endSelect(String endData, Date e) {
                mDayEndTime.setText(simpleDateFormat.format(e));
            }
        });

    }


    private void initView() {
        mContent = (ConstraintLayout) findViewById(R.id.content);
        mDayStartTime = (TextView) findViewById(R.id.day_start_time);
        mStartDesc = (TextView) findViewById(R.id.start_desc);
        mZhusuTime = (TextView) findViewById(R.id.zhusu_time);
        mDayEndTime = (TextView) findViewById(R.id.day_end_time);
        mEndDesc = (TextView) findViewById(R.id.end_desc);
        mPrice = (TextView) findViewById(R.id.price);
        mFinishBtn = (TextView) findViewById(R.id.finish_btn);
        mContent = (ConstraintLayout) findViewById(R.id.content);
        mDayStartTime = (TextView) findViewById(R.id.day_start_time);
        mStartDesc = (TextView) findViewById(R.id.start_desc);
        mZhusuTime = (TextView) findViewById(R.id.zhusu_time);
        mDayEndTime = (TextView) findViewById(R.id.day_end_time);
        mEndDesc = (TextView) findViewById(R.id.end_desc);
        mPrice = (TextView) findViewById(R.id.price);
        mFinishBtn = (TextView) findViewById(R.id.finish_btn);
        mCl = (CalendarList) findViewById(R.id.cl);
    }

    public void setDialogDate(Date startInDate, Date endOutDate) {

        mCl.setSetSelectDate(startInDate,endOutDate);
    }


    public interface  OnFinishClick{
      void   onDateSelec(Date d,Date e);

    }
}
