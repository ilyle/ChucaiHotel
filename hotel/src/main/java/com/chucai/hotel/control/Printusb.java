package com.chucai.hotel.control;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;

import java.util.Collection;
import java.util.HashMap;

public class Printusb {
    static String TAG="print";
    static Context mcontext;
    static Pointer Usbpointer;
    public static void print_init( Context scontext)
    {
        mcontext= scontext;
        Usbpointer=null;

    }
    // 获取打印机设备
    public static UsbDevice GetPrinterUsbDevice() {
        UsbManager usbManager = (UsbManager) mcontext.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceHashMap = usbManager.getDeviceList();
        Collection<UsbDevice> deviceCollection = deviceHashMap.values();
        for (UsbDevice usbDevice : deviceCollection) {
            // 检查是否是我们的打印机
            Log.d("","getVendorId="+usbDevice.getVendorId());
            if ((usbDevice.getVendorId() == 0x4b43) || (usbDevice.getVendorId() == 0x0fe6)) {
                return usbDevice;
            }
        }
        return null;
    }

    // 检查是否有权限，如果没有权限，申请权限
    // 这一步一定要做，如果没有权限，所有的USB读写操作都是不被允许的
    // 而且每次设备USB数据线断开重连之后，都要重新申请权限
    // 具体的请看UsbManager的requestPermission函数就可以知道了
    public static boolean CheckUsbDevicePermission(UsbDevice usbDevice) {
        UsbManager usbManager = (UsbManager) mcontext.getSystemService(Context.USB_SERVICE);
        if (usbManager.hasPermission(usbDevice)) {
            Log.d("pyq chk ","chk ok");
            return true;
        } else {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, 0, new Intent(mcontext.getPackageName()), 0);
            usbManager.requestPermission(usbDevice, pendingIntent);
            Log.d("pyq chk ","error");
            return false;
        }
    }

    // 打开设备
    public static Pointer OpenUsbDevice(UsbDevice usbDevice) {
        Pointer h = Pointer.NULL;
        if (usbDevice != null) {
            String usbPort = String.format("VID:0x%04X,PID:0x%04X", usbDevice.getVendorId(), usbDevice.getProductId());
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb(usbPort, 0);
        }
        Log.i(TAG, h == Pointer.NULL ? "OpenPort Failed" : "OpenPort Success");
        if (h == Pointer.NULL) {
            Log.i(TAG,"OpenPort Failed");
        }
        return h;
    }
    public static Pointer getPointer()
    {
        /*
        if (Usbpointer==null) {
            UsbDevice usbDevice = GetPrinterUsbDevice();
            if (usbDevice == null) {
                Log.i(TAG, "Not Found Usb Printer");
            }
            if (!CheckUsbDevicePermission(usbDevice)) {
                Log.i(TAG, "Not Usb Permission");
            }
            Usbpointer = OpenUsbDevice(usbDevice);
        }

         */
        Usbpointer = OpenPort();

        return Usbpointer;
    }
    public static Pointer OpenPort()
    {
        Pointer h = Pointer.NULL;
        Log.d("","open in");
        String[] listUsbPort = AutoReplyPrint.CP_Port_EnumUsb_Helper.EnumUsb();
        Log.d("","open list");
        if (listUsbPort != null) {
            for (String usbPort : listUsbPort) {
                if (usbPort.contains("0x4B43") || usbPort.contains("0x0FE6")) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb(usbPort, 1);
                    break;
                }
            }
        }
        Log.i(TAG, h == Pointer.NULL ? "OpenPort Failed" : "OpenPort Success");
        if (h == Pointer.NULL) {
            Log.i(TAG,"OpenPort Failed");
        }
        return h;
    }

/*
    public static void Test_Pos_QueryPrintResult(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 30000);
        if (!result)
            Log.i(TAG, "Print failed");
        else
            Log.i(TAG, "Print Success");
    }
*/


}
