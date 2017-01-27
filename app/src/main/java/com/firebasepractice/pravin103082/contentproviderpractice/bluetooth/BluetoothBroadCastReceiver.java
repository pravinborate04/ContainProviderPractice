package com.firebasepractice.pravin103082.contentproviderpractice.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Pravin103082 on 23-12-2016.
 */

public class BluetoothBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG =BluetoothBroadCastReceiver.class.getSimpleName() ;

    AvaiableDeviceListener avaiableDeviceListener;
    BluetoothOnOffListener bluetoothOnOffListener;

    public BluetoothBroadCastReceiver(AvaiableDeviceListener avaiableDeviceListener,BluetoothOnOffListener bluetoothOnOffListener){
        this.avaiableDeviceListener=avaiableDeviceListener;
        this.bluetoothOnOffListener=bluetoothOnOffListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String action=intent.getAction();
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            if(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)== BluetoothAdapter.STATE_OFF){

                bluetoothOnOffListener.bluetoothStatus(false);
            }else {
                bluetoothOnOffListener.bluetoothStatus(true);
            }
        }
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            Log.e(TAG, "onReceive: ACTION_FOUND" );
            // Get the BluetoothADevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // Add the name and address to an array adapter to show in a ListView
            Log.e(TAG, "onReceive: "+(device.getName() + "\n" + device.getAddress()));

            BluetoothADevice bluetoothADevice=new BluetoothADevice();
            bluetoothADevice.setName(device.getName());
            BluetoothClass bluetoothClass = device.getBluetoothClass();
            Log.d(TAG, "AVAIBLE Device: "+bluetoothClass.getDeviceClass() );

            bluetoothADevice.setPhysicalAddress(device.getAddress());
            bluetoothADevice.setBondStatus(device.getBondState());
            avaiableDeviceListener.addBluetoothDevice(bluetoothADevice);
        }


    }
}
