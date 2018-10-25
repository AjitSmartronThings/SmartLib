package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifMediaProfileListener;
import com.things.smartlib.models.OnvifType;

/**
 * The type Get media profiles request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetMediaProfilesRequest implements OnvifRequest {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetMediaProfilesRequest.class.getSimpleName();

    //Attributes
    private final OnvifMediaProfileListener listener;

    /**
     * Instantiates a new Get media profiles request.
     *
     * @param listener the listener
     */
//Constructors
    public GetMediaProfilesRequest(OnvifMediaProfileListener listener) {
        super();
        this.listener = listener;
    }

    //Properties

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public OnvifMediaProfileListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetProfiles xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_MEDIA_PROFILES;
    }

}
