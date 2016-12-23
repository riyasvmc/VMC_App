package com.zeefive.vmcapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zeefive.vmcapp.GlobalVariables;
import com.zeefive.vmcapp.R;
import com.zeefive.vmcapp.Utilities;
import com.zeefive.vmcapp.adapter.TodoAdapter;
import com.zeefive.vmcapp.data.Data;
import com.zeefive.vmcapp.printer.PrinterCommands;
import com.zeefive.vmcapp.view.helper.SimpleItemTouchHelperCallback;

import java.io.IOException;
import java.util.BitSet;
import java.util.UUID;

import hugo.weaving.DebugLog;

import static com.zeefive.vmcapp.adapter.TodoAdapter.mActionMode;

public class ActivityTodo extends ActivityBase implements Runnable {

    private static final String TITLE = "Todos";
    private static Context mContext;
    private static TodoAdapter mAdapter;
    public static RecyclerView mRecyclerView;

    // printer
    private static final String TAG = "Riyas Vmc";
    private static final String SUCCESS = "success";
    private static final int BLUETOOTH_ENABLE = 18;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BitSet dots;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setUpActionBar(TITLE, true);

        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = Data.QUERY_TODOS;

        mAdapter = new TodoAdapter(query, this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActionMode = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id){
            case R.id.action_print:
                print();
                break;
        }
        return true;
    }

    private void print() {
        findBT();
    }

    // Thermal Printing section
    private void sendData() throws IOException {

        if(GlobalVariables.getInstance().mOutputStream != null) {
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.INIT);
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.SELECT_FONT_B);
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.JUSTIFY_CENTER);

            print_image();

            GlobalVariables.getInstance().mOutputStream.write("VMC todos\n---------\n\n".getBytes());
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.JUSTIFY_LEFT);
            DatabaseReference reference = (DatabaseReference) Data.QUERY_TODOS;
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        int i = 1;
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            String title = (String) child.child("title").getValue();
                            String project = (String) child.child("project").child("title").getValue();
                            GlobalVariables.getInstance().mOutputStream.write((i + ". " + project + " : " + title + "\n").getBytes());
                            i++;
                        }
                        GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.FEED_LINE);
                        GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.FEED_LINE);
                        GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.FEED_LINE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            showToast("Data sent");
        }else{
            Toast.makeText(this, "Output Stream is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void print_image() throws IOException {
        Bitmap bmp = Utilities.getBitmapFromAsset(getBaseContext(), "icon.bmp");
        convertArgbToGrayscale(bmp);
        GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.SET_LINE_SPACING_24);

        int offset = 0;
        while (offset < bmp.getHeight()) {
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
            for (int x = 0; x < bmp.getWidth(); ++x) {

                for (int k = 0; k < 3; ++k) {

                    byte slice = 0;
                    for (int b = 0; b < 8; ++b) {
                        int y = (((offset / 8) + k) * 8) + b;
                        int i = (y * bmp.getWidth()) + x;
                        boolean v = false;
                        if (i < dots.length()) {
                            v = dots.get(i);
                        }
                        slice |= (byte) ((v ? 1 : 0) << (7 - b));
                    }
                    GlobalVariables.getInstance().mOutputStream.write(slice);
                }
            }
            offset += 24;
            GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.FEED_LINE);
        }
        GlobalVariables.getInstance().mOutputStream.write(PrinterCommands.SET_LINE_SPACING_30);
    }

    private void convertArgbToGrayscale(Bitmap bmpOriginal) {
        int pixel;
        int k = 0;
        int B = 0, G = 0, R = 0;
        dots = new BitSet();
        try {

            for (int x = 0; x < bmpOriginal.getHeight(); x++) {
                for (int y = 0; y < bmpOriginal.getWidth(); y++) {
                    // get one pixel color
                    pixel = bmpOriginal.getPixel(y, x);

                    // retrieve color of all channels
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    // take conversion up to one single value by calculating
                    // pixel intensity.
                    R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                    // set bit into bitset, by calculating the pixel's lumad
                    if (R < 55) {
                        dots.set(k);//this is the bitset that i'm printing
                    }
                    k++;
                }


            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, e.toString());
        }
    }

    private void findBT()
    {
        GlobalVariables.getInstance().mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(GlobalVariables.getInstance().mBluetoothAdapter == null)
        {
            // No Bluetooth device found!
            return;
        }else {
            if (!GlobalVariables.getInstance().mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, BLUETOOTH_ENABLE);
            }else {
                if(GlobalVariables.getInstance().mDevice == null) {
                    connectDevice();
                }else{
                    try {
                        sendData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @DebugLog
    private void connectDevice(){
        Intent connectIntent = new Intent(mContext, ActivityDeviceList.class);
        startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case BLUETOOTH_ENABLE:
                if(resultCode == Activity.RESULT_OK){
                    connectDevice();
                }else{
                    Toast.makeText(this, "Access denied!", Toast.LENGTH_SHORT).show();
                }

                break;

            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle mExtra = data.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    GlobalVariables.getInstance().mDevice = GlobalVariables.getInstance().mBluetoothAdapter.getRemoteDevice(mDeviceAddress);

                    mBluetoothConnectProgressDialog = ProgressDialog.show(this, "Connecting...",
                            GlobalVariables.getInstance().mDevice.getName(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void run() {
        try {
            GlobalVariables.getInstance().mSocket = GlobalVariables.getInstance().mDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            GlobalVariables.getInstance().mBluetoothAdapter.cancelDiscovery();
            GlobalVariables.getInstance().mSocket.connect();
            GlobalVariables.getInstance().mOutputStream = GlobalVariables.getInstance().mSocket.getOutputStream();
            GlobalVariables.getInstance().mInputStream = GlobalVariables.getInstance().mSocket.getInputStream();
            mHandler_Printer.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(GlobalVariables.getInstance().mSocket);
            mBluetoothConnectProgressDialog.cancel();
            //Toast.makeText(ActivityOrderDetail.this, "Connecting failed", Toast.LENGTH_SHORT).show();
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

    private Handler mHandler_Printer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(ActivityTodo.this, "DeviceConnected", Toast.LENGTH_LONG).show();
            try {
                sendData();
            }catch (Exception e){

            }
        }
    };
}