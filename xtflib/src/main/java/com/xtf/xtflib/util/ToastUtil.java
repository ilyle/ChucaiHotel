package com.xtf.xtflib.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtil
{

	protected static Toast toast   = null;
	private static String oldMsg;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void showShort(Context context, String s){
		if(toast==null){
			toast = Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT);
			toast.show();
			toast.setGravity(Gravity.CENTER, 0, 0);
			oneTime= System.currentTimeMillis();
		}else{
			twoTime= System.currentTimeMillis();
			if(s.equals(oldMsg)){
				if(twoTime-oneTime> Toast.LENGTH_SHORT){
					toast.show();
				}
			}else{
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime=twoTime;
	}


	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}


}