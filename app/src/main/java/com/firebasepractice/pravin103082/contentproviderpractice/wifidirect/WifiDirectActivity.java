package com.firebasepractice.pravin103082.contentproviderpractice.wifidirect;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import java.util.ArrayList;
import java.util.List;

public class WifiDirectActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener{
    private static final String TAG = WifiDirectActivity.class.getSimpleName();
    private final IntentFilter intentFilter = new IntentFilter();

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    WifiBroadCastReceiver receiver;
    ListView lstWifiDirect;
    DeviceAdapter deviceAdapter;
    List<Device> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_direct);

        lstWifiDirect = (ListView) findViewById(R.id.lstWifiDirect);
        devices = new ArrayList<>();
        devices.add(new Device("Test"));

        deviceAdapter = new DeviceAdapter(this, devices);

        lstWifiDirect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: "+position );
                WifiP2pDevice wifiP2pDevice= (WifiP2pDevice) peers.get(position-1);
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = wifiP2pDevice.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(WifiDirectActivity.this, "Connect failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        lstWifiDirect.setAdapter(deviceAdapter);
        //  Indicates a change in the Wi-Fi Peer-to-Peer status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

       /* final List peers = new ArrayList();
        WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {

                // Out with the old, in with the new.
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.
                Log.d(TAG, "SIze" + peers.size());

                if (peers.size() == 0) {
                    Log.d(TAG, "No devices found");
                    return;
                }


                for (WifiP2pDevice wifiP2pDevice : peerList.getDeviceList()) {
                    Log.e("NAME ", wifiP2pDevice.deviceName);
                    Device device = new Device();
                    device.setName(wifiP2pDevice.deviceName);
                    devices.add(device);
                    deviceAdapter.notifyDataSetChanged();

                }
            }
        };*/

        mManager.requestPeers(mChannel,this);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "discoverPeers onSuccess");

            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "discoverPeers onFailure");

                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiBroadCastReceiver(mManager, mChannel, this,this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    List peers;
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peersList) {
        peers = new ArrayList();
// Out with the old, in with the new.
        peers.clear();
        peers.addAll(peersList.getDeviceList());

        // If an AdapterView is backed by this data, notify it
        // of the change.  For instance, if you have a ListView of available
        // peers, trigger an update.
        Log.d(TAG, "SIze" + peers.size());

        if (peers.size() == 0) {
            Log.d(TAG, "No devices found");
            return;
        }


        for (WifiP2pDevice wifiP2pDevice : peersList.getDeviceList()) {
            Log.e("NAME ", wifiP2pDevice.deviceName);
            Device device = new Device();
            device.setName(wifiP2pDevice.deviceName);
            devices.add(device);
            deviceAdapter.notifyDataSetChanged();

        }
    }
}
