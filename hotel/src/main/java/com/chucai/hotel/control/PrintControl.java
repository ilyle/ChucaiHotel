package com.chucai.hotel.control;

import android.content.Context;
import android.os.Bundle;
import android.util.Printer;

import com.szsicod.print.escpos.PrinterAPI;
import com.szsicod.print.io.SerialAPI;

import java.io.File;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;


import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;

public class PrintControl {
    SerialAPI io;
    private PrinterAPI mPrinter;
    static PrintControl sPrintControl;



    public PrinterAPI getmPrinter() {
        return mPrinter;
    }

    public static PrintControl newInstance() {
        if(sPrintControl==null){
            sPrintControl=new PrintControl();
        }
        return sPrintControl;
    }

    static {
        //如果使用UsbNativeAPI 必须加载 适用root板 这个不需要权限窗口和申请权限
        System.loadLibrary("usb1.0");
        //串口
        System.loadLibrary("serial_icod");
        //图片
        System.loadLibrary("image_icod");
    }


    public void connect(){
        io = new SerialAPI(new File("/dev/ttyS4"),38400,0);
        mPrinter=PrinterAPI.getInstance();
        mPrinter.connect(io);
//        try {
//            mPrinter.printString("测试","gbk",true);
//            mPrinter.setFontStyle(0,false,true,true,false);
//            mPrinter.printString("\n");
//            mPrinter.printString("测试","gbk",true);
//            mPrinter.printString("\n");
//            mPrinter.printString("\n");
//            mPrinter.printString("\n");
//            mPrinter.printString("\n");
//
//            mPrinter.fullCut();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }


}
