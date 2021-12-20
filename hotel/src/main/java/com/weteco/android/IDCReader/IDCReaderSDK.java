package com.weteco.android.IDCReader;

public class IDCReaderSDK {
    static
    {
        System.loadLibrary("weunpack");
    }
    public static native int wltGetbmp(byte[] src ,byte[] dst,byte[] base64);
}
