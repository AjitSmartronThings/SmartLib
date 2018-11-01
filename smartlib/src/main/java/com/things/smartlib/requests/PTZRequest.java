package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifPTZListener;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifType;
import com.things.smartlib.models.PTZType;

import java.util.Locale;

import static com.things.smartlib.TronXConstants.REQUEST_PTZ;

/**
 * The type Ptz request.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         10/29/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class PTZRequest implements OnvifRequest{

    private static final String TAG = PTZRequest.class.getSimpleName();

    private final OnvifMediaProfile onvifMediaProfile;
    private final int xMove,yMove,zoomVal;
    private final OnvifPTZListener onvifPTZListener;

    /**
     * Instantiates a new Ptz request.
     *
     * @param onvifMediaProfile the onvif media profile
     * @param ptzType           the ptz type
     * @param onvifPTZListener  the onvif ptz listener
     */
    public PTZRequest(OnvifMediaProfile onvifMediaProfile, PTZType ptzType, OnvifPTZListener onvifPTZListener) {
        super();
        this.onvifMediaProfile = onvifMediaProfile;
        this.xMove=ptzType.x;
        this.yMove=ptzType.y;
        this.zoomVal=ptzType.z;
        this.onvifPTZListener = onvifPTZListener;
    }

    /**
     * Gets onvif media profile.
     *
     * @return the onvif media profile
     */
    public OnvifMediaProfile getOnvifMediaProfile() {
        return onvifMediaProfile;
    }

    /**
     * Gets onvif ptz listener.
     *
     * @return the onvif ptz listener
     */
    public OnvifPTZListener getOnvifPTZListener() {
        return onvifPTZListener;
    }

    @Override
    public String getXml() {
        String ptz = String.format(Locale.getDefault(), REQUEST_PTZ, onvifMediaProfile.getToken(),Integer.toString(xMove),Integer.toString(yMove),Integer.toString(zoomVal));
        return ptz;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.PTZ;
    }
}
