package com.things.smartcam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.things.smartlib.models.Device;

import java.util.ArrayList;

public class DevicesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Device> list;

    public DevicesAdapter(Context context, ArrayList<Device> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_device, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.deviceUrl.setText(list.get(position).getHost());
        return convertView;
    }

    private class ViewHolder {
        private TextView deviceUrl;

        public ViewHolder(View view) {
            this.deviceUrl = (TextView) view.findViewById(R.id.device_url);
        }
    }
}
