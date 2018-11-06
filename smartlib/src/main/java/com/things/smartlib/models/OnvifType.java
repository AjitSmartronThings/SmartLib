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
public enum OnvifType {
    CUSTOM(""),
    GET_SERVICES(WSDL_SERVICE),
    GET_DEVICE_INFORMATION(WSDL_DEVICE),
    GET_MEDIA_PROFILES(WSDL_MEDIA_PROFILE),
    GET_STREAM_URI(WSDL_MEDIA_URI),
    PTZ(WSDL_PTZ),
    PTZ_STOP(WSDL_PTZ),
    DEVICE_DISCOVER_MODE(""),
    DEVICE_DNS(""),
    DEVICE_HOSTNAME(""),
    DEVICE_NWGATEWAY(""),
    DEVICE_NWINTERFACES(""),
    DEVICE_NWPROTOCOLS(""),
    DEVICE_SCOPES("");

    public final String namespace;

    OnvifType(String namespace) {
        this.namespace = namespace;
    }
}
