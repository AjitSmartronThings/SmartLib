package com.things.smartlib;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public enum DiscoveryMode {
    ONVIF(3702),
    UPNP(1900);

    public final int port;

    DiscoveryMode(int port) {
        this.port = port;
    }
}
