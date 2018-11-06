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
public class DeviceDNS {

    private static final String TAG = DeviceDNS.class.getSimpleName();

    private String fromdhcp;
    private String dnstype;
    private String dnsip;

    public DeviceDNS() {
    }

    public String getFromdhcp() {
        return fromdhcp;
    }

    public void setFromdhcp(String fromdhcp) {
        this.fromdhcp = fromdhcp;
    }

    public String getDnstype() {
        return dnstype;
    }

    public void setDnstype(String dnstype) {
        this.dnstype = dnstype;
    }

    public String getDnsip() {
        return dnsip;
    }

    public void setDnsip(String dnsip) {
        this.dnsip = dnsip;
    }

    @Override
    public String toString() {
        return "DeviceDNS{" +
                "fromdhcp=" + fromdhcp +
                ", dnstype='" + dnstype + '\'' +
                ", dnsip='" + dnsip + '\'' +
                '}';
    }
}
