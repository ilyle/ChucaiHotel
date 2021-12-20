package com.chucai.hotel.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import com.chucai.hotel.core.DeviceHelper;

import java.io.File;
import java.util.List;

public class StartRemountAppUtil {

    public static  void doUpdateTask( final String url) {
        try {

        Intent intent = new Intent();
        intent.putExtra("packageName", DeviceHelper.getContext().getPackageName());
        intent.putExtra("curDownloadUrl",url);
//        intent.putExtra("connectIp",AppRequest.SELERVET_IP);
//        intent.putExtra("connectUserName",MqttControl.MQTT_USER_NAME);
//        intent.putExtra("connectPwd",MqttControl.MQTT_PASSWORD);
        intent.setAction("com.puze.update");intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("deviceId",DeviceHelper.getsDeviceId());
        ComponentName componetName = new ComponentName(
                "com.puze.systemupdataapp",  //这个是另外一个应用程序的包名
                "com.puze.systemupdataapp.MainActivity");   //这个参数是要启动的Activity的全路径名
        intent.setComponent(componetName);
        DeviceHelper.getContext(). startActivity(intent);

        System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }






}
