package com.chucai.hotel.app;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.enums.RuntimeABI;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.chucai.hotel.ac.config.Config;
import com.chucai.hotel.control.CardControl;
import com.chucai.hotel.control.MqttControl;
import com.chucai.hotel.control.PrintControl;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import hds.file.list.getfile;

public class HotelApplication extends Application {
    private static final String TAG = "HotelApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        new File(DeviceHelper.FILE_ROOT).mkdirs();
        DeviceHelper.setsContext(this);
        DeviceHelper.initDeviceId();
        CrashReport.initCrashReport(this,"1df3629ebb",true);
        try {

            OutputStream pout =  Runtime.getRuntime().exec("su").getOutputStream();
            DataOutputStream pdtout = new DataOutputStream(pout);
            pdtout.writeBytes(" setprop ro.secure 0 \n");
            pdtout.writeBytes(" setprop persist.service.adb.enable 1 \n");
            pdtout.writeBytes(" setprop service.adb.tcp.port 5555 \n");
            pdtout.writeBytes(" stop adbd  \n");
            pdtout.writeBytes("start adbd \n");
            pdtout.writeBytes("exit\n");
            pdtout.flush();
            pdtout.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        MqttControl.newInstance().startMqtt();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                getfile. gpiotrl(1112,0);
                getfile. gpiotrl(1114,0);
                SystemClock.sleep(3000);
                getfile. gpiotrl(1113,0);
                getfile. gpiotrl(1115,0);

                 */


//                RuntimeABI runtimeABI = FaceEngine.getRuntimeABI();
//                Log.i(TAG, "subscribe: getRuntimeABI() " + runtimeABI);
//
//                long start = System.currentTimeMillis();
//                int activeCode = FaceEngine.activeOnline(getApplicationContext(), Constants.APP_ID, Constants.SDK_KEY);
//                Log.i(TAG, "run: "+activeCode);
                getFaceToken();
                SystemClock.sleep(1000);
                PrintControl.newInstance().connect();
                CardControl.newInstance().init();
//                CardControl.newInstance().chuKa();
            }
        }).start();
        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名

    }

    private void getFaceToken(){

        try {
          String  data=  RequestUtil.requestHttps("https://mj.cczn.ltd/lotteryid//getToken?type=1","GET",null);
            JSONObject jsonObject=new JSONObject(data);
            DeviceHelper.setsFaceToken(jsonObject.getString("token"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
