package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.models.OnvifType;

/**
 * The type Get services request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetServicesRequest implements OnvifRequest {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetServicesRequest.class.getSimpleName();

    //Attributes
    private final OnvifServiceListener listener;

    /**
     * Instantiates a new Get services request.
     *
     * @param listener the listener
     */
//Constructors
    public GetServicesRequest(OnvifServiceListener listener) {
        super();
        this.listener = listener;
    }

    //Properties

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public OnvifServiceListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetServices xmlns=\"http://www.onvif.org/ver10/device/wsdl\">" +
                "<IncludeCapability>false</IncludeCapability>" +
                "</GetServices>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_SERVICES;
    }

}
