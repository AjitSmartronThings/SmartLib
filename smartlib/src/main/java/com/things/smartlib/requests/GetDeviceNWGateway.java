package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
import com.things.smartlib.listeners.DeviceHostnameListener;
import com.things.smartlib.listeners.DeviceNWGatewayListener;
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
public class GetDeviceNWGateway implements OnvifRequest{

    private static final String TAG = GetDeviceNWGateway.class.getSimpleName();
    private DeviceNWGatewayListener deviceNWGatewayListener;

    public GetDeviceNWGateway(DeviceNWGatewayListener deviceNWGatewayListener) {
        super();
        this.deviceNWGatewayListener = deviceNWGatewayListener;
    }

    public DeviceNWGatewayListener getDeviceNWGatewayListener() {
        return deviceNWGatewayListener;
    }

    public void setDeviceNWGatewayListener(DeviceNWGatewayListener deviceNWGatewayListener) {
        this.deviceNWGatewayListener = deviceNWGatewayListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_NWGATEWAY;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_NWGATEWAY;
    }
}
