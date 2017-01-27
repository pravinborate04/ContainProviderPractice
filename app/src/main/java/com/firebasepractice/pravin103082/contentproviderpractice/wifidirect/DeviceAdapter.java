package com.firebasepractice.pravin103082.contentproviderpractice.wifidirect;

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
 * Created by Pravin103082 on 22-12-2016.
 */

public class DeviceAdapter extends ArrayAdapter<Device> {

    Context mContext;
    List<Device> devices;
    LayoutInflater layoutInflater;


    public DeviceAdapter(Context context,List<Device> deviceList) {
        super(context, R.layout.device_single_row);
        this.mContext=context;
        this.devices=deviceList;
        layoutInflater=LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceViewHolder holder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.device_single_row,parent,false);
            holder=new DeviceViewHolder();
            holder.textView= (TextView) convertView.findViewById(R.id.txtSingleRowDevice);
            convertView.setTag(holder);
        }else {
            holder=(DeviceViewHolder)convertView.getTag();
        }
        holder.textView.setText(devices.get(position).getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    class DeviceViewHolder{
        TextView textView;

    }
}
