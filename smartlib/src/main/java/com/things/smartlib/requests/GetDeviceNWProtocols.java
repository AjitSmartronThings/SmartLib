package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
import com.things.smartlib.listeners.DeviceNWInterfacesListener;
import com.things.smartlib.listeners.DeviceNWProtoclosListener;
import com.things.smartlib.models.DeviceNWProtocols;
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
public class GetDeviceNWProtocols implements OnvifRequest{

    private static final String TAG = GetDeviceNWProtocols.class.getSimpleName();
    private DeviceNWProtoclosListener nwProtoclosListener;

    public GetDeviceNWProtocols(DeviceNWProtoclosListener nwProtoclosListener) {
        super();
        this.nwProtoclosListener = nwProtoclosListener;
    }

    public DeviceNWProtoclosListener getNwProtoclosListener() {
        return nwProtoclosListener;
    }

    public void setNwProtoclosListener(DeviceNWProtoclosListener nwProtoclosListener) {
        this.nwProtoclosListener = nwProtoclosListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_NWPROTOCOLS;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_NWPROTOCOLS;
    }
}
