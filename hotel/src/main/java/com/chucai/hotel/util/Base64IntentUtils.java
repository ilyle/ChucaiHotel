package com.chucai.hotel.util;

public class Base64IntentUtils {
    private static Base64IntentUtils instance = null;

    // 身份证采集到的证件照
    private String mLocalBase64;

    // 人脸识别采集到的照片
    private String mCollectBase64;

    /**
     * 单例模式
     * @return FaceSDKManager实体
     */
    public static Base64IntentUtils getInstance() {
        if (instance == null) {
            synchronized (Base64IntentUtils.class) {
                if (instance == null) {
                    instance = new Base64IntentUtils();
                }
            }
        }
        return instance;
    }

    public String getmLocalBase64() {
        return mLocalBase64;
    }

    public void setmLocalBase64(String mLocalBase64) {
        this.mLocalBase64 = mLocalBase64;
    }

    public String getmCollectBase64() {
        return mCollectBase64;
    }

    public void setmCollectBase64(String mCollectBase64) {
        this.mCollectBase64 = mCollectBase64;
    }

    public void release() {
        if (instance != null) {
            instance = null;
        }
    }
}
