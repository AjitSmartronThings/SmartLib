package com.things.smartlib.listeners;

import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.responses.OnvifResponse;

/**
 * The interface Onvif response listener.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface OnvifResponseListener {
    /**
     * On response.
     *
     * @param onvifDevice   the onvif devcie
     * @param onvifResponse the onvif response
     */
    void onResponse(OnvifDevice onvifDevice, OnvifResponse onvifResponse);

    /**
     * On error.
     *
     * @param onvifDevice  the onvif devcie
     * @param errorCode    the error code
     * @param errorMessage the error message
     */
    void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage);
}
