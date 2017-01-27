package com.firebasepractice.pravin103082.contentproviderpractice.wifi;

import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.Device;

import java.util.List;

/**
 * Created by Pravin103082 on 22-12-2016.
 */

public interface NewWifiDeviceListener {

    public void newDevices(List<Device> deviceList);
}
