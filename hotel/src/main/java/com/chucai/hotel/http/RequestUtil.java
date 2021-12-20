package com.chucai.hotel.http;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.chucai.hotel.core.DeviceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2018-01-19 019.
 */

public class RequestUtil {
    private static final String TAG = "RequestUtil";
//        public static  String IP="120.78.217.97";
        public static  String IP="192.168.0.158";
//        public static String DEFAULT_PATH="http://81.71.66.18:8080/shop/";
        public static String DEFAULT_PATH="http://81.71.66.18:8080/shop/";
    public static String SEC_PATH="http://171.220.244.143:8010/openapi/zkx/";
    private static  boolean isFirstLoad=true;

    public static String THREE_PATH="https://pms.ifofu.com/sys/version2_0/data/public/index.php";


//    public static final String IP="192.168.0.159";
//    public static String DEFAULT_PATH="http://"+IP+":8086/";
    public static String formateUrl(String path){
        return DEFAULT_PATH+path;
    }
    public static String secUrl(String path){
        return SEC_PATH+path;
    }


    public static String threeURL(String path){

        return THREE_PATH+path;
    }
    static {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                Log.i("skyapp", "checkClientTrusted");
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                Log.i("skyapp", "checkServerTrusted");
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        });
    }




    /**
     */
    public static String request(String path, String method, String data)

            throws IOException {
        if(TextUtils.isEmpty(data)){
            data="";
        }
        URL url = null;
        if(method.equalsIgnoreCase("GET")){
            path+="?"+data;
        }
        url = new URL(path);


        Log.i(TAG, "路径    " + path + "\n方法" + method + "\n提交数据" + data);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(20000);


        if(!method.equalsIgnoreCase("GET")){
            boolean isJson=false;
            try {
                new JSONObject(data);
                isJson=true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");

            }
            Log.i(TAG, "request: "+DeviceHelper.getAuthToken());
            httpURLConnection.setRequestProperty("AUTHTOKEN", DeviceHelper.getAuthToken());
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data.getBytes());
            if(isFirstLoad){
                SystemClock.setCurrentTimeMillis(httpURLConnection.getDate());
            }

            outputStream.flush();
            outputStream.close();

        }else {
            httpURLConnection.setRequestProperty("AUTHTOKEN", DeviceHelper.getAuthToken());
        }
        Log.i(TAG, "request: "+httpURLConnection.getResponseCode());
        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }

        bos.close();

        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "返回数据" + readData);
        return readData;
    }

    public static  String valToKey(String ... val) throws Exception {
        if(val==null||val.length==0){
            return "";
        }else {
            if(val.length%2!=0){
                throw new Exception("错误参数");
            }
            String target = "";
            for(int i=0;i<val.length;i++){
                if(i>0) {
                    if (i % 2 == 1) {
                        target += "=";
                    } else {
                        if (i < val.length - 1) {
                            target += "&";
                        }
                    }
                }
                target+=val[i];

            }
            return target;


        }
    };

    public static String requestHttps(String path, String method, String data)
            throws IOException {
        URL url = null;
        url = new URL(path);
        Log.i(TAG, "路径    " + path + "\n方法" + method + "\n提交数据" + data);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        if(!TextUtils.isEmpty(data)){
            boolean isJson=false;
            try {
                JSONObject jsonObject=new JSONObject(data);
                isJson=true;
                jsonObject=null;
            } catch (JSONException e) {
            }
            if(!isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
            }else {

                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");
            }
        }
        httpURLConnection.setRequestProperty("authtoken", DeviceHelper.getAuthToken());
        Log.i(TAG, "requestHttps: token = "+DeviceHelper.getAuthToken());
        if(DeviceHelper.getsCookies()!=null) {
            String[] split = DeviceHelper.getsCookies().split("=");
            Log.i(TAG, "requestHttps: --------------------"+DeviceHelper.getsCookies());
            httpURLConnection.setRequestProperty("Cookie",DeviceHelper.getsCookies());

        }


        if(!method.equals("GET")){
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }

        if(!TextUtils.isEmpty(data)) {
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(data.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }


        String headerField = httpURLConnection.getHeaderField("Set-Cookie");
        if(!TextUtils.isEmpty(headerField)) {
            DeviceHelper.setsCookies(headerField);
            Log.i(TAG, "requestHttps: headerField = "+headerField);
        }

        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }
        if(isFirstLoad){
            SystemClock.setCurrentTimeMillis(httpURLConnection.getDate());
        }
        bos.close();
        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "返回数据" + readData);
        return readData;
    }
    public static String requestHttps2(String path, String method, String data)
            throws IOException {
        URL url = null;
        url = new URL(path);
        Log.i(TAG, "路径    " + path + "\n方法" + method + "\n提交数据" + data);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url
                .openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);
        if(!TextUtils.isEmpty(data)){
            boolean isJson=false;
            try {
                JSONObject jsonObject=new JSONObject(data);
                isJson=true;
                jsonObject=null;
            } catch (JSONException e) {
            }
            if(!isJson) {
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
            }else {

                httpURLConnection.setRequestProperty("Content-Type",
                        "application/json");
            }
        }
        httpURLConnection.setRequestProperty("authtoken", DeviceHelper.getAuthToken());
        Log.i(TAG, "requestHttps: token = "+DeviceHelper.getAuthToken());
//        if(DeviceHelper.getsCookies()!=null) {
//            String[] split = DeviceHelper.getsCookies().split("=");
//            Log.i(TAG, "requestHttps: --------------------"+DeviceHelper.getsCookies());
//            httpURLConnection.setRequestProperty("Cookie",DeviceHelper.getsCookies());
//
//        }


        if(!method.equals("GET")){
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
        }

        if(!TextUtils.isEmpty(data)) {
            OutputStream outputStream = httpURLConnection.getOutputStream();

            outputStream.write(data.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }


//        String headerField = httpURLConnection.getHeaderField("Set-Cookie");
//        if(!TextUtils.isEmpty(headerField)) {
//            DeviceHelper.setsCookies(headerField);
//            Log.i(TAG, "requestHttps: headerField = "+headerField);
//        }

        BufferedInputStream bis = new BufferedInputStream(
                httpURLConnection.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while ((len = bis.read(arr)) != -1) {
            bos.write(arr, 0, len);
            bos.flush();
        }
        if(isFirstLoad){
            SystemClock.setCurrentTimeMillis(httpURLConnection.getDate());
        }
        bos.close();
        httpURLConnection.disconnect();
        String readData = bos.toString("utf-8");
        Log.i(TAG, "返回数据" + readData);
        return readData;
    }

    public static JSONObject getReqJson(String method,JSONObject data){

        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("method",method);
            jsonObject.put("data",data);

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




    public static String toKeyVal(JSONObject jsonObject) throws JSONException {
        StringBuffer stringBuffer=new StringBuffer();
        Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Object value = jsonObject.get(key);
                if(value!=null) {
                    stringBuffer.append(key + "=" + value);
                }
                stringBuffer.append("&");
            }
             stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
            return stringBuffer.toString();
    }


}
