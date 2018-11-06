package com.things.smartlib.listeners;

import com.things.smartlib.models.DeviceDNS;
import com.things.smartlib.models.DeviceHostname;
import com.things.smartlib.models.OnvifDevice;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface DeviceHostnameListener {
    void OnHostnameReceived(OnvifDevice onvifDevice, DeviceHostname deviceHostname);
}
