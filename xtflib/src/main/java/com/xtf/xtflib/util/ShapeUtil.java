package com.xtf.xtflib.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class ShapeUtil {

    public static GradientDrawable getSoldWithStrockRadiusBg( int colorSold,float strockWidth,int colorStrock,float radius){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius((float) radius);
        drawable.setColor(colorSold);
        drawable.setStroke((int)strockWidth,colorStrock);
        return drawable;
    }


    public static GradientDrawable getSoldRadiusBg( int colorSold,float radius){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius((float) radius);
        drawable.setColor(colorSold);
        return drawable;
    }

    public static GradientDrawable getSoldRadiusBg( int colorSold,float[] radius){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(radius);
        drawable.setColor(colorSold);
        return drawable;
    }

    public static GradientDrawable getSoldWithStrockRadiusBg( int colorSold,float strockWidth,int colorStrock,float[] radius){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(colorSold);
        drawable.setCornerRadii(radius);
        drawable.setStroke((int)strockWidth,colorStrock);
        return drawable;

    }



    public  static StateListDrawable getPressCheckedBg(Drawable nor,Drawable sel){
        StateListDrawable stateListDrawable=new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, sel);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, sel);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, sel);
        stateListDrawable.addState(new int[]{},nor);
        return stateListDrawable;
    }

    public  static ColorStateList  getPressCheckedColor(int nor,int sel){
        int[] colors = new int[] { sel, sel, nor };
        int[][] states = new int[3][];
        states[0] = new int[] { android.R.attr.state_pressed};
        states[1] = new int[] { android.R.attr.state_checked };
        states[2] = new int[] {};
        ColorStateList colorStateList=new ColorStateList(states,colors);
        return colorStateList;
    }


    public static LayerDrawable getLayerDrawable(Drawable[] drawables,int[] left,int top[],int right[],int button[]){
            LayerDrawable layerDrawable=new LayerDrawable(drawables);
            for(int i=0;i<drawables.length;i++){
                layerDrawable.setLayerInset(i,left[i],top[i],right[i],button[i]);
            }
            return layerDrawable;
    }

}
