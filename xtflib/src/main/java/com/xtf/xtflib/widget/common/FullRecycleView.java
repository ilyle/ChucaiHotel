package com.xtf.xtflib.widget.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;



public class FullRecycleView extends RecyclerView {

    public FullRecycleView(@NonNull Context context) {
        super(context);
    }

    public FullRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FullRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
