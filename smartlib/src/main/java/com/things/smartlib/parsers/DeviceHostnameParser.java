package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceDNS;
import com.things.smartlib.models.DeviceHostname;
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
 * @date :         11/6/2018
 * @see <a href="https://smartron.com/things.html">TThings a Smartron Company</a>
 */
public class DeviceHostnameParser extends OnvifParser<DeviceHostname>{

    private static final String TAG = DeviceHostnameParser.class.getSimpleName();
    private static final String KEY_FROMDHCP = "FromDHCP";
    private static final String KEY_HOSTNAME = "Name";

    @Override
    public DeviceHostname parse(OnvifResponse response) {

        DeviceHostname deviceHostname = new DeviceHostname();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_FROMDHCP))
                {
                    getXmlPullParser().next();
                    deviceHostname.setFromdhcp(getXmlPullParser().getText());
                }
                else if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_HOSTNAME))
                {
                    getXmlPullParser().next();
                    deviceHostname.setHostname(getXmlPullParser().getText());
                }

                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceHostname;
    }
}
