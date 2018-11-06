package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
import com.things.smartlib.listeners.DeviceDNSListener;
import com.things.smartlib.listeners.DeviceDiscoverModeListener;
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
public class GetDeviceDNS implements OnvifRequest{

    private static final String TAG = GetDeviceDNS.class.getSimpleName();
    private DeviceDNSListener deviceDNSListener;

    public GetDeviceDNS(DeviceDNSListener deviceDNSListener) {
        super();
        this.deviceDNSListener = deviceDNSListener;
    }

    public DeviceDNSListener getDeviceDNSListener() {
        return deviceDNSListener;
    }

    public void setDeviceDNSListener(DeviceDNSListener deviceDNSListener) {
        this.deviceDNSListener = deviceDNSListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_DNS;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_DNS;
    }
}
