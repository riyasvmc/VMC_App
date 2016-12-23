package com.zeefive.vmcapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.printer.PrinterCommands;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class ActivityReceiptPrint extends Activity implements Runnable{
    // constants
    public static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // views
    private ProgressDialog mBluetoothConnectProgressDialog;
    TextView myLabel;
    EditText myTextbox;

    // bluetooth printer
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button openButton = (Button)findViewById(R.id.button_open);
        openButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(ActivityReceiptPrint.this, "Device Not Support", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    } else {
                        Intent connectIntent = new Intent(ActivityReceiptPrint.this, ActivityDeviceList.class);
                        startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });

        Button printButton = (Button)findViewById(R.id.button_print);
        printButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                Thread t = new Thread() {
                    public void run() {
                        try {

                            OutputStream os = mBluetoothSocket.getOutputStream();
                            String BILL = "";

                            BILL = "\nProjects List\n";
                            BILL = BILL
                                    + "------------------------------";
                            BILL = BILL + "\n";

                            BILL += "\n\n\n";
                            os.write(PrinterCommands.INIT);
                            os.write(BILL.getBytes());

                        } catch (Exception e) {
                            Log.e("Main", "Exe ", e);
                        }
                    }
                };
                t.start();
            }
        });
        Button closeButton = (Button)findViewById(R.id.button_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                if (mBluetoothAdapter != null)
                    mBluetoothAdapter.disable();
            }
        });

        myLabel = (TextView)findViewById(R.id.label);
        myTextbox = (EditText)findViewById(R.id.editText);

    }

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    public void onActivityResult(int mRequestCode, int mResultCode, Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);
        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString(ActivityDeviceList.TAG);
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);

                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Connecting...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent(ActivityReceiptPrint.this, ActivityDeviceList.class);
                    startActivityForResult(intent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(ActivityReceiptPrint.this, "Bluetooth activate denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            mBluetoothConnectProgressDialog.cancel();
            Toast.makeText(ActivityReceiptPrint.this, "Connecting failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(ActivityReceiptPrint.this, "DeviceConnected", Toast.LENGTH_LONG).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

    public byte[] sel(int val) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(val);
        buffer.flip();
        return buffer.array();
    }

    public static class UnicodeFormatter {

        static public String byteToHex(byte b) {
            // Returns hex String representation of byte b
            char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
            return new String(array);
        }

        static public String charToHex(char c) {
            // Returns hex String representation of char c
            byte hi = (byte) (c >>> 8);
            byte lo = (byte) (c & 0xff);
            return byteToHex(hi) + byteToHex(lo);
        }

    } // class
}