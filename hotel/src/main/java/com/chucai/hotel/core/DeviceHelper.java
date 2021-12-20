package com.chucai.hotel.core;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.RoomMessage;
import com.chucai.hotel.bean.RoomSaleType;
import com.xtf.xtflib.util.CacheUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeviceHelper {
    private static Context sContext;
    public static final String FILE_ROOT="/sdcard/hotel/";
    public static  String  sHOTELID="6C040507-A6B4-4ECD-915B-4C37B47360E4";
    private static List<NewRoom.DataDTO.ListDTO> sAllRoomList;
    private static Map<Integer, NewRoom.DataDTO.ListDTO> allRoomTypeList;

    private static String sCookies;

    private static List<RoomSaleType.DataDTO>  sRoomSaleTypeDataList;


    public static List<RoomSaleType.DataDTO> getsRoomSaleTypeDataList() {
        return sRoomSaleTypeDataList;
    }

    public static void setsRoomSaleTypeDataList(List<RoomSaleType.DataDTO> sRoomSaleTypeDataList) {
        DeviceHelper.sRoomSaleTypeDataList = sRoomSaleTypeDataList;
    }

    public static void setsCookies(String sCookies) {
        DeviceHelper.sCookies = sCookies;
    }

    public static String getsCookies() {
        return sCookies;
    }

    public static void setAllRoomTypeList(Map<Integer, NewRoom.DataDTO.ListDTO> allRoomTypeList) {
        DeviceHelper.allRoomTypeList = allRoomTypeList;
    }




    public static List<NewRoom.DataDTO.ListDTO> getsAllRoomList() {
        return sAllRoomList;
    }


    public static void setsAllRoomList(List<NewRoom.DataDTO.ListDTO> sAllRoomList) {
        DeviceHelper.sAllRoomList = sAllRoomList;
    }



    public static NewRoom.DataDTO.ListDTO  queryRoomByRoomNum(String roomNo){
        for(NewRoom.DataDTO.ListDTO  resultDTO:sAllRoomList){
            if(resultDTO.getRoom_number().equals(roomNo)){
                return  resultDTO;
            }
        }


        return null;



    }


    public static Map<Integer, NewRoom.DataDTO.ListDTO> getAllRoomTypeList() {
        return allRoomTypeList;
    }

    public static String sDeviceId;

    private static String sFaceToken;


    public static String getsHOTELID() {
        return sHOTELID;
    }

    public static void setsFaceToken(String sFaceToken) {
        DeviceHelper.sFaceToken = sFaceToken;
    }

    public static String getsFaceToken() {
        return sFaceToken;
    }

    public static void initDeviceId() {
         sDeviceId=   CacheUtil.get(getContext()).getString("deviceId","");
        if(TextUtils.isEmpty(sDeviceId)) {
            WifiManager wifimanage = (WifiManager) sContext.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
            if (!wifimanage.isWifiEnabled()) {
                wifimanage.setWifiEnabled(true);
            }

            WifiInfo wifiinfo = wifimanage.getConnectionInfo();

            String macAddress = wifiinfo.getMacAddress();
            String replace = macAddress.replace(":", "");
            CacheUtil.get(getContext()).put("deviceId", replace);
            sDeviceId=replace;
        }




    }

    public static void setsContext(Context sContext) {
        DeviceHelper.sContext = sContext;
    }

    public static String getsDeviceId() {
        return sDeviceId;
    }

    public static Context getContext() {
        return sContext;
    }

    public static String createOrderNo(){
            String orderNo=DeviceHelper.getsDeviceId()+System.currentTimeMillis()+ UUID.randomUUID().toString().substring(0,6);
            return orderNo;

    }
        private static String sAuthToken;
    public static String getAuthToken() {
        return sAuthToken;
    }


    public static void setsAuthToken(String sAuthToken) {
        DeviceHelper.sAuthToken = sAuthToken;
    }

    public static int getPriceProjectType() {

        return 0;
    }
}
