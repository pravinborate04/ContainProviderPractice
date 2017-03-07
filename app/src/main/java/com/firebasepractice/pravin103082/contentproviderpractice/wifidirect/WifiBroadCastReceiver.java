package com.firebasepractice.pravin103082.contentproviderpractice.wifidirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

/**
 * Created by Pravin103082 on 22-12-2016.
 */

public class WifiBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = WifiBroadCastReceiver.class.getSimpleName();
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    WifiP2pManager.PeerListListener listener;

    public WifiBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel mChannel, Context context, WifiP2pManager.PeerListListener peerListListener) {
        this.mManager = manager;
        this.mChannel = mChannel;
        this.listener = peerListListener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.e(TAG, "onReceive: WIFI_P2P_STATE_CHANGED_ACTION ");

            // Determine if Wifi Direct mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //activity.setIsWifiP2pEnabled(true);
                Log.e(TAG, "onReceive: WIFI_P2P_STATE_CHANGED_ACTION Enable");
            } else {
                Log.e(TAG, "onReceive: WIFI_P2P_STATE_CHANGED_ACTION Disable");
                // activity.setIsWifiP2pEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.e(TAG, "onReceive: WIFI_P2P_PEERS_CHANGED_ACTION ");
            mManager.requestPeers(mChannel, listener);
            // The peer list has changed!  We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.e(TAG, "onReceive: WIFI_P2P_CONNECTION_CHANGED_ACTION ");

            // Connection state changed!  We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.e(TAG, "onReceive: WIFI_P2P_THIS_DEVICE_CHANGED_ACTION ");

            Log.d(TAG, "onReceive: " + intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
    }
}
