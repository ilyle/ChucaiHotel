package com.act.r1_demo;

import java.io.Serializable;

/**
 * Created by chy on 15-4-15.
 */
@SuppressWarnings("serial")
public class SerialPortItem implements Serializable {
    private String _name;
    private String _fullPath;

    protected SerialPortItem(String name, String fullPath) {
        _name = name;
        _fullPath = fullPath;
    }

    public String getName() {
        return _name;
    }

    public String getFullPath() {
        return _fullPath;
    }

    @Override
    public String toString() {
        return _name;
    }
}
