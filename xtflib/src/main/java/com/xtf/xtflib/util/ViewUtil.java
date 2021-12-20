package com.xtf.xtflib.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class ViewUtil {

    public static void setViewVisible(View view, int state){
            switch (state){
                    case View.VISIBLE:
                        if(view.getVisibility()!= View.VISIBLE){
                            view.setVisibility(View.VISIBLE);
                        }
                        break;
                    case View.INVISIBLE:
                        if(view.getVisibility()!= View.INVISIBLE){
                            view.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case View.GONE:
                        if(view.getVisibility()!= View.GONE){
                            view.setVisibility(View.GONE);
                        }
                        break;
            }

    }



    public static int[]  getViewWidthHight(View view){
        int[] arr=new int[2];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        height= (int) ScreenUtils.dip2px(view.getContext(),height);
        int width = view.getMeasuredWidth();
        width= (int) ScreenUtils.dip2px(view.getContext(),width);
        System.out.println("measure width=" + width + " height=" + height);
        arr[0]=width;
        arr[1]=height;
        return arr;

    }
    public static int[]  getViewWidthHight2(View view){
        int[] arr=new int[2];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        System.out.println("measure width=" + width + " height=" + height);
        arr[0]=width;
        arr[1]=height;
        return arr;

    }

    public static int[] getViewPosInScreen(View view){
        int[] location = new  int[2] ;
        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        return location;
    }

    public static int[] getViewPosInLayout(View view){
        int[] location = new  int[2] ;
        view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        return location;

    }



    public static  void setEditable(EditText editText, boolean edit){
        if(!edit) {
            editText.setFocusable(false);

            editText.setFocusableInTouchMode(false);
        }else {
            editText.setFocusableInTouchMode(true);

            editText.setFocusable(true);

            editText.requestFocus();
        }
    }
    /**
     * view转bitmap
     *
     * @param v View
     * @return Bitmap
     */
    public static Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

//        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
    public static Bitmap getViewBpWithoutBottom(ViewGroup view) {
        int height = 0;
        //理论上scrollView只会有一个子View啦
        for (int i = 0; i < view.getChildCount(); i++) {
            height += view.getChildAt(i).getHeight();
        }
        //创建保存缓存的bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.RGB_565);
        //可以简单的把Canvas理解为一个画板 而bitmap就是块画布
        Canvas canvas = new Canvas(bitmap);
        //获取ScrollView的背景颜色
        Drawable background = view.getBackground();
        //画出ScrollView的背景色 这里只用了color一种 有需要也可以自己扩展 也可以自己直接指定一种背景色
        if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            int color = colorDrawable.getColor();
            canvas.drawColor(color);
        }
        canvas.drawColor(Color.WHITE);
        //把view的内容都画到指定的画板Canvas上
        view.draw(canvas);
        return bitmap;
    }

}
