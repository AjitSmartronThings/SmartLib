package com.things.smartlib.requests;

import com.things.smartlib.listeners.DeviceScopesListener;
import com.things.smartlib.models.OnvifType;

import static com.things.smartlib.TronXConstants.DEVICE_SCOPES;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.requests
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetDeviceScopes implements OnvifRequest{

    private static final String TAG = GetDeviceScopes.class.getSimpleName();

    private DeviceScopesListener scopesListener;

    public GetDeviceScopes(DeviceScopesListener scopesListener) {
        super();
        this.scopesListener = scopesListener;
    }

    public DeviceScopesListener getScopesListener() {
        return scopesListener;
    }

    public void setScopesListener(DeviceScopesListener scopesListener) {
        this.scopesListener = scopesListener;
    }

    @Override
    public String getXml() {
        return DEVICE_SCOPES;
    }

    @Override
    public OnvifType getType() {
        return OnvifType.DEVICE_SCOPES;
    }
}
