package com.things.smartlib.models;

import static com.things.smartlib.TronXConstants.DT_DEVICE;
import static com.things.smartlib.TronXConstants.DT_NW_VIDEO_TRANSMITTER;
import static com.things.smartlib.TronXConstants.ONE;
import static com.things.smartlib.TronXConstants.ZERO;

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
    DEVICE(ZERO, DT_DEVICE),
    /**
     * Network video transmitter discovery type.
     */
    NETWORK_VIDEO_TRANSMITTER(ONE, DT_NW_VIDEO_TRANSMITTER);

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
