package com.chucai.hotel.bean;

import android.graphics.Bitmap;

import com.huaka.usb.sdk.HkIDCardInfo;

public class IdCardUser implements Cloneable{
    private Bitmap bitmap;
    private Bitmap newBit;


    public void setNewBit(Bitmap newBit) {
        this.newBit = newBit;
    }


    public Bitmap getNewBit() {
        return newBit;
    }

    private HkIDCardInfo hkIDCardInfo;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public HkIDCardInfo getHkIDCardInfo() {
        return hkIDCardInfo;
    }

    public void setHkIDCardInfo(HkIDCardInfo hkIDCardInfo) {
        this.hkIDCardInfo = hkIDCardInfo;
    }
}
