package com.chucai.hotel.ac.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chucai.hotel.R;

public class InRoomFinish extends Fragment {
    private ImageView mIamge;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_in_room, container, false);
        initView(inflate);
        return inflate;


    }

    private void initView(View inflate) {
        mIamge = (ImageView) inflate.findViewById(R.id.iamge);
    }
}
