package com.xtf.xtflib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * bitmap工具类：文字转成图片
 * Created by frank on 2018/1/24.
 */

public class BitmapUtil {

    private final static int TEXT_SIZE = 16;
    private final static int TEXT_COLOR = Color.RED;

    /**
     * 文本转成Bitmap
     * @param text 文本内容
     * @param context 上下文
     * @return 图片的bitmap
     */
    private static Bitmap textToBitmap(String text , Context context) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(text);
        tv.setTextSize(scale * TEXT_SIZE);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(TEXT_COLOR);
        tv.setBackgroundColor(Color.WHITE);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        tv.buildDrawingCache();
        Bitmap bitmap = tv.getDrawingCache();
        int rate = bitmap.getHeight() / 20;
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/rate, 20, false);
    }



    /**
     * 删除源文件
     * @param filePath filePath
     * @return 删除是否成功
     */
    public static boolean deleteTextFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }









    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();

                result =   android.util.Base64.encodeToString(bitmapBytes,   android.util.Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes =   android.util.Base64.decode(base64Data,   android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
