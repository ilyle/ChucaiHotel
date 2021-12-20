package com.chucai.hotel.ac.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.xtf.xtflib.widget.NiceImageView.NiceImageView;

public class CheckOutFragement extends Fragment {

    private RadioGroup mRgGroup;
    private RadioButton mXuZhuRb;
    private RadioButton mTuiFangRb;
    private TextView mOrderNoTv;
    private TextView mRoomNum;
    private TextView mRoomTypeName;
    private ImageView mDir;
    private TextView mHuanFangTv;
    private LinearLayout mRuZhuMessage;
    private TextView mNameTv;
    private TextView mPhoneTv;
    private TextView mGuigeTv;
    private TextView mPeopleTv;
    private NiceImageView mImage;
    private TextView mDescTv;
    private RelativeLayout mPayLayout;
    private ImageView mCodeImage;
    private TextView mRedBtn;
    private TextView mNumTv;
    private TextView mAddBtn;
    private TextView mRuzhuDate;
    private TextView mLidianDate;
    private TextView mTvDay;
    private TextView mPayDescTv;
    private TextView mMoneyTv;
    private RelativeLayout mYaJinLayout;
    private TextView mYaJinPrice;
    private Button mTuiFangBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragement_check_out, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mRgGroup = (RadioGroup) inflate.findViewById(R.id.rg_group);
        mXuZhuRb = (RadioButton) inflate.findViewById(R.id.xu_zhu_rb);
        mTuiFangRb = (RadioButton) inflate.findViewById(R.id.tui_fang_rb);
        mOrderNoTv = (TextView) inflate.findViewById(R.id.order_no_tv);
        mRoomNum = (TextView) inflate.findViewById(R.id.room_num);
        mRoomTypeName = (TextView) inflate.findViewById(R.id.room_type_name);
        mDir = (ImageView) inflate.findViewById(R.id.dir);
        mHuanFangTv = (TextView) inflate.findViewById(R.id.huan_fang_tv);
        mRuZhuMessage = (LinearLayout) inflate.findViewById(R.id.ru_zhu_message);
        mNameTv = (TextView) inflate.findViewById(R.id.name_tv);
        mPhoneTv = (TextView) inflate.findViewById(R.id.phone_tv);
        mGuigeTv = (TextView) inflate.findViewById(R.id.guige_tv);
        mPeopleTv = (TextView) inflate.findViewById(R.id.people_tv);
        mImage = (NiceImageView) inflate.findViewById(R.id.image);
        mDescTv = (TextView) inflate.findViewById(R.id.desc_tv);
        mPayLayout = (RelativeLayout) inflate.findViewById(R.id.pay_layout);
        mCodeImage = (ImageView) inflate.findViewById(R.id.code_image);
        mRedBtn = (TextView) inflate.findViewById(R.id.red_btn);
        mNumTv = (TextView) inflate.findViewById(R.id.num_tv);
        mAddBtn = (TextView) inflate.findViewById(R.id.add_btn);
        mRuzhuDate = (TextView) inflate.findViewById(R.id.ruzhu_date);
        mLidianDate = (TextView) inflate.findViewById(R.id.lidian_date);
        mTvDay = (TextView) inflate.findViewById(R.id.tv_day);
        mPayDescTv = (TextView) inflate.findViewById(R.id.pay_desc_tv);
        mMoneyTv = (TextView) inflate.findViewById(R.id.money_tv);
        mYaJinLayout = (RelativeLayout)inflate. findViewById(R.id.ya_jin_layout);
        mYaJinPrice = (TextView) inflate.findViewById(R.id.ya_jin_price);
        mTuiFangBtn = (Button) inflate.findViewById(R.id.tui_fang_btn);
    }
}
