package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.models.OnvifType;

import static com.things.smartlib.TronXConstants.REQUEST_DEVICE_INFO;

/**
 * The type Get device information request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetDeviceInformationRequest implements OnvifRequest {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetDeviceInformationRequest.class.getSimpleName();

    //Attributes
    private final OnvifDeviceInformationListener listener;

    /**
     * Instantiates a new Get device information request.
     *
     * @param listener the listener
     */
//Constructors
    public GetDeviceInformationRequest(OnvifDeviceInformationListener listener) {
        super();
        this.listener = listener;
    }

    //Properties

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public OnvifDeviceInformationListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return REQUEST_DEVICE_INFO;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_DEVICE_INFORMATION;
    }

}

