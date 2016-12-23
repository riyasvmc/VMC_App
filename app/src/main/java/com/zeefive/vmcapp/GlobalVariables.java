package com.zeefive.vmcapp;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.io.OutputStream;

public class GlobalVariables extends Application {
    private static GlobalVariables instance;

    // Thermal printer
    public static BluetoothAdapter mBluetoothAdapter;
    public static BluetoothSocket mSocket;
    public static BluetoothDevice mDevice;
    public static OutputStream mOutputStream;
    public static InputStream mInputStream;

    public static synchronized GlobalVariables getInstance() {

        if(instance == null){
            instance = new GlobalVariables();
        }
        return instance;
    }
}
