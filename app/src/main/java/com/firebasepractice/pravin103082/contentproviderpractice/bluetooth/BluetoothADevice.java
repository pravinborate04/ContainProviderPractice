package com.firebasepractice.pravin103082.contentproviderpractice.bluetooth;

/**
 * Created by Pravin103082 on 23-12-2016.
 */

public class BluetoothADevice
{
    String name;
    String physicalAddress;
    int bondStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public int getBondStatus() {
        return bondStatus;
    }

    public void setBondStatus(int bondStatus) {
        this.bondStatus = bondStatus;
    }
}
