package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceCapabilities;
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
 * @date :         11/20/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceCapabilitiesParser extends OnvifParser<DeviceCapabilities>{
    private static final String TAG = DeviceCapabilitiesParser.class.getSimpleName();
    private String KEY_MEDIA = "Media";
    private String KEY_PTZ = "PTZ";
    private String KEY_EVENTS = "Events";
    private String KEY_ANALYTICS = "Analytics";
    private String KEY_IMAGING = "Imaging";
    private String KEY_ADDRESS = "XAddr";

    @Override
    public DeviceCapabilities parse(OnvifResponse response) {

        DeviceCapabilities deviceCapabilities=new DeviceCapabilities();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (getXmlPullParser().getName().equals(KEY_MEDIA)) {
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName() != null && getXmlPullParser().getName().equals(KEY_ADDRESS)) {
                                deviceCapabilities.setMediaUrl(getXmlPullParser().nextText());
                            }
                        } else if (getXmlPullParser().getName().equals(KEY_PTZ)) {
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName() != null && getXmlPullParser().getName().equals(KEY_ADDRESS)) {
                                deviceCapabilities.setPtzUrl(getXmlPullParser().nextText());
                            }
                        } else if (getXmlPullParser().getName().equals(KEY_EVENTS)) {
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName() != null && getXmlPullParser().getName().equals(KEY_ADDRESS)) {
                                deviceCapabilities.setEventUrl(getXmlPullParser().nextText());
                            }
                        } else if (getXmlPullParser().getName().equals(KEY_ANALYTICS)) {
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName() != null && getXmlPullParser().getName().equals(KEY_ADDRESS)) {
                                deviceCapabilities.setAnalyticsUrl(getXmlPullParser().nextText());
                            }
                        } else if (getXmlPullParser().getName().equals(KEY_IMAGING)) {
                            getXmlPullParser().nextTag();
                            if (getXmlPullParser().getName() != null && getXmlPullParser().getName().equals(KEY_ADDRESS)) {
                                deviceCapabilities.setImageUrl(getXmlPullParser().nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }

                eventType = getXmlPullParser().next();

            }
        }catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


        return deviceCapabilities;
    }
}
