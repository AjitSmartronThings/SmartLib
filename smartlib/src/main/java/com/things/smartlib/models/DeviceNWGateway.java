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
public class DeviceNWGateway {

    private static final String TAG = DeviceNWGateway.class.getSimpleName();

    private String gatewayaddress;

    public DeviceNWGateway() {
    }

    public String getGatewayaddress() {
        return gatewayaddress;
    }

    public void setGatewayaddress(String gatewayaddress) {
        this.gatewayaddress = gatewayaddress;
    }

    @Override
    public String toString() {
        return "DeviceNWGateway{" +
                "gatewayaddress='" + gatewayaddress + '\'' +
                '}';
    }
}
