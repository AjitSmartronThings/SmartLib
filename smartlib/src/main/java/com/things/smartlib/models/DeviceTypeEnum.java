package com.things.smartlib.models;

import android.support.annotation.StringDef;

import com.things.smartlib.TronXConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The enum Device type.
 *
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.models
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceTypeEnum {
    @Retention(RetentionPolicy.SOURCE)                    //declare retention policy source i.e complile time
    @StringDef({TronXConstants.ONVIF, TronXConstants.UPNP})
    public @interface DeviceTypes {}
    @DeviceTypes String type ;

    public String getType() {
        return type;
    }
}
