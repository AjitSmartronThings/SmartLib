package com.things.smartlib.requests;

import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifType;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/29/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class PTZStopRequest implements OnvifRequest{

    private static final String TAG = PTZStopRequest.class.getSimpleName();

    private final  OnvifMediaProfile mediaProfile;

    public PTZStopRequest(OnvifMediaProfile mediaProfile) {
        super();
        this.mediaProfile = mediaProfile;
    }

    public OnvifMediaProfile getMediaProfile() {
        return mediaProfile;
    }

    @Override
    public String getXml() {
        return "<Stop xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">" +
                "<ProfileToken>" + mediaProfile.getToken() + "</ProfileToken>" +
                "<PanTilt>" +
                "true" +
                "</PanTilt>" +
                "<Zoom>" +
                "true" +
                "</Zoom>" +
                "</Stop>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.PTZ;
    }
}