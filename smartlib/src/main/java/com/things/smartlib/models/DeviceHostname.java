package com.things.smartlib.models;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceHostname {

    private static final String TAG = DeviceHostname.class.getSimpleName();

    private String fromdhcp;
    private String hostname;

    public DeviceHostname() {
    }

    public String getFromdhcp() {
        return fromdhcp;
    }

    public void setFromdhcp(String fromdhcp) {
        this.fromdhcp = fromdhcp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "DeviceHostname{" +
                "fromdhcp='" + fromdhcp + '\'' +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
