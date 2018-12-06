package com.things.smartlib.models;

import static com.things.smartlib.TronXConstants.WSDL_DEVICE;
import static com.things.smartlib.TronXConstants.WSDL_MEDIA_PROFILE;
import static com.things.smartlib.TronXConstants.WSDL_MEDIA_URI;
import static com.things.smartlib.TronXConstants.WSDL_PTZ;
import static com.things.smartlib.TronXConstants.WSDL_SERVICE;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public enum PTZMoveType {
    PTZ_ABSOLUTE("Absolute Move"),
    PTZ_RELATIVE("Relative Move"),
    PTZ_CONTINUOUS("Continuous Move");

    public final String namespace;

    PTZMoveType(String namespace) {
        this.namespace = namespace;
    }
}
