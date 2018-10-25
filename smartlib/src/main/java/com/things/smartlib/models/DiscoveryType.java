package com.things.smartlib.models;

/**
 * The enum Discovery type.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public enum DiscoveryType {
    /**
     * Device discovery type.
     */
    DEVICE(0, "Device"),
    /**
     * Network video transmitter discovery type.
     */
    NETWORK_VIDEO_TRANSMITTER(1, "NetworkVideoTransmitter");

    /**
     * The Id.
     */
    public final int id;
    /**
     * The Type.
     */
    public final String type;

    DiscoveryType(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
