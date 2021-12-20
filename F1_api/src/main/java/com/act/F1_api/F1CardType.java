package com.act.F1_api;

/**
 * Created by chy on 15-4-20.
 */
public enum F1CardType {
    ;
    private byte mValue;
    F1CardType(int value) {
        mValue = (byte)value;
    }
    public byte getValue() {
        return mValue;
    }
}
