package com.things.smartlib.models;

import com.things.smartlib.listeners.DeviceDiscoverModeListener;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceDiscoveryMode {

    private static final String TAG = DeviceDiscoveryMode.class.getSimpleName();

    private String discoverymode;

    public DeviceDiscoveryMode() {
    }

    public String getDiscoverymode() {
        return discoverymode;
    }

    public void setDiscoverymode(String discoverymode) {
        this.discoverymode = discoverymode;
    }

    @Override
    public String toString() {
        return "DeviceDiscoveryMode{" +
                "discoverymode='" + discoverymode + '\'' +
                '}';
    }
}
