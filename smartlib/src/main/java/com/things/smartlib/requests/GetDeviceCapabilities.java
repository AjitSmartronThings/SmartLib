package com.things.smartlib.requests;

import com.things.smartlib.listeners.DeviceCapabilitiesListener;
import com.things.smartlib.models.OnvifType;

import static com.things.smartlib.TronXConstants.REQUEST_CAPABILITIES;
import static com.things.smartlib.models.OnvifType.GET_CAPABILITIES;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         11/19/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetDeviceCapabilities implements OnvifRequest{

    private static final String TAG = GetDeviceCapabilities.class.getSimpleName();

    private DeviceCapabilitiesListener deviceCapabilitiesListener;

    public GetDeviceCapabilities(DeviceCapabilitiesListener deviceCapabilitiesListener) {
        super();
        this.deviceCapabilitiesListener = deviceCapabilitiesListener;
    }

    public DeviceCapabilitiesListener getDeviceCapabilitiesListener() {
        return deviceCapabilitiesListener;
    }

    @Override
    public String getXml() {
        return REQUEST_CAPABILITIES;
    }

    @Override
    public OnvifType getType() {
        return GET_CAPABILITIES;
    }
}
