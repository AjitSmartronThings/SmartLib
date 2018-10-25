package com.things.smartlib.listeners;

import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifMediaProfile;

/**
 * The interface Onvif stream uri listener.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.listeners
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface OnvifStreamUriListener {
    /**
     * Onvif stream uri received.
     *
     * @param onvifDevice       the onvif devcie
     * @param onvifMediaProfile the onvif media profile
     * @param uri               the uri
     */
    void onvifStreamUriReceived(OnvifDevice onvifDevice, OnvifMediaProfile onvifMediaProfile, String uri);
}
