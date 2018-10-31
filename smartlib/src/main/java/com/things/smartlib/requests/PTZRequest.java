package com.things.smartlib.requests;

import com.things.smartlib.listeners.OnvifPTZListener;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.models.OnvifType;
import com.things.smartlib.models.PTZType;

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
        return "<ContinuousMove xmlns=\"http://www.onvif.org/ver20/ptz/wsdl\">"
                + "<ProfileToken>" + onvifMediaProfile.getToken() + "</ProfileToken>"
                + "<Velocity>"
                + "<PanTilt x=\""
                + Integer.toString(xMove)
                + "\" y=\""
                + Integer.toString(yMove)
                + "\" xmlns=\"http://www.onvif.org/ver10/schema\"/>"
                + "<Zoom x=\""
                + Integer.toString(zoomVal)
                + "\" xmlns=\"http://www.onvif.org/ver10/schema\"/>"
                + "</Velocity>"
                + "</ContinuousMove>";
    }

    @Override
    public OnvifType getType() {
        return OnvifType.PTZ;
    }
}
