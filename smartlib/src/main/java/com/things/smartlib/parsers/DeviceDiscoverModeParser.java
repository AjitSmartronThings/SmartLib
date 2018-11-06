package com.things.smartlib.parsers;

import com.things.smartlib.models.DeviceDiscoveryMode;
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
public class DeviceDiscoverModeParser extends OnvifParser<DeviceDiscoveryMode>{

    private static final String TAG = DeviceDiscoverModeParser.class.getSimpleName();
    private static final String KEY_DISCOVERYMODE = "DiscoveryMode";


    @Override
    public DeviceDiscoveryMode parse(OnvifResponse response) {

        DeviceDiscoveryMode deviceDiscoveryMode = new DeviceDiscoveryMode();

        try {
            getXmlPullParser().setInput(new StringReader(response.getXml()));
            eventType = getXmlPullParser().getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG && getXmlPullParser().getName().equals(KEY_DISCOVERYMODE))
                {
                    getXmlPullParser().next();
                    deviceDiscoveryMode.setDiscoverymode(getXmlPullParser().getText());
                }
                eventType = getXmlPullParser().next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceDiscoveryMode;
    }
}
