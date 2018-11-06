package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
import com.things.smartlib.listeners.DeviceDNSListener;
import com.things.smartlib.listeners.DeviceHostnameListener;
import com.things.smartlib.models.OnvifType;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 * This operation gets the DNS settings from a device.
 * The device shall return its DNS configurations through the GetDeviceDNS command.
 */
public class GetDeviceHostname implements OnvifRequest{

    private static final String TAG = GetDeviceHostname.class.getSimpleName();
    private DeviceHostnameListener deviceHostnameListener;

    public GetDeviceHostname(DeviceHostnameListener deviceHostnameListener) {
        super();
        this.deviceHostnameListener = deviceHostnameListener;
    }

    public DeviceHostnameListener getDeviceHostnameListener() {
        return deviceHostnameListener;
    }

    public void setDeviceHostnameListener(DeviceHostnameListener deviceHostnameListener) {
        this.deviceHostnameListener = deviceHostnameListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_HOSTNAME;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_HOSTNAME;
    }
}
