package com.things.smartlib.upnp;

import android.support.annotation.Nullable;

import com.things.smartlib.models.UPnPDevice;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.upnp
 * @date :         11/1/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class UPnPManager implements UPnPResponseListener {

    //Constants
    public static final String TAG = UPnPManager.class.getSimpleName();

    //Attributes
    private UPnPExecutor executor;
    private UPnPResponseListener responseListener;

    //Constructors
    public UPnPManager() {
        this(null);
    }

    private UPnPManager(@Nullable UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
        executor = new UPnPExecutor(this);
    }

    /**
     * Gets the device information for a given UPnP device.
     * The location attribute of the device should exist.
     * This could be filled in manually or from a discovery
     *
     * @param device
     */
    public void getDeviceInformation(UPnPDevice device, UPnPDeviceInformationListener listener) {
        executor.getDeviceInformation(device, listener);
    }

    public void setResponseListener(UPnPResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onResponse(UPnPDevice onvifDevice) {

    }

    @Override
    public void onError(UPnPDevice onvifDevice, int errorCode, String errorMessage) {

    }

}
