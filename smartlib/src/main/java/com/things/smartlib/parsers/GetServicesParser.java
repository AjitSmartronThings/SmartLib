package com.things.smartlib.parsers;

import com.things.smartlib.OnvifUtils;
import com.things.smartlib.models.OnvifServices;
import com.things.smartlib.models.OnvifType;
import com.things.smartlib.responses.OnvifResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author :       Ajit Gaikwad
 * @version :      V1.0
 * @email :        ajitprakash.g@tthings.smartron.com
 * @project :      SmartCam
 * @package :      com.things.smartlib.parsers
 * @date :         10/25/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class GetServicesParser extends OnvifParser<OnvifServices> {

    @Override
    public OnvifServices parse(OnvifResponse response) {
        OnvifServices path = new OnvifServices();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals("Namespace")) {
                    getXmlPullParser().next();
                    String currentNamespace = getXmlPullParser().getText();

                    if (currentNamespace.equals(OnvifType.GET_DEVICE_INFORMATION.namespace)) {
                        String uri = OnvifUtils.retrieveXAddr(getXmlPullParser());
                        path.setDeviceinfomationpath(OnvifUtils.getPathFromURL(uri));
                    } else if (currentNamespace.equals(OnvifType.GET_MEDIA_PROFILES.namespace)
                            || currentNamespace.equals(OnvifType.GET_STREAM_URI.namespace)) {
                        String uri = OnvifUtils.retrieveXAddr(getXmlPullParser());
                        path.setProfilespath(OnvifUtils.getPathFromURL(uri));
                        path.setStreamURIpath(OnvifUtils.getPathFromURL(uri));
                    }
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return path;
    }

}

