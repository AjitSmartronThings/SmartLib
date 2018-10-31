package com.things.smartlib;

import android.nfc.Tag;
import android.widget.Toast;

import static com.things.smartlib.TronXConstants.DISCOVERY_SOAP_BODY;
import static com.things.smartlib.TronXConstants.DISCOVERY_SOAP_HEADER;
import static com.things.smartlib.TronXConstants.ENVELOPE_END;
import static com.things.smartlib.TronXConstants.SOAP_HEADER;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/24/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifXMLBuilder {

    public static final String TAG = OnvifXMLBuilder.class.getSimpleName();

    public static String getSoapHeader() {
        return SOAP_HEADER;
    }

    public static String getEnvelopeEnd() {
        return ENVELOPE_END;
    }

    public static String getDiscoverySoapHeader() {
        return DISCOVERY_SOAP_HEADER;
    }

    public static String getDiscoverySoapBody() {
        return DISCOVERY_SOAP_BODY;
    }

}
