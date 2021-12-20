package com.act.F1_api;

/**
 * Created by chy on 15-4-21.
 */
public class R1Exception extends Exception {
    private int mCode;

    public R1Exception(int code, String detail) {
        super(detail);
        mCode = code;
    }

    public int getErrorCode() {
        return mCode;
    }
}
