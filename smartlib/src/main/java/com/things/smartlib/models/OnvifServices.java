package com.things.smartlib.models;

/**
 * The type Onvif services.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifServices {

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifServices.class.getSimpleName();//Tag For Class Name

    public static final String ONVIF_PATH_SERVICES = "/onvif/device_service";//Device Services URI
    public static final String ONVIF_PATH_DEVICE_INFORMATION = "/onvif/device_service";//Device Information URI
    public static final String ONVIF_PATH_PROFILES = "/onvif/device_service";//Device Profile URI
    public static final String ONVIF_PATH_STREAM_URI = "/onvif/device_service";//Device Media Stream URI

    //Attributes
    private String servicepath = ONVIF_PATH_SERVICES;
    private String deviceinfomationpath=ONVIF_PATH_DEVICE_INFORMATION;
    private String profilespath=ONVIF_PATH_PROFILES;
    private String streamURIpath=ONVIF_PATH_STREAM_URI;

    /**
     * Instantiates a new Onvif services.
     */
    public OnvifServices() { }
    /**
     * Gets servicepath.
     *
     * @return the servicepath
     */
    public String getServicepath() {
        return servicepath;
    }

    /**
     * Sets servicepath.
     *
     * @param servicepath the servicepath
     */
    public void setServicepath(String servicepath) {
        this.servicepath = servicepath;
    }

    /**
     * Gets deviceinfomationpath.
     *
     * @return the deviceinfomationpath
     */
    public String getDeviceinfomationpath() {
        return deviceinfomationpath;
    }

    /**
     * Sets deviceinfomationpath.
     *
     * @param deviceinfomationpath the deviceinfomationpath
     */
    public void setDeviceinfomationpath(String deviceinfomationpath) {
        this.deviceinfomationpath = deviceinfomationpath;
    }

    /**
     * Gets profilespath.
     *
     * @return the profilespath
     */
    public String getProfilespath() {
        return profilespath;
    }

    /**
     * Sets profilespath.
     *
     * @param profilespath the profilespath
     */
    public void setProfilespath(String profilespath) {
        this.profilespath = profilespath;
    }

    /**
     * Gets stream uripath.
     *
     * @return the stream uripath
     */
    public String getStreamURIpath() {
        return streamURIpath;
    }

    /**
     * Sets stream uripath.
     *
     * @param streamURIpath the stream uripath
     */
    public void setStreamURIpath(String streamURIpath) {
        this.streamURIpath = streamURIpath;
    }
}
