package com.xtf.xtflib.widget.flowlayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;



/**
 * Created by zhy on 15/9/10.
 */
public class TagView extends FrameLayout implements Checkable
{
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};
    public TagView(Context context)
    {
        super(context);
    }

    public View getTagView()
    {
        return getChildAt(0);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace)
    {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
        {
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }


    /**
     * Change the checked state of the view
     *
     * @param checked The new checked state
     */
    @Override
    public void setChecked(boolean checked)
    {
        if (this.isChecked != checked)
        {
            this.isChecked = checked;
            findChildView(this,isChecked);
            refreshDrawableState();
        }
//         ImageView select= findViewById(R.id.select_icon);
//        if(select!=null) {
//            if (isChecked) {
//                ViewUtil.setViewVisible(select,View.VISIBLE);
//            }else {
//                ViewUtil.setViewVisible(select,View.GONE);
//            }
//        }

    }

    /**
     * @return The current checked state of the view
     */
    @Override
    public boolean isChecked()
    {
        return isChecked;
    }

    /**
     * Change the checked state of the view to the inverse of its current state
     */
    @Override
    public void toggle()
    {
        setChecked(!isChecked);
    }
    /**
     * 查找界面中的所有控件
     * @param view
     */
    public void  findChildView(View view,boolean isChecked){
        if(view==null){
            return;
        }
        if(view instanceof ViewGroup){
            ViewGroup viewGroup= (ViewGroup) view;
            for(int i=0;i<((ViewGroup) view).getChildCount();i++){
                findChildView(viewGroup.getChildAt(i),isChecked);
            }


        }else {
            doViewTask(view,isChecked);
        }


    }

    private void doViewTask(View view,boolean checked) {
        if(view instanceof Checkable){
            ((Checkable) view).setChecked(checked);
        }
    }

}
