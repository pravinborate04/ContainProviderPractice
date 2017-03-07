package com.firebasepractice.pravin103082.contentproviderpractice.bluetooth;

import android.bluetooth.BluetoothDevice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import java.util.List;

/**
 * Created by Pravin103082 on 23-12-2016.
 */

public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothADevice> {

    Context mContext;
    LayoutInflater layoutInflater;
    List<BluetoothDevice> bluetoothADevices;



    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> bluetoothADevices) {

        super(context, R.layout.device_single_row);
        this.mContext=context;
        layoutInflater=LayoutInflater.from(mContext);
        this.bluetoothADevices = bluetoothADevices;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlueToothDeviceViewHolder holder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.device_single_row,parent,false);
            holder=new BlueToothDeviceViewHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.txtSingleRowDevice);
            convertView.setTag(holder);
        }else {
            holder=(BlueToothDeviceViewHolder)convertView.getTag();
        }
        if(bluetoothADevices.get(position).getName()!=null){
            holder.textView.setText(bluetoothADevices.get(position).getName());
        }else {
            holder.textView.setText(bluetoothADevices.get(position).getAddress());


        }
        return convertView;

    }

    class BlueToothDeviceViewHolder{

        TextView textView;

    }


    @Override
    public int getCount() {
        return bluetoothADevices.size();
    }
}
