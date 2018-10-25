package com.things.smartlib.models;

import com.things.smartlib.upnp.UPnPDeviceInformation;

/**
 * The type U pn p device.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class UPnPDevice extends Device {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = UPnPDevice.class.getSimpleName();

    //Attributes
    private String header;
    private String location;
    private String server;
    private String usn;
    private String st;
    private UPnPDeviceInformation deviceInformation;

    //Constructors

    /**
     * Instantiates a new U pn p device.
     *
     * @param hostName the host name
     */
    public UPnPDevice(String hostName) {
        this(hostName, "", "", "", "", "");
    }

    /**
     * Instantiates a new U pn p device.
     *
     * @param hostName the host name
     * @param header   the header
     * @param location the location
     * @param server   the server
     * @param usn      the usn
     * @param st       the st
     */
    public UPnPDevice(String hostName, String header, String location,
                      String server, String usn, String st) {
        super(hostName);
        deviceInformation = new UPnPDeviceInformation();
        initHeaders(header, location, server, usn, st);
    }

    //Methods
    private void initHeaders(String header, String location,
                             String server, String usn, String st) {
        this.header = header;
        this.location = location;
        this.server = server;
        this.usn = usn;
        this.st = st;
    }

    //Properties

    /**
     * Gets header.
     *
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets header.
     *
     * @param header the header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets server.
     *
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets server.
     *
     * @param server the server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets usn.
     *
     * @return the usn
     */
    public String getUSN() {
        return usn;
    }

    /**
     * Sets usn.
     *
     * @param usn the usn
     */
    public void setUSN(String usn) {
        this.usn = usn;
    }

    /**
     * Gets st.
     *
     * @return the st
     */
    public String getST() {
        return st;
    }

    /**
     * Sets st.
     *
     * @param st the st
     */
    public void setST(String st) {
        this.st = st;
    }

    /**
     * Gets device information.
     *
     * @return the device information
     */
    public UPnPDeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    /**
     * Sets device information.
     *
     * @param deviceInformation the device information
     */
    public void setDeviceInformation(UPnPDeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.UPNP;
    }

}
