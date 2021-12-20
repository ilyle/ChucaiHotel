package com.xtf.xtflib.widget.flowlayout;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.Checkable;


public class CheckableTextView extends AppCompatTextView implements Checkable {
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    private boolean mChecked = false;
    private boolean canSelect = true;
    private OnCheckChangedListener onCheckChangedListener;

    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;
    }

    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {

        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (canSelect) {
            if (isChecked()) {
                mergeDrawableStates(states, CHECK_STATE);
            }
        }
        return states;
    }


    public CheckableTextView(Context context) {
        super(context);
    }

    public CheckableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    public interface OnCheckChangedListener {
        void onCheckChanged(boolean isChecked);
    }

    @Override
    public void setChecked(boolean checked) {
        if (canSelect) {
            if (mChecked != checked) {
                mChecked = checked;
                if (onCheckChangedListener != null) {
                    onCheckChangedListener.onCheckChanged(mChecked);
                }
                refreshDrawableState();
            }
        }
    }

}   
