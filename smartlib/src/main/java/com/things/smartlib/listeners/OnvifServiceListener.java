package com.things.smartlib.listeners;

import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifServices;

/**
 * The interface Onvif service listener.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface OnvifServiceListener {
    /**
     * On services received.
     *
     * @param onvifDevice the onvif devcie
     * @param path        the path
     */
    void onServicesReceived(OnvifDevice onvifDevice, OnvifServices path);
}
