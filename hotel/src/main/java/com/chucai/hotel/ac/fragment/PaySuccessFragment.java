package com.chucai.hotel.ac.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chucai.hotel.R;

public class PaySuccessFragment extends Fragment {
    private ImageView mImage;
    AnimationDrawable animationDrawable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_pay_success, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mImage = (ImageView) inflate.findViewById(R.id.image);
        mImage.setImageResource(R.drawable.a2_list);
    animationDrawable= (AnimationDrawable) mImage.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animationDrawable.stop();
    }
}
