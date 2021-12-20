package com.xtf.xtflib.widget.bam;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Created by Administrator on 2017/5/20 0020.
 */

public class SimplePageTransform implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {
        int width = view.getWidth();
        int pivotX = 0;
        if (position <= 1 && position > 0) {// right scrolling
            pivotX = 0;
        } else if (position == 0) {

        } else if (position < 0 && position >= -1) {// left scrolling
            pivotX = width;
        }
        //设置x轴的锚点
        view.setPivotX(pivotX);
        //设置绕Y轴旋转的角度
        view.setRotationY(90f * position);
    }
}