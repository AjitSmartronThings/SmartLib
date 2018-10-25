package com.things.smartlib;

import android.nfc.Tag;
import android.widget.Toast;

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
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" >" +
                "<soap:Body>";
    }

    public static String getEnvelopeEnd() {
        return "</soap:Body></soap:Envelope>";
    }

    public static String getDiscoverySoapHeader() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" " +
                "xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                "<soap:Header>" +
                "<wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action>" +
                "<wsa:MessageID>urn:uuid:%s</wsa:MessageID>\n" +
                "<wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>\n" +
                "</soap:Header>" +
                "<soap:Body>";
    }

    public static String getDiscoverySoapBody() {
        return "<tns:Probe/>";
    }

}
