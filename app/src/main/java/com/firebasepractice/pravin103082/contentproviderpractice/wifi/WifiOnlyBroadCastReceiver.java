package com.firebasepractice.pravin103082.contentproviderpractice.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pravin103082 on 22-12-2016.
 */

public class WifiOnlyBroadCastReceiver extends BroadcastReceiver {

    WifiManager mWifiManager;
    NewWifiDeviceListener listener;
    public static final String TAG=WifiOnlyBroadCastReceiver.class.getSimpleName();
    public WifiOnlyBroadCastReceiver(WifiManager wifiManager,NewWifiDeviceListener newWifiDeviceListener){
        this.mWifiManager=wifiManager;
        this.listener=newWifiDeviceListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(TAG, "onReceive: " );
        Log.e(TAG,intent.getAction());
        String action=intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            List<Device> devices=new ArrayList<>();
            Log.e(TAG, "onReceive: inside" );
            List<ScanResult> mScanResults = mWifiManager.getScanResults();
            // add your logic here
            Log.e("SIZE",mWifiManager.getScanResults()+"");
            for (ScanResult scanResult:mScanResults){
                Log.d(TAG, "onReceive: "+scanResult.SSID);
                if(!TextUtils.isEmpty(scanResult.SSID)){
                    Device device=new Device(scanResult.SSID);
                    devices.add(device);
                }
            }

            listener.newDevices(devices);
        }
    }
}
