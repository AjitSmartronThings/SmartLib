package com.things.smartlib.requests;

import com.things.smartlib.listeners.PTZConfigurationsListener;
import com.things.smartlib.models.OnvifType;

import static com.things.smartlib.TronXConstants.REQUEST_PTZ_CONFIGURATIONS;
import static com.things.smartlib.TronXConstants.REQUEST_PTZ_NODES;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         11/27/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetPTZNodes implements OnvifRequest {

    private static final String TAG = GetPTZNodes.class.getSimpleName();

    private PTZConfigurationsListener ptzConfigurationsListener;

    public GetPTZNodes(PTZConfigurationsListener ptzConfigurationsListener) {
        super();
        this.ptzConfigurationsListener = ptzConfigurationsListener;
    }

    public PTZConfigurationsListener getPtzConfigurationsListener() {
        return ptzConfigurationsListener;
    }

    @Override
    public String getXml() {
        return REQUEST_PTZ_NODES;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.PTZ_NODES;
    }
}
