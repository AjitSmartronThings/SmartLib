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
public class DeviceNWInterfaces {

    private static final String TAG = DeviceNWInterfaces.class.getSimpleName();

    private String hwaddress;
    private String address;
    private String prefixlength;
    private String dhcp;

    public DeviceNWInterfaces() {
    }

    public String getHwaddress() {
        return hwaddress;
    }

    public void setHwaddress(String hwaddress) {
        this.hwaddress = hwaddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrefixlength() {
        return prefixlength;
    }

    public void setPrefixlength(String prefixlength) {
        this.prefixlength = prefixlength;
    }

    public String getDhcp() {
        return dhcp;
    }

    public void setDhcp(String dhcp) {
        this.dhcp = dhcp;
    }

    @Override
    public String toString() {
        return "DeviceNWInterfaces{" +
                "hwaddress='" + hwaddress + '\'' +
                ", address='" + address + '\'' +
                ", prefixlength='" + prefixlength + '\'' +
                ", dhcp='" + dhcp + '\'' +
                '}';
    }
}
