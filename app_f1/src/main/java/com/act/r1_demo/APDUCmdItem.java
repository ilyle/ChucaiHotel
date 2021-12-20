package com.act.r1_demo;

/**
 * Created by chy on 15-4-22.
 */
public class APDUCmdItem {
    String mName;
    byte[] mCmdData;

    protected APDUCmdItem(String name, byte[] cmdData) {
        mName = name;
        mCmdData = cmdData;
    }

    public String getName() {
        return mName;
    }

    public byte[] getCmdData() {
        return mCmdData;
    }

    @Override
    public String toString() {
        return mName + "\t{ " + Utils.bytesToHexString(mCmdData) + " }";
    }
}
