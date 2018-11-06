package com.things.smartlib.models;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceScopes {

    private static final String TAG = DeviceScopes.class.getSimpleName();

    private String scopedef;
    private String scopeitem;

    public DeviceScopes() {
    }

    public String getScopedef() {
        return scopedef;
    }

    public void setScopedef(String scopedef) {
        this.scopedef = scopedef;
    }

    public String getScopeitem() {
        return scopeitem;
    }

    public void setScopeitem(String scopeitem) {
        this.scopeitem = scopeitem;
    }

    @Override
    public String toString() {
        return "DeviceScopes{" +
                "scopedef='" + scopedef + '\'' +
                ", scopeitem='" + scopeitem + '\'' +
                '}';
    }
}
