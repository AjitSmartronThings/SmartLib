package com.things.smartlib.upnp;

import com.things.smartlib.models.UPnPDevice;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.upnp
 * @date :         11/1/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface UPnPDeviceInformationListener {
    void onDeviceInformationReceived(UPnPDevice device, UPnPDeviceInformation deviceInformation);

    void onError(UPnPDevice onvifDevice, int errorCode, String errorMessage);
}
