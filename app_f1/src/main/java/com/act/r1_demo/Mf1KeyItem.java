package com.act.r1_demo;

/**
 * Created by chy on 15-4-21.
 */
class Mf1KeyItem {
    private String mName;
    private boolean mIsKeyA;

    protected Mf1KeyItem(String name, boolean isKeyA) {
        mName = name;
        mIsKeyA = isKeyA;
    }

    public String getName() {
        return mName;
    }

    public boolean isKeyA() {
        return mIsKeyA;
    }

    @Override
    public String toString() {
        return mName;
    }
}
