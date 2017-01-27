package com.firebasepractice.pravin103082.contentproviderpractice.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebasepractice.pravin103082.contentproviderpractice.R;
import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity implements AvaiableDeviceListener,BluetoothOnOffListener {

    BluetoothBroadCastReceiver bluetoothBroadCastReceiver;
    IntentFilter intentFilter=new IntentFilter();

    ListView lstBluetoothDevices;
    BluetoothDeviceAdapter bluetoothDeviceAdapter,bluetoothAvaiableDevices;
    List<BluetoothADevice> bluetoothADevices,avaiableDevices;
    ListView lstAvaivbleDevices;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        linearLayout=(LinearLayout)findViewById(R.id.activity_bluetooth) ;
        lstAvaivbleDevices=new ListView(this);
        lstBluetoothDevices=(ListView)findViewById(R.id.lstBluetoothDevices);
        TextView textView=new TextView(this);
        textView.setText("Paired Device");
        lstBluetoothDevices.addHeaderView(textView);
        bluetoothBroadCastReceiver=new BluetoothBroadCastReceiver(this,this);


        avaiableDevices=new ArrayList<>();
        linearLayout.addView(lstAvaivbleDevices);
        TextView textView1=new TextView(this);
        textView1.setText("Avaiable Devices");
        lstAvaivbleDevices.addHeaderView(textView1);
        bluetoothAvaiableDevices=new BluetoothDeviceAdapter(this,avaiableDevices);
        lstAvaivbleDevices.setAdapter(bluetoothAvaiableDevices);

        bluetoothADevices =new ArrayList<>();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);


       /*
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                Toast.makeText(BluetoothActivity.this,"DISABLE",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(BluetoothActivity.this,"ENABLE",Toast.LENGTH_SHORT).show();

            }
        }*/
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        mBluetoothAdapter.startDiscovery();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                BluetoothADevice bluetoothADevice =new BluetoothADevice();
                bluetoothADevice.setName(deviceName);
                bluetoothADevice.setPhysicalAddress(deviceHardwareAddress);
                bluetoothADevice.setBondStatus(device.getBondState());



                Log.e("NAME "+deviceName, "device Address "+deviceHardwareAddress+"  "+ bluetoothADevice.getBondStatus()+" device"+device.getBluetoothClass());
                BluetoothClass bluetoothClass = device.getBluetoothClass();
                Log.d(TAG, "Device: "+bluetoothClass.getDeviceClass() );
                if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES) {
                  Log.e(">"+device.getName(),"AUDIO_VIDEO_HEADPHONES");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE){
                    Log.e(">"+device.getName(),"AUDIO_VIDEO_HANDSFREE");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.PHONE_SMART){
                    Log.e(">"+device.getName(),"PHONE_SMART");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO){
                    Log.e(">"+device.getName(),"AUDIO_VIDEO_HIFI_AUDIO");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.WEARABLE_WRIST_WATCH){
                    Log.e(">"+device.getName(),"WEARABLE_WRIST_WATCH");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.WEARABLE_UNCATEGORIZED){
                    Log.e(">"+device.getName(),"WEARABLE_UNCATEGORIZED");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE){
                    Log.e(">"+device.getName(),"AUDIO_VIDEO_MICROPHONE");
                }else if(bluetoothClass.getDeviceClass() == BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET){
                    Log.e(">"+device.getName(),"AUDIO_VIDEO_WEARABLE_HEADSET");
                }else if(bluetoothClass.getDeviceClass()==BluetoothClass.Device.COMPUTER_LAPTOP){
                    Log.e(">"+device.getName(),"AUDIO_VIDEO_WEARABLE_HEADSET");
                }
                bluetoothADevices.add(bluetoothADevice);
            }
        }

        bluetoothDeviceAdapter=new BluetoothDeviceAdapter(BluetoothActivity.this, bluetoothADevices);
        if(lstBluetoothDevices!=null){
            lstBluetoothDevices.setAdapter(new BluetoothDeviceAdapter(BluetoothActivity.this, bluetoothADevices));
        }else {
            Log.e("Null","Null");
        }
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("Please Enable Bluetooth");
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.e(TAG, "onKey: " );
                    finish();
                    dialog.dismiss();
                }
                return true;
            }
        });
    }
    Dialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bluetoothBroadCastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(bluetoothBroadCastReceiver);
    }

    @Override
    public void addBluetoothDevice(BluetoothADevice bluetoothADevice) {
        avaiableDevices.add(bluetoothADevice);
        bluetoothAvaiableDevices.notifyDataSetChanged();
    }

    public static final String TAG=BluetoothActivity.class.getSimpleName();
    @Override
    public void bluetoothStatus(boolean status) {
        Log.e(TAG, "bluetoothStatus: "+status );
        if(!status){
            dialog.show();
        }else {
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
