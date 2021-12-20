package com.chucai.hotel.ac.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.xtf.xtflib.util.ScreenUtils;
import com.xtf.xtflib.util.ShapeUtil;

public class PhoneHintDialog extends Dialog {
    private TextView mHintTv;
    private TextView mBtn1;
    private TextView mBtn2;
    private LinearLayout mContent;

    public PhoneHintDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_phone_hint);
        initView();

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width= (int) getContext().getResources().getDimension(R.dimen.dp_590);
        attributes.height= (int) getContext().getResources().getDimension(R.dimen.dp_292);
        attributes.gravity= Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;

        getWindow().setAttributes(attributes);
    }

    private void initView() {
        mHintTv = (TextView) findViewById(R.id.hint_tv);
        mBtn1 = (TextView) findViewById(R.id.btn1);
        mBtn2 = (TextView) findViewById(R.id.btn2);

        mContent = (LinearLayout) findViewById(R.id.content);
        mContent.setBackground(ShapeUtil.getSoldRadiusBg(Color.WHITE, ScreenUtils.dip2px(getContext(),10)));
        mBtn1.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#3A3A3A"),new float[]{0,0,0,0,0,0,ScreenUtils.dip2px(getContext(),10),ScreenUtils.dip2px(getContext(),10)}));
        mBtn2.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"),new float[]{0,0,0,0,ScreenUtils.dip2px(getContext(),10),ScreenUtils.dip2px(getContext(),10),0,0}));

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBtnClick!=null){
                    onBtnClick.onLeftBtnClick();
                }
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onBtnClick!=null){
                    onBtnClick.onRightBtnClick();
                }
            }
        });

    }



    public void setData(String content,String left,String right){
        mHintTv.setText(content);
        if(!TextUtils.isEmpty(left)) {
            mBtn1.setText(left);
        }
        if(!TextUtils.isEmpty(right)) {
            mBtn2.setText(right);
        }
    }

    OnBtnClick onBtnClick;



    public void setOnBtnClick(OnBtnClick onBtnClick) {
        this.onBtnClick = onBtnClick;
    }

    public interface  OnBtnClick{
        void onLeftBtnClick();

        void onRightBtnClick();

    }
}
