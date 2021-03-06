package com.things.smartlib.requests;

import com.things.smartlib.OnvifXMLBuilder;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.DeviceMediaProfile;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifType;

import java.util.Locale;

import static com.things.smartlib.TronXConstants.REQUEST_MEDIA_STREAM_URI;

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
    private final DeviceMediaProfile mediaProfile;
    private final OnvifStreamUriListener listener;

    /**
     * Instantiates a new Get media stream request.
     *
     * @param mediaProfile the media profile
     * @param listener     the listener
     */
//Constructors
    public GetMediaStreamRequest(DeviceMediaProfile mediaProfile, OnvifStreamUriListener listener) {
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
    public DeviceMediaProfile getMediaProfile() {
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
        String stremUri = String.format(Locale.getDefault(), REQUEST_MEDIA_STREAM_URI, mediaProfile.getToken());
        return stremUri;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.GET_STREAM_URI;
    }

}
