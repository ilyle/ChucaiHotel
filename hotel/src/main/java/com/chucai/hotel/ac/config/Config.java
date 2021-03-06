/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.chucai.hotel.ac.config;


public class Config {

    // 为了apiKey,secretKey为您调用百度人脸在线接口的，如注册，识别等。
    // 为了的安全，建议放在您的服务端，端把人脸传给服务器，在服务端端进行人脸注册、识别放在示例里面是为了您快速看到效果
    public static String appId = "25402142";
    public static String apiKey = "wPwGzi4WixnZs32DnWN3Nlqm";
    public static String secretKey = "u6z7EQ6BxfnSF9DElEHVTbkEom2X6nYr";
    public static String licenseID = "com-chucai-hotel-face-androi";
    public static String licenseFileName = "idl-license.face-android";


    /**
     * groupId，标识一组用户（由数字、字母、下划线组成），长度限制128B，可以自行定义，只要注册和识别都是同一个组。
     * 详情见 http://ai.baidu.com/docs#/Face-API/top
     * <p>
     * 人脸识别 接口 https://aip.baidubce.com/rest/2.0/face/v2/identify
     * 人脸注册 接口 https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add
     */

    public static String groupID = "";

}
