package com.firebasepractice.pravin103082.contentproviderpractice.wifi;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.firebasepractice.pravin103082.contentproviderpractice.R;
import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.Device;
import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.DeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class WifiActivity extends AppCompatActivity implements NewWifiDeviceListener {
    WifiManager mWifiManager;
    WifiOnlyBroadCastReceiver mWifiScanReceiver;
    ListView lstWifiSearches;
    DeviceAdapter deviceAdapter;
    List<Device> deviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        lstWifiSearches=(ListView)findViewById(R.id.lstWifiSearches);
        deviceList=new ArrayList<>();

        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        // add your logic here
        Log.e("SIZE",mWifiManager.getScanResults()+"");
        for (ScanResult scanResult:mScanResults){
            if(!TextUtils.isEmpty(scanResult.SSID)){
                String capabilities = scanResult.capabilities;
                Device device;
                Log.e("cap",capabilities);
                if (capabilities.toUpperCase().contains("WEP")) {
                    // WEP Network
                     device=new Device(scanResult.SSID);
                } else if (capabilities.toUpperCase().contains("WPA")
                        || capabilities.toUpperCase().contains("WPA2")) {
                    // WPA or WPA2 Network
                     device=new Device(scanResult.SSID);
                } else {
                    // Open Network
                     device=new Device(scanResult.SSID+" ( OPEN )");
                }
                deviceList.add(device);
            }

        }

        deviceAdapter=new DeviceAdapter(this,deviceList);
        lstWifiSearches.setAdapter(deviceAdapter);
        mWifiManager.startScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWifiScanReceiver=new WifiOnlyBroadCastReceiver(mWifiManager,this);
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mWifiScanReceiver);

    }

    @Override
    public void newDevices(List<Device> devices) {
        deviceList.clear();
        deviceList.addAll(devices);
        deviceAdapter.notifyDataSetChanged();
    }
}
