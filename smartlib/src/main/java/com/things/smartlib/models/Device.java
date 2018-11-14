package com.things.smartlib.models;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * The type Device.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public abstract class Device implements Comparable<Device>{

    /**
     * The constant TAG.
     */
    public static final String TAG = Device.class.getSimpleName();
    private static final String FORMAT_HTTP = "http://%s";

    private String host;
    private String username;
    private String password;
    private boolean connected;

    /**
     * Instantiates a new Device.
     *
     * @param host the host
     */
    public Device(String host) {
        this(host,"","");
    }


    /**
     * Instantiates a new Device.
     *
     * @param host     the host
     * @param username the username
     * @param password the password
     */
    public Device(String host, String username, String password) {
        this.host = buildUrl(host);
        //this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets host.
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Is connected boolean.
     *
     * @return the boolean
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Sets connected.
     *
     * @param connected the connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public int compareTo(@NonNull Device device) {
        return getHost().compareTo(device.getHost());
    }

    private String buildUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://"))
            return url;

        return String.format(Locale.getDefault(), FORMAT_HTTP, url);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public abstract DeviceType getType();
}
