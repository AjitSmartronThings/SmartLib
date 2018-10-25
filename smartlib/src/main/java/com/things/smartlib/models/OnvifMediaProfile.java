package com.things.smartlib.models;

/**
 * The type Onvif media profile.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifMediaProfile {

    /**
     * The constant TAG.
     */
    public static final String TAG = OnvifMediaProfile.class.getSimpleName();

    private final String name;
    private final String token;

    /**
     * Instantiates a new Onvif media profile.
     *
     * @param name  the name
     * @param token the token
     */
    public OnvifMediaProfile(String name, String token) {
        this.name = name;
        this.token = token;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "OnvifMediaProfile{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
