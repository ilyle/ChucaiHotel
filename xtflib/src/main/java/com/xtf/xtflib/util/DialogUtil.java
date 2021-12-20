package com.xtf.xtflib.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtf.xtflib.R;


/**
 * 对话框加载类
 *
 * @author tianfengx
 * @description 类简述 TODO 具体内容说明
 * @date 2016年5月26日
 */
public class DialogUtil {


    public static Dialog showWaitDialog(final Context context) {
        final long time = System.currentTimeMillis();
        if (context == null) {
            return null;
        }
//        if (!(context instanceof Activity)) {
//            return null;
//        }
        final Dialog wait = new Dialog(context, R.style.Small_dialog);
        View v = View.inflate(context, R.layout.dialog_loading, null);
        wait.setContentView(v);

        TextView numberIn = (TextView) v.findViewById(R.id.loading_number_in_view);
        ImageView bar = (ImageView) v.findViewById(R.id.iv_load);
        TextView desc = (TextView) v.findViewById(R.id.load_desc);

        bar.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);
        numberIn.setVisibility(View.GONE);

        desc.setText("加载中");

        RotateAnimation animation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(2000);// 设置动画持续时间
        /** 常用方法 */
        animation.setRepeatCount(-1);// 设置重复次数
        animation.setFillAfter(false);// 动画执行完后是否停留在执行完的状态
        animation.setStartOffset(0);// 执行前的等待时间
        animation.setInterpolator(new LinearInterpolator());
        bar.setAnimation(animation);
        /** 开始动画 */
        animation.startNow();
        Window dialogWindow = wait.getWindow();

        // 动画效果
        WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();

        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.CENTER;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        wait.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialogWindow.setAttributes(lp);
        wait.setCanceledOnTouchOutside(false);
        wait.show();
        return wait;
    }




}
