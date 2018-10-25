package com.things.smartlib;

import com.things.smartlib.listeners.OnvifDeviceInformationListener;
import com.things.smartlib.listeners.OnvifMediaProfileListener;
import com.things.smartlib.listeners.OnvifResponseListener;
import com.things.smartlib.listeners.OnvifServiceListener;
import com.things.smartlib.listeners.OnvifStreamUriListener;
import com.things.smartlib.models.OnvifDevice;
import com.things.smartlib.models.OnvifMediaProfile;
import com.things.smartlib.requests.GetDeviceInformationRequest;
import com.things.smartlib.requests.GetMediaProfilesRequest;
import com.things.smartlib.requests.GetMediaStreamRequest;
import com.things.smartlib.requests.GetServicesRequest;
import com.things.smartlib.requests.OnvifRequest;
import com.things.smartlib.responses.OnvifResponse;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifManager implements OnvifResponseListener{

    private static final String TAG = OnvifManager.class.getSimpleName();

    private OnvifExecutor onvifExecutor;
    private OnvifResponseListener onvifResponseListener;

    public OnvifManager() {
        this(null);
    }

    public OnvifManager(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
        onvifExecutor = new OnvifExecutor(this);
    }

    public void getServices(OnvifDevice device, OnvifServiceListener listener) {
        OnvifRequest request = new GetServicesRequest(listener);
        onvifExecutor.sendRequest(device, request);
    }

    public void getDeviceInformation(OnvifDevice device, OnvifDeviceInformationListener listener) {
        OnvifRequest request = new GetDeviceInformationRequest(listener);
        onvifExecutor.sendRequest(device, request);
    }

    public void getMediaProfiles(OnvifDevice device, OnvifMediaProfileListener listener) {
        OnvifRequest request;
        request = new GetMediaProfilesRequest(listener);
        onvifExecutor.sendRequest(device, request);
    }

    public void getMediaStreamURI(OnvifDevice device, OnvifMediaProfile profile, OnvifStreamUriListener listener) {
        OnvifRequest request = new GetMediaStreamRequest(profile, listener);
        onvifExecutor.sendRequest(device, request);
    }

    public void sendOnvifRequest(OnvifDevice device, OnvifRequest request) {
        onvifExecutor.sendRequest(device, request);
    }

    public void setOnvifResponseListener(OnvifResponseListener onvifResponseListener) {
        this.onvifResponseListener = onvifResponseListener;
    }

    /**
     * Clear up the resources.
     */
    public void destroy() {
        onvifResponseListener = null;
        onvifExecutor.clear();
    }

    @Override
    public void onResponse(OnvifDevice onvifDevice, OnvifResponse response) {
        if (onvifResponseListener != null)
            onvifResponseListener.onResponse(onvifDevice, response);
    }

    @Override
    public void onError(OnvifDevice onvifDevice, int errorCode, String errorMessage) {
        if (onvifResponseListener != null)
            onvifResponseListener.onError(onvifDevice, errorCode, errorMessage);
    }
}
