package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
import com.things.smartlib.listeners.DeviceNWGatewayListener;
import com.things.smartlib.listeners.DeviceNWInterfacesListener;
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
public class GetDeviceNWInterfaces implements OnvifRequest{

    private static final String TAG = GetDeviceNWInterfaces.class.getSimpleName();
    private DeviceNWInterfacesListener nwInterfacesListener;

    public GetDeviceNWInterfaces(DeviceNWInterfacesListener nwInterfacesListener) {
        super();
        this.nwInterfacesListener = nwInterfacesListener;
    }

    public DeviceNWInterfacesListener getNwInterfacesListener() {
        return nwInterfacesListener;
    }

    public void setNwInterfacesListener(DeviceNWInterfacesListener nwInterfacesListener) {
        this.nwInterfacesListener = nwInterfacesListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_NWINTERFACES;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_NWINTERFACES;
    }
}
