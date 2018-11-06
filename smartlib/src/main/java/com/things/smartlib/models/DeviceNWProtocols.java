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
public class DeviceNWProtocols {

    private static final String TAG = DeviceNWProtocols.class.getSimpleName();

    private String name;
    private String isenabled;
    private String port;

    public DeviceNWProtocols() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(String isenabled) {
        this.isenabled = isenabled;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "DeviceNWProtocols{" +
                "name='" + name + '\'' +
                ", isenabled='" + isenabled + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
