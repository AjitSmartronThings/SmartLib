package com.things.smartlib.responses;

import com.things.smartlib.requests.OnvifRequest;

/**
 * The type Onvif response.
 *
 * @param <T> the type parameter
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.responses
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifResponse<T> {

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifResponse.class.getSimpleName();

    private boolean success;
    private int errorcode;
    private String errormessage;
    private String xml;

    private OnvifRequest onvifRequest;

    /**
     * Instantiates a new Onvif response.
     *
     * @param xml the xml
     */
    public OnvifResponse(String xml) {
        this.xml = xml;
    }

    /**
     * Instantiates a new Onvif response.
     *
     * @param onvifRequest the onvif request
     */
    public OnvifResponse(OnvifRequest onvifRequest) {
        this.onvifRequest = onvifRequest;
    }

    /**
     * Is success boolean.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets success.
     *
     * @param success the success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets errorcode.
     *
     * @return the errorcode
     */
    public int getErrorcode() {
        return errorcode;
    }

    /**
     * Sets errorcode.
     *
     * @param errorcode the errorcode
     */
    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    /**
     * Gets errormessage.
     *
     * @return the errormessage
     */
    public String getErrormessage() {
        return errormessage;
    }

    /**
     * Sets errormessage.
     *
     * @param errormessage the errormessage
     */
    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    /**
     * Gets xml.
     *
     * @return the xml
     */
    public String getXml() {
        return xml;
    }

    /**
     * Sets xml.
     *
     * @param xml the xml
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    /**
     * Gets onvif request.
     *
     * @return the onvif request
     */
    public OnvifRequest getOnvifRequest() {
        return onvifRequest;
    }

    /**
     * Sets onvif request.
     *
     * @param onvifRequest the onvif request
     */
    public void setOnvifRequest(OnvifRequest onvifRequest) {
        this.onvifRequest = onvifRequest;
    }
}
