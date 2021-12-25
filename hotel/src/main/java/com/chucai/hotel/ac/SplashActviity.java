package com.chucai.hotel.ac;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.chucai.hotel.R;
import com.chucai.hotel.bean.HotelData;
import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.PriceData;
import com.chucai.hotel.bean.RoomSaleType;
import com.chucai.hotel.bean.UpdateBean;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.xtf.xtflib.util.ApkUtil;
import com.xtf.xtflib.util.Md5Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActviity extends AppCompatActivity {
    private static final String TAG = "SplashActviity";
    int clickTimes;
    long lastTouchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actviity);
        Bundle bundle = new Bundle();
        bundle.putString("data", "122");
        bundle.toString();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickTimes == 0) {

                    lastTouchTime = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - lastTouchTime < 2000) {
                    clickTimes++;
                    lastTouchTime = System.currentTimeMillis();
                    if (clickTimes >= 6) {
                        clickTimes = 0;
                        lastTouchTime = 0;
                        Intent mIntent = new Intent();
                        ComponentName comp = new ComponentName("com.android.settings",
                                "com.android.settings.Settings");
                        mIntent.setComponent(comp);
                        mIntent.setAction("android.intent.action.VIEW");
                        startActivity(mIntent);

                    }
                } else {
                    clickTimes = 0;
                    lastTouchTime = 0;
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0 && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0 && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == 0) {
            jumpAc();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
            } else {
                jumpAc();
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void jumpAc() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {


                        boolean roomMessage = getRoomMessage(getApplicationContext());
                        if (roomMessage) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(SplashActviity.this, MainActivity_new.class));
                                    finish();
                                }
                            });
                            break;
                        } else {
                            SystemClock.sleep(10 * 1000);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        SystemClock.sleep(10 * 1000);
                    }
                }


            }
        }).start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == 0 && grantResults[1] == 0 && grantResults[2] == 0) {
            jumpAc();
        }
    }

    /**
     * 参数配置方法
     */
    private static boolean setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），也可以根据实际需求进行数值调整

        // 设置活体动作，通过设置list，LivenessTypeEunm.Eye, LivenessTypeEunm.Mouth,
        // LivenessTypeEunm.HeadUp, LivenessTypeEunm.HeadDown, LivenessTypeEunm.HeadLeft,
        // LivenessTypeEunm.HeadRight

        List<LivenessTypeEnum> list = new ArrayList<>();
        list.add(LivenessTypeEnum.Eye);
        list.add(LivenessTypeEnum.Mouth);
        config.setLivenessTypeList(list);
        // 原图缩放系数
        config.setScale(FaceEnvironment.VALUE_SCALE);
        // 抠图宽高的设定，为了保证好的抠图效果，建议高宽比是4：3
        config.setCropHeight(FaceEnvironment.VALUE_CROP_HEIGHT);
        config.setCropWidth(FaceEnvironment.VALUE_CROP_WIDTH);
        // 抠图人脸框与背景比例
        config.setEnlargeRatio(FaceEnvironment.VALUE_CROP_ENLARGERATIO);
        // 加密类型，0：Base64加密，上传时image_sec传false；1：百度加密文件加密，上传时image_sec传true
        config.setSecType(FaceEnvironment.VALUE_SEC_TYPE);
        // 检测超时设置
        config.setTimeDetectModule(FaceEnvironment.TIME_DETECT_MODULE);
        // 检测框远近比率
        config.setFaceFarRatio(FaceEnvironment.VALUE_FAR_RATIO);
        config.setFaceClosedRatio(FaceEnvironment.VALUE_CLOSED_RATIO);
        FaceSDKManager.getInstance().setFaceConfig(config);
        return true;
    }


    /**
     * 初始化人脸识别
     */
    private static void initFace(Context context) {

        setFaceConfig();

        FaceSDKManager.getInstance().initialize(context, "com-chucai-hotel-face-android",
                "idl-license.face-android", new IInitCallback() {
                    @Override
                    public void initSuccess() {
                        Log.i(TAG, "初始化成功");
                    }

                    @Override
                    public void initFailure(final int errCode, final String errMsg) {
                        Log.e(TAG, "初始化失败 = " + errCode + " " + errMsg);
                    }
                });
    }

    public static boolean getRoomMessage(Context context) throws IOException, JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("machineCode", ApkUtil.getVersionCode(context.getApplicationContext()));
        String request = RequestUtil.request(RequestUtil.formateUrl("machineVersion/ph"), "POST", RequestUtil.getReqJson("getNewVersion", jsonObject).toString());
        UpdateBean updateBean = JSON.parseObject(request, UpdateBean.class);
        if (updateBean.getData() != null) {
            String machineVersionUrl = updateBean.getData().getMachineVersionUrl();
            //  StartRemountAppUtil.doUpdateTask(machineVersionUrl);
            doUpdateTask(machineVersionUrl);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "1");
        jsonObject1.put("pwd", Md5Utils.GetMD5Code("123451"));
        String post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/Admin/login"), "POST", RequestUtil.toKeyVal(jsonObject1));
        HotelData hotelData = JSON.parseObject(post, HotelData.class);
        String user_token = hotelData.getData().getUser_token();
        DeviceHelper.setsAuthToken(user_token);
        Log.i("RequestUtil", "run: " + user_token);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("limit", 1000);
        jsonObject2.put("page", 0);
        SystemClock.sleep(1000);

        String data = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/Room/getRoomList"), "POST", RequestUtil.toKeyVal(jsonObject2));
        NewRoom newRoom = JSON.parseObject(data, NewRoom.class);
        DeviceHelper.setsAllRoomList(newRoom.getData().getList());
        Map<Integer, NewRoom.DataDTO.ListDTO> integerMap = new HashMap<>();

        for (NewRoom.DataDTO.ListDTO listDTO : newRoom.getData().getList()) {
            integerMap.put(listDTO.getRoom_type(), listDTO);

        }
        DeviceHelper.setAllRoomTypeList(integerMap);
        // 房间售卖方式
        data = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/Room/getConsumes"), "POST", RequestUtil.toKeyVal(jsonObject2));
        DeviceHelper.setsRoomSaleTypeDataList(JSON.parseObject(data, RoomSaleType.class).getData());

        initFace(context);

        return true;
    }


    public void getPriceMessage() {
        try {


            String data2 = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomPrice/getRoomPrice"), "POST", "");
            PriceData priceData = JSON.parseObject(data2, PriceData.class);

            Log.i(TAG, "run:1111111111111111 " + priceData.getData().getDataSource());
            List<PriceData.DataDTO.ColumnsDTO> columns = priceData.getData().getColumns();

            JSONArray jsonObject3 = new JSONArray(priceData.getData().getDataSource().toString());
            int index = 0;
            for (PriceData.DataDTO.ColumnsDTO columnsDTO : columns) {
                JSONObject s = jsonObject3.getJSONObject(index);
                JSONArray jsonArray = s.getJSONArray(columnsDTO.getDate());
                Log.i(TAG, "run:142333 " + jsonArray);
                if (jsonArray.length() > 0) {
                    JSONArray jsonArray1 = jsonArray.getJSONArray(0);
                    List<PriceData.DataDTO.RoomPriceData> roomPriceDatas = JSON.parseArray(jsonArray1.toString(), PriceData.DataDTO.RoomPriceData.class);
                    columnsDTO.setRoomPriceData(roomPriceDatas);
                    Log.i(TAG, "run: 11111112223 " + columnsDTO.getRoomPriceData());
                    index++;


                }
            }
            Log.i(TAG, "run: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doUpdateTask(final String url) {
        try {
            Intent intent = new Intent();
            intent.putExtra("installLocalApk", true);
            intent.putExtra("packageName", DeviceHelper.getContext().getPackageName());
            intent.putExtra("curDownloadUrl", url);
            intent.putExtra("connectIp", "");
            intent.putExtra("connectUserName", "");
            intent.putExtra("connectPwd", "");
            intent.setAction("com.puze.update");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("deviceId", DeviceHelper.getsDeviceId());
            ComponentName componetName = new ComponentName(
                    "com.puze.systemupdataapp",  //这个是另外一个应用程序的包名
                    "com.puze.systemupdataapp.MainActivity");   //这个参数是要启动的Activity的全路径名
            intent.setComponent(componetName);
            DeviceHelper.getContext().startActivity(intent);

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}