package com.things.smartlib.requests;

import com.things.smartlib.models.OnvifType;

/**
 * The interface Onvif request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public interface OnvifRequest {
    /**
     * Gets xml.
     *
     * @return the xml
     */
    String getXml();

    /**
     * Gets type.
     *
     * @return the type
     */
    OnvifType getType();
}
