package com.things.smartlib.requests;

import com.things.smartlib.TronXConstants;
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
 * This operation sets the discovery mode operation of a device
 */
public class GetDeviceDiscoveryMode implements OnvifRequest{

    private static final String TAG = GetDeviceDiscoveryMode.class.getSimpleName();
    private DeviceDiscoverModeListener deviceDiscoverModeListener;

    public DeviceDiscoverModeListener getDeviceDiscoverModeListener() {
        return deviceDiscoverModeListener;
    }

    public void setDeviceDiscoverModeListener(DeviceDiscoverModeListener deviceDiscoverModeListener) {
        this.deviceDiscoverModeListener = deviceDiscoverModeListener;
    }

    public GetDeviceDiscoveryMode(DeviceDiscoverModeListener deviceDiscoverModeListener) {
        super();
        this.deviceDiscoverModeListener = deviceDiscoverModeListener;
    }

    @Override
    public String getXml() {
        return TronXConstants.DEVICE_DISCOVER_MODE;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_DISCOVER_MODE;
    }
}
