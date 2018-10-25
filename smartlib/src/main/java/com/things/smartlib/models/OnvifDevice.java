package com.things.smartlib.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Onvif device.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifDevice extends Device{

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifDevice.class.getSimpleName();

    /**
     * The Path.
     */
//Attributes
    OnvifServices path;
    private List<String> addresses;

    /**
     * Instantiates a new Onvif device.
     *
     * @param host the host
     */
    public OnvifDevice(String host) {
        this(host,"","");
    }

    /**
     * Instantiates a new Onvif device.
     *
     * @param host     the host
     * @param username the username
     * @param password the password
     */
    public OnvifDevice(String host, String username, String password) {
        super(host, username, password);
        path = new OnvifServices();
        addresses = new ArrayList<>();
    }

    /**
     * Add address.
     *
     * @param address the address
     */
    public void addAddress(String address) {
        addresses.add(address);
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public OnvifServices getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(OnvifServices path) {
        this.path = path;
    }

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    /**
     * Sets addresses.
     *
     * @param addresses the addresses
     */
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.ONVIF;
    }
}
