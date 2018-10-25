package com.things.smartlib;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class OnvifUtils {

    /**
     * Util method to retrieve a path from an URL (without IP address and port)
     *
     * @param uri example input: `http://192.168.1.0:8791/cam/realmonitor?audio=1`
     * @example:
     * @result example output: `cam/realmonitor?audio=1`
     */
    public static String getPathFromURL(String uri) {
        URL url;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            return "";
        }

        String result = url.getPath();

        if (url.getQuery() != null) {
            result += url.getQuery();
        }

        return result;
    }

    /**
     * Util method for parsing. Retrieve the XAddr from the XmlPullParser given.
     */
    public static String retrieveXAddr(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT || (eventType == XmlPullParser.END_TAG && xpp.getName().equals("Service"))) {

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("XAddr")) {
                xpp.next();
                result = xpp.getText();
                break;
            }
            eventType = xpp.next();
        }

        return result;
    }

    /**
     * Util method for parsing.
     * Retrieve the XAddrs from the XmlPullParser given.
     */
    public static String retrieveXAddrs(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String result = "";
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("XAddrs")) {
                xpp.next();
                result = xpp.getText();
                break;
            }
            eventType = xpp.next();
        }

        return result;
    }

}
