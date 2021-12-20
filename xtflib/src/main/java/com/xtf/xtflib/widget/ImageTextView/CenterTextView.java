package com.xtf.xtflib.widget.ImageTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.xtf.xtflib.R;


public class CenterTextView extends TextView {
    private int drawableWidth;
    private int drawableHeight;



    public CenterTextView(Context context) {
        super(context);

    }

    public CenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);




    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.center_text_view);
        drawableWidth=array.getDimensionPixelSize(R.styleable.center_text_view_drawableWidth,25);
        drawableHeight=array.getDimensionPixelSize(R.styleable.center_text_view_drawableHeight, 25);
        array.recycle();
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0],compoundDrawables[1],compoundDrawables[2],compoundDrawables[3]);
    }

    public CenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

   public void setWidthHight(int width,int height){
        this.drawableWidth=width;
        this.drawableHeight=height;
        invalidate();
   }


    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Rect rect=new Rect(0,0,drawableWidth,drawableHeight);
        if(left!=null){
            left.setBounds(rect);
        }
        if(top!=null){
            top.setBounds(rect);
        }
        if(right!=null){
            right.setBounds(rect);
        }

        if(bottom!=null){
            bottom.setBounds(rect);
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (null != drawables) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            Drawable drawableTop = drawables[1];
            Drawable drawableButton = drawables[3];
            float textWidth = getPaint().measureText(getText().toString());
            if (null != drawableLeft) {
                setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableWidth;
                if (getWidth() - contentWidth > 0) {
                    canvas.translate((getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
                }
            }
            if (null != drawableRight) {
                setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableWidth;
                if (getWidth() - contentWidth > 0) {
                    canvas.translate(-(getWidth() - contentWidth - getPaddingRight() - getPaddingLeft())/2, 0);
                }
            }
            if(null!=drawableTop){
                setGravity(Gravity.CENTER_HORIZONTAL);
                float contentHeight = getTextSize() + getCompoundDrawablePadding() + drawableHeight;
                if (getHeight() - contentHeight > 0) {
                    canvas.translate(0, (getHeight() - contentHeight - getPaddingTop() - getPaddingBottom()) / 2);
                }
            }


            if(null!=drawableButton){
                setGravity( Gravity.CENTER_HORIZONTAL);
                float contentHeight = getTextSize() + getCompoundDrawablePadding() + drawableHeight;
                if (getHeight() - contentHeight > 0) {
                    canvas.translate(0, -(getHeight() - contentHeight - getPaddingTop() - getPaddingBottom()) / 2);
                }
            }

//            if (null == drawableRight && null == drawableLeft) {
//                setGravity(Gravity.CENTER);
//            }
        }
        super.onDraw(canvas);
    }


}