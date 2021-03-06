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
        view.getLocationOnScreen(location);//???????????????????????????????????????
        return location;
    }

    public static int[] getViewPosInLayout(View view){
        int[] location = new  int[2] ;
        view.getLocationInWindow(location); //???????????????????????????????????????
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
     * view???bitmap
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
        /** ???????????????canvas????????????????????????????????? */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
    public static Bitmap getViewBpWithoutBottom(ViewGroup view) {
        int height = 0;
        //?????????scrollView??????????????????View???
        for (int i = 0; i < view.getChildCount(); i++) {
            height += view.getChildAt(i).getHeight();
        }
        //?????????????????????bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.RGB_565);
        //??????????????????Canvas????????????????????? ???bitmap???????????????
        Canvas canvas = new Canvas(bitmap);
        //??????ScrollView???????????????
        Drawable background = view.getBackground();
        //??????ScrollView???????????? ???????????????color?????? ?????????????????????????????? ??????????????????????????????????????????
        if (background instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) background;
            int color = colorDrawable.getColor();
            canvas.drawColor(color);
        }
        canvas.drawColor(Color.WHITE);
        //???view?????????????????????????????????Canvas???
        view.draw(canvas);
        return bitmap;
    }

}
