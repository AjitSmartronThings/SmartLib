package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifType;

/**
 * The type Get media stream request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetMediaStreamRequest implements OnvifRequest {

    /**
     * The constant TAG.
     */
//Constants
    public static final String TAG = GetMediaStreamRequest.class.getSimpleName();

    //Attributes
    private final OnvifMediaProfile mediaProfile;
    private final OnvifStreamUriListener listener;

    /**
     * Instantiates a new Get media stream request.
     *
     * @param mediaProfile the media profile
     * @param listener     the listener
     */
//Constructors
    public GetMediaStreamRequest(OnvifMediaProfile mediaProfile, OnvifStreamUriListener listener) {
        super();
        this.mediaProfile = mediaProfile;
        this.listener = listener;
    }

    //Properties

    /**
     * Gets media profile.
     *
     * @return the media profile
     */
    public OnvifMediaProfile getMediaProfile() {
        return mediaProfile;
    }

    /**
     * Gets listener.
     *
     * @return the listener
     */
    public OnvifStreamUriListener getListener() {
        return listener;
    }

    @Override
    public String getXml() {
        return "<GetStreamUri xmlns=\"http://www.onvif.org/ver10/media/wsdl\">"
                + "<StreamSetup>"
                + "<Stream xmlns=\"http://www.onvif.org/ver10/schema\">RTP-Unicast</Stream>"
                + "<Transport xmlns=\"http://www.onvif.org/ver10/schema\"><Protocol>RTSP</Protocol></Transport>"
                + "</StreamSetup>"
                + "<ProfileToken>" + mediaProfile.getToken() + "</ProfileToken>"
                + "</GetStreamUri>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STREAM_URI;
    }

}
